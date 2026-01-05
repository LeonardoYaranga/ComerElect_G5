package ec.edu.yaranga.BanQuito.service;

import ec.edu.yaranga.BanQuito.dto.AmortizacionDTO;
import ec.edu.yaranga.BanQuito.dto.CreditoDTO;
import ec.edu.yaranga.BanQuito.model.*;
import ec.edu.yaranga.BanQuito.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreditoService {

    private final ClienteRepository clienteRepo;
    private final CuentaRepository cuentaRepo;
    private final MovimientoRepository movimientoRepo;
    private final CreditoRepository creditoRepo;
    private final AmortizacionRepository amortizacionRepo;

    private static final BigDecimal TASA_ANUAL = BigDecimal.valueOf(0.16);
    private static final BigDecimal TASA_MENSUAL = TASA_ANUAL.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);

    // ÍTEM 5: Validar si es sujeto de crédito
    public boolean esSujetoDeCredito(String cedula) {
        log.info(">>>> Iniciando verificación de crédito para cédula: {}", cedula);

        // 1. Es cliente del banco
        Cliente cliente = clienteRepo.findById(cedula).orElse(null);

        if (cliente == null) {
            log.info("RECHAZO: El cliente con cédula {} NO existe en el banco.", cedula);
            return false;
        }
        log.info("El cliente {} fue encontrado.", cliente.getNombre());

        // 2. Tiene al menos un depósito en el último mes
        LocalDate hoy = LocalDate.now();
        LocalDate inicioMes = hoy.minusMonths(1).withDayOfMonth(1);

        // NOTA: Se recomienda extraer este cálculo del stream para mejor debug.
        boolean tieneDeposito = cuentaRepo.findByClienteCedula(cedula).stream()
                .flatMap(c -> movimientoRepo.findByCuentaNumCuentaAndFechaBetweenOrderByFechaDesc(
                        c.getNumCuenta(), inicioMes, hoy).stream())
                .anyMatch(m -> "DEP".equals(m.getTipo()));

        if (!tieneDeposito) {
            log.info("RECHAZO: Cliente {} no tiene depósitos en el último mes ({}).", cliente.getNombre(), inicioMes);
            return false;
        }
        log.info("APROBADO: Cliente {} tiene depósitos recientes.", cliente.getNombre());


        // 3. Si casado, mayor a 25 años
        if ("C".equals(cliente.getEstadoCivil())) {
            int edad = hoy.getYear() - cliente.getFechaNacimiento().getYear();
            if (hoy.getDayOfYear() < cliente.getFechaNacimiento().getDayOfYear()) edad--;

            log.info("Cliente casado, edad: {} años.", edad); // Muestra la edad calculada

            if (edad < 25) {
                log.info("RECHAZO: Cliente casado {} tiene {} años (requiere >= 25).", cliente.getNombre(), edad);
                return false;
            }
        }

        // 4. No tiene crédito activo
        boolean tieneCreditoActivo = creditoRepo.existsByClienteCedulaAndEstado(cedula, "A");

        if (tieneCreditoActivo) {
            log.info("RECHAZO: Cliente {} ya tiene un crédito ACTIVO ('A').", cliente.getNombre());
            return false;
        }

        log.info(">>>> APROBADO: El cliente {} es sujeto de crédito.", cliente.getNombre());
        return true;
    }

    // ÍTEM 6: Monto máximo de crédito
    public BigDecimal calcularMontoMaximo(String cedula) {
        if (!clienteRepo.existsById(cedula)) return BigDecimal.ZERO;

        LocalDate hoy = LocalDate.now();
        LocalDate inicio = hoy.minusMonths(3).withDayOfMonth(1);

        List<Movimiento> movimientos = cuentaRepo.findByClienteCedula(cedula).stream()
                .flatMap(c -> movimientoRepo.findByCuentaNumCuentaAndFechaBetweenOrderByFechaDesc(
                        c.getNumCuenta(), inicio, hoy).stream())
                .toList();

        BigDecimal sumaDep = BigDecimal.ZERO;
        BigDecimal sumaRet = BigDecimal.ZERO;
        int mesesDep = 0, mesesRet = 0;

        for (Movimiento m : movimientos) {
            if ("DEP".equals(m.getTipo())) {
                sumaDep = sumaDep.add(m.getValor());
                mesesDep++;
            } else if ("RET".equals(m.getTipo())) {
                sumaRet = sumaRet.add(m.getValor());
                mesesRet++;
            }
        }

        BigDecimal promDep = mesesDep > 0 ? sumaDep.divide(BigDecimal.valueOf(mesesDep), 2, RoundingMode.HALF_UP) : BigDecimal.ZERO;
        BigDecimal promRet = mesesRet > 0 ? sumaRet.divide(BigDecimal.valueOf(mesesRet), 2, RoundingMode.HALF_UP) : BigDecimal.ZERO;

        BigDecimal diferencia = promDep.subtract(promRet);
        if (diferencia.compareTo(BigDecimal.ZERO) <= 0) return BigDecimal.ZERO;

        return diferencia.multiply(BigDecimal.valueOf(0.6)).multiply(BigDecimal.valueOf(9))
                .setScale(2, RoundingMode.HALF_UP);
    }

    // ÍTEM 7: Otorgar crédito + amortización
    public CreditoDTO otorgarCredito(String cedula, BigDecimal precioElectrodomestico, Integer numeroCuotas) {
        if (!esSujetoDeCredito(cedula)) {
            throw new RuntimeException("Cliente no es sujeto de crédito");
        }

        BigDecimal montoMax = calcularMontoMaximo(cedula);
        if (precioElectrodomestico.compareTo(montoMax) > 0) {
            throw new RuntimeException("Precio del electrodomestico excede monto máximo aprobado: " + montoMax);
        }
        log.info("El monto maximo es "+ montoMax);

        if (numeroCuotas < 3 || numeroCuotas > 24) {
            throw new RuntimeException("Plazo debe ser entre 3 y 24 meses");
        }

        Cliente cliente = clienteRepo.findById(cedula).orElseThrow();

        Credito credito = new Credito();
        credito.setCliente(cliente);
        credito.setMonto(precioElectrodomestico);
        credito.setPlazoMeses(numeroCuotas);
        credito.setTasaAnual(TASA_ANUAL.multiply(BigDecimal.valueOf(100))); // 16.00
        credito.setEstado("A");
        credito = creditoRepo.save(credito);
        log.info("Credito creado en BD" );
        log.info("Se va a generar tabla de amotizacion");
        generarTablaAmortizacion(credito);
        log.info("Se genero tabla de amortizacion");
        return toCreditoDTO(credito);
    }

    private CreditoDTO toCreditoDTO(Credito credito) {
        CreditoDTO dto = new CreditoDTO();
        dto.setIdCredito(credito.getIdCredito());
        dto.setCedula(credito.getCliente().getCedula());
        dto.setNombreCliente(credito.getCliente().getNombre());
        dto.setMonto(credito.getMonto());
        dto.setPlazoMeses(credito.getPlazoMeses());
        dto.setValorCuotaFija(credito.getValorCuotaFija());
        dto.setTasaAnual(credito.getTasaAnual());
        dto.setFechaOtorgamiento(credito.getFechaOtorgamiento());
        dto.setEstado(credito.getEstado());

        List<AmortizacionDTO> amortizacionDTOs = credito.getAmortizaciones().stream()
                .map(this::toAmortizacionDTO)
                .toList();
        dto.setAmortizaciones(amortizacionDTOs);

        return dto;
    }

    private AmortizacionDTO toAmortizacionDTO(Amortizacion a) {
        AmortizacionDTO dto = new AmortizacionDTO();
        dto.setNumCuota(a.getNumCuota());
        dto.setValorCuota(a.getValorCuota());
        dto.setInteres(a.getInteres());
        dto.setCapital(a.getCapital());
        dto.setSaldo(a.getSaldo());
        return dto;
    }

    private void generarTablaAmortizacion(Credito credito) {
        BigDecimal monto = credito.getMonto();
        int n = credito.getPlazoMeses();
        BigDecimal r = BigDecimal.valueOf(16).divide(BigDecimal.valueOf(1200), 10, RoundingMode.HALF_UP); // 16%/12

        // 1. CALCULAR CUOTA FIJA (UNA SOLA VEZ)
        BigDecimal cuotaFija = calcularCuotaFija(monto, r, n);
        credito.setValorCuotaFija(cuotaFija);
        creditoRepo.save(credito);

        log.info("CUOTA FIJA CALCULADA: {}", cuotaFija);

        BigDecimal saldo = monto;
        List<Amortizacion> tabla = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            // Interés = saldo * tasa mensual
            BigDecimal interes = saldo.multiply(r).setScale(2, RoundingMode.HALF_UP);
            log.info("Cuota {} | Saldo anterior: {} | Interés: {}", i, saldo, interes);

            // Capital = cuota fija - interés
            BigDecimal capital = cuotaFija.subtract(interes);
            log.info("Cuota {} | Capital: {}", i, capital);

            // Saldo = saldo anterior - capital
            saldo = saldo.subtract(capital);
            if (saldo.compareTo(BigDecimal.ZERO) < 0) {
                saldo = BigDecimal.ZERO;
            }
            log.info("Cuota {} | Saldo nuevo: {}", i, saldo);

            Amortizacion a = new Amortizacion();
            a.setCredito(credito);
            a.setNumCuota(i);
            a.setValorCuota(cuotaFija);  // ← SIEMPRE LA MISMA
            a.setInteres(interes);
            a.setCapital(capital);
            a.setSaldo(saldo);
            tabla.add(a);
        }

        // AJUSTE FINAL: si hay redondeo, corregir última cuota
        BigDecimal ultimoSaldo = tabla.get(n - 1).getSaldo();
        if (ultimoSaldo.compareTo(BigDecimal.ZERO) > 0) {
            Amortizacion ultima = tabla.get(n - 1);
            BigDecimal ajuste = ultimoSaldo;
            ultima.setCapital(ultima.getCapital().add(ajuste));
            ultima.setValorCuota(ultima.getInteres().add(ultima.getCapital()));
            ultima.setSaldo(BigDecimal.ZERO);
            log.info("AJUSTE FINAL: +{} en capital, cuota = {}", ajuste, ultima.getValorCuota());
        }

        credito.getAmortizaciones().clear();
        credito.getAmortizaciones().addAll(tabla);
        amortizacionRepo.saveAll(tabla);
    }

    private BigDecimal calcularCuotaFija(BigDecimal monto, BigDecimal r, int n) {
        if (r.compareTo(BigDecimal.ZERO) == 0) {
            return monto.divide(BigDecimal.valueOf(n), 2, RoundingMode.HALF_UP);
        }

        BigDecimal unoMasR = BigDecimal.ONE.add(r);
        BigDecimal potencia = unoMasR.pow(n);
        BigDecimal numerador = r.multiply(potencia);
        BigDecimal denominador = potencia.subtract(BigDecimal.ONE);

        return monto.multiply(numerador.divide(denominador, 10, RoundingMode.HALF_UP))
                .setScale(2, RoundingMode.HALF_UP);
    }

    // Para ítem 10: consultar tabla
    public List<AmortizacionDTO> obtenerTablaAmortizacion(Integer idCredito) {

        // 1. Obtener la lista de entidades (Modelos)
        List<Amortizacion> listAmortizaciones =
                amortizacionRepo.findByCreditoIdCreditoOrderByNumCuota(idCredito);

        // 2. Usar Java Stream para mapear cada entidad a un DTO
        return listAmortizaciones.stream()
                .map(this::toAmortizacionDTO) // Llama al método auxiliar por cada elemento
                .collect(Collectors.toList()); // Recolecta los resultados en una nueva lista
    }
}