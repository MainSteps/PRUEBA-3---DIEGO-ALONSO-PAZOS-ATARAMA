package com.arriendos.ms_reservas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor

public class ReservaResponseDTO {

    private Integer id;
    private Integer clienteId;
    private Integer vehiculoId;
    private String codigoReserva;
    private Integer diasArriendo;
    private Double montoTotal;
    private Boolean confirmada;
    private LocalDate fechaInicio;
    private Integer estadoReservaId;
}