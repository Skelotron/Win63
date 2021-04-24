package ru.skelotron.win63.service.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.skelotron.win63.entity.Settings;
import ru.skelotron.win63.repository.SettingsRepository;

import java.net.IDN;

@Service
public class SettingsServiceImpl implements SettingsService {

    private final SettingsRepository settingsRepository;

    private final String protocol;
    private final String baseUrl;
    private final String url;

    @Autowired
    public SettingsServiceImpl(SettingsRepository settingsRepository, @Value("${target.protocol}") String protocol,
                               @Value("${target.base.url}") String baseUrl, @Value("${target.url}") String url) {
        this.settingsRepository = settingsRepository;
        this.protocol = protocol;
        this.baseUrl = baseUrl;
        this.url = url;
    }

    @Override
    public String getProtocol() {
        Settings settings = settingsRepository.findByName("target.protocol");
        return (settings != null) ? settings.getValue() : protocol;
    }

    @Override
    public String getHost() {
        Settings settings = settingsRepository.findByName("target.url");
        return (settings != null) ? settings.getValue() : url;
    }

    @Override
    public String getBaseUri() {
        Settings settings = settingsRepository.findByName("target.base.url");
        return (settings != null) ? settings.getValue() : baseUrl;
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
