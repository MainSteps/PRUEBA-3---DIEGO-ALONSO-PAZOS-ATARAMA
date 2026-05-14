package com.arriendos.ms_reservas.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "reserva")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer clienteId;

    @Column(nullable = false)
    private Integer vehiculoId;

    @Column(nullable = false)
    private String codigoReserva;

    @Column(nullable = false)
    private Integer diasArriendo;

    @Column(nullable = false)
    private Double montoTotal;

    @Column(nullable = false)
    private Boolean confirmada;

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @ManyToOne
    @JoinColumn(name = "estado_reserva_id", nullable = false)
    private EstadoReserva estadoReserva;
}