package com.milkstgo.milkstgo.controllers;

import com.milkstgo.milkstgo.entities.SubirGrasasEntity;
import com.milkstgo.milkstgo.services.SubirGrasasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping
public class SubirGrasasController {

    @Autowired
    private SubirGrasasService subirGrasas;

    @GetMapping("/subir-grasas")
    public String main() {
        return "subir-grasas";
    }

    @PostMapping("/subir-grasas")
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        subirGrasas.guardar(file);

        redirectAttributes.addFlashAttribute("mensaje", "¡Archivo cargado correctamente!");
        subirGrasas.leerArchivo("Grasas.csv");
        return "redirect:/subir-grasas";
    }
}
