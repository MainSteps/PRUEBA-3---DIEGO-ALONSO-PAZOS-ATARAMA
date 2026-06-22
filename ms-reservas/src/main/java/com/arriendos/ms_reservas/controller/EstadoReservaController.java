package com.arriendos.ms_reservas.controller;

import com.arriendos.ms_reservas.dto.EstadoReservaRequestDTO;
import com.arriendos.ms_reservas.dto.EstadoReservaResponseDTO;
import com.arriendos.ms_reservas.service.EstadoReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/estados-reserva")
@Tag(name = "Estados de reserva", description = "Endpoints para administrar estados de reserva")
public class EstadoReservaController {

    private final EstadoReservaService estadoReservaService;
    public EstadoReservaController(EstadoReservaService estadoReservaService) {
        this.estadoReservaService = estadoReservaService;
    }

    @Operation(summary = "Listar estados de reserva", description = "Obtiene todos los estados de reserva registrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estados obtenidos correctamente",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = EstadoReservaResponseDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping
    public ResponseEntity<List<EstadoReservaResponseDTO>> listar() {
        List<EstadoReservaResponseDTO> estados = estadoReservaService.obtenerTodos()
                .stream()
                .map(this::agregarLinks)
                .collect(Collectors.toList());

        return ResponseEntity.ok(estados);
    }

    @Operation(summary = "Obtener estado de reserva por ID", description = "Obtiene un estado de reserva segun su identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado encontrado", content = @Content(schema = @Schema(implementation = EstadoReservaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Estado de reserva no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<EstadoReservaResponseDTO> obtenerPorId(@Parameter(description = "ID del estado de reserva", example = "1") @PathVariable Integer id) {
        return ResponseEntity.ok(agregarLinks(estadoReservaService.obtenerPorId(id)));
    }

    @Operation(summary = "Crear estado de reserva", description = "Registra un nuevo estado de reserva",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del estado de reserva a crear", required = true,
                    content = @Content(schema = @Schema(implementation = EstadoReservaRequestDTO.class),
                            examples = @ExampleObject(value = "{\"nombre\":\"Confirmada\",\"descripcion\":\"Reserva confirmada por el cliente\",\"prioridad\":1,\"activo\":true,\"fechaCreacion\":\"2026-06-20\"}"))))
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Estado creado correctamente", content = @Content(schema = @Schema(implementation = EstadoReservaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping
    public ResponseEntity<EstadoReservaResponseDTO> guardar(@Valid @RequestBody EstadoReservaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agregarLinks(estadoReservaService.guardar(dto)));
    }

    @Operation(summary = "Actualizar estado de reserva", description = "Actualiza un estado de reserva existente",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos actualizados del estado de reserva", required = true,
                    content = @Content(schema = @Schema(implementation = EstadoReservaRequestDTO.class),
                            examples = @ExampleObject(value = "{\"nombre\":\"Confirmada\",\"descripcion\":\"Reserva confirmada y vigente\",\"prioridad\":1,\"activo\":true,\"fechaCreacion\":\"2026-06-20\"}"))))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente", content = @Content(schema = @Schema(implementation = EstadoReservaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Estado de reserva no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<EstadoReservaResponseDTO> actualizar(
            @Parameter(description = "ID del estado de reserva", example = "1") @PathVariable Integer id,
            @Valid @RequestBody EstadoReservaRequestDTO dto) {

        return ResponseEntity.ok(agregarLinks(estadoReservaService.actualizar(id, dto)));
    }

    @Operation(summary = "Eliminar estado de reserva", description = "Elimina un estado de reserva segun su identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Estado eliminado correctamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Estado de reserva no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID del estado de reserva", example = "1") @PathVariable Integer id) {
        estadoReservaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private EstadoReservaResponseDTO agregarLinks(EstadoReservaResponseDTO estadoReserva) {
        estadoReserva.add(linkTo(methodOn(EstadoReservaController.class).obtenerPorId(estadoReserva.getId())).withSelfRel());
        estadoReserva.add(linkTo(methodOn(EstadoReservaController.class).listar()).withRel("listar-todos"));
        estadoReserva.add(linkTo(methodOn(EstadoReservaController.class).actualizar(estadoReserva.getId(), null)).withRel("actualizar"));
        estadoReserva.add(linkTo(methodOn(EstadoReservaController.class).eliminar(estadoReserva.getId())).withRel("eliminar"));
        return estadoReserva;
    }
}
