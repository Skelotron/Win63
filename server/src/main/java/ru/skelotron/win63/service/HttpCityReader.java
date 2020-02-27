package ru.skelotron.win63.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.skelotron.win63.entity.CityEntity;
import ru.skelotron.win63.repository.CityRepository;
import ru.skelotron.win63.service.settings.SettingsService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
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
        String url = settingsService.getCatalogUrl();

        Iterable<CityEntity> existingCities = cityRepository.findAll();
        List<CityEntity> existingCitiesList = new ArrayList<>();
        for (CityEntity cityEntity : existingCities) {
            existingCitiesList.add(cityEntity);
        }

        RestTemplate restTemplate = new RestTemplate();
        String htmlContent = restTemplate.getForObject(url, String.class);

        Set<CityEntity> newCities = new HashSet<>();
        if (htmlContent != null) {
            Document document = Jsoup.parse(htmlContent);
            Elements citiesElement = document.selectFirst("div[am-cities]").select("a");
            for (Element city : citiesElement) {
                String cityExternalId = city.attr("data-id");
                String cityName = city.text();

                CityEntity cityEntity = new CityEntity(cityExternalId, cityName, true);
                if (existingCitiesList.contains(cityEntity)) {
                    continue;
                }
                newCities.add(cityEntity);
            }
        }
        cityRepository.saveAll(newCities);
    }
}
