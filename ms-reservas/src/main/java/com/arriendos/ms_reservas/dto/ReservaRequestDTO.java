package com.arriendos.ms_reservas.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservaRequestDTO {

    @NotNull(message = "El clienteId es obligatorio")
    private Integer clienteId;

    @NotNull(message = "El vehiculoId es obligatorio")
    private Integer vehiculoId;

    @NotBlank(message = "El codigo de reserva es obligatorio")
    private String codigoReserva;

    @NotNull(message = "Los dias de arriendo son obligatorios")
    @Positive(message = "Los dias deben ser positivos")
    private Integer diasArriendo;

    @NotNull(message = "El monto total es obligatorio")
    @DecimalMin(value = "0.0", message = "El monto debe ser mayor o igual a 0")
    private Double montoTotal;

    @NotNull(message = "La confirmacion es obligatoria")
    private Boolean confirmada;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @FutureOrPresent(message = "La fecha no puede ser pasada")
    private LocalDate fechaInicio;

    @NotNull(message = "El estadoReservaId es obligatorio")
    private Integer estadoReservaId;
    
}