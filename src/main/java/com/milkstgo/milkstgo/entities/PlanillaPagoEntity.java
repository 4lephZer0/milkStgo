package com.milkstgo.milkstgo.entities;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "planillaPago")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlanillaPagoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer ID;
    LocalDate quincena;
    String codigoProv;
    String nombreProv;
    Integer totalKlsLeche;
    Integer diasEnvioLeche;
    double promKlsLeche;
    double variacionLeche;
    Integer porcentajeGrasas;
    double variacionGrasas;
    Integer solidosTotales;
    double variacionST;
    Integer pagoLeche;
    Integer pagoGrasa;
    Integer pagoST;
    double bonoFrecuencia;
    Integer dctoVarLeche;
    Integer dctoVarGrasa;
    Integer dctoVarST;
    Integer pagoTotal;
    Integer montoRetencion;
    Integer montoFinal;
}

