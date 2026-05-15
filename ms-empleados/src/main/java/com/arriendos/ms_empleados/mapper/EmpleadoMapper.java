package com.arriendos.ms_empleados.mapper;

import com.arriendos.ms_empleados.dto.EmpleadoRequestDTO;
import com.arriendos.ms_empleados.dto.EmpleadoResponseDTO;
import com.arriendos.ms_empleados.model.Empleado;
import org.springframework.stereotype.Component;

@Component
public class EmpleadoMapper {

    public Empleado toEntity(EmpleadoRequestDTO dto) {
        Empleado empleado = new Empleado();
        empleado.setNombre(dto.getNombre());
        empleado.setCargo(dto.getCargo());
        empleado.setEmail(dto.getEmail());
        empleado.setSueldo(dto.getSueldo());
        empleado.setActivo(dto.getActivo());
        empleado.setFechaContratacion(dto.getFechaContratacion());
        empleado.setAniosExperiencia(dto.getAniosExperiencia());

        return empleado;
    }

    public EmpleadoResponseDTO toDTO(Empleado empleado) {
        return new EmpleadoResponseDTO(
                empleado.getId(),
                empleado.getNombre(),
                empleado.getCargo(),
                empleado.getEmail(),
                empleado.getSueldo(),
                empleado.getActivo(),
                empleado.getFechaContratacion(),
                empleado.getAniosExperiencia()
        );
    }

    
}