package com.arriendos.ms_sucursales.mapper;

import com.arriendos.ms_sucursales.dto.SucursalRequestDTO;
import com.arriendos.ms_sucursales.dto.SucursalResponseDTO;
import com.arriendos.ms_sucursales.model.Sucursal;
import org.springframework.stereotype.Component;

@Component
public class SucursalMapper {

    public Sucursal toEntity(SucursalRequestDTO dto) {
        Sucursal sucursal = new Sucursal();
        sucursal.setNombre(dto.getNombre());
        sucursal.setDireccion(dto.getDireccion());
        sucursal.setTelefono(dto.getTelefono());
        sucursal.setCapacidadVehiculos(dto.getCapacidadVehiculos());
        sucursal.setOperativa(dto.getOperativa());
        sucursal.setFechaApertura(dto.getFechaApertura());
        sucursal.setRegionId(dto.getRegionId());

        return sucursal;
    }

    public SucursalResponseDTO toDTO(Sucursal sucursal) {
        return new SucursalResponseDTO(
                sucursal.getId(),
                sucursal.getNombre(),
                sucursal.getDireccion(),
                sucursal.getTelefono(),
                sucursal.getCapacidadVehiculos(),
                sucursal.getOperativa(),
                sucursal.getFechaApertura(),
                sucursal.getRegionId()
        );
    }
}