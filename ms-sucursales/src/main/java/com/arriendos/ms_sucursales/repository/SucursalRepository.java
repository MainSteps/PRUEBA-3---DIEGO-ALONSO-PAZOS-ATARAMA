package com.arriendos.ms_sucursales.repository;

import com.arriendos.ms_sucursales.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SucursalRepository extends JpaRepository<Sucursal, Integer> {
    @Query(value = "SELECT * FROM sucursal WHERE operativa = true ORDER BY nombre ASC", nativeQuery = true)
    List<Sucursal> buscarSucursalesOperativas();
}