package com.autotech.autotech.productos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    long countByStockLessThanEqual(Integer stockMinimo);

    List<Producto> findByStockLessThanEqual(Integer stockMinimo);
}