package ru.skelotron.win63.converer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.skelotron.win63.entity.PhotoEntity;
import ru.skelotron.win63.http_entities.Photo;
import ru.skelotron.win63.service.photo_loader.PhotoLoader;

@Component
public class PhotoConverter implements Converter<Photo, PhotoEntity> {
    private final PhotoLoader photoLoader;

    @Autowired
    public PhotoConverter(@Qualifier("CachePhotoLoader") PhotoLoader photoLoader) {
        this.photoLoader = photoLoader;
    }

    @Override
    public PhotoEntity convertToEntity(Photo record) {
        photoLoader.load(record);
        return new PhotoEntity(record.getContent(), record.getDescription(), record.getImageUrl());
    }
}
