package com.arriendos.ms_empleados.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "empleado")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String cargo;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Double sueldo;

    @Column(nullable = false)
    private Boolean activo;

    @Column(nullable = false)
    private LocalDate fechaContratacion;

    @Column(nullable = false)
    private Integer aniosExperiencia;
    
}