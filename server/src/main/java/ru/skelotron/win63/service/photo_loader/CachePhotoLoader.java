package ru.skelotron.win63.service.photo_loader;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.skelotron.win63.http_entities.Photo;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Qualifier("CachePhotoLoader")
@Log
public class CachePhotoLoader implements PhotoLoader {
    @Override
    public Photo load(Photo photo) {
        String imageUrl = photo.getImageUrl();
        String filename = imageUrl.contains("/") ? imageUrl.substring(imageUrl.lastIndexOf("/") + 1) : imageUrl;
        try {
            URL resourceUrl = getClass().getResource("/images/" + filename);
            if (resourceUrl != null) {
                Path path = Paths.get(resourceUrl.toURI());
                byte[] content = Files.readAllBytes(path);
                photo.setContent(content);
            }
        } catch (Exception e) {
            log.throwing("CachePhotoLoader", "load", e);
        }

        return photo;
    }
}
