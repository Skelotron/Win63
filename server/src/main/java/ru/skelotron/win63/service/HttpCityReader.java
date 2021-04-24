package ru.skelotron.win63.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.skelotron.win63.entity.CityEntity;
import ru.skelotron.win63.http_entities.geo.GetCities;
import ru.skelotron.win63.http_entities.geo.GetCity;
import ru.skelotron.win63.repository.CityRepository;
import ru.skelotron.win63.service.settings.SettingsService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Component
@Slf4j
public class HttpCityReader implements CityReader {
    private final CityRepository cityRepository;
    private final SettingsService settingsService;

    @Autowired
    public HttpCityReader(CityRepository cityRepository, SettingsService settingsService) {
        this.cityRepository = cityRepository;
        this.settingsService = settingsService;
    }

    @Override
    public void read() {
        Iterable<CityEntity> existingCities = cityRepository.findAll();
        Collection<CityEntity> existingCitiesList = new ArrayList<>();
        for (CityEntity cityEntity : existingCities) {
            existingCitiesList.add(cityEntity);
        }

        GetCities cities = loadCities();
        Collection<CityEntity> newCities = new HashSet<>();
        if (cities != null) {
            for (GetCity city : cities.getData()) {
                String cityExternalId = city.getId();
                String cityName = city.getTitle();

                CityEntity cityEntity = new CityEntity(cityExternalId, cityName, true);
                if (existingCitiesList.contains(cityEntity)) {
                    continue;
                }
                newCities.add(cityEntity);
            }
        }

        cityRepository.saveAll(newCities);
    }

    private GetCities loadCities() {
        String url = settingsService.getCitiesUrl();
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-requested-with", "XMLHttpRequest");
        headers.add(HttpHeaders.CONTENT_TYPE, "text/html; charset=UTF-8");
        ResponseEntity<String> result = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        // we have to deserialize response manually because it is text/html request
        return deserializeResponse(result.getBody());
    }

    private GetCities deserializeResponse(String response) {
        try {
            if (response != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(response, GetCities.class);
            }
        } catch (JsonProcessingException e) {
            log.error("Can't parse response to GetCities", e);
        }
        return null;
    }
}
