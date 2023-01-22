package com.minsait.springboot.web.app.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "autores", uniqueConstraints = {@UniqueConstraint(columnNames = {"nombres", "apellidos"})})
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String nombres;

    @Column(length = 30, nullable = false)
    private String apellidos;
}
