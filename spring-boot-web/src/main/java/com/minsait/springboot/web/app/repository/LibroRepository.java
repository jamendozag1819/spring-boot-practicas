package com.minsait.springboot.web.app.repository;

import com.minsait.springboot.web.app.models.Autor;
import com.minsait.springboot.web.app.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LibroRepository extends JpaRepository<Libro, Long> {
}
