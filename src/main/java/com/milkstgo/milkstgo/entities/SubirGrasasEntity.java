package com.milkstgo.milkstgo.entities;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ValoresGrasas")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubirGrasasEntity {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;
    private String proveedor;
    private Integer grasas;
    private Integer solidoTotal;
}