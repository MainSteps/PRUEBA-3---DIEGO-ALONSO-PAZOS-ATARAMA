package com.arriendos.ms_sucursales.controller;

import com.arriendos.ms_sucursales.dto.RegionRequestDTO;
import com.arriendos.ms_sucursales.dto.RegionResponseDTO;
import com.arriendos.ms_sucursales.service.RegionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/regiones")
public class RegionController {

    private final RegionService regionService;
    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping
    public ResponseEntity<List<RegionResponseDTO>> listar() {
        return ResponseEntity.ok(regionService.obtenerTodas());
    }
    @GetMapping("/{id}")
    public ResponseEntity<RegionResponseDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(regionService.obtenerPorId(id));
    }



    @PostMapping
    public ResponseEntity<RegionResponseDTO> guardar(@Valid @RequestBody RegionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(regionService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegionResponseDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody RegionRequestDTO dto) {

        return ResponseEntity.ok(regionService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        regionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }


}