package ru.skelotron.win63.converer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.skelotron.win63.entity.CategoryEntity;
import ru.skelotron.win63.entity.Item;
import ru.skelotron.win63.entity.PhotoEntity;
import ru.skelotron.win63.http_entities.GoodsEntry;
import ru.skelotron.win63.http_entities.Photo;
import ru.skelotron.win63.repository.CategoryRepository;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Component
public class ItemConverter implements Converter<GoodsEntry, Item> {
    private final CategoryRepository categoryRepository;
    private final PhotoConverter photoConverter;

    @Autowired
    public ItemConverter(CategoryRepository categoryRepository, PhotoConverter photoConverter) {
        this.categoryRepository = categoryRepository;
        this.photoConverter = photoConverter;
    }

    @Override
    public Item convertToEntity(GoodsEntry record) {
        CategoryEntity category = categoryRepository.findByUrl(record.getUrl());
        Set<PhotoEntity> photos = new HashSet<>();
        for (Photo photo : record.getPhotos()) {
            photos.add(photoConverter.convertToEntity(photo));
        }

        BigDecimal amount = parseAmount(record.getAmount());
        return new Item(record.getUrl(), record.getTitle(), amount, record.getInsertTime(), category, photos);
    }

    private static BigDecimal parseAmount(String amount) {
        return BigDecimal.valueOf( Double.parseDouble( StringUtils.trimAllWhitespace( amount ) ) );
    }
}
