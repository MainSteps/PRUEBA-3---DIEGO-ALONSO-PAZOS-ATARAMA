package com.arriendos.ms_empleados.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmpleadoRequestDTO {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "El cargo es obligatorio")
    private String cargo;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener formato valido")
    private String email;

    @NotNull(message = "El sueldo es obligatorio")
    @DecimalMin(value = "0.0", message = "El sueldo debe ser mayor o igual a 0")
    private Double sueldo;

    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activo;

    @NotNull(message = "La fecha de contratacion es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser futura")
    private LocalDate fechaContratacion;

    @NotNull(message = "Los años de experiencia son obligatorios")
    @Min(value = 0, message = "La experiencia no puede ser negativa")
    private Integer aniosExperiencia;
    
}