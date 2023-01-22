package com.minsait.springboot.web.app.repository;

import com.minsait.springboot.web.app.models.Editorial;
import com.minsait.springboot.web.app.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EditorialRepository extends JpaRepository<Editorial, Long> {

    @Query("select l from Libro l join l.editorial e where e.id = ?1")
    List<Libro> findLibrosByEditorial(Long id);

    @Query("select e from Editorial e where e.nombre like %?1%")
    List<Editorial> findEditorialesByNombre(String nombre);

}
