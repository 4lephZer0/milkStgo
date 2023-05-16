package com.milkstgo.milkstgo.entities;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Acopio")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubirAcopioEntity {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;
    private String fecha;
    private String turno;
    private String proveedor;
    private String kgleche;
}
