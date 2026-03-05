package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Datos {

    private List<DatosLibro> results;

    public List<DatosLibro> getResults() {
        return results;
    }
}
