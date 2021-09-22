package ru.skelotron.win63.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skelotron.win63.entity.CityEntity;
import ru.skelotron.win63.mvc.converter.CityModelConverter;
import ru.skelotron.win63.mvc.model.CityModel;
import ru.skelotron.win63.repository.CityRepository;

@RestController
@RequestMapping("/city")
public class CityController extends AbstractController<CityModelConverter, CityRepository, CityEntity, CityModel> {

    @Autowired
    public CityController(CityRepository cityRepository, CityModelConverter cityModelConverter) {
        super(cityModelConverter, cityRepository);
    }

    @GetMapping("/")
    public ResponseEntity<CollectionModel<CityModel>> getAll() {
        return ResponseEntity.ok(getAllRecordsHolder());
    }
}
