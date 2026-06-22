package com.arriendos.ms_reservas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "Datos requeridos para crear o actualizar un estado de reserva")
public class EstadoReservaRequestDTO {

    @Schema(description = "Nombre del estado de reserva", example = "Confirmada", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @Schema(description = "Descripcion del estado de reserva", example = "Reserva confirmada por el cliente", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "La descripcion es obligatoria")
    @Size(min = 5, max = 200, message = "La descripcion debe tener entre 5 y 200 caracteres")
    private String descripcion;

    @Schema(description = "Prioridad del estado de reserva", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "La prioridad es obligatoria")
    @Positive(message = "La prioridad debe ser positiva")
    private Integer prioridad;

    @Schema(description = "Indica si el estado se encuentra activo", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activo;

    @Schema(description = "Fecha de creacion del estado", example = "2026-06-20", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "La fecha de creacion es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser futura")
    private LocalDate fechaCreacion;
}
