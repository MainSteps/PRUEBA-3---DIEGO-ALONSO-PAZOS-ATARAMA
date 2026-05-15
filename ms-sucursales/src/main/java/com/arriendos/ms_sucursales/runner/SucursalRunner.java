package com.arriendos.ms_sucursales.runner;

import com.arriendos.ms_sucursales.model.Region;
import com.arriendos.ms_sucursales.model.Sucursal;
import com.arriendos.ms_sucursales.repository.RegionRepository;
import com.arriendos.ms_sucursales.repository.SucursalRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class SucursalRunner implements CommandLineRunner {

    private final RegionRepository regionRepository;
    private final SucursalRepository sucursalRepository;
    public SucursalRunner(RegionRepository regionRepository, SucursalRepository sucursalRepository) {
        this.regionRepository = regionRepository;
        this.sucursalRepository = sucursalRepository;
    }

    @Override
    public void run(String... args) {

        if (regionRepository.count() == 0) {

            Region region1 = new Region();
            region1.setNombre("Metropolitana");
            region1.setCodigo("RM");
            region1.setCantidadSucursales(5);
            region1.setActiva(true);
            region1.setFechaCreacion(LocalDate.now());

            Region region2 = new Region();
            region2.setNombre("Valparaiso");
            region2.setCodigo("V");
            region2.setCantidadSucursales(3);
            region2.setActiva(true);
            region2.setFechaCreacion(LocalDate.now());

            Region region3 = new Region();
            region3.setNombre("Los Lagos");
            region3.setCodigo("L");
            region3.setCantidadSucursales(2);
            region3.setActiva(true);
            region3.setFechaCreacion(LocalDate.now());

            regionRepository.save(region1);
            regionRepository.save(region2);
            regionRepository.save(region3);

            Sucursal sucursal1 = new Sucursal();
            sucursal1.setNombre("Sucursal Santiago Centro");
            sucursal1.setDireccion("Juan de Barros 123");
            sucursal1.setTelefono("42344242");
            sucursal1.setCapacidadVehiculos(50);
            sucursal1.setOperativa(true);
            sucursal1.setFechaApertura(LocalDate.now());
            sucursal1.setRegionId(region1.getId());

            Sucursal sucursal2 = new Sucursal();
            sucursal2.setNombre("Sucursal Viña del Mar");
            sucursal2.setDireccion("Av. Alameda 416");
            sucursal2.setTelefono("32223111");
            sucursal2.setCapacidadVehiculos(30);
            sucursal2.setOperativa(true);
            sucursal2.setFechaApertura(LocalDate.now());
            sucursal2.setRegionId(region2.getId());

            Sucursal sucursal3 = new Sucursal();
            sucursal3.setNombre("Sucursal Valdivia");
            sucursal3.setDireccion("Los lagos 3992");
            sucursal3.setTelefono("41221245");
            sucursal3.setCapacidadVehiculos(20);
            sucursal3.setOperativa(false);
            sucursal3.setFechaApertura(LocalDate.now());
            sucursal3.setRegionId(region3.getId());

            sucursalRepository.save(sucursal1);
            sucursalRepository.save(sucursal2);
            sucursalRepository.save(sucursal3);

            System.out.println("Datos iniciales de regiones y sucursales cargados correctamente");
        }
    }
}