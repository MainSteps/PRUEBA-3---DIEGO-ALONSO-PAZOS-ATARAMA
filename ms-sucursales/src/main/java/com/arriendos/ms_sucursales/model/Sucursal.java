package com.arriendos.ms_sucursales.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "sucursal")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private Integer capacidadVehiculos;

    @Column(nullable = false)
    private Boolean operativa;

    @Column(nullable = false)
    private LocalDate fechaApertura;

    @Column(nullable = false)
    private Integer regionId;
    
}