package com.arriendos.ms_reservas.service;

import com.arriendos.ms_reservas.client.ClienteClient;
import com.arriendos.ms_reservas.client.VehiculoClient;
import com.arriendos.ms_reservas.dto.ReservaRequestDTO;
import com.arriendos.ms_reservas.dto.ReservaResponseDTO;
import com.arriendos.ms_reservas.exception.ResourceNotFoundException;
import com.arriendos.ms_reservas.mapper.ReservaMapper;
import com.arriendos.ms_reservas.model.EstadoReserva;
import com.arriendos.ms_reservas.model.Reserva;
import com.arriendos.ms_reservas.repository.EstadoReservaRepository;
import com.arriendos.ms_reservas.repository.ReservaRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private EstadoReservaRepository estadoReservaRepository;

    @Mock
    private ReservaMapper reservaMapper;

    @Mock
    private ClienteClient clienteClient;

    @Mock
    private VehiculoClient vehiculoClient;

    @InjectMocks
    private ReservaService reservaService;

    @Test
    void obtenerTodas_debeRetornarReservas() {
        EstadoReserva estado = crearEstadoReserva();
        Reserva reserva = crearReserva(estado);
        ReservaResponseDTO response = crearResponse();

        when(reservaRepository.findAll()).thenReturn(List.of(reserva));
        when(reservaMapper.toDTO(reserva)).thenReturn(response);

        List<ReservaResponseDTO> resultado = reservaService.obtenerTodas();

        assertEquals(1, resultado.size());
        assertEquals("RES-001", resultado.get(0).getCodigoReserva());
    }

    @Test
    void obtenerPorId_cuandoExiste_debeRetornarReserva() {
        EstadoReserva estado = crearEstadoReserva();
        Reserva reserva = crearReserva(estado);
        ReservaResponseDTO response = crearResponse();

        when(reservaRepository.findById(1)).thenReturn(Optional.of(reserva));
        when(reservaMapper.toDTO(reserva)).thenReturn(response);

        ReservaResponseDTO resultado = reservaService.obtenerPorId(1);

        assertEquals(1, resultado.getId());
        assertEquals(1, resultado.getClienteId());
    }

    @Test
    void obtenerPorId_cuandoNoExiste_debeLanzarExcepcion() {
        when(reservaRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> reservaService.obtenerPorId(99));
    }

    @Test
    void guardar_cuandoDatosSonValidos_debeGuardarReserva() {
        EstadoReserva estado = crearEstadoReserva();
        ReservaRequestDTO request = crearRequest();
        Reserva reserva = crearReserva(estado);
        ReservaResponseDTO response = crearResponse();

        when(clienteClient.obtenerClientePorId(1)).thenReturn(new Object());
        when(vehiculoClient.obtenerVehiculoPorId(2)).thenReturn(new Object());
        when(estadoReservaRepository.findById(1)).thenReturn(Optional.of(estado));
        when(reservaMapper.toEntity(request, estado)).thenReturn(reserva);
        when(reservaRepository.save(reserva)).thenReturn(reserva);
        when(reservaMapper.toDTO(reserva)).thenReturn(response);

        ReservaResponseDTO resultado = reservaService.guardar(request);

        assertEquals("RES-001", resultado.getCodigoReserva());
        verify(reservaRepository).save(reserva);
    }

    @Test
    void guardar_cuandoClienteNoExiste_debeLanzarExcepcion() {
        ReservaRequestDTO request = crearRequest();
        doThrow(new RuntimeException()).when(clienteClient).obtenerClientePorId(1);

        assertThrows(ResourceNotFoundException.class, () -> reservaService.guardar(request));
    }

    @Test
    void actualizar_cuandoExiste_debeActualizarReserva() {
        EstadoReserva estado = crearEstadoReserva();
        ReservaRequestDTO request = crearRequest();
        Reserva reserva = crearReserva(estado);
        ReservaResponseDTO response = crearResponse();

        when(reservaRepository.findById(1)).thenReturn(Optional.of(reserva));
        when(clienteClient.obtenerClientePorId(1)).thenReturn(new Object());
        when(vehiculoClient.obtenerVehiculoPorId(2)).thenReturn(new Object());
        when(estadoReservaRepository.findById(1)).thenReturn(Optional.of(estado));
        when(reservaRepository.save(reserva)).thenReturn(reserva);
        when(reservaMapper.toDTO(reserva)).thenReturn(response);

        ReservaResponseDTO resultado = reservaService.actualizar(1, request);

        assertEquals(5, resultado.getDiasArriendo());
        verify(reservaRepository).save(reserva);
    }

    @Test
    void eliminar_cuandoExiste_debeEliminarReserva() {
        EstadoReserva estado = crearEstadoReserva();
        Reserva reserva = crearReserva(estado);

        when(reservaRepository.findById(1)).thenReturn(Optional.of(reserva));

        reservaService.eliminar(1);

        verify(reservaRepository).delete(reserva);
    }

    @Test
    void buscarDesdeFecha_debeRetornarReservasFiltradas() {
        LocalDate fecha = LocalDate.of(2026, 6, 20);
        EstadoReserva estado = crearEstadoReserva();
        Reserva reserva = crearReserva(estado);
        ReservaResponseDTO response = crearResponse();

        when(reservaRepository.buscarReservasDesdeFecha(fecha)).thenReturn(List.of(reserva));
        when(reservaMapper.toDTO(reserva)).thenReturn(response);

        List<ReservaResponseDTO> resultado = reservaService.buscarDesdeFecha(fecha);

        assertEquals(1, resultado.size());
        assertEquals(fecha, resultado.get(0).getFechaInicio());
    }

    private ReservaRequestDTO crearRequest() {
        ReservaRequestDTO dto = new ReservaRequestDTO();
        dto.setClienteId(1);
        dto.setVehiculoId(2);
        dto.setCodigoReserva("RES-001");
        dto.setDiasArriendo(5);
        dto.setMontoTotal(250000.0);
        dto.setConfirmada(true);
        dto.setFechaInicio(LocalDate.of(2026, 6, 20));
        dto.setEstadoReservaId(1);
        return dto;
    }

    private EstadoReserva crearEstadoReserva() {
        return new EstadoReserva(1, "Confirmada", "Reserva confirmada", 1, true, LocalDate.of(2026, 6, 20), null);
    }

    private Reserva crearReserva(EstadoReserva estado) {
        return new Reserva(1, 1, 2, "RES-001", 5, 250000.0, true, LocalDate.of(2026, 6, 20), estado);
    }

    private ReservaResponseDTO crearResponse() {
        return new ReservaResponseDTO(1, 1, 2, "RES-001", 5, 250000.0, true, LocalDate.of(2026, 6, 20), 1);
    }
}
