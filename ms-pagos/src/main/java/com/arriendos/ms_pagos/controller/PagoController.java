package com.arriendos.ms_pagos.controller;

import com.arriendos.ms_pagos.dto.PagoRequestDTO;
import com.arriendos.ms_pagos.dto.PagoResponseDTO;
import com.arriendos.ms_pagos.service.PagoService;
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
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/pagos")
@Tag(name = "Pagos", description = "Endpoints para administrar pagos de reservas")
public class PagoController {

    private final PagoService pagoService;
    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @Operation(summary = "Listar pagos", description = "Obtiene todos los pagos registrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pagos obtenidos correctamente",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PagoResponseDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping
    public ResponseEntity<List<PagoResponseDTO>> listar() {
        List<PagoResponseDTO> pagos = pagoService.obtenerTodos()
                .stream()
                .map(this::agregarLinks)
                .collect(Collectors.toList());

        return ResponseEntity.ok(pagos);
    }

    @Operation(summary = "Obtener pago por ID", description = "Obtiene un pago segun su identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pago encontrado", content = @Content(schema = @Schema(implementation = PagoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> obtenerPorId(@Parameter(description = "ID del pago", example = "1") @PathVariable Integer id) {
        return ResponseEntity.ok(agregarLinks(pagoService.obtenerPorId(id)));
    }

    @Operation(summary = "Crear pago", description = "Registra un nuevo pago",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del pago a crear", required = true,
                    content = @Content(schema = @Schema(implementation = PagoRequestDTO.class),
                            examples = @ExampleObject(value = "{\"reservaId\":1,\"codigoPago\":\"PAG-001\",\"monto\":250000.0,\"metodoPago\":\"Tarjeta\",\"pagado\":true,\"fechaPago\":\"2026-06-20\",\"numeroCuotas\":3}"))))
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pago creado correctamente", content = @Content(schema = @Schema(implementation = PagoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping
    public ResponseEntity<PagoResponseDTO> guardar(@Valid @RequestBody PagoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agregarLinks(pagoService.guardar(dto)));
    }

    @Operation(summary = "Actualizar pago", description = "Actualiza un pago existente",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos actualizados del pago", required = true,
                    content = @Content(schema = @Schema(implementation = PagoRequestDTO.class),
                            examples = @ExampleObject(value = "{\"reservaId\":1,\"codigoPago\":\"PAG-001\",\"monto\":250000.0,\"metodoPago\":\"Transferencia\",\"pagado\":true,\"fechaPago\":\"2026-06-20\",\"numeroCuotas\":1}"))))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pago actualizado correctamente", content = @Content(schema = @Schema(implementation = PagoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> actualizar(@Parameter(description = "ID del pago", example = "1") @PathVariable Integer id, @Valid @RequestBody PagoRequestDTO dto) {
        return ResponseEntity.ok(agregarLinks(pagoService.actualizar(id, dto)));
    }

    @Operation(summary = "Eliminar pago", description = "Elimina un pago segun su identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pago eliminado correctamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID del pago", example = "1") @PathVariable Integer id) {
        pagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar pagos por rango de monto", description = "Obtiene pagos cuyo monto se encuentra entre los valores indicados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pagos obtenidos correctamente",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PagoResponseDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/rango-monto")
    public ResponseEntity<List<PagoResponseDTO>> buscarPorRangoMonto(
            @Parameter(description = "Monto minimo de busqueda", example = "100000.0") @RequestParam Double montoMin,
            @Parameter(description = "Monto maximo de busqueda", example = "300000.0") @RequestParam Double montoMax) {
        List<PagoResponseDTO> pagos = pagoService.buscarPorRangoMonto(montoMin, montoMax)
                .stream()
                .map(this::agregarLinks)
                .collect(Collectors.toList());

        return ResponseEntity.ok(pagos);
    }

    private PagoResponseDTO agregarLinks(PagoResponseDTO pago) {
        pago.add(linkTo(methodOn(PagoController.class).obtenerPorId(pago.getId())).withSelfRel());
        pago.add(linkTo(methodOn(PagoController.class).listar()).withRel("listar-todos"));
        pago.add(linkTo(methodOn(PagoController.class).actualizar(pago.getId(), null)).withRel("actualizar"));
        pago.add(linkTo(methodOn(PagoController.class).eliminar(pago.getId())).withRel("eliminar"));
        pago.add(Link.of("/api/v1/reservas/" + pago.getReservaId()).withRel("reserva"));
        return pago;
    }
}
