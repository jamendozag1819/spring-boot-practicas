package com.minsait.springboot.web.app.models;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Data
@Entity
@Table(name = "libros")
public class Libro implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 80, nullable = false)
    private String nombre;

    private String isbn;

    @OneToOne(fetch = FetchType.EAGER)
    private Editorial editorial;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "libros_autores", joinColumns = @JoinColumn(name = "libro_id", unique = false),
            inverseJoinColumns = @JoinColumn(name = "autor_id", unique = false))
    private List<Autor> autor;

    public Libro() {
        autor = new ArrayList<>();
    }

    public void addAutor(Autor autor) {
        this.autor.add(autor);
    }

    public void removeAutor(Autor autor) {
        this.autor.remove(autor);
    }

    public void cleanAutor() {
        this.autor.clear();
    }
}
