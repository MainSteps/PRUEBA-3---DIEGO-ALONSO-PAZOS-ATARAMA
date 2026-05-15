package com.arriendos.ms_sucursales.controller;

import com.arriendos.ms_sucursales.dto.SucursalRequestDTO;
import com.arriendos.ms_sucursales.dto.SucursalResponseDTO;
import com.arriendos.ms_sucursales.service.SucursalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sucursales")
public class SucursalController {

    private final SucursalService sucursalService;
    public SucursalController(SucursalService sucursalService) {
        this.sucursalService = sucursalService;
    }

    @GetMapping
    public ResponseEntity<List<SucursalResponseDTO>> listar() {
        return ResponseEntity.ok(sucursalService.obtenerTodas());
    }
    @GetMapping("/{id}")
    public ResponseEntity<SucursalResponseDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(sucursalService.obtenerPorId(id));
    }


    @PostMapping
    public ResponseEntity<SucursalResponseDTO> guardar(@Valid @RequestBody SucursalRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sucursalService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SucursalResponseDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody SucursalRequestDTO dto) {

        return ResponseEntity.ok(sucursalService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        sucursalService.eliminar(id);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/operativas")
    public ResponseEntity<List<SucursalResponseDTO>> buscarOperativas() {
        return ResponseEntity.ok(sucursalService.buscarOperativas());
    }
}