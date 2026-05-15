package com.arriendos.ms_sucursales.service;

import com.arriendos.ms_sucursales.dto.SucursalRequestDTO;
import com.arriendos.ms_sucursales.dto.SucursalResponseDTO;
import com.arriendos.ms_sucursales.exception.ResourceNotFoundException;
import com.arriendos.ms_sucursales.mapper.SucursalMapper;
import com.arriendos.ms_sucursales.model.Region;
import com.arriendos.ms_sucursales.model.Sucursal;
import com.arriendos.ms_sucursales.repository.RegionRepository;
import com.arriendos.ms_sucursales.repository.SucursalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SucursalService {

    private static final Logger log = LoggerFactory.getLogger(SucursalService.class);
    private final SucursalRepository sucursalRepository;
    private final RegionRepository regionRepository;
    private final SucursalMapper sucursalMapper;


    public SucursalService(SucursalRepository sucursalRepository, RegionRepository regionRepository, SucursalMapper sucursalMapper) {
        this.sucursalRepository = sucursalRepository;
        this.regionRepository = regionRepository;
        this.sucursalMapper = sucursalMapper;
    }

    public List<SucursalResponseDTO> obtenerTodas() {
        log.info("Listando sucursales");

        return sucursalRepository.findAll()
                .stream()
                .map(sucursalMapper::toDTO)
                .collect(Collectors.toList());
    }

    public SucursalResponseDTO obtenerPorId(Integer id) {
        log.info("Buscando sucursal por id {}", id);

        Sucursal sucursal = sucursalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sucursal no encontrada con id " + id));
        return sucursalMapper.toDTO(sucursal);
    }

    public SucursalResponseDTO guardar(SucursalRequestDTO dto) {
        try {
            log.info("Guardando sucursal {}", dto.getNombre());

            Region region = regionRepository.findById(dto.getRegionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Region no encontrada con id " + dto.getRegionId()));
            Sucursal sucursal = sucursalMapper.toEntity(dto);
            Sucursal guardada = sucursalRepository.save(sucursal);

            return sucursalMapper.toDTO(guardada);

        } catch (Exception e) {
            log.error("Error al guardar sucursal: {}", e.getMessage());
            throw e;
        }
    }

    public SucursalResponseDTO actualizar(Integer id, SucursalRequestDTO dto) {
        log.info("Actualizando sucursal con id {}", id);

        Sucursal sucursal = sucursalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sucursal no encontrada con id " + id));
        Region region = regionRepository.findById(dto.getRegionId())
                .orElseThrow(() -> new ResourceNotFoundException("Region no encontrada con id " + dto.getRegionId()));

        sucursal.setNombre(dto.getNombre());
        sucursal.setDireccion(dto.getDireccion());
        sucursal.setTelefono(dto.getTelefono());
        sucursal.setCapacidadVehiculos(dto.getCapacidadVehiculos());
        sucursal.setOperativa(dto.getOperativa());
        sucursal.setFechaApertura(dto.getFechaApertura());
        sucursal.setRegionId(region.getId());
        Sucursal actualizada = sucursalRepository.save(sucursal);

        return sucursalMapper.toDTO(actualizada);
    }

    public void eliminar(Integer id) {
        log.info("Eliminando sucursal con id {}", id);

        Sucursal sucursal = sucursalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sucursal no encontrada con id " + id));
        sucursalRepository.delete(sucursal);
    }

    public List<SucursalResponseDTO> buscarOperativas() {
        log.info("Buscando sucursales operativas");

        return sucursalRepository.buscarSucursalesOperativas()
                .stream()
                .map(sucursalMapper::toDTO)
                .collect(Collectors.toList());
    }
}