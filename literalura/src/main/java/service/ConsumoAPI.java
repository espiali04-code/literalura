package com.alura.literalura.service;

import com.alura.literalura.model.Datos;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ConsumoAPI {

    public Datos obtenerDatos(String direccion) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(direccion))
                .build();

        try {
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(response.body(), Datos.class);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
