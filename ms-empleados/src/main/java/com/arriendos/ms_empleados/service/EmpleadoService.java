package com.arriendos.ms_empleados.service;

import com.arriendos.ms_empleados.dto.EmpleadoRequestDTO;
import com.arriendos.ms_empleados.dto.EmpleadoResponseDTO;
import com.arriendos.ms_empleados.exception.ResourceNotFoundException;
import com.arriendos.ms_empleados.mapper.EmpleadoMapper;
import com.arriendos.ms_empleados.model.Empleado;
import com.arriendos.ms_empleados.repository.EmpleadoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpleadoService {

    private static final Logger log = LoggerFactory.getLogger(EmpleadoService.class);
    private final EmpleadoRepository empleadoRepository;
    private final EmpleadoMapper empleadoMapper;

    public EmpleadoService(EmpleadoRepository empleadoRepository, EmpleadoMapper empleadoMapper) {
        this.empleadoRepository = empleadoRepository;
        this.empleadoMapper = empleadoMapper;
    }

    public List<EmpleadoResponseDTO> obtenerTodos() {

        log.info("Listando empleados");

        return empleadoRepository.findAll()
                .stream()
                .map(empleadoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public EmpleadoResponseDTO obtenerPorId(Integer id) {

        log.info("Buscando empleado por id {}", id);

        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con id " + id));

        return empleadoMapper.toDTO(empleado);
    }

    public EmpleadoResponseDTO guardar(EmpleadoRequestDTO dto) {

        try {

            log.info("Guardando empleado {}", dto.getNombre());

            Empleado empleado = empleadoMapper.toEntity(dto);
            Empleado guardado = empleadoRepository.save(empleado);

            return empleadoMapper.toDTO(guardado);

        } catch (Exception e) {

            log.error("Error al guardar empleado: {}", e.getMessage());

            throw e;
        }
    }

    public EmpleadoResponseDTO actualizar(Integer id, EmpleadoRequestDTO dto) {

        log.info("Actualizando empleado con id {}", id);

        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Empleado no encontrado con id " + id));
        empleado.setNombre(dto.getNombre());
        empleado.setCargo(dto.getCargo());
        empleado.setEmail(dto.getEmail());
        empleado.setSueldo(dto.getSueldo());
        empleado.setActivo(dto.getActivo());
        empleado.setFechaContratacion(dto.getFechaContratacion());
        empleado.setAniosExperiencia(dto.getAniosExperiencia());
        Empleado actualizado = empleadoRepository.save(empleado);

        return empleadoMapper.toDTO(actualizado);
    }

    public void eliminar(Integer id) {

        log.info("Eliminando empleado con id {}", id);

        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Empleado no encontrado con id " + id));
        empleadoRepository.delete(empleado);
    }

    public List<EmpleadoResponseDTO> buscarActivosPorAnio(Integer anio) {

        log.info("Buscando empleados activos del año {}", anio);

        return empleadoRepository.buscarActivosPorAnioContratacion(anio)
                .stream()
                .map(empleadoMapper::toDTO)
                .collect(Collectors.toList());
    }
}