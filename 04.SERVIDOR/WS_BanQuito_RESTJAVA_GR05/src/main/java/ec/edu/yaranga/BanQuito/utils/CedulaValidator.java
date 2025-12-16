package ec.edu.yaranga.BanQuito.utils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CedulaValidator {
    public static boolean esCedulaValida(String cedula) {
        log.info("Validando Cedula DTO: " + cedula);
        if (cedula == null || cedula.length() != 10 || !cedula.matches("\\d{10}")) {
            return false;
        }

        int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2};
        int suma = 0;
        for (int i = 0; i < 9; i++) {
            int digito = Character.getNumericValue(cedula.charAt(i)) * coeficientes[i];
            suma += digito > 9 ? digito - 9 : digito;
        }

        int verificador = Integer.parseInt(cedula.substring(9));
        int calculado = (10 - (suma % 10)) % 10;
        return calculado == verificador;
    }
}
