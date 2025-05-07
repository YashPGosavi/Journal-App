package com.hasher.journal.service;

import com.hasher.journal.api.response.WhetherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    private static final String apiKey = "64136ad6a2ce72a01ef9f27da7e4c5ef";

    private static final String API = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    private RestTemplate restTemplate;

    public WhetherResponse getWhether(String city){
        String finalApi = API.replace("CITY", city).replace("API_KEY",apiKey);

        ResponseEntity<WhetherResponse> whetherResponseResponseEntity = restTemplate.exchange(finalApi, HttpMethod.GET,null, WhetherResponse.class);

        WhetherResponse body = whetherResponseResponseEntity.getBody();

        return body;
    }
}
