package com.minsait.springboot.web.app.service;

import com.minsait.springboot.web.app.models.Autor;
import com.minsait.springboot.web.app.models.Libro;
import com.minsait.springboot.web.app.repository.AutorRepository;
import com.minsait.springboot.web.app.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroServiceImpl implements ILibroService {


    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Override
    public List<Libro> findAll() {
        return libroRepository.findAll();
    }

    @Override
    public Optional<Libro> findById(Long id) {
        return libroRepository.findById(id);
    }

    @Override
    public Libro save(Libro libro) {

        if (libro.getId() != 0 && !libro.getAutor().isEmpty()) {
            Optional<Libro> libroOptional = libroRepository.findById(libro.getId());
            if (libroOptional.isPresent()) {
                libroOptional.get().getAutor().clear();
                for (Autor autor : libro.getAutor()) {
                    Optional<Autor> autorOptional = autorRepository.findById(autor.getId());
                    if (autorOptional.isPresent()) {
                        libroOptional.get().addAutor(autorOptional.get());
                    }
                }
                return libroRepository.save(libroOptional.get());
            }
        }
        return libroRepository.save(libro);
    }

    @Override
    public Autor guardarAutor(Autor autor) {
        return autorRepository.save(autor);
    }

    @Override
    public Optional<Autor> buscarAutorPorNombreYApellidos(String nombre, String apellidos) {
        return autorRepository.findByNombresAndApellidos(nombre, apellidos);
    }

    @Override
    public Optional<Autor> buscarAutorPorId(Long id) {
        return autorRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        libroRepository.deleteById(id);
    }
}
