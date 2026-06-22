package com.arriendos.ms_pagos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "Respuesta con los datos de un pago")
public class PagoResponseDTO extends RepresentationModel<PagoResponseDTO> {
    @Schema(description = "Identificador del pago", example = "1")
    private Integer id;
    @Schema(description = "Identificador de la reserva asociada", example = "1")
    private Integer reservaId;
    @Schema(description = "Codigo unico del pago", example = "PAG-001")
    private String codigoPago;
    @Schema(description = "Monto del pago", example = "250000.0")
    private Double monto;
    @Schema(description = "Metodo utilizado para realizar el pago", example = "Tarjeta")
    private String metodoPago;
    @Schema(description = "Indica si el pago fue realizado", example = "true")
    private Boolean pagado;
    @Schema(description = "Fecha en que se realizo el pago", example = "2026-06-20")
    private LocalDate fechaPago;
    @Schema(description = "Cantidad de cuotas del pago", example = "3")
    private Integer numeroCuotas;

    
}
