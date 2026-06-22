package com.arriendos.ms_pagos.service;

import com.arriendos.ms_pagos.client.ReservaClient;
import com.arriendos.ms_pagos.dto.PagoRequestDTO;
import com.arriendos.ms_pagos.dto.PagoResponseDTO;
import com.arriendos.ms_pagos.exception.ResourceNotFoundException;
import com.arriendos.ms_pagos.mapper.PagoMapper;
import com.arriendos.ms_pagos.model.Pago;
import com.arriendos.ms_pagos.repository.PagoRepository;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private PagoMapper pagoMapper;

    @Mock
    private ReservaClient reservaClient;

    @InjectMocks
    private PagoService pagoService;

    @Test
    void obtenerTodos_debeRetornarPagos() {
        Pago pago = crearPago();
        PagoResponseDTO response = crearResponse();

        when(pagoRepository.findAll()).thenReturn(List.of(pago));
        when(pagoMapper.toDTO(pago)).thenReturn(response);

        List<PagoResponseDTO> resultado = pagoService.obtenerTodos();

        assertEquals(1, resultado.size());
        assertEquals("PAG-001", resultado.get(0).getCodigoPago());
    }

    @Test
    void obtenerPorId_cuandoExiste_debeRetornarPago() {
        Pago pago = crearPago();
        PagoResponseDTO response = crearResponse();

        when(pagoRepository.findById(1)).thenReturn(Optional.of(pago));
        when(pagoMapper.toDTO(pago)).thenReturn(response);

        PagoResponseDTO resultado = pagoService.obtenerPorId(1);

        assertEquals(1, resultado.getId());
        assertEquals(250000.0, resultado.getMonto());
    }

    @Test
    void obtenerPorId_cuandoNoExiste_debeLanzarExcepcion() {
        when(pagoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> pagoService.obtenerPorId(99));
    }

    @Test
    void guardar_cuandoReservaExiste_debeGuardarPago() {
        PagoRequestDTO request = crearRequest();
        Pago pago = crearPago();
        PagoResponseDTO response = crearResponse();

        when(reservaClient.obtenerReservaPorId(1)).thenReturn(new Object());
        when(pagoMapper.toEntity(request)).thenReturn(pago);
        when(pagoRepository.save(pago)).thenReturn(pago);
        when(pagoMapper.toDTO(pago)).thenReturn(response);

        PagoResponseDTO resultado = pagoService.guardar(request);

        assertEquals("PAG-001", resultado.getCodigoPago());
        verify(pagoRepository).save(pago);
    }

    @Test
    void guardar_cuandoReservaNoExiste_debeLanzarExcepcion() {
        PagoRequestDTO request = crearRequest();
        doThrow(new RuntimeException()).when(reservaClient).obtenerReservaPorId(1);

        assertThrows(ResourceNotFoundException.class, () -> pagoService.guardar(request));
    }

    @Test
    void actualizar_cuandoExiste_debeActualizarPago() {
        PagoRequestDTO request = crearRequest();
        Pago pago = crearPago();
        PagoResponseDTO response = crearResponse();

        when(pagoRepository.findById(1)).thenReturn(Optional.of(pago));
        when(reservaClient.obtenerReservaPorId(1)).thenReturn(new Object());
        when(pagoRepository.save(pago)).thenReturn(pago);
        when(pagoMapper.toDTO(pago)).thenReturn(response);

        PagoResponseDTO resultado = pagoService.actualizar(1, request);

        assertEquals("Tarjeta", resultado.getMetodoPago());
        verify(pagoRepository).save(pago);
    }

    @Test
    void eliminar_cuandoExiste_debeEliminarPago() {
        Pago pago = crearPago();

        when(pagoRepository.findById(1)).thenReturn(Optional.of(pago));

        pagoService.eliminar(1);

        verify(pagoRepository).delete(pago);
    }

    @Test
    void buscarPorRangoMonto_debeRetornarPagosFiltrados() {
        Pago pago = crearPago();
        PagoResponseDTO response = crearResponse();

        when(pagoRepository.buscarPorRangoMonto(100000.0, 300000.0)).thenReturn(List.of(pago));
        when(pagoMapper.toDTO(pago)).thenReturn(response);

        List<PagoResponseDTO> resultado = pagoService.buscarPorRangoMonto(100000.0, 300000.0);

        assertEquals(1, resultado.size());
        assertEquals(250000.0, resultado.get(0).getMonto());
    }

    private PagoRequestDTO crearRequest() {
        PagoRequestDTO dto = new PagoRequestDTO();
        dto.setReservaId(1);
        dto.setCodigoPago("PAG-001");
        dto.setMonto(250000.0);
        dto.setMetodoPago("Tarjeta");
        dto.setPagado(true);
        dto.setFechaPago(LocalDate.of(2026, 6, 20));
        dto.setNumeroCuotas(3);
        return dto;
    }

    private Pago crearPago() {
        return new Pago(1, 1, "PAG-001", 250000.0, "Tarjeta", true, LocalDate.of(2026, 6, 20), 3);
    }

    private PagoResponseDTO crearResponse() {
        return new PagoResponseDTO(1, 1, "PAG-001", 250000.0, "Tarjeta", true, LocalDate.of(2026, 6, 20), 3);
    }
}
