package com.minsait.springboot.web.app.repository;

import com.minsait.springboot.web.app.models.Autor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombresAndApellidos(String nombre, String apellidos);

}
