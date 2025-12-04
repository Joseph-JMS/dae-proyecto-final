package com.joseph.tienda_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estudiantes")
@CrossOrigin(origins = "http://localhost:4200")
public class EstudianteController {
    @Autowired
    private EstudianteService estudianteService;
    // GET - Todos los productos
    @GetMapping
    public List<Estudiante> obtenerTodos() {
        return estudianteService.obtenerTodos();
    }
    // GET - Producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> obtenerPorId(@PathVariable Long id) {
        Optional<Estudiante> estudiante =
                estudianteService.obtenerPorId(id);
        return estudiante.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // POST - Crear producto
    @PostMapping
    public Estudiante crear(@RequestBody Estudiante estudiante) throws Exception {
        return estudianteService.crear(estudiante);
    }
    // PUT - Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> actualizar(@PathVariable Long id, @RequestBody Estudiante estudiante) {
        try {
            Estudiante estudianteActualizado = estudianteService.actualizar(id, estudiante);
            return ResponseEntity.ok(estudianteActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    // DELETE - Eliminar estudiante
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            estudianteService.eliminar(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
