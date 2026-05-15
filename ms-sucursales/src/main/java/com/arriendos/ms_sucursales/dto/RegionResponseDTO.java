package com.arriendos.ms_sucursales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor

public class RegionResponseDTO {
    private Integer id;
    private String nombre;
    private String codigo;

    private Integer cantidadSucursales;
    private Boolean activa;
    private LocalDate fechaCreacion;
}