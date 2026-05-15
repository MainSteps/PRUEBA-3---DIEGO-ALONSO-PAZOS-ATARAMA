package com.arriendos.ms_sucursales.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "region")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String codigo;

    @Column(nullable = false)
    private Integer cantidadSucursales;

    @Column(nullable = false)
    private Boolean activa;

    @Column(nullable = false)
    private LocalDate fechaCreacion;

    
}