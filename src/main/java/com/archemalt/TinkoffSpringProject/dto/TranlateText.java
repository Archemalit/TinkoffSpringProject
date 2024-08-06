package com.archemalt.TinkoffSpringProject.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;

public class TranlateText implements Callable<String> {
    private String url;
    private RestTemplate restTemplate;
    private ObjectMapper mapper;

    public TranlateText(String url, RestTemplate restTemplate, ObjectMapper mapper) {
        this.url = url;
        this.restTemplate = restTemplate;
        this.mapper = mapper;
    }

    @Override
    public String call() {
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            try {
                JsonNode root = mapper.readTree(response.getBody());
                String responseBody = root.get(0).get(0).get(0).asText();
                return responseBody;
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException(e);
            }
        } else {
            throw new IllegalArgumentException("Error" + response.getStatusCode());
        }
    }
}