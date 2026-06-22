package com.arriendos.ms_reportes.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "ms-reservas")

public interface ReservaClient {
    @GetMapping("/api/v1/reservas")
    List<Object> obtenerReservas();
}
