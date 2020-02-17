package ru.skelotron.win63.service.photo_loader;

import ru.skelotron.win63.http_entities.Photo;

public interface PhotoLoader {
    Photo load(Photo photo);
}
