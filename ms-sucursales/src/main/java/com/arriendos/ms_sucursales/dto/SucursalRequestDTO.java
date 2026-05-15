package com.arriendos.ms_sucursales.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SucursalRequestDTO {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "La direccion es obligatoria")
    private String direccion;

    @NotBlank(message = "El telefono es obligatorio")
    private String telefono;

    @NotNull(message = "La capacidad de vehiculos es obligatoria")
    @Positive(message = "La capacidad debe ser positiva")
    private Integer capacidadVehiculos;

    @NotNull(message = "El estado operativa es obligatorio")
    private Boolean operativa;

    @NotNull(message = "La fecha de apertura es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser futura")
    private LocalDate fechaApertura;

    @NotNull(message = "El regionId es obligatorio")
    private Integer regionId;

    
}