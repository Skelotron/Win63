package ru.skelotron.win63.mvc.converter;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import ru.skelotron.win63.mvc.controller.AbstractController;
import ru.skelotron.win63.mvc.model.SettingsModel;
import ru.skelotron.win63.entity.Settings;
import ru.skelotron.win63.exception.EntityNotFoundException;
import ru.skelotron.win63.repository.SettingsRepository;

@Component
public class SettingsModelConverter extends RepresentationModelAssemblerSupport<Settings, SettingsModel> implements ModelConverter<Settings, SettingsModel> {
    private final SettingsRepository settingsRepository;

    @Autowired
    public SettingsModelConverter(SettingsRepository settingsRepository) {
        super(AbstractController.class, SettingsModel.class);
        this.settingsRepository = settingsRepository;
    }

    @Override
    @NotNull
    public SettingsModel toModel(Settings entity) {
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
