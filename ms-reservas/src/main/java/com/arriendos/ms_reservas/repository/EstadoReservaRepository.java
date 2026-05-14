package com.arriendos.ms_reservas.repository;

import com.arriendos.ms_reservas.model.EstadoReserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoReservaRepository extends JpaRepository<EstadoReserva, Integer> {
}