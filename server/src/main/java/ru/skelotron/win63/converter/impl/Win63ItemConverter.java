package ru.skelotron.win63.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.skelotron.win63.converter.ItemConverter;
import ru.skelotron.win63.entity.CategoryEntity;
import ru.skelotron.win63.entity.CityEntity;
import ru.skelotron.win63.entity.Item;
import ru.skelotron.win63.entity.PhotoEntity;
import ru.skelotron.win63.http_entities.GoodsEntry;
import ru.skelotron.win63.http_entities.Photo;
import ru.skelotron.win63.repository.CategoryRepository;
import ru.skelotron.win63.repository.CityRepository;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Component
public class Win63ItemConverter implements ItemConverter<GoodsEntry> {
    private final CategoryRepository categoryRepository;
    private final Win63PhotoConverter photoConverter;
    private final CityRepository cityRepository;

    @Autowired
    public Win63ItemConverter(CategoryRepository categoryRepository, Win63PhotoConverter photoConverter, CityRepository cityRepository) {
        this.categoryRepository = categoryRepository;
        this.photoConverter = photoConverter;
        this.cityRepository = cityRepository;
    }

    @Override
    public Item convertToEntity(GoodsEntry record) {
        CategoryEntity category = categoryRepository.findByExternalId(record.getFkCategory());
        String cityExternalId = record.getFkCity().get(0);
        CityEntity cityEntity = cityRepository.findByExternalId(cityExternalId);
        Set<PhotoEntity> photos = new HashSet<>();
        for (Photo photo : record.getPhotos()) {
            photos.add(photoConverter.convertToEntity(photo));
        }

        BigDecimal amount = parseAmount(record.getAmount());
        return new Item(record.getUrl(), record.getTitle(), amount, record.getInsertTime(), category, photos, cityEntity);
    }

    private static BigDecimal parseAmount(String amount) {
        return BigDecimal.valueOf( Double.parseDouble( StringUtils.trimAllWhitespace( amount ) ) );
    }
}
