package com.milkstgo.milkstgo.controllers;

import com.milkstgo.milkstgo.entities.ProveedorEntity;
import com.milkstgo.milkstgo.services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class ProveedorController {

    @Autowired
    ProveedorService proveedorService;

    @GetMapping("/listar-proveedores")
    public String listar(Model model){
        List<ProveedorEntity> proveedores = proveedorService.traerProveedores();
        model.addAttribute("proveedores", proveedores);
        return "listar-proveedores";
    }

    @GetMapping("/ingresar-proveedor")
    public String proveedor(){
        return "ingresar-proveedor";
    }
    @PostMapping("/ingresar-proveedor")
    public String ingresarProveedor(@RequestParam("codigo") String codigo,
                                    @RequestParam("nombre") String nombre,
                                    @RequestParam("categoria") String categoria,
                                    @RequestParam("afecto") String afecto){

       proveedorService.crearProveedor(codigo, nombre, categoria, afecto);

        return "redirect:/ingresar-proveedor";
    }
}
