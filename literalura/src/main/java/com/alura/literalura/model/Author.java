package com.alura.literalura.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer birthYear;
    private Integer deathYear;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Book> books;

    public Author() {}

    public Author(String name, Integer birthYear, Integer deathYear) {
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public List<Book> getBooks() {
        return books;
    }
}
