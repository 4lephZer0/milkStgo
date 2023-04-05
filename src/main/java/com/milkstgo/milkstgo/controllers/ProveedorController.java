package com.milkstgo.milkstgo.controllers;

import com.milkstgo.milkstgo.entities.ProveedorEntity;
import com.milkstgo.milkstgo.services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping
public class ProveedorController {

    @Autowired
    ProveedorService proveedorService;

    @GetMapping("/listar-proveedores")
    public String listar(Model model){
        ArrayList<ProveedorEntity> proveedores = proveedorService.traerProveedores();
        model.addAttribute("proveedores", proveedores);
        return "index";
    }

    @PutMapping("/ingresar-proveedor")
    public String ingresarProveedor(@RequestParam("codigo") int codigo,
                                    @RequestParam("nombre") String nombre,
                                    @RequestParam("categoria") String categoria,
                                    @RequestParam("afecto") String afecto){

        proveedorService.crearProveedor(codigo, nombre, categoria, afecto);

        return "index";
    }
}
