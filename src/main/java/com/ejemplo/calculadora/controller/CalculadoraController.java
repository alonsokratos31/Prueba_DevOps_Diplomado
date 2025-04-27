package com.ejemplo.calculadora.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@Controller
@RequestMapping("/api/calculadora")
public class CalculadoraController {

    @GetMapping("/")
    public String mostrarCalculadora() {
        System.out.println("mostrando calculadora");
        return "calculadora"; // Devolver el nombre de la vista
    }

    @GetMapping("/sumar")
    public ResponseEntity<Double> sumar(@RequestParam(name = "a") double a, @RequestParam(name = "b") double b) {
        return ResponseEntity.ok(a + b);  // Devolver el resultado en un ResponseEntity
    }

    @GetMapping("/restar")
    public ResponseEntity<Double> restar(@RequestParam(name = "a") double a, @RequestParam(name = "b") double b) {
        return ResponseEntity.ok(a - b);
    }

    @GetMapping("/multiplicar")
    public ResponseEntity<Double> multiplicar(@RequestParam(name = "a") double a, @RequestParam(name = "b") double b) {
        return ResponseEntity.ok(a * b);
    }

    @GetMapping("/dividir")
    public ResponseEntity<Double> dividir(@RequestParam(name = "a") double a, @RequestParam(name = "b") double b) {
        if (b == 0) throw new IllegalArgumentException("No se puede dividir por cero");
        return ResponseEntity.ok(a / b);
    }
}
