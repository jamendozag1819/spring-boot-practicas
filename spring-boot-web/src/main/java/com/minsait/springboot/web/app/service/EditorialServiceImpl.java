package com.minsait.springboot.web.app.service;

import com.minsait.springboot.web.app.models.Editorial;
import com.minsait.springboot.web.app.models.Libro;
import com.minsait.springboot.web.app.repository.EditorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EditorialServiceImpl implements IEditorialService {

    @Autowired
    private EditorialRepository editorialRepository;

    @Override
    public List<Editorial> findAll() {
        return editorialRepository.findAll();
    }

    @Override
    public Editorial save(Editorial editorial) {
        return editorialRepository.save(editorial);
    }

    @Override
    public void deleteById(Long id) {
        editorialRepository.deleteById(id);
    }

    @Override
    public Optional<Editorial> findById(Long id) {
        return editorialRepository.findById(id);
    }

    @Override
    public List<Libro> findLibrosByEditorial(Long id) {
        return editorialRepository.findLibrosByEditorial(id);
    }

    @Override
    public List<Editorial> findEditorialesByNombre(String nombre) {
        return editorialRepository.findEditorialesByNombre(nombre);
    }
}
