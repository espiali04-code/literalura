package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DatosAutor {

    private String name;
    private Integer birth_year;
    private Integer death_year;

    public String getName() {
        return name;
    }

    public Integer getBirthYear() {
        return birth_year;
    }

    public Integer getDeathYear() {
        return death_year;
    }
}

