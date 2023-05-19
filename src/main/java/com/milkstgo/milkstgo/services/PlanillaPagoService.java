package com.milkstgo.milkstgo.services;

import com.milkstgo.milkstgo.entities.PlanillaPagoEntity;
import com.milkstgo.milkstgo.entities.ProveedorEntity;
import com.milkstgo.milkstgo.entities.SubirAcopioEntity;
import com.milkstgo.milkstgo.entities.SubirGrasasEntity;
import com.milkstgo.milkstgo.repositories.PlanillaPagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class PlanillaPagoService {

    @Autowired
    PlanillaPagoRepository planillaPagoRepository;

    @Autowired
    SubirAcopioService subirAcopioService;

    @Autowired
    ProveedorService proveedorService;

    @Autowired
    SubirGrasasService subirGrasasService;

    public ArrayList<PlanillaPagoEntity> crearPlanillaPago(){
        ArrayList<String> codigoProveedor = proveedorService.obtenerCodProveedores();
        ArrayList<PlanillaPagoEntity> planillas = new ArrayList<>();

        if (codigoProveedor.isEmpty()){
            return planillas;
        }

        for (String codigo: codigoProveedor){
            planillas.add(planillaPagoRepository.save(calcularPlanilla(codigo)));
        }
        return planillas;
    }

    public PlanillaPagoEntity calcularPlanilla(String codigoProveedor) {

        PlanillaPagoEntity planillaPagoEntity = new PlanillaPagoEntity();
        PlanillaPagoEntity quincenaAnt = new PlanillaPagoEntity();
        ArrayList<PlanillaPagoEntity> quincenasProveedor = traerPlanillaDeProv(codigoProveedor);

        ProveedorEntity proveedor = proveedorService.findByCode(codigoProveedor);
        if (proveedor.getCodigo() == null){
            return null;
        }

        SubirGrasasEntity subirGrasasEntity = subirGrasasService.buscarPorProveedor(codigoProveedor);
        if (subirGrasasEntity.getProveedor() == null){
            return null;
        }

        ArrayList<SubirAcopioEntity> acopios = subirAcopioService.findByProveedor(codigoProveedor);
        if (acopios.isEmpty()) {
            return null;
        }

        if (quincenasProveedor.size() > 0){
            quincenaAnt = quincenasProveedor.get(quincenasProveedor.size() - 1);
        }



        ArrayList<LocalDate> fechas = stringToFecha(acopios);

        planillaPagoEntity.setQuincena(calcQuincena(fechas));
        planillaPagoEntity.setCodigoProv(codigoProveedor);
        planillaPagoEntity.setNombreProv(proveedor.getNombre());
        planillaPagoEntity.setTotalKlsLeche(totalKlsLeche(acopios));
        planillaPagoEntity.setDiasEnvioLeche(diasEnvio(fechas));
        planillaPagoEntity.setPromKlsLeche(promedioKlsLeche(acopios));
        planillaPagoEntity.setVariacionLeche(variacionLeche(planillaPagoEntity.getTotalKlsLeche(), quincenaAnt));
        planillaPagoEntity.setPorcentajeGrasas(subirGrasasEntity.getGrasas());
        planillaPagoEntity.setVariacionGrasas(variacionGrasas(planillaPagoEntity.getPorcentajeGrasas(), quincenaAnt));
        planillaPagoEntity.setSolidosTotales(subirGrasasEntity.getSolidoTotal());
        planillaPagoEntity.setVariacionST(variacionST(planillaPagoEntity.getSolidosTotales(), quincenaAnt));
        planillaPagoEntity.setPagoLeche(pagoPorLeche(proveedor.getCategoria(), planillaPagoEntity.getTotalKlsLeche()));
        planillaPagoEntity.setPagoGrasa(pagoPorGrasa(planillaPagoEntity.getPorcentajeGrasas(), planillaPagoEntity.getTotalKlsLeche()));
        planillaPagoEntity.setPagoST(pagoPorST(planillaPagoEntity.getSolidosTotales(), planillaPagoEntity.getTotalKlsLeche()));
        planillaPagoEntity.setBonoFrecuencia(bonificacion(turnosAcopio(acopios), planillaPagoEntity));
        planillaPagoEntity.setDctoVarLeche(descuentoPorLeche(planillaPagoEntity.getVariacionLeche()));
        planillaPagoEntity.setDctoVarGrasa(descuentoPorGrasa(planillaPagoEntity.getVariacionGrasas()));
        planillaPagoEntity.setDctoVarST(descuentoPorST(planillaPagoEntity.getVariacionST()));
        planillaPagoEntity.setPagoTotal(calcPagoTotal(planillaPagoEntity));
        planillaPagoEntity.setMontoRetencion(calcRetencion(planillaPagoEntity, proveedor));
        planillaPagoEntity.setMontoFinal(pagoFinal(planillaPagoEntity));

        return planillaPagoEntity;
    }

    public ArrayList<PlanillaPagoEntity> traerPlanillaDeProv(String codigoProveedor){
        return planillaPagoRepository.findByCodigo(codigoProveedor);
    }

    public LocalDate calcQuincena(ArrayList<LocalDate> fechas) {
        int quincena;
        int month;
        int year;
        if (!fechas.isEmpty()) {
            if (fechas.get(0).getDayOfMonth() < 15) {
                quincena = 1;
            } else {
                quincena = 2;
            }
            month = fechas.get(0).getMonthValue();
            year = fechas.get(0).getYear();
        }
        else {
            return null;
        }
        return LocalDate.of(year, month, quincena);
    }

    public double promedioKlsLeche(ArrayList<SubirAcopioEntity> acopios){

        double prom = 0;
        for(SubirAcopioEntity acopioEntity : acopios){
            prom = prom + acopioEntity.getKgleche();
        }

        return Math.round(prom / (double) acopios.size());
    }

    public int totalKlsLeche(ArrayList<SubirAcopioEntity> acopios) {
        int totalKls = 0;
        for (SubirAcopioEntity subirAcopioEntity : acopios) {
            totalKls = totalKls + subirAcopioEntity.getKgleche();
        }
        return totalKls;
    }

    public double variacionLeche( Integer totalKls, PlanillaPagoEntity quincenaAnt) {

        if (quincenaAnt.getTotalKlsLeche() == null){
            return 0;
        }
        else{
            return (Math.round((double) ((totalKls - quincenaAnt.getTotalKlsLeche()) / quincenaAnt.getTotalKlsLeche()) * 10000))/100.0;
        }

    }

    public double variacionGrasas( double grasas, PlanillaPagoEntity quincenaAnt) {

        if (quincenaAnt.getPorcentajeGrasas() == null){
            return 0;
        }

        else{
            return (Math.round((grasas - quincenaAnt.getPorcentajeGrasas()) / quincenaAnt.getPorcentajeGrasas() * 10000))/100.0;
        }
    }

    public double variacionST( double solidos, PlanillaPagoEntity quincenaAnt) {

        if (quincenaAnt.getSolidosTotales() == null){
            return 0;
        }
        else {
            return (Math.round((solidos - quincenaAnt.getSolidosTotales()) / quincenaAnt.getSolidosTotales() * 10000)) / 100.0;
        }
    }

    public int pagoPorLeche(String categoria, int totalKlsLeche) {
        int pago = 0;
        switch (categoria) {
            case "A":
                pago = totalKlsLeche * 700;
                break;
            case "B":
                pago = totalKlsLeche * 550;
                break;
            case "C":
                pago = totalKlsLeche * 400;
                break;
            case "D":
                pago = totalKlsLeche * 250;
                break;
        }
        return pago;
    }

    public int pagoPorGrasa(int grasas, int totalKlsLeche) {
        int pago = 0;
        if (grasas >= 0 && grasas <= 20) {
            pago = totalKlsLeche * 30;
        }
        else if (grasas >= 21 && grasas <= 45) {
            pago = totalKlsLeche * 80;
        }
        else if (grasas >= 46) {
            pago = totalKlsLeche * 120;
        }
        return pago;
    }

    public int pagoPorST(int solidosTotales, int totalKlsLeche) {

        int pago = 0;
        if (solidosTotales >= 0 && solidosTotales <= 7) {
            pago = totalKlsLeche * -130;
        }
        else if (solidosTotales >= 8 && solidosTotales <= 18) {
            pago = totalKlsLeche * -90;
        }
        else if (solidosTotales >= 19 && solidosTotales <= 35) {
            pago = totalKlsLeche * 95;
        }
        else if (solidosTotales >= 36) {
            pago = totalKlsLeche * 150;
        }
        return pago;
    }

    public ArrayList<String> turnosAcopio(ArrayList<SubirAcopioEntity> acopios){

        ArrayList<String> turnos = new ArrayList<>();
        for (SubirAcopioEntity acopio : acopios){
            turnos.add(acopio.getTurno());
        }
        return turnos;
    }

    public double bonificacion(ArrayList<String> turnos, PlanillaPagoEntity planillaPagoEntity){

        double bono = 0;
        int manana = Collections.frequency(turnos, "M");
        int tarde = Collections.frequency(turnos, "T");

        if ((manana > 10) && (tarde > 10)){
            bono = 0.2;
        }
        else if(manana > 10){
            bono = 0.12;
        }
        else if(tarde > 10){
            bono = 0.08;
        }
        return (planillaPagoEntity.getPagoST() + planillaPagoEntity.getPagoGrasa() + planillaPagoEntity.getPagoLeche()) * bono;
    }

    public int descuentoPorLeche(double varLeche) {
        int dcto = 0;
        if (varLeche <= -9 && varLeche >= -25) {
            dcto = 7;
        }
        else if(varLeche <= -26 && varLeche >= -45) {
            dcto = 15;
        }
        else if(varLeche <= -46) {
            dcto = 30;
        }
        return dcto;
    }

    public int descuentoPorGrasa(double varGrasa) {
        int dcto = 0;
        if (varGrasa <= -16 && varGrasa >= -25) {
            dcto = 12;
        }
        else if(varGrasa <= -26 && varGrasa >= -40) {
            dcto = 20;
        }
        else if(varGrasa <= -41) {
            dcto = 30;
        }
        return dcto;
    }

    public int descuentoPorST(double varST) {
        int dcto = 0;
        if (varST <= -7 && varST >= -12) {
            dcto = 18;
        }
        else if(varST <= -13 && varST >= -35) {
            dcto = 27;
        }
        else if(varST <= -36) {
            dcto = 45;
        }
        return dcto;
    }

    public int calcPagoTotal(PlanillaPagoEntity planillaPagoEntity){

        double pagoTotal;
        double descuentos;

        pagoTotal = planillaPagoEntity.getPagoLeche() + planillaPagoEntity.getPagoGrasa() + planillaPagoEntity.getPagoST() + planillaPagoEntity.getBonoFrecuencia();
        descuentos = (planillaPagoEntity.getDctoVarLeche() + planillaPagoEntity.getDctoVarGrasa() + planillaPagoEntity.getDctoVarST())/ 100.0;

        return ((int) (pagoTotal - (pagoTotal * descuentos)));
    }

    public int calcRetencion(PlanillaPagoEntity planillaPagoEntity, ProveedorEntity proveedor){

        double retencion = 0.0;

        if ((proveedor.getAfecto().equals("Si")) && (planillaPagoEntity.getPagoTotal() > 950000)){
            retencion = 0.13;
        }

        return (int) (planillaPagoEntity.getPagoTotal() * retencion);
    }

    public int pagoFinal(PlanillaPagoEntity planillaPagoEntity){
        return (planillaPagoEntity.getPagoTotal() - planillaPagoEntity.getMontoRetencion());
    }

    public ArrayList<LocalDate> stringToFecha(ArrayList<SubirAcopioEntity> subirAcopioEntities) {
        ArrayList<LocalDate> fechas = new ArrayList<>();
        for (SubirAcopioEntity acopioLecheEntity : subirAcopioEntities) {
            fechas.add(acopioLecheEntity.getFecha());
        }
        return fechas;
    }

    public int diasEnvio(ArrayList<LocalDate> fechas) {

        Set<LocalDate> set = new LinkedHashSet<>(fechas);
        ArrayList<LocalDate> fechasSinRepetir = new ArrayList<>(set);
        return fechasSinRepetir.size();
    }

    public ArrayList<PlanillaPagoEntity> traerPlanilla(){
        return planillaPagoRepository.findAll();
    }
}
