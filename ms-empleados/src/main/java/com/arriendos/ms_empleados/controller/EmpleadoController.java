package com.arriendos.ms_empleados.controller;

import com.arriendos.ms_empleados.dto.EmpleadoRequestDTO;
import com.arriendos.ms_empleados.dto.EmpleadoResponseDTO;
import com.arriendos.ms_empleados.service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;
    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping
    public ResponseEntity<List<EmpleadoResponseDTO>> listar() {
        return ResponseEntity.ok(empleadoService.obtenerTodos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(empleadoService.obtenerPorId(id));
    }



    @PostMapping
    public ResponseEntity<EmpleadoResponseDTO> guardar(@Valid @RequestBody EmpleadoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody EmpleadoRequestDTO dto) {
        return ResponseEntity.ok(empleadoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        empleadoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/activos/anio/{anio}")
    public ResponseEntity<List<EmpleadoResponseDTO>> buscarActivosPorAnio(@PathVariable Integer anio) {

        return ResponseEntity.ok(empleadoService.buscarActivosPorAnio(anio));
    }
}