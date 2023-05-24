package com.milkstgo.milkstgo;

import com.milkstgo.milkstgo.entities.SubirAcopioEntity;
import com.milkstgo.milkstgo.services.SubirAcopioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class SubirAcopioTests {

    @Autowired
    SubirAcopioService subirAcopioService;

    @Test
    void test1CrearAcopio() {

        String fechaStr = "2023/05/01";
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate fecha = LocalDate.parse(fechaStr, formato);

        String turno = "M";
        String proveedor = "01001";
        int kgleche = 50;

        SubirAcopioEntity subirAcopioEntityPrueba1 = new SubirAcopioEntity(null, fecha, turno, proveedor, kgleche);
        SubirAcopioEntity subirAcopioEntityPruebaB = subirAcopioService.crearAcopio("2023/05/01","M","01001", 50);

        assertEquals(subirAcopioEntityPrueba1.getFecha(),subirAcopioEntityPruebaB.getFecha());
        assertEquals(subirAcopioEntityPrueba1.getTurno(), subirAcopioEntityPruebaB.getTurno());
        assertEquals(subirAcopioEntityPrueba1.getProveedor(), subirAcopioEntityPruebaB.getProveedor());
        assertEquals(subirAcopioEntityPrueba1.getKgleche(), subirAcopioEntityPruebaB.getKgleche());
    }

    @Test
    void  test1VerDatos(){

        SubirAcopioEntity subirAcopioEntityPrueba1 = subirAcopioService.crearAcopio("2023/05/01","M","01001", 50);
        List<SubirAcopioEntity> proveedores = subirAcopioService.verDatos();
        assertTrue(proveedores.contains(subirAcopioEntityPrueba1));
    }

    @Test
    void  test1FindByProveedor(){

        String proveedor = "01001";

        subirAcopioService.crearAcopio("2023/05/01","M","01001", 50);
        subirAcopioService.crearAcopio("2023/05/01","T","01001", 40);
        subirAcopioService.crearAcopio("2023/05/02","M","01002", 45);
        List<SubirAcopioEntity> acopios = subirAcopioService.findByProveedor(proveedor);

        for ( SubirAcopioEntity acopio : acopios){
            assertEquals(acopio.getProveedor(), proveedor);
        }

    }

    @Test
    void test1EliminarData(){

        subirAcopioService.crearAcopio("2023/05/01","M","01001", 50);
        subirAcopioService.crearAcopio("2023/05/01","T","01001", 40);
        subirAcopioService.crearAcopio("2023/05/02","M","01002", 45);
        subirAcopioService.eliminarData();
        List<SubirAcopioEntity> acopios = subirAcopioService.verDatos();
        assertTrue(acopios.isEmpty());

    }
}
