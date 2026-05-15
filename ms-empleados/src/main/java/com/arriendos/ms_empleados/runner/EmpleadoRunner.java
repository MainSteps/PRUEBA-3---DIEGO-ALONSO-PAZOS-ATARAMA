package com.arriendos.ms_empleados.runner;

import com.arriendos.ms_empleados.model.Empleado;
import com.arriendos.ms_empleados.repository.EmpleadoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class EmpleadoRunner implements CommandLineRunner {

    private final EmpleadoRepository empleadoRepository;
    public EmpleadoRunner(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public void run(String... args) {

        if (empleadoRepository.count() == 0) {

            Empleado empleado1 = new Empleado();
            empleado1.setNombre("Jose Antonio Kast");
            empleado1.setCargo("Ejecutivo de ventas");
            empleado1.setEmail("kast@arriendos.cl");
            empleado1.setSueldo(750000.0);
            empleado1.setActivo(true);
            empleado1.setFechaContratacion(LocalDate.of(2026, 1, 10));
            empleado1.setAniosExperiencia(3);

            Empleado empleado2 = new Empleado();
            empleado2.setNombre("Naya Facil");
            empleado2.setCargo("Administradora de sucursal");
            empleado2.setEmail("naya@arriendos.cl");
            empleado2.setSueldo(950000.0);
            empleado2.setActivo(true);
            empleado2.setFechaContratacion(LocalDate.of(2025, 8, 15));
            empleado2.setAniosExperiencia(5);

            Empleado empleado3 = new Empleado();
            empleado3.setNombre("Cristiano Ronaldo");
            empleado3.setCargo("Asistente operativo");
            empleado3.setEmail("cr7@arriendos.cl");
            empleado3.setSueldo(650000.0);
            empleado3.setActivo(false);
            empleado3.setFechaContratacion(LocalDate.of(2026, 3, 20));
            empleado3.setAniosExperiencia(2);

            empleadoRepository.save(empleado1);
            empleadoRepository.save(empleado2);
            empleadoRepository.save(empleado3);

            System.out.println("Datos iniciales de empleados cargados correctamente");
        }
    }
}