package com.milkstgo.milkstgo.services;

import com.milkstgo.milkstgo.entities.ProveedorEntity;
import com.milkstgo.milkstgo.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class ProveedorService {

    @Autowired
    ProveedorRepository proveedorRepository;

    public ProveedorEntity crearProveedor(String codigo, String nombre, String categoria, String afecto){

        ProveedorEntity proveedorEntity = new ProveedorEntity();
        proveedorEntity.setCodigo(codigo);
        proveedorEntity.setNombre(nombre);
        proveedorEntity.setCategoria(categoria);
        proveedorEntity.setAfecto(afecto);
        return proveedorRepository.save(proveedorEntity);
    }

    public ArrayList<ProveedorEntity> traerProveedores(){
        return (ArrayList<ProveedorEntity>) proveedorRepository.findAll();
    }

    public ArrayList<String> obtenerCodProveedores(){
        return proveedorRepository.findAllCodigo();
    }

    public ProveedorEntity findByCode(String codigo) {return proveedorRepository.findByCodigo(codigo); }

    public void borrarProveedores(){proveedorRepository.deleteAll();}
}
