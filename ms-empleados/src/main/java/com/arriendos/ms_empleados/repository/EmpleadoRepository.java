package com.arriendos.ms_empleados.repository;

import com.arriendos.ms_empleados.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    @Query(value = "SELECT * FROM empleado WHERE activo = true AND YEAR(fecha_contratacion) = :anio", nativeQuery = true)
    List<Empleado> buscarActivosPorAnioContratacion(Integer anio);
}