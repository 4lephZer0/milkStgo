package com.milkstgo.milkstgo;

import com.milkstgo.milkstgo.entities.SubirGrasasEntity;
import com.milkstgo.milkstgo.services.SubirGrasasService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SubirGrasasTests {

    @Autowired
    SubirGrasasService subirGrasasService;

    @Test
    void test1CrearGrasas() {

        String proveedor = "01001";
        int grasas = 20;
        int solidos = 50;

        SubirGrasasEntity subirGrasasEntityPrueba1 = new SubirGrasasEntity(null, proveedor, grasas, solidos);
        SubirGrasasEntity subirGrasasEntityPruebaB = subirGrasasService.crearGrasas("01001",20, 50);

        assertEquals(subirGrasasEntityPrueba1.getProveedor(),subirGrasasEntityPruebaB.getProveedor());
        assertEquals(subirGrasasEntityPrueba1.getGrasas(), subirGrasasEntityPruebaB.getGrasas());
        assertEquals(subirGrasasEntityPrueba1.getSolidoTotal(), subirGrasasEntityPruebaB.getSolidoTotal());
    }

    @Test
    void test1BuscarPorProveedor(){

        String proveedor = "01001";

        subirGrasasService.crearGrasas("01001",30,50);
        subirGrasasService.crearGrasas("01002",17,45);
        SubirGrasasEntity grasas = subirGrasasService.buscarPorProveedor(proveedor);
        assertEquals(grasas.getProveedor(), proveedor);
    }

    @Test
    void test1EliminarData(){

        subirGrasasService.crearGrasas("01001",30,50);
        subirGrasasService.crearGrasas("01002",17,45);
        subirGrasasService.crearGrasas("01003",10,20);
        subirGrasasService.eliminarData();
        ArrayList<SubirGrasasEntity> grasas = subirGrasasService.verGrasas();
        assertTrue(grasas.isEmpty());

    }
}
