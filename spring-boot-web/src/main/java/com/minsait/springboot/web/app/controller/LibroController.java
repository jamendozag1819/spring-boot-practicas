package com.minsait.springboot.web.app.controller;

import com.minsait.springboot.web.app.models.Autor;
import com.minsait.springboot.web.app.models.Editorial;
import com.minsait.springboot.web.app.models.Libro;
import com.minsait.springboot.web.app.service.IEditorialService;
import com.minsait.springboot.web.app.service.ILibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private ILibroService libroService;

    @GetMapping("/")
    public List<Libro> findAll() {
        return libroService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return libroService.findById(id)
                .map(libro -> ResponseEntity.ok().body(libro))
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody Libro libro, BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        if (result.hasErrors()) {
            result.getFieldErrors().forEach(err -> {
                errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }

        for (Autor autor : libro.getAutor()) {
            Optional<Autor> autorDB = libroService.buscarAutorPorId(autor.getId());
            if (autorDB.isPresent()) {
                autor.setId(autorDB.get().getId());
                autor.setNombres(autorDB.get().getNombres());
                autor.setApellidos(autorDB.get().getApellidos());
            } else {
                libroService.guardarAutor(autor);
            }
        }
        return ResponseEntity.ok(libroService.save(libro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Libro libro, BindingResult result, @PathVariable Long id) {
        Map<String, String> errors = new HashMap<>();
        if (result.hasErrors()) {
            result.getFieldErrors().forEach(err -> {
                errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }
        return libroService.findById(id)
                .map(libroDB -> {
                    libroDB.setNombre(libro.getNombre());
                    libroDB.setAutor(libro.getAutor());
                    return ResponseEntity.ok(libroService.save(libroDB));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        libroService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/autor")
    public ResponseEntity<?> guardarAutor(@Valid @RequestBody Autor autor, BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        Optional<Autor> autorDB = libroService.buscarAutorPorNombreYApellidos(autor.getNombres(), autor.getApellidos());

        if (autorDB.isPresent())
            return ResponseEntity.badRequest().body("El autor ya existe");

        if (result.hasErrors()) {
            result.getFieldErrors().forEach(err -> {
                errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }

        return ResponseEntity.ok(libroService.guardarAutor(autor));
    }
}
