package com.autotech.autotech.compras;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompraRepository extends JpaRepository<Compra, Long> {

    long countByFecha(LocalDate fecha);

    @Query("SELECT c FROM Compra c "
        + "WHERE (:proveedorNombre IS NULL OR LOWER(c.proveedor.nombre) LIKE LOWER(CONCAT('%', :proveedorNombre, '%'))) "
        + "AND (:fechaInicio IS NULL OR c.fecha >= :fechaInicio) "
        + "AND (:fechaFin IS NULL OR c.fecha <= :fechaFin) "
        + "AND (:itemTexto IS NULL OR LOWER(c.descripcionItems) LIKE LOWER(CONCAT('%', :itemTexto, '%')))")
    List<Compra> buscarConFiltros(
        @Param("fechaInicio") LocalDate fechaInicio,
        @Param("fechaFin") LocalDate fechaFin,
        @Param("proveedorNombre") String proveedorNombre,
        @Param("itemTexto") String itemTexto);
}
