package com.arriendos.ms_sucursales.service;

import com.arriendos.ms_sucursales.dto.RegionRequestDTO;
import com.arriendos.ms_sucursales.dto.RegionResponseDTO;
import com.arriendos.ms_sucursales.exception.ResourceNotFoundException;
import com.arriendos.ms_sucursales.mapper.RegionMapper;
import com.arriendos.ms_sucursales.model.Region;
import com.arriendos.ms_sucursales.repository.RegionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegionService {

    private static final Logger log = LoggerFactory.getLogger(RegionService.class);
    private final RegionRepository regionRepository;
    private final RegionMapper regionMapper;
    public RegionService(RegionRepository regionRepository, RegionMapper regionMapper) {
        this.regionRepository = regionRepository;
        this.regionMapper = regionMapper;
    }

    public List<RegionResponseDTO> obtenerTodas() {

        log.info("Listando regiones");

        return regionRepository.findAll()
                .stream()
                .map(regionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public RegionResponseDTO obtenerPorId(Integer id) {

        log.info("Buscando region por id {}", id);

        Region region = regionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Region no encontrada con id " + id));
        return regionMapper.toDTO(region);
    }

    public RegionResponseDTO guardar(RegionRequestDTO dto) {

        try {

            log.info("Guardando region {}", dto.getNombre());

            Region region = regionMapper.toEntity(dto);
            Region guardada = regionRepository.save(region);

            return regionMapper.toDTO(guardada);

        } catch (Exception e) {

            log.error("Error al guardar region: {}", e.getMessage());

            throw e;
        }
    }

    public RegionResponseDTO actualizar(Integer id, RegionRequestDTO dto) {

        log.info("Actualizando region con id {}", id);

        Region region = regionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Region no encontrada con id " + id));
        region.setNombre(dto.getNombre());
        region.setCodigo(dto.getCodigo());
        region.setCantidadSucursales(dto.getCantidadSucursales());
        region.setActiva(dto.getActiva());
        region.setFechaCreacion(dto.getFechaCreacion());
        Region actualizada = regionRepository.save(region);

        return regionMapper.toDTO(actualizada);
    }

    public void eliminar(Integer id) {

        log.info("Eliminando region con id {}", id);

        Region region = regionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Region no encontrada con id " + id));
        regionRepository.delete(region);
    }
}