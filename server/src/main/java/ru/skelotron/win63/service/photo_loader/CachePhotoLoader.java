package ru.skelotron.win63.service.photo_loader;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.skelotron.win63.http_entities.Photo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Qualifier("CachePhotoLoader")
@Log
public class CachePhotoLoader implements PhotoLoader {
    @Override
    public Photo load(Photo photo) {

        String filename = photo.getImageUrl().substring(photo.getImageUrl().lastIndexOf("/") + 1);
        try {
            Path path = Paths.get(getClass().getResource("/images/" + filename).toURI());
            byte[] content = Files.readAllBytes(path);
            photo.setContent(content);
        } catch (Exception e) {
            log.throwing("CachePhotoLoader", "load", e);
        }

        return photo;
    }
}
