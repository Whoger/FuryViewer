package com.furyviewer.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class DateConversorService {
    @Autowired
    private NAEraserService naEraserService;

    /**
     * Método que se encarga de convertir el String con formato (día-mes-año) al formato adecuado de LocalDate para la
     * base de datos.
     * @param date String | Contiene la fecha que se debe guardar en la base de datos.
     * @return LocalDate | Devuelve la fecha con el formato adecuado.
     */
    public LocalDate releseDateOMDB(String date){
        LocalDate localDate = null;

        if(naEraserService.eraserNA(date) != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);

            localDate = LocalDate.parse(date, formatter);
        }

        return localDate;
    }

    /**
     * Método que se encarga de convertir el String con formato (año-mes-día) al formato adecuado de LocalDate para la
     * base de datos.
     * @param date String | Contiene la fecha que se debe guardar en la base de datos.
     * @return LocalDate | Devuelve la fecha con el formato adecuado.
     */
    public LocalDate releaseDateOMDBSeason(String date){
        LocalDate localDate = null;

        if(naEraserService.eraserNA(date) != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);

            localDate = LocalDate.parse(date, formatter);
        }

        return localDate;
    }
}