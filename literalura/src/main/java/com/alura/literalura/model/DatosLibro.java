package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DatosLibro {

    private Long id;
    private String title;
    private List<String> languages;
    private Integer download_count;
    private List<DatosAutor> authors;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public Integer getDownloadCount() {
        return download_count;
    }

    public List<DatosAutor> getAuthors() {
        return authors;
    }
}

