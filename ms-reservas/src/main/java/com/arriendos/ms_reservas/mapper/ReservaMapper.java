package com.arriendos.ms_reservas.mapper;

import com.arriendos.ms_reservas.dto.ReservaRequestDTO;
import com.arriendos.ms_reservas.dto.ReservaResponseDTO;
import com.arriendos.ms_reservas.model.EstadoReserva;
import com.arriendos.ms_reservas.model.Reserva;
import org.springframework.stereotype.Component;

@Component
public class ReservaMapper {

    public Reserva toEntity(ReservaRequestDTO dto, EstadoReserva estadoReserva) {

        Reserva reserva = new Reserva();
        reserva.setClienteId(dto.getClienteId());
        reserva.setVehiculoId(dto.getVehiculoId());
        reserva.setCodigoReserva(dto.getCodigoReserva());
        reserva.setDiasArriendo(dto.getDiasArriendo());
        reserva.setMontoTotal(dto.getMontoTotal());
        reserva.setConfirmada(dto.getConfirmada());
        reserva.setFechaInicio(dto.getFechaInicio());
        reserva.setEstadoReserva(estadoReserva);

        return reserva;
    }

    public ReservaResponseDTO toDTO(Reserva reserva) {
        return new ReservaResponseDTO(
                reserva.getId(),
                reserva.getClienteId(),
                reserva.getVehiculoId(),
                reserva.getCodigoReserva(),
                reserva.getDiasArriendo(),
                reserva.getMontoTotal(),
                reserva.getConfirmada(),
                reserva.getFechaInicio(),
                reserva.getEstadoReserva().getId()
        );
    }
}