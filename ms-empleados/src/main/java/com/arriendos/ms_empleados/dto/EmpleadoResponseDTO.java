package com.arriendos.ms_empleados.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor


public class EmpleadoResponseDTO {
    private Integer id;
    private String nombre;
    private String cargo;
    private String email;
    private Double sueldo;
    private Boolean activo;
    private LocalDate fechaContratacion;
    private Integer aniosExperiencia;
}