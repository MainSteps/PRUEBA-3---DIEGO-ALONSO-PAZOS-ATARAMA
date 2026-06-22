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
@Schema(description = "Respuesta con los datos de un estado de reserva")
public class EstadoReservaResponseDTO extends RepresentationModel<EstadoReservaResponseDTO> {

    @Schema(description = "Identificador del estado de reserva", example = "1")
    private Integer id;
    @Schema(description = "Nombre del estado de reserva", example = "Confirmada")
    private String nombre;
    @Schema(description = "Descripcion del estado de reserva", example = "Reserva confirmada por el cliente")
    private String descripcion;
    @Schema(description = "Prioridad del estado de reserva", example = "1")
    private Integer prioridad;
    @Schema(description = "Indica si el estado se encuentra activo", example = "true")
    private Boolean activo;
    
    @Schema(description = "Fecha de creacion del estado", example = "2026-06-20")
    private LocalDate fechaCreacion;

}
