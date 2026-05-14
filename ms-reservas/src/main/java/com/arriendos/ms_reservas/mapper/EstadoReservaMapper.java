package com.arriendos.ms_reservas.mapper;

import com.arriendos.ms_reservas.dto.EstadoReservaRequestDTO;
import com.arriendos.ms_reservas.dto.EstadoReservaResponseDTO;
import com.arriendos.ms_reservas.model.EstadoReserva;
import org.springframework.stereotype.Component;

@Component
public class EstadoReservaMapper {

    public EstadoReserva toEntity(EstadoReservaRequestDTO dto) {
        EstadoReserva estadoReserva = new EstadoReserva();
        estadoReserva.setNombre(dto.getNombre());
        estadoReserva.setDescripcion(dto.getDescripcion());
        estadoReserva.setPrioridad(dto.getPrioridad());
        estadoReserva.setActivo(dto.getActivo());
        estadoReserva.setFechaCreacion(dto.getFechaCreacion());

        return estadoReserva;
    }

    public EstadoReservaResponseDTO toDTO(EstadoReserva estadoReserva) {
        return new EstadoReservaResponseDTO(
                estadoReserva.getId(),
                estadoReserva.getNombre(),
                estadoReserva.getDescripcion(),
                estadoReserva.getPrioridad(),
                estadoReserva.getActivo(),
                estadoReserva.getFechaCreacion()
        );
    }
}