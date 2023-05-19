package com.milkstgo.milkstgo;

import com.milkstgo.milkstgo.entities.PlanillaPagoEntity;
import com.milkstgo.milkstgo.entities.ProveedorEntity;
import com.milkstgo.milkstgo.entities.SubirAcopioEntity;
import com.milkstgo.milkstgo.services.PlanillaPagoService;
import com.milkstgo.milkstgo.services.ProveedorService;
import com.milkstgo.milkstgo.services.SubirAcopioService;
import com.milkstgo.milkstgo.services.SubirGrasasService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PlanillaPagoTests {

    @Autowired
    PlanillaPagoService planillaPagoService;

    @Autowired
    SubirAcopioService subirAcopioService;

    @Autowired
    ProveedorService proveedorService;

    @Autowired
    SubirGrasasService subirGrasasService;

    @Test
    void test1CalcQuincena(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String fechaStr1 = "2023/05/01";
        String fechaStr2 = "2023/05/02";
        String fechaStr3 = "2023/05/03";
        String fechaStr4 = "2023/05/04";
        LocalDate fecha1 = LocalDate.parse(fechaStr1, formatter);
        LocalDate fecha2 = LocalDate.parse(fechaStr2, formatter);
        LocalDate fecha3 = LocalDate.parse(fechaStr3, formatter);
        LocalDate fecha4 = LocalDate.parse(fechaStr4, formatter);
        LocalDate fechaPrueba = LocalDate.of(2023, 5, 1);

        ArrayList<LocalDate> fechas = new ArrayList<>();
        fechas.add(fecha1);
        fechas.add(fecha2);
        fechas.add(fecha3);
        fechas.add(fecha4);

        LocalDate quincena = planillaPagoService.calcQuincena(fechas);
        assertEquals(quincena, fechaPrueba);
    }

    @Test
    void test1PromedioKlsLeche(){

        SubirAcopioEntity subirAcopioEntity1 = subirAcopioService.crearAcopio("2023/05/01","M","01001", 50);
        SubirAcopioEntity subirAcopioEntity2 = subirAcopioService.crearAcopio("2023/05/02","T","01001", 40);
        SubirAcopioEntity subirAcopioEntity3 = subirAcopioService.crearAcopio("2023/05/03","M","01001", 45);

        ArrayList<SubirAcopioEntity> acopios = new ArrayList<>();
        acopios.add(subirAcopioEntity1);
        acopios.add(subirAcopioEntity2);
        acopios.add(subirAcopioEntity3);

        double promedioTest = planillaPagoService.promedioKlsLeche(acopios);

        assertEquals(45.00, promedioTest, 0.0);
    }

    @Test
    void test1TotalKlsLeche(){

        SubirAcopioEntity subirAcopioEntity1 = subirAcopioService.crearAcopio("2023/05/01","M","01001", 50);
        SubirAcopioEntity subirAcopioEntity2 = subirAcopioService.crearAcopio("2023/05/02","T","01001", 40);
        SubirAcopioEntity subirAcopioEntity3 = subirAcopioService.crearAcopio("2023/05/03","M","01001", 45);

        ArrayList<SubirAcopioEntity> acopios = new ArrayList<>();
        acopios.add(subirAcopioEntity1);
        acopios.add(subirAcopioEntity2);
        acopios.add(subirAcopioEntity3);

        double totalklsTest = planillaPagoService.totalKlsLeche(acopios);

        assertEquals(135.00, totalklsTest, 0.0);
    }

    @Test
    void test1VariacionLeche(){

        int totalkls = 100;

        LocalDate quincena = LocalDate.of(2023, 5, 1);
        PlanillaPagoEntity planillaPagoAnt = new PlanillaPagoEntity(null, quincena, "01001", "Pepe", 50, 11, 18.2,
                0, 10, 0, 30, 0,
                35000, 1500, 1500, 4560.0, 0, 0,
                0, 42560, 0, 42560);

        double varLecheTest = planillaPagoService.variacionLeche(totalkls, planillaPagoAnt);

        assertEquals(100.00, varLecheTest, 0.0);
    }

    @Test
    void test1VariacionGrasas(){

        int porcGrasas = 20;

        LocalDate quincena = LocalDate.of(2023, 5, 1);
        PlanillaPagoEntity planillaPagoAnt = new PlanillaPagoEntity(null, quincena, "01001", "Pepe", 50, 11, 18.2,
                0, 10, 0, 30, 0,
                35000, 1500, 1500, 4560.0, 0, 0,
                0, 42560, 0, 42560);

        double varGrasasTest = planillaPagoService.variacionGrasas(porcGrasas, planillaPagoAnt);

        assertEquals(100.00, varGrasasTest, 0.0);
    }

    @Test
    void test1VariacionST(){

        int porcST = 20;

        LocalDate quincena = LocalDate.of(2023, 5, 1);
        PlanillaPagoEntity planillaPagoAnt = new PlanillaPagoEntity(null, quincena, "01001", "Pepe", 50, 11, 18.2,
                0, 10, 0, 30, 0,
                35000, 1500, 1500, 4560.0, 0, 0,
                0, 42560, 0, 42560);

        double varStTest = planillaPagoService.variacionST(porcST, planillaPagoAnt);
        assertEquals(-33.33, varStTest, 0.0);
    }
    @Test
    void test1PagoPorLeche(){

        String categoria = "B";

        LocalDate quincena = LocalDate.of(2023, 5, 1);
        PlanillaPagoEntity planillaPagoAnt = new PlanillaPagoEntity(null, quincena, "01001", "Pepe", 50, 11, 18.2,
                0, 10, 0, 30, 0,
                35000, 1500, 1500, 4560.0, 0, 0,
                0, 42560, 0, 42560);

        int pagoPorLecheTest = planillaPagoService.pagoPorLeche(categoria, planillaPagoAnt.getTotalKlsLeche());
        assertEquals(27500, pagoPorLecheTest, 0.0);

    }

    @Test
    void test1PagoPorGrasa(){

        LocalDate quincena = LocalDate.of(2023, 5, 1);
        PlanillaPagoEntity planillaPagoAnt = new PlanillaPagoEntity(null, quincena, "01001", "Pepe", 50, 11, 18.2,
                0, 10, 0, 30, 0,
                35000, 1500, 1500, 4560.0, 0, 0,
                0, 42560, 0, 42560);

        int pagoPorGrasaTest = planillaPagoService.pagoPorGrasa(planillaPagoAnt.getPorcentajeGrasas(), planillaPagoAnt.getTotalKlsLeche());
        assertEquals(1500, pagoPorGrasaTest, 0.0);

    }

    @Test
    void test1PagoPorSt(){

        LocalDate quincena = LocalDate.of(2023, 5, 1);
        PlanillaPagoEntity planillaPagoAnt = new PlanillaPagoEntity(null, quincena, "01001", "Pepe", 50, 11, 18.2,
                0, 10, 0, 30, 0,
                35000, 1500, 4750, 4560.0, 0, 0,
                0, 42560, 0, 42560);

        int pagoPorStTest = planillaPagoService.pagoPorST(planillaPagoAnt.getSolidosTotales(), planillaPagoAnt.getTotalKlsLeche());
        assertEquals(4750, pagoPorStTest, 0.0);

    }

    @Test
    void test1TurnosAcopio(){

        SubirAcopioEntity subirAcopioEntity1 = subirAcopioService.crearAcopio("2023/05/01","M","01001", 50);
        SubirAcopioEntity subirAcopioEntity2 = subirAcopioService.crearAcopio("2023/05/02","M","01001", 40);
        SubirAcopioEntity subirAcopioEntity3 = subirAcopioService.crearAcopio("2023/05/03","M","01001", 45);
        SubirAcopioEntity subirAcopioEntity4 = subirAcopioService.crearAcopio("2023/05/02","M","01001", 40);
        SubirAcopioEntity subirAcopioEntity5 = subirAcopioService.crearAcopio("2023/05/03","M","01001", 45);
        SubirAcopioEntity subirAcopioEntity6 = subirAcopioService.crearAcopio("2023/05/02","M","01001", 40);
        SubirAcopioEntity subirAcopioEntity7 = subirAcopioService.crearAcopio("2023/05/03","M","01001", 45);
        SubirAcopioEntity subirAcopioEntity8 = subirAcopioService.crearAcopio("2023/05/02","M","01001", 40);
        SubirAcopioEntity subirAcopioEntity9 = subirAcopioService.crearAcopio("2023/05/03","M","01001", 45);
        SubirAcopioEntity subirAcopioEntity10 = subirAcopioService.crearAcopio("2023/05/02","M","01001", 40);
        SubirAcopioEntity subirAcopioEntity11 = subirAcopioService.crearAcopio("2023/05/03","M","01001", 45);
        SubirAcopioEntity subirAcopioEntity12 = subirAcopioService.crearAcopio("2023/05/02","T","01001", 40);
        SubirAcopioEntity subirAcopioEntity13 = subirAcopioService.crearAcopio("2023/05/03","T","01001", 45);
        SubirAcopioEntity subirAcopioEntity14 = subirAcopioService.crearAcopio("2023/05/02","T","01001", 40);
        SubirAcopioEntity subirAcopioEntity15 = subirAcopioService.crearAcopio("2023/05/03","T","01001", 45);

        ArrayList<SubirAcopioEntity> acopios = new ArrayList<>();
        acopios.add(subirAcopioEntity1);
        acopios.add(subirAcopioEntity2);
        acopios.add(subirAcopioEntity3);
        acopios.add(subirAcopioEntity4);
        acopios.add(subirAcopioEntity5);
        acopios.add(subirAcopioEntity6);
        acopios.add(subirAcopioEntity7);
        acopios.add(subirAcopioEntity8);
        acopios.add(subirAcopioEntity9);
        acopios.add(subirAcopioEntity10);
        acopios.add(subirAcopioEntity11);
        acopios.add(subirAcopioEntity12);
        acopios.add(subirAcopioEntity13);
        acopios.add(subirAcopioEntity14);
        acopios.add(subirAcopioEntity15);

        ArrayList<String> turnosTest = planillaPagoService.turnosAcopio(acopios);
        ArrayList<String> turnos = new ArrayList<>();

        turnos.add("M");
        turnos.add("M");
        turnos.add("M");
        turnos.add("M");
        turnos.add("M");
        turnos.add("M");
        turnos.add("M");
        turnos.add("M");
        turnos.add("M");
        turnos.add("M");
        turnos.add("M");
        turnos.add("T");
        turnos.add("T");
        turnos.add("T");
        turnos.add("T");

        assertEquals(turnos, turnosTest);
    }

    @Test
    void test1Bonificacion(){

        SubirAcopioEntity subirAcopioEntity1 = subirAcopioService.crearAcopio("2023/05/01","M","01001", 50);
        SubirAcopioEntity subirAcopioEntity2 = subirAcopioService.crearAcopio("2023/05/02","M","01001", 40);
        SubirAcopioEntity subirAcopioEntity3 = subirAcopioService.crearAcopio("2023/05/03","M","01001", 45);
        SubirAcopioEntity subirAcopioEntity4 = subirAcopioService.crearAcopio("2023/05/02","M","01001", 40);
        SubirAcopioEntity subirAcopioEntity5 = subirAcopioService.crearAcopio("2023/05/03","M","01001", 45);
        SubirAcopioEntity subirAcopioEntity6 = subirAcopioService.crearAcopio("2023/05/02","M","01001", 40);
        SubirAcopioEntity subirAcopioEntity7 = subirAcopioService.crearAcopio("2023/05/03","M","01001", 45);
        SubirAcopioEntity subirAcopioEntity8 = subirAcopioService.crearAcopio("2023/05/02","M","01001", 40);
        SubirAcopioEntity subirAcopioEntity9 = subirAcopioService.crearAcopio("2023/05/03","M","01001", 45);
        SubirAcopioEntity subirAcopioEntity10 = subirAcopioService.crearAcopio("2023/05/02","M","01001", 40);
        SubirAcopioEntity subirAcopioEntity11 = subirAcopioService.crearAcopio("2023/05/03","M","01001", 45);
        SubirAcopioEntity subirAcopioEntity12 = subirAcopioService.crearAcopio("2023/05/02","T","01001", 40);
        SubirAcopioEntity subirAcopioEntity13 = subirAcopioService.crearAcopio("2023/05/03","T","01001", 45);
        SubirAcopioEntity subirAcopioEntity14 = subirAcopioService.crearAcopio("2023/05/02","T","01001", 40);
        SubirAcopioEntity subirAcopioEntity15 = subirAcopioService.crearAcopio("2023/05/03","T","01001", 45);

        ArrayList<SubirAcopioEntity> acopios = new ArrayList<>();
        acopios.add(subirAcopioEntity1);
        acopios.add(subirAcopioEntity2);
        acopios.add(subirAcopioEntity3);
        acopios.add(subirAcopioEntity4);
        acopios.add(subirAcopioEntity5);
        acopios.add(subirAcopioEntity6);
        acopios.add(subirAcopioEntity7);
        acopios.add(subirAcopioEntity8);
        acopios.add(subirAcopioEntity9);
        acopios.add(subirAcopioEntity10);
        acopios.add(subirAcopioEntity11);
        acopios.add(subirAcopioEntity12);
        acopios.add(subirAcopioEntity13);
        acopios.add(subirAcopioEntity14);
        acopios.add(subirAcopioEntity15);

        LocalDate quincena = LocalDate.of(2023, 5, 1);
        PlanillaPagoEntity planillaPago = new PlanillaPagoEntity(null, quincena, "01001", "Pepe", 50, 11, 18.2,
                0, 10, 0, 30, 0,
                35000, 1500, 4750, 4560.0, 0, 0,
                0, 42560, 0, 42560);

        double bonoTest = planillaPagoService.bonificacion(planillaPagoService.turnosAcopio(acopios), planillaPago);
        assertEquals(4950.0, bonoTest, 0.0);
    }
    @Test
    void test1DctoLeche(){

        LocalDate quincena = LocalDate.of(2023, 5, 1);
        PlanillaPagoEntity planillaPago = new PlanillaPagoEntity(null, quincena, "01001", "Pepe", 50, 11, 18.2,
                -10, 10, 0, 30, 0,
                35000, 1500, 4750, 4560.0, 0, 0,
                0, 42560, 0, 42560);

        double dctoLecheTest = planillaPagoService.descuentoPorLeche(planillaPago.getVariacionLeche());
        assertEquals(7, dctoLecheTest, 0.0);
    }

    @Test
    void test1DctoGrasas(){

        LocalDate quincena = LocalDate.of(2023, 5, 1);
        PlanillaPagoEntity planillaPago = new PlanillaPagoEntity(null, quincena, "01001", "Pepe", 50, 11, 18.2,
                -10, 10, -20, 30, 0,
                27500, 1500, 4750, 4560.0, 0, 0,
                0, 42560, 0, 42560);

        double dctoGrasasTest = planillaPagoService.descuentoPorGrasa(planillaPago.getVariacionGrasas());
        assertEquals(12, dctoGrasasTest, 0.0);
    }

    @Test
    void test1DctoST(){

        LocalDate quincena = LocalDate.of(2023, 5, 1);
        PlanillaPagoEntity planillaPago = new PlanillaPagoEntity(null, quincena, "01001", "Pepe", 50, 11, 18.2,
                -10, 10, -20, 30, -8,
                35000, 1500, 4750, 4560.0, 0, 0,
                0, 42560, 0, 42560);

        double dctoStTest = planillaPagoService.descuentoPorST(planillaPago.getVariacionST());
        assertEquals(18, dctoStTest, 0.0);
    }

    @Test
    void test1PagoTotal(){

        LocalDate quincena = LocalDate.of(2023, 5, 1);
        PlanillaPagoEntity planillaPago = new PlanillaPagoEntity(null, quincena, "01001", "Pepe", 50, 11, 18.2,
                -10, 10, -20, 30, -8,
                27500, 1500, 4750, 4950.0, 7, 12,
                18, 0, 0, 0);

        double pagoTotalTest = planillaPagoService.calcPagoTotal(planillaPago);
        assertEquals(24381, pagoTotalTest, 0.0);
    }

    @Test
    void test1CalcRetencion(){

        ProveedorEntity proveedorPrueba = new ProveedorEntity("01001", "Pepe", "B", "Si");

        LocalDate quincena = LocalDate.of(2023, 5, 1);
        PlanillaPagoEntity planillaPago = new PlanillaPagoEntity(null, quincena, "01001", "Pepe", 50, 11, 18.2,
                -10, 10, -20, 30, -8,
                27500, 1500, 4750, 4950.0, 7, 12,
                18, 24381, 0, 0);

        double retencionTest = planillaPagoService.calcRetencion(planillaPago, proveedorPrueba);
        assertEquals(0, retencionTest, 0.0);
    }

    @Test
    void test1PagoFinal(){

        LocalDate quincena = LocalDate.of(2023, 5, 1);
        PlanillaPagoEntity planillaPago = new PlanillaPagoEntity(null, quincena, "01001", "Pepe", 50, 11, 18.2,
                -10, 10, -20, 30, -8,
                27500, 1500, 4750, 4950.0, 7, 12,
                18, 24381, 0, 0);

        double pagoTotalTest = planillaPagoService.pagoFinal(planillaPago);
        assertEquals(24381, pagoTotalTest, 0.0);
    }

    @Test
    void test1stringToFecha(){

        ArrayList<LocalDate> fechas = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String fechaStr1 = "2023/05/01";
        String fechaStr2 = "2023/05/02";
        String fechaStr3 = "2023/05/03";
        String fechaStr4 = "2023/05/02";
        String fechaStr5 = "2023/05/03";
        String fechaStr6 = "2023/05/02";
        String fechaStr7 = "2023/05/03";
        String fechaStr8 = "2023/05/02";
        String fechaStr9 = "2023/05/03";
        String fechaStr10 = "2023/05/02";
        String fechaStr11 = "2023/05/03";
        String fechaStr12 = "2023/05/02";
        String fechaStr13 = "2023/05/03";
        String fechaStr14 = "2023/05/02";
        String fechaStr15 = "2023/05/03";

        fechas.add(LocalDate.parse(fechaStr1, formatter));
        fechas.add(LocalDate.parse(fechaStr2, formatter));
        fechas.add(LocalDate.parse(fechaStr3, formatter));
        fechas.add(LocalDate.parse(fechaStr4, formatter));
        fechas.add(LocalDate.parse(fechaStr5, formatter));
        fechas.add(LocalDate.parse(fechaStr6, formatter));
        fechas.add(LocalDate.parse(fechaStr7, formatter));
        fechas.add(LocalDate.parse(fechaStr8, formatter));
        fechas.add(LocalDate.parse(fechaStr9, formatter));
        fechas.add(LocalDate.parse(fechaStr10, formatter));
        fechas.add(LocalDate.parse(fechaStr11, formatter));
        fechas.add(LocalDate.parse(fechaStr12, formatter));
        fechas.add(LocalDate.parse(fechaStr13, formatter));
        fechas.add(LocalDate.parse(fechaStr14, formatter));
        fechas.add(LocalDate.parse(fechaStr15, formatter));

        SubirAcopioEntity subirAcopioEntity1 = subirAcopioService.crearAcopio("2023/05/01","M","01001", 50);
        SubirAcopioEntity subirAcopioEntity2 = subirAcopioService.crearAcopio("2023/05/02","M","01001", 40);
        SubirAcopioEntity subirAcopioEntity3 = subirAcopioService.crearAcopio("2023/05/03","M","01001", 45);
        SubirAcopioEntity subirAcopioEntity4 = subirAcopioService.crearAcopio("2023/05/02","M","01001", 40);
        SubirAcopioEntity subirAcopioEntity5 = subirAcopioService.crearAcopio("2023/05/03","M","01001", 45);
        SubirAcopioEntity subirAcopioEntity6 = subirAcopioService.crearAcopio("2023/05/02","M","01001", 40);
        SubirAcopioEntity subirAcopioEntity7 = subirAcopioService.crearAcopio("2023/05/03","M","01001", 45);
        SubirAcopioEntity subirAcopioEntity8 = subirAcopioService.crearAcopio("2023/05/02","M","01001", 40);
        SubirAcopioEntity subirAcopioEntity9 = subirAcopioService.crearAcopio("2023/05/03","M","01001", 45);
        SubirAcopioEntity subirAcopioEntity10 = subirAcopioService.crearAcopio("2023/05/02","M","01001", 40);
        SubirAcopioEntity subirAcopioEntity11 = subirAcopioService.crearAcopio("2023/05/03","M","01001", 45);
        SubirAcopioEntity subirAcopioEntity12 = subirAcopioService.crearAcopio("2023/05/02","T","01001", 40);
        SubirAcopioEntity subirAcopioEntity13 = subirAcopioService.crearAcopio("2023/05/03","T","01001", 45);
        SubirAcopioEntity subirAcopioEntity14 = subirAcopioService.crearAcopio("2023/05/02","T","01001", 40);
        SubirAcopioEntity subirAcopioEntity15 = subirAcopioService.crearAcopio("2023/05/03","T","01001", 45);

        ArrayList<SubirAcopioEntity> acopios = new ArrayList<>();
        acopios.add(subirAcopioEntity1);
        acopios.add(subirAcopioEntity2);
        acopios.add(subirAcopioEntity3);
        acopios.add(subirAcopioEntity4);
        acopios.add(subirAcopioEntity5);
        acopios.add(subirAcopioEntity6);
        acopios.add(subirAcopioEntity7);
        acopios.add(subirAcopioEntity8);
        acopios.add(subirAcopioEntity9);
        acopios.add(subirAcopioEntity10);
        acopios.add(subirAcopioEntity11);
        acopios.add(subirAcopioEntity12);
        acopios.add(subirAcopioEntity13);
        acopios.add(subirAcopioEntity14);
        acopios.add(subirAcopioEntity15);

        ArrayList<LocalDate> stringToFechaTest = planillaPagoService.stringToFecha(acopios);

        assertEquals(fechas, stringToFechaTest);
    }

    @Test
    void test1DiasEnvio(){

        ArrayList<LocalDate> fechas = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String fechaStr1 = "2023/05/01";
        String fechaStr2 = "2023/05/02";
        String fechaStr3 = "2023/05/03";
        String fechaStr4 = "2023/05/03";
        String fechaStr5 = "2023/05/04";
        String fechaStr6 = "2023/05/04";
        String fechaStr7 = "2023/05/05";
        String fechaStr8 = "2023/05/05";
        String fechaStr9 = "2023/05/06";
        String fechaStr10 = "2023/05/06";
        String fechaStr11 = "2023/05/07";
        String fechaStr12 = "2023/05/07";
        String fechaStr13 = "2023/05/08";
        String fechaStr14 = "2023/05/08";
        String fechaStr15 = "2023/05/09";

        fechas.add(LocalDate.parse(fechaStr1, formatter));
        fechas.add(LocalDate.parse(fechaStr2, formatter));
        fechas.add(LocalDate.parse(fechaStr3, formatter));
        fechas.add(LocalDate.parse(fechaStr4, formatter));
        fechas.add(LocalDate.parse(fechaStr5, formatter));
        fechas.add(LocalDate.parse(fechaStr6, formatter));
        fechas.add(LocalDate.parse(fechaStr7, formatter));
        fechas.add(LocalDate.parse(fechaStr8, formatter));
        fechas.add(LocalDate.parse(fechaStr9, formatter));
        fechas.add(LocalDate.parse(fechaStr10, formatter));
        fechas.add(LocalDate.parse(fechaStr11, formatter));
        fechas.add(LocalDate.parse(fechaStr12, formatter));
        fechas.add(LocalDate.parse(fechaStr13, formatter));
        fechas.add(LocalDate.parse(fechaStr14, formatter));
        fechas.add(LocalDate.parse(fechaStr15, formatter));

        int diasEnvioTest = planillaPagoService.diasEnvio(fechas);

        assertEquals(9, diasEnvioTest, 0.0);
    }

    @Test
    void test1CrearPlanilla(){

        subirAcopioService.crearAcopio("2023/05/01","M","01001", 50);
        proveedorService.crearProveedor("01001", "Pepe", "B", "Si");
        subirGrasasService.crearGrasas("01001", 10,30);

        planillaPagoService.crearPlanillaPago();

        ArrayList<PlanillaPagoEntity> planillaPagoTest = planillaPagoService.traerPlanilla();

        LocalDate quincena = LocalDate.of(2023, 5, 1);
        PlanillaPagoEntity planillaPago = new PlanillaPagoEntity(null, quincena, "01001", "Pepe", 50, 1, 50,
                0, 10, 0, 30, 0,
                27500, 1500, 4750, 0, 0, 0,
                0, 33750, 0, 33750);

        assertEquals(planillaPago.getQuincena(), planillaPagoTest.get(0).getQuincena());
        assertEquals(planillaPago.getCodigoProv(), planillaPagoTest.get(0).getCodigoProv());
        assertEquals(planillaPago.getNombreProv(), planillaPagoTest.get(0).getNombreProv());
        assertEquals(planillaPago.getTotalKlsLeche(), planillaPagoTest.get(0).getTotalKlsLeche());
        assertEquals(planillaPago.getDiasEnvioLeche(), planillaPagoTest.get(0).getDiasEnvioLeche());
        assertEquals(planillaPago.getPromKlsLeche(), planillaPagoTest.get(0).getPromKlsLeche());
        assertEquals(planillaPago.getVariacionLeche(), planillaPagoTest.get(0).getVariacionLeche());
        assertEquals(planillaPago.getPorcentajeGrasas(), planillaPagoTest.get(0).getPorcentajeGrasas());
        assertEquals(planillaPago.getVariacionGrasas(), planillaPagoTest.get(0).getVariacionLeche());
        assertEquals(planillaPago.getSolidosTotales(), planillaPagoTest.get(0).getSolidosTotales());
        assertEquals(planillaPago.getVariacionST(), planillaPagoTest.get(0).getVariacionST());
        assertEquals(planillaPago.getPagoLeche(), planillaPagoTest.get(0).getPagoLeche());
        assertEquals(planillaPago.getPagoGrasa(), planillaPagoTest.get(0).getPagoGrasa());
        assertEquals(planillaPago.getPagoST(), planillaPagoTest.get(0).getPagoST());
        assertEquals(planillaPago.getBonoFrecuencia(), planillaPagoTest.get(0).getBonoFrecuencia());
        assertEquals(planillaPago.getDctoVarLeche(), planillaPagoTest.get(0).getDctoVarLeche());
        assertEquals(planillaPago.getDctoVarGrasa(), planillaPagoTest.get(0).getDctoVarGrasa());
        assertEquals(planillaPago.getVariacionST(), planillaPagoTest.get(0).getVariacionST());
        assertEquals(planillaPago.getPagoTotal(), planillaPagoTest.get(0).getPagoTotal());
        assertEquals(planillaPago.getMontoRetencion(), planillaPagoTest.get(0).getMontoRetencion());
        assertEquals(planillaPago.getMontoFinal(), planillaPagoTest.get(0).getMontoFinal());
    }
}
