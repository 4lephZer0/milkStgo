package com.milkstgo.milkstgo.controllers;

import com.milkstgo.milkstgo.services.SubirAcopioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping
public class SubirAcopioController {

    @Autowired
    private SubirAcopioService subirAcopio;

    @GetMapping("/subir-acopio")
    public String main() {
        return "subir-acopio";
    }

    @PostMapping("/subir-acopio")
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        subirAcopio.guardar(file);

        redirectAttributes.addFlashAttribute("mensaje", "¡Archivo cargado correctamente!");
        subirAcopio.leerArchivo("Acopio.csv");
        return "redirect:/subir-acopio";
    }
}
