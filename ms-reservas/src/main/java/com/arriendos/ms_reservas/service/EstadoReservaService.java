package com.arriendos.ms_reservas.service;

import com.arriendos.ms_reservas.dto.EstadoReservaRequestDTO;
import com.arriendos.ms_reservas.dto.EstadoReservaResponseDTO;
import com.arriendos.ms_reservas.exception.ResourceNotFoundException;
import com.arriendos.ms_reservas.mapper.EstadoReservaMapper;
import com.arriendos.ms_reservas.model.EstadoReserva;
import com.arriendos.ms_reservas.repository.EstadoReservaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstadoReservaService {

    private static final Logger log = LoggerFactory.getLogger(EstadoReservaService.class);
    private final EstadoReservaRepository estadoReservaRepository;
    private final EstadoReservaMapper estadoReservaMapper;

    public EstadoReservaService(EstadoReservaRepository estadoReservaRepository, EstadoReservaMapper estadoReservaMapper) {
        this.estadoReservaRepository = estadoReservaRepository;
        this.estadoReservaMapper = estadoReservaMapper;
    }

    public List<EstadoReservaResponseDTO> obtenerTodos() {

        log.info("Listando estados de reserva");

        return estadoReservaRepository.findAll()
                .stream()
                .map(estadoReservaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public EstadoReservaResponseDTO obtenerPorId(Integer id) {

        log.info("Buscando estado reserva por id {}", id);

        EstadoReserva estadoReserva = estadoReservaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Estado reserva no encontrado con id " + id));
        return estadoReservaMapper.toDTO(estadoReserva);
    }

    public EstadoReservaResponseDTO guardar(EstadoReservaRequestDTO dto) {

        try {

            log.info("Guardando estado reserva {}", dto.getNombre());

            EstadoReserva estadoReserva = estadoReservaMapper.toEntity(dto);

            EstadoReserva guardado = estadoReservaRepository.save(estadoReserva);

            return estadoReservaMapper.toDTO(guardado);

        } catch (Exception e) {

            log.error("Error al guardar estado reserva: {}", e.getMessage());

            throw e;
        }
    }

    public EstadoReservaResponseDTO actualizar(Integer id, EstadoReservaRequestDTO dto) {

        log.info("Actualizando estado reserva con id {}", id);

        EstadoReserva estadoReserva = estadoReservaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Estado reserva no encontrado con id " + id));
        estadoReserva.setNombre(dto.getNombre());
        estadoReserva.setDescripcion(dto.getDescripcion());
        estadoReserva.setPrioridad(dto.getPrioridad());
        estadoReserva.setActivo(dto.getActivo());
        estadoReserva.setFechaCreacion(dto.getFechaCreacion());
        EstadoReserva actualizado = estadoReservaRepository.save(estadoReserva);

        return estadoReservaMapper.toDTO(actualizado);
    }

    public void eliminar(Integer id) {

        log.info("Eliminando estado reserva con id {}", id);

        EstadoReserva estadoReserva = estadoReservaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Estado reserva no encontrado con id " + id));

        estadoReservaRepository.delete(estadoReserva);
    }
}