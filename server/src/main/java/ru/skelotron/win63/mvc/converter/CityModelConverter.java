package ru.skelotron.win63.mvc.converter;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import ru.skelotron.win63.mvc.model.CityModel;
import ru.skelotron.win63.entity.CityEntity;
import ru.skelotron.win63.exception.EntityNotFoundException;
import ru.skelotron.win63.repository.CityRepository;

@Component
public class CityModelConverter extends RepresentationModelAssemblerSupport<CityEntity, CityModel> implements ModelConverter<CityEntity, CityModel> {
    private final CityRepository cityRepository;

    @Autowired
    public CityModelConverter(CityRepository cityRepository) {
        super(CityEntity.class, CityModel.class);
        this.cityRepository = cityRepository;
    }

    @Override
    public CityEntity convertToEntity(CityModel model) {
        return cityRepository.findById(model.getId()).orElseThrow(() -> new EntityNotFoundException(CityEntity.class, model.getId()));
    }

    @Override
    @NotNull
    public CityModel toModel(CityEntity entity) {
        CityModel city = new CityModel();
        city.setId(entity.getId());
        city.setName(entity.getName());
        return city;
    }
}
