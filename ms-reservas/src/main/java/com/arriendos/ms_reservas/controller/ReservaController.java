package com.arriendos.ms_reservas.controller;

import com.arriendos.ms_reservas.dto.ReservaRequestDTO;
import com.arriendos.ms_reservas.dto.ReservaResponseDTO;
import com.arriendos.ms_reservas.service.ReservaService;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/reservas")
@Tag(name = "Reservas", description = "Endpoints para administrar reservas de vehiculos")
public class ReservaController {

    private final ReservaService reservaService;
    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @Operation(summary = "Listar reservas", description = "Obtiene todas las reservas registradas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservas obtenidas correctamente",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReservaResponseDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> listar() {
        List<ReservaResponseDTO> reservas = reservaService.obtenerTodas()
                .stream()
                .map(this::agregarLinks)
                .collect(Collectors.toList());

        return ResponseEntity.ok(reservas);
    }

    @Operation(summary = "Obtener reserva por ID", description = "Obtiene una reserva segun su identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva encontrada", content = @Content(schema = @Schema(implementation = ReservaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> obtenerPorId(@Parameter(description = "ID de la reserva", example = "1") @PathVariable Integer id) {
        return ResponseEntity.ok(agregarLinks(reservaService.obtenerPorId(id)));
    }

    @Operation(summary = "Crear reserva", description = "Registra una nueva reserva",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la reserva a crear", required = true,
                    content = @Content(schema = @Schema(implementation = ReservaRequestDTO.class),
                            examples = @ExampleObject(value = "{\"clienteId\":1,\"vehiculoId\":2,\"codigoReserva\":\"RES-001\",\"diasArriendo\":5,\"montoTotal\":250000.0,\"confirmada\":true,\"fechaInicio\":\"2026-06-20\",\"estadoReservaId\":1}"))))
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reserva creada correctamente", content = @Content(schema = @Schema(implementation = ReservaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Cliente, vehiculo o estado de reserva no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping
    public ResponseEntity<ReservaResponseDTO> guardar(@Valid @RequestBody ReservaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agregarLinks(reservaService.guardar(dto)));
    }

    @Operation(summary = "Actualizar reserva", description = "Actualiza una reserva existente",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos actualizados de la reserva", required = true,
                    content = @Content(schema = @Schema(implementation = ReservaRequestDTO.class),
                            examples = @ExampleObject(value = "{\"clienteId\":1,\"vehiculoId\":2,\"codigoReserva\":\"RES-001\",\"diasArriendo\":7,\"montoTotal\":320000.0,\"confirmada\":true,\"fechaInicio\":\"2026-06-20\",\"estadoReservaId\":1}"))))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva actualizada correctamente", content = @Content(schema = @Schema(implementation = ReservaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> actualizar(
            @Parameter(description = "ID de la reserva", example = "1") @PathVariable Integer id,
            @Valid @RequestBody ReservaRequestDTO dto) {

        return ResponseEntity.ok(agregarLinks(reservaService.actualizar(id, dto)));
    }

    @Operation(summary = "Eliminar reserva", description = "Elimina una reserva segun su identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Reserva eliminada correctamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID de la reserva", example = "1") @PathVariable Integer id) {
        reservaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }




    @Operation(summary = "Buscar reservas desde una fecha", description = "Obtiene reservas cuya fecha de inicio es igual o posterior a la fecha indicada")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservas obtenidas correctamente",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReservaResponseDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/desde-fecha")
    public ResponseEntity<List<ReservaResponseDTO>> buscarDesdeFecha(
            @Parameter(description = "Fecha inicial de busqueda en formato ISO", example = "2026-06-20")
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fecha) {

        List<ReservaResponseDTO> reservas = reservaService.buscarDesdeFecha(fecha)
                .stream()
                .map(this::agregarLinks)
                .collect(Collectors.toList());

        return ResponseEntity.ok(reservas);
    }

    private ReservaResponseDTO agregarLinks(ReservaResponseDTO reserva) {
        reserva.add(linkTo(methodOn(ReservaController.class).obtenerPorId(reserva.getId())).withSelfRel());
        reserva.add(linkTo(methodOn(ReservaController.class).listar()).withRel("listar-todas"));
        reserva.add(linkTo(methodOn(ReservaController.class).actualizar(reserva.getId(), null)).withRel("actualizar"));
        reserva.add(linkTo(methodOn(ReservaController.class).eliminar(reserva.getId())).withRel("eliminar"));
        reserva.add(linkTo(methodOn(EstadoReservaController.class).obtenerPorId(reserva.getEstadoReservaId())).withRel("estado-reserva"));
        reserva.add(Link.of("/api/v1/clientes/" + reserva.getClienteId()).withRel("cliente"));
        reserva.add(Link.of("/api/v1/vehiculos/" + reserva.getVehiculoId()).withRel("vehiculo"));
        return reserva;
    }
}
