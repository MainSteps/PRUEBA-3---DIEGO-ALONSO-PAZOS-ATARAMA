package com.arriendos.ms_pagos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "Datos requeridos para crear o actualizar un pago")
public class PagoRequestDTO {

    @Schema(description = "Identificador de la reserva asociada al pago", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El reservaId es obligatorio")
    private Integer reservaId;

    @Schema(description = "Codigo unico del pago", example = "PAG-001", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El codigo de pago es obligatorio")
    private String codigoPago;

    @Schema(description = "Monto del pago", example = "250000.0", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.0", message = "El monto debe ser mayor o igual a 0")
    private Double monto;

    @Schema(description = "Metodo utilizado para realizar el pago", example = "Tarjeta", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El metodo de pago es obligatorio")
    private String metodoPago;

    @Schema(description = "Indica si el pago fue realizado", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El estado pagado es obligatorio")
    private Boolean pagado;

    @Schema(description = "Fecha en que se realizo el pago", example = "2026-06-20", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "La fecha de pago es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser futura")
    private LocalDate fechaPago;

    @Schema(description = "Cantidad de cuotas del pago", example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El numero de cuotas es obligatorio")
    @Positive(message = "El numero de cuotas debe ser positivo")
    private Integer numeroCuotas;
}
