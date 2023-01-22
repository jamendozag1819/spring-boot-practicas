package com.minsait.springboot.web.app.controller;

import com.minsait.springboot.web.app.models.Editorial;
import com.minsait.springboot.web.app.service.IEditorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/editoriales")
public class EditorialController {
    @Autowired
    private IEditorialService editorialService;

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(editorialService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Editorial> editorial = editorialService.findById(id);
        if (editorial.isPresent()) {
            return ResponseEntity.ok().body(editorial);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> findEditorialesByNombre(@PathVariable String nombre) {
        List<Editorial> editorial = editorialService.findEditorialesByNombre(nombre);
        if (!editorial.isEmpty()) {
            return ResponseEntity.ok().body(editorialService.findEditorialesByNombre(nombre));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/editorial/{id}")
    public ResponseEntity<?> findLibrosByEditorial(@PathVariable Long id) {
        Optional<Editorial> editorial = editorialService.findById(id);
        if (editorial.isPresent()) {
            return ResponseEntity.ok().body(editorialService.findLibrosByEditorial(id));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<?> save(@Valid @RequestBody Editorial editorial, BindingResult result) {
        Map<String, Object> response = new HashMap<>();

        if (editorial.getNombre().length() > 80) {
            response.put("mensaje", "El nombre de la editorial debe tener menos de 80 caracteres");
        }

        if (editorial.getDireccion().length() > 80) {
            response.put("mensaje", "La direccion de la editorial debe tener menos de 80 caracteres");
        }

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
        }

        if (response.size() > 0) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }


        return ResponseEntity.status(HttpStatus.CREATED).body(editorialService.save(editorial));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        editorialService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Editorial editorial, BindingResult result, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }


        Optional<Editorial> editorialOptional = editorialService.findById(id);
        if (editorialOptional.isPresent()) {
            Editorial editorialUpdate = editorialOptional.get();
            editorialUpdate.setNombre(editorial.getNombre());
            editorialUpdate.setDireccion(editorial.getDireccion());
            editorialUpdate.setTelefono(editorial.getTelefono());
            editorialUpdate.setLibros(editorial.getLibros());
            return ResponseEntity.status(HttpStatus.CREATED).body(editorialService.save(editorialUpdate));
        }
        return ResponseEntity.notFound().build();
    }

}
