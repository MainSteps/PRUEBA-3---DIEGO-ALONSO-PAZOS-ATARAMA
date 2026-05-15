package com.arriendos.ms_sucursales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class SucursalResponseDTO {
    private Integer id;
    private String nombre;
    private String direccion;
    private String telefono;
    private Integer capacidadVehiculos;
    private Boolean operativa;
    private LocalDate fechaApertura;
    private Integer regionId;
    
}