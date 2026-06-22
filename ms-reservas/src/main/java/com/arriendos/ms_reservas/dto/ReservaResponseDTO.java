package com.arriendos.ms_reservas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "Respuesta con los datos de una reserva")
public class ReservaResponseDTO extends RepresentationModel<ReservaResponseDTO> {

    @Schema(description = "Identificador de la reserva", example = "1")
    private Integer id;
    @Schema(description = "Identificador del cliente asociado", example = "1")
    private Integer clienteId;
    @Schema(description = "Identificador del vehiculo asociado", example = "2")
    private Integer vehiculoId;
    @Schema(description = "Codigo unico de la reserva", example = "RES-001")
    private String codigoReserva;
    @Schema(description = "Cantidad de dias de arriendo", example = "5")
    private Integer diasArriendo;
    @Schema(description = "Monto total de la reserva", example = "250000.0")
    private Double montoTotal;
    @Schema(description = "Indica si la reserva esta confirmada", example = "true")
    private Boolean confirmada;
    @Schema(description = "Fecha de inicio de la reserva", example = "2026-06-20")
    private LocalDate fechaInicio;
    @Schema(description = "Identificador del estado de la reserva", example = "1")
    private Integer estadoReservaId;
}
