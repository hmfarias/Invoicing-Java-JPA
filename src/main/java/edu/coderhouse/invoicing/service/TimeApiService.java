package edu.coderhouse.invoicing.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

@Service
public class TimeApiService {

    private static final String TIME_API_URL = "https://api.timeapi.io/api/Time/current/zone?timeZone=America/Argentina/Cordoba";
    private static final Logger logger = Logger.getLogger(TimeApiService.class.getName());

    // Método que hace la solicitud HTTP a la API y obtiene la fecha y hora
    public LocalDateTime getCurrentDateTime() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            // Realizo la solicitud GET a la API
            ResponseEntity<String> response = restTemplate.getForEntity(TIME_API_URL, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                // Parseo la respuesta JSON
                JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());

                // Extraigo la fecha y hora en formato ISO 8601
                String dateTimeString = jsonNode.path("dateTime").asText();
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

                // Convierto la fecha y hora a LocalDateTime
                return LocalDateTime.parse(dateTimeString, formatter);
            } else {
                logger.warning("Error getting date and time from TimeAPI.");
                return LocalDateTime.now(); // Fallback en caso de error
            }
        } catch (HttpClientErrorException e) {
            logger.severe("Client error when calling the API: " + e.getMessage());
            return LocalDateTime.now(); // Fallback en caso de error
        } catch (Exception e) {
            logger.severe("General error when calling the API: " + e.getMessage());
            return LocalDateTime.now(); // Fallback en caso de error
        }
    }
}