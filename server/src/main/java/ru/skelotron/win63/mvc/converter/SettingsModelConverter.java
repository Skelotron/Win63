package ru.skelotron.win63.mvc.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skelotron.win63.mvc.model.SettingsModel;
import ru.skelotron.win63.entity.Settings;
import ru.skelotron.win63.exception.EntityNotFoundException;
import ru.skelotron.win63.repository.SettingsRepository;

@Component
public class SettingsModelConverter implements ModelConverter<Settings, SettingsModel> {
    private final SettingsRepository settingsRepository;

    @Autowired
    public SettingsModelConverter(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    @Override
    public SettingsModel convertToModel(Settings entity) {
        return new SettingsModel(entity.getId(), entity.getName(), entity.getValue());
    }

    @Override
    public Settings convertToEntity(SettingsModel model) {
        Settings settings;
        if (model.getId() != null) {
            settings = settingsRepository.findById(model.getId()).orElseThrow(() -> new EntityNotFoundException(Settings.class, model.getId()));
        } else {
            settings = new Settings();
        }
        settings.setName(model.getName());
        settings.setValue(model.getValue());
        return settings;
    }
}
