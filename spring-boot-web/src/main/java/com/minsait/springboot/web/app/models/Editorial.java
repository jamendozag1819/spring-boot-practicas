package com.minsait.springboot.web.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "editoriales")
public class Editorial implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(length = 80, nullable = false)
    private String nombre;

    @NotEmpty
    @Column(length = 120)
    private String direccion;

    @NotEmpty
    @Column(length = 20, nullable = false)
    private String telefono;

    @NotEmpty
    @Email
    @Column(length = 35, unique = true)
    private String email;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "editorial_id")
    private List<Libro> libros;

    public Editorial() {
        libros = new ArrayList<>();
    }

    private final static long serialVersionUID = 1L;

}
