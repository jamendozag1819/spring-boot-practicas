package com.minsait.springboot.web.app.service;

import com.minsait.springboot.web.app.models.Editorial;
import com.minsait.springboot.web.app.models.Libro;

import java.util.List;
import java.util.Optional;

public interface IEditorialService {

    List<Editorial> findAll();

    Editorial save(Editorial editorial);

    void deleteById(Long id);

    Optional<Editorial> findById(Long id);

    List<Libro> findLibrosByEditorial(Long id);

    List<Editorial> findEditorialesByNombre(String nombre);

}
