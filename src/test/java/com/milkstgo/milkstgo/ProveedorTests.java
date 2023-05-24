package com.milkstgo.milkstgo;

import com.milkstgo.milkstgo.entities.ProveedorEntity;
import com.milkstgo.milkstgo.entities.SubirAcopioEntity;
import com.milkstgo.milkstgo.services.ProveedorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProveedorTests {

    @Autowired
    ProveedorService proveedorService;

    @Test
    void test1CrearProveedor() {

        String codigo = "01001";
        String nombre = "Pepe";
        String categoria = "B";
        String afecto = "Si";

        ProveedorEntity proveedorPrueba = new ProveedorEntity(codigo, nombre, categoria, afecto);

        ProveedorEntity proveedorPruebaA = proveedorService.crearProveedor(codigo, nombre, categoria, afecto);

        assertEquals(proveedorPrueba.getCodigo(), proveedorPruebaA.getCodigo());
        assertEquals(proveedorPrueba.getNombre(), proveedorPruebaA.getNombre());
        assertEquals(proveedorPrueba.getCategoria(), proveedorPruebaA.getCategoria());
        assertEquals(proveedorPrueba.getAfecto(), proveedorPruebaA.getAfecto());

    }

    @Test
    void  test1TraerProveedores(){

        ProveedorEntity proveedorPrueba = proveedorService.crearProveedor("01002", "Michelle", "A", "No");
        ProveedorEntity proveedorPrueba2 = proveedorService.crearProveedor("01003", "Lucho", "B", "Si");
        ProveedorEntity proveedorPrueba3 = proveedorService.crearProveedor("01004", "Juan", "C", "No");
        ProveedorEntity proveedorPrueba4 = proveedorService.crearProveedor("01005", "Alex", "D", "Si");
        List<ProveedorEntity> proveedores = proveedorService.traerProveedores();

        assertTrue(proveedores.contains(proveedorPrueba));
        assertTrue(proveedores.contains(proveedorPrueba2));
        assertTrue(proveedores.contains(proveedorPrueba3));
        assertTrue(proveedores.contains(proveedorPrueba4));
    }

    @Test
    void  test1ObtenerCodProveedores(){

        ProveedorEntity proveedorPrueba = proveedorService.crearProveedor("01003", "Lucho", "C", "No");
        List<String> codigos = proveedorService.obtenerCodProveedores();

        assertTrue(codigos.contains(proveedorPrueba.getCodigo()));
    }

    @Test
    void test1FindByCode(){

        ProveedorEntity proveedorPrueba = proveedorService.crearProveedor("01004", "Juan", "D", "Si");
        ProveedorEntity proveedor = proveedorService.findByCode("01004");
        assertEquals(proveedorPrueba, proveedor);
    }

    @Test
    void test1BorrarProveedores(){

        proveedorService.crearProveedor("01001","Pepe","B", "Si");
        proveedorService.borrarProveedores();
        List<ProveedorEntity> proveedores = proveedorService.traerProveedores();
        assertTrue(proveedores.isEmpty());
    }
}
