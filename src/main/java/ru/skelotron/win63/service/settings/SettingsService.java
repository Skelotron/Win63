package ru.skelotron.win63.service.settings;

public interface SettingsService {
    String getProtocol();
    String getHost();
    String getBaseUri();

    String getHostUrl();
    String getCatalogUrl();
}
