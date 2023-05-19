package com.milkstgo.milkstgo.entities;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Acopio")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubirAcopioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer ID;
    private LocalDate fecha;
    private String turno;
    private String proveedor;
    private Integer kgleche;
}
