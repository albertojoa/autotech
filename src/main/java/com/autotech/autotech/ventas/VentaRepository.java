package com.autotech.autotech.ventas;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    long countByFecha(LocalDate fecha);

    @Query("SELECT v FROM Venta v "
        + "WHERE (:clienteNombre IS NULL OR LOWER(v.cliente.nombre) LIKE LOWER(CONCAT('%', :clienteNombre, '%'))) "
        + "AND (:fechaInicio IS NULL OR v.fecha >= :fechaInicio) "
        + "AND (:fechaFin IS NULL OR v.fecha <= :fechaFin) "
        + "AND (:itemTexto IS NULL OR LOWER(v.descripcionItems) LIKE LOWER(CONCAT('%', :itemTexto, '%')))")
    List<Venta> buscarConFiltros(
        @Param("fechaInicio") LocalDate fechaInicio,
        @Param("fechaFin") LocalDate fechaFin,
        @Param("clienteNombre") String clienteNombre,
        @Param("itemTexto") String itemTexto);
}
