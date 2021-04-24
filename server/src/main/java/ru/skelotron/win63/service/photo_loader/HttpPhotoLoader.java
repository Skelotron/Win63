package ru.skelotron.win63.service.photo_loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.skelotron.win63.http_entities.Photo;
import ru.skelotron.win63.service.settings.SettingsService;

@Component
@Primary
public class HttpPhotoLoader implements PhotoLoader {
    private final SettingsService settingsService;

    @Autowired
    public HttpPhotoLoader(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @Override
    public Photo load(Photo photo) {
        String url = settingsService.getHostUrl() + photo.getImageUrl();

        RestTemplate restTemplate = new RestTemplate();
        byte[] content = restTemplate.getForObject(url, byte[].class);
        photo.setContent(content);

        return photo;
    }
}
