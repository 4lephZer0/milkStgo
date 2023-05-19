package com.milkstgo.milkstgo.entities;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer ID;
    private String proveedor;
    private Integer grasas;
    private Integer solidoTotal;
}