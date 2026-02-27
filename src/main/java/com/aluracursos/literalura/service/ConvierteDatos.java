package com.aluracursos.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos implements IConvierteDatos{
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String datosJson, Class<T> clase) {
        try {
            return objectMapper.readValue(datosJson, clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
