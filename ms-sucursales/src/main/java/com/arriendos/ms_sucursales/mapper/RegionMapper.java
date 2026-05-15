package com.arriendos.ms_sucursales.mapper;

import com.arriendos.ms_sucursales.dto.RegionRequestDTO;
import com.arriendos.ms_sucursales.dto.RegionResponseDTO;
import com.arriendos.ms_sucursales.model.Region;
import org.springframework.stereotype.Component;

@Component
public class RegionMapper {
    public Region toEntity(RegionRequestDTO dto) {
        Region region = new Region();
        region.setNombre(dto.getNombre());
        region.setCodigo(dto.getCodigo());
        region.setCantidadSucursales(dto.getCantidadSucursales());
        region.setActiva(dto.getActiva());
        region.setFechaCreacion(dto.getFechaCreacion());

        return region;
    }

    public RegionResponseDTO toDTO(Region region) {
        return new RegionResponseDTO(
                region.getId(),
                region.getNombre(),
                region.getCodigo(),
                region.getCantidadSucursales(),
                region.getActiva(),
                region.getFechaCreacion()
        );
    }
}