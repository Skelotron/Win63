package ru.skelotron.win63.service.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skelotron.win63.entity.Settings;
import ru.skelotron.win63.repository.SettingsRepository;
import ru.skelotron.win63.service.PropertiesHolder;

import java.net.IDN;

@Service
public class SettingsServiceImpl implements SettingsService {

    private final SettingsRepository settingsRepository;
    private final PropertiesHolder propertiesHolder;

    @Autowired
    public SettingsServiceImpl(SettingsRepository settingsRepository, PropertiesHolder propertiesHolder) {
        this.settingsRepository = settingsRepository;
        this.propertiesHolder = propertiesHolder;
    }

    @Override
    public String getProtocol() {
        Settings settings = settingsRepository.findByName("target.protocol");
        return (settings != null) ? settings.getValue() : propertiesHolder.getProperty("target.protocol");
    }

    @Override
    public String getHost() {
        Settings settings = settingsRepository.findByName("target.url");
        return (settings != null) ? settings.getValue() : propertiesHolder.getProperty("target.url");
    }

    @Override
    public String getBaseUri() {
        Settings settings = settingsRepository.findByName("target.base.url");
        return (settings != null) ? settings.getValue() : propertiesHolder.getProperty("target.base.url");
    }

    @Override
    public String getHostUrl() {
        return getProtocol() + "://" + IDN.toASCII(getHost());
    }

    @Override
    public String getCatalogUrl() {
        return getHostUrl() + getBaseUri();
    }
}
