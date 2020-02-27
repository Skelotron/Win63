package ru.skelotron.win63.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skelotron.win63.controller.converter.CityModelConverter;
import ru.skelotron.win63.controller.model.Cities;
import ru.skelotron.win63.controller.model.CityModel;
import ru.skelotron.win63.entity.CityEntity;
import ru.skelotron.win63.repository.CityRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/city")
public class CityController {
    private final CityRepository cityRepository;
    private final CityModelConverter cityModelConverter;

    @Autowired
    public CityController(CityRepository cityRepository, CityModelConverter cityModelConverter) {
        this.cityRepository = cityRepository;
        this.cityModelConverter = cityModelConverter;
    }

    @GetMapping("/")
    public ResponseEntity<Cities> getAll() {
        Iterable<CityEntity> cities = cityRepository.findAll();

        List<CityModel> cityModels = new ArrayList<>();

        for (CityEntity city : cities) {
            cityModels.add( cityModelConverter.convertToModel(city) );
        }

        return ResponseEntity.ok(new Cities(cityModels));
    }
}
