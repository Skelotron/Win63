package ru.skelotron.win63.service.photo_loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.skelotron.win63.http_entities.Photo;

import java.util.Locale;

@Component
public class HttpPhotoLoader implements PhotoLoader {
    private final MessageSource messageSource;

    @Autowired
    public HttpPhotoLoader(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public Photo load(Photo photo) {
        String schema = messageSource.getMessage("target.schema", new Object[] {}, Locale.getDefault());
        String host = messageSource.getMessage("target.url", new Object[] {}, Locale.getDefault());

        String url = schema + "://" + host + photo.getImageUrl();

        RestTemplate restTemplate = new RestTemplate();
        byte[] content = restTemplate.getForObject(url, byte[].class);
        photo.setContent(content);

        return photo;
    }
}
