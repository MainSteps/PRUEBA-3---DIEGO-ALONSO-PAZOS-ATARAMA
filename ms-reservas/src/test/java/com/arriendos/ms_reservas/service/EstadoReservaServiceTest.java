package com.arriendos.ms_reservas.service;

import com.arriendos.ms_reservas.dto.EstadoReservaRequestDTO;
import com.arriendos.ms_reservas.dto.EstadoReservaResponseDTO;
import com.arriendos.ms_reservas.exception.ResourceNotFoundException;
import com.arriendos.ms_reservas.mapper.EstadoReservaMapper;
import com.arriendos.ms_reservas.model.EstadoReserva;
import com.arriendos.ms_reservas.repository.EstadoReservaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EstadoReservaServiceTest {

    @Mock
    private EstadoReservaRepository estadoReservaRepository;

    @Mock
    private EstadoReservaMapper estadoReservaMapper;

    @InjectMocks
    private EstadoReservaService estadoReservaService;

    @Test
    void obtenerTodos_debeRetornarEstados() {
        EstadoReserva estado = crearEstado();
        EstadoReservaResponseDTO response = crearResponse();

        when(estadoReservaRepository.findAll()).thenReturn(List.of(estado));
        when(estadoReservaMapper.toDTO(estado)).thenReturn(response);

        List<EstadoReservaResponseDTO> resultado = estadoReservaService.obtenerTodos();

        assertEquals(1, resultado.size());
        assertEquals("Confirmada", resultado.get(0).getNombre());
    }

    @Test
    void obtenerPorId_cuandoExiste_debeRetornarEstado() {
        EstadoReserva estado = crearEstado();
        EstadoReservaResponseDTO response = crearResponse();

        when(estadoReservaRepository.findById(1)).thenReturn(Optional.of(estado));
        when(estadoReservaMapper.toDTO(estado)).thenReturn(response);

        EstadoReservaResponseDTO resultado = estadoReservaService.obtenerPorId(1);

        assertEquals(1, resultado.getId());
        assertEquals(true, resultado.getActivo());
    }

    @Test
    void obtenerPorId_cuandoNoExiste_debeLanzarExcepcion() {
        when(estadoReservaRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> estadoReservaService.obtenerPorId(99));
    }

    @Test
    void guardar_debeGuardarEstado() {
        EstadoReservaRequestDTO request = crearRequest();
        EstadoReserva estado = crearEstado();
        EstadoReservaResponseDTO response = crearResponse();

        when(estadoReservaMapper.toEntity(request)).thenReturn(estado);
        when(estadoReservaRepository.save(estado)).thenReturn(estado);
        when(estadoReservaMapper.toDTO(estado)).thenReturn(response);

        EstadoReservaResponseDTO resultado = estadoReservaService.guardar(request);

        assertEquals("Confirmada", resultado.getNombre());
        verify(estadoReservaRepository).save(estado);
    }

    @Test
    void actualizar_cuandoExiste_debeActualizarEstado() {
        EstadoReservaRequestDTO request = crearRequest();
        EstadoReserva estado = crearEstado();
        EstadoReservaResponseDTO response = crearResponse();

        when(estadoReservaRepository.findById(1)).thenReturn(Optional.of(estado));
        when(estadoReservaRepository.save(estado)).thenReturn(estado);
        when(estadoReservaMapper.toDTO(estado)).thenReturn(response);

        EstadoReservaResponseDTO resultado = estadoReservaService.actualizar(1, request);

        assertEquals("Confirmada", resultado.getNombre());
        verify(estadoReservaRepository).save(estado);
    }

    @Test
    void eliminar_cuandoExiste_debeEliminarEstado() {
        EstadoReserva estado = crearEstado();

        when(estadoReservaRepository.findById(1)).thenReturn(Optional.of(estado));

        estadoReservaService.eliminar(1);

        verify(estadoReservaRepository).delete(estado);
    }

    private EstadoReservaRequestDTO crearRequest() {
        EstadoReservaRequestDTO dto = new EstadoReservaRequestDTO();
        dto.setNombre("Confirmada");
        dto.setDescripcion("Reserva confirmada");
        dto.setPrioridad(1);
        dto.setActivo(true);
        dto.setFechaCreacion(LocalDate.of(2026, 6, 20));
        return dto;
    }

    private EstadoReserva crearEstado() {
        return new EstadoReserva(1, "Confirmada", "Reserva confirmada", 1, true, LocalDate.of(2026, 6, 20), null);
    }

    private EstadoReservaResponseDTO crearResponse() {
        return new EstadoReservaResponseDTO(1, "Confirmada", "Reserva confirmada", 1, true, LocalDate.of(2026, 6, 20));
    }
}
