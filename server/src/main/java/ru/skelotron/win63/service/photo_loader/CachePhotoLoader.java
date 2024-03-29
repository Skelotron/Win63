package ru.skelotron.win63.service.photo_loader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.skelotron.win63.http_entities.Photo;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Qualifier("CachePhotoLoader")
@Slf4j
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
        } catch (IOException | URISyntaxException | RuntimeException e) {
            log.error("Exception on trying to get image from filesystem", e);
        }

        return photo;
    }
}
