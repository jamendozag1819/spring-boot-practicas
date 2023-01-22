package com.minsait.springboot.web.app.service;

import com.minsait.springboot.web.app.models.Autor;
import com.minsait.springboot.web.app.models.Libro;

import java.util.List;
import java.util.Optional;

public interface ILibroService {

    List<Libro> findAll();

    Optional<Libro> findById(Long id);

    Libro save(Libro libro);

    Autor guardarAutor(Autor autor);

    Optional<Autor> buscarAutorPorNombreYApellidos(String nombre, String apellidos);

    Optional<Autor> buscarAutorPorId(Long id);

    void deleteById(Long id);



}
