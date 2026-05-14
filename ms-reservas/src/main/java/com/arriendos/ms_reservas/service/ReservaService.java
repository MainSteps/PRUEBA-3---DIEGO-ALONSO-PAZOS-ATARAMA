package com.arriendos.ms_reservas.service;

import com.arriendos.ms_reservas.client.ClienteClient;
import com.arriendos.ms_reservas.client.VehiculoClient;
import com.arriendos.ms_reservas.dto.ReservaRequestDTO;
import com.arriendos.ms_reservas.dto.ReservaResponseDTO;
import com.arriendos.ms_reservas.exception.ResourceNotFoundException;
import com.arriendos.ms_reservas.mapper.ReservaMapper;
import com.arriendos.ms_reservas.model.EstadoReserva;
import com.arriendos.ms_reservas.model.Reserva;
import com.arriendos.ms_reservas.repository.EstadoReservaRepository;
import com.arriendos.ms_reservas.repository.ReservaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    private static final Logger log = LoggerFactory.getLogger(ReservaService.class);

    private final ReservaRepository reservaRepository;
    private final EstadoReservaRepository estadoReservaRepository;
    private final ReservaMapper reservaMapper;
    private final ClienteClient clienteClient;
    private final VehiculoClient vehiculoClient;

    public ReservaService(ReservaRepository reservaRepository, EstadoReservaRepository estadoReservaRepository, ReservaMapper reservaMapper, ClienteClient clienteClient, VehiculoClient vehiculoClient) {
        this.reservaRepository = reservaRepository;
        this.estadoReservaRepository = estadoReservaRepository;
        this.reservaMapper = reservaMapper;
        this.clienteClient = clienteClient;
        this.vehiculoClient = vehiculoClient;
    }






    public List<ReservaResponseDTO> obtenerTodas() {
        log.info("Listando todas las reservas");

        return reservaRepository.findAll()
                .stream()
                .map(reservaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ReservaResponseDTO obtenerPorId(Integer id) {
        log.info("Buscando reserva por id {}", id);

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id " + id));

        return reservaMapper.toDTO(reserva);
    }

    public ReservaResponseDTO guardar(ReservaRequestDTO dto) {
        try {
            log.info("Guardando reserva para cliente {} y vehiculo {}", dto.getClienteId(), dto.getVehiculoId());

            try {
                clienteClient.obtenerClientePorId(dto.getClienteId());
            } catch (Exception e) {
                throw new ResourceNotFoundException("Cliente no encontrado con id " + dto.getClienteId());
            }

            try {
                vehiculoClient.obtenerVehiculoPorId(dto.getVehiculoId());
            } catch (Exception e) {
                throw new ResourceNotFoundException("Vehiculo no encontrado con id " + dto.getVehiculoId());
            }

            EstadoReserva estadoReserva = estadoReservaRepository.findById(dto.getEstadoReservaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Estado reserva no encontrado con id " + dto.getEstadoReservaId()));

            Reserva reserva = reservaMapper.toEntity(dto, estadoReserva);
            Reserva guardada = reservaRepository.save(reserva);

            return reservaMapper.toDTO(guardada);

        } catch (Exception e) {
            log.error("Error al guardar reserva: {}", e.getMessage());
            throw e;
        }
    }

    public ReservaResponseDTO actualizar(Integer id, ReservaRequestDTO dto) {
        log.info("Actualizando reserva con id {}", id);

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id " + id));

        try {
            clienteClient.obtenerClientePorId(dto.getClienteId());
        } catch (Exception e) {
            throw new ResourceNotFoundException("Cliente no encontrado con id " + dto.getClienteId());
        }

        try {
            vehiculoClient.obtenerVehiculoPorId(dto.getVehiculoId());
        } catch (Exception e) {
            throw new ResourceNotFoundException("Vehiculo no encontrado con id " + dto.getVehiculoId());
        }

        EstadoReserva estadoReserva = estadoReservaRepository.findById(dto.getEstadoReservaId())
                .orElseThrow(() -> new ResourceNotFoundException("Estado reserva no encontrado con id " + dto.getEstadoReservaId()));
        reserva.setClienteId(dto.getClienteId());
        reserva.setVehiculoId(dto.getVehiculoId());
        reserva.setCodigoReserva(dto.getCodigoReserva());
        reserva.setDiasArriendo(dto.getDiasArriendo());
        reserva.setMontoTotal(dto.getMontoTotal());
        reserva.setConfirmada(dto.getConfirmada());
        reserva.setFechaInicio(dto.getFechaInicio());
        reserva.setEstadoReserva(estadoReserva);
        Reserva actualizada = reservaRepository.save(reserva);

        return reservaMapper.toDTO(actualizada);
    }

    public void eliminar(Integer id) {
        log.info("Eliminando reserva con id {}", id);

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id " + id));
        reservaRepository.delete(reserva);
    }

    public List<ReservaResponseDTO> buscarDesdeFecha(LocalDate fecha) {
        log.info("Buscando reservas desde fecha {}", fecha);

        return reservaRepository.buscarReservasDesdeFecha(fecha)
                .stream()
                .map(reservaMapper::toDTO)
                .collect(Collectors.toList());
    }
}