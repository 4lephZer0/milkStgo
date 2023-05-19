package com.milkstgo.milkstgo.controllers;


import com.milkstgo.milkstgo.entities.PlanillaPagoEntity;
import com.milkstgo.milkstgo.services.PlanillaPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping
public class PlanillaPagoController {

    @Autowired
    PlanillaPagoService planillaPagoService;



    @GetMapping("/listar-planillas")
    public String listar(Model model){
        ArrayList<PlanillaPagoEntity> planillas = planillaPagoService.traerPlanilla();
        model.addAttribute("planillas", planillas);
        return "listar-planillas";
    }

    @GetMapping("/crear-planillas")
    public String main() {
        return "crear-planillas";
    }

    @PostMapping("/crear-planillas")
    public String crear(RedirectAttributes redirectAttributes){
        planillaPagoService.crearPlanillaPago();
        redirectAttributes.addFlashAttribute("mensaje", "¡Planilla calculada con exito!");
        return "redirect:/crear-planillas";
    }
}
