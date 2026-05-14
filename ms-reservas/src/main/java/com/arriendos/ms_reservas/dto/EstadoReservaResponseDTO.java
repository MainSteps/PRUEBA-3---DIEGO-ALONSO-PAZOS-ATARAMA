package com.arriendos.ms_reservas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class EstadoReservaResponseDTO {

    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer prioridad;
    private Boolean activo;
    
    private LocalDate fechaCreacion;

}