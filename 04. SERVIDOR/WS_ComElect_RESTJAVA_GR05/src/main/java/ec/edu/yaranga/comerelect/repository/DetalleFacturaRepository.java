package ec.edu.yaranga.comerelect.repository;

import ec.edu.yaranga.comerelect.model.DetalleFactura;
import ec.edu.yaranga.comerelect.model.DetalleFacturaId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleFacturaRepository extends JpaRepository<DetalleFactura, DetalleFacturaId> {}