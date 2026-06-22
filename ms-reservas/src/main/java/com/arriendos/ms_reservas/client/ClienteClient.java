package com.arriendos.ms_reservas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-clientes")
public interface ClienteClient {



    @GetMapping("/api/v1/clientes/{id}")
    Object obtenerClientePorId(@PathVariable Integer id);
}
