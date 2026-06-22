package com.arriendos.ms_reservas.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Data
@Schema(description = "Datos requeridos para crear o actualizar una reserva")
public class ReservaRequestDTO {

    @Schema(description = "Identificador del cliente asociado a la reserva", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El clienteId es obligatorio")
    private Integer clienteId;

    @Schema(description = "Identificador del vehiculo asociado a la reserva", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El vehiculoId es obligatorio")
    private Integer vehiculoId;

    @Schema(description = "Codigo unico de la reserva", example = "RES-001", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El codigo de reserva es obligatorio")
    private String codigoReserva;

    @Schema(description = "Cantidad de dias de arriendo", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Los dias de arriendo son obligatorios")
    @Positive(message = "Los dias deben ser positivos")
    private Integer diasArriendo;

    @Schema(description = "Monto total de la reserva", example = "250000.0", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El monto total es obligatorio")
    @DecimalMin(value = "0.0", message = "El monto debe ser mayor o igual a 0")
    private Double montoTotal;

    @Schema(description = "Indica si la reserva esta confirmada", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "La confirmacion es obligatoria")
    private Boolean confirmada;

    @Schema(description = "Fecha de inicio de la reserva", example = "2026-06-20", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "La fecha de inicio es obligatoria")
    @FutureOrPresent(message = "La fecha no puede ser pasada")
    private LocalDate fechaInicio;

    @Schema(description = "Identificador del estado de la reserva", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El estadoReservaId es obligatorio")
    private Integer estadoReservaId;
    
}
