package ru.skelotron.win63.controller.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skelotron.win63.controller.model.CityModel;
import ru.skelotron.win63.entity.CityEntity;
import ru.skelotron.win63.exception.EntityNotFoundException;
import ru.skelotron.win63.repository.CityRepository;

@Component
public class CityModelConverter implements ModelConverter<CityEntity, CityModel> {
    private final CityRepository cityRepository;

    @Autowired
    public CityModelConverter(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public CityModel convertToModel(CityEntity entity) {
        CityModel city = new CityModel();
        city.setId(entity.getId());
        city.setName(entity.getName());
        return city;
    }

    @Override
    public CityEntity convertToEntity(CityModel model) {
        return cityRepository.findById(model.getId()).orElseThrow(() -> new EntityNotFoundException(CityEntity.class, model.getId()));
    }
}
