package ru.skelotron.win63.mvc.converter;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skelotron.win63.entity.NotificationType;
import ru.skelotron.win63.entity.Notified;
import ru.skelotron.win63.entity.TelegramNotified;
import ru.skelotron.win63.exception.EntityNotFoundException;
import ru.skelotron.win63.mvc.controller.AbstractController;
import ru.skelotron.win63.mvc.model.NotifiedModel;
import ru.skelotron.win63.mvc.model.TelegramNotifiedModel;
import ru.skelotron.win63.repository.TelegramNotifiedRepository;

@Component
public class TelegramNotifiedModelConverter extends NotifiedModelConverter<TelegramNotified, TelegramNotifiedModel> {
    private final TelegramNotifiedRepository telegramNotifiedRepository;

    @Autowired
    public TelegramNotifiedModelConverter(FilterModelConverter filterModelConverter, TelegramNotifiedRepository telegramNotifiedRepository) {
        super(filterModelConverter, AbstractController.class, TelegramNotifiedModel.class);
        this.telegramNotifiedRepository = telegramNotifiedRepository;
    }

    @Override
    public TelegramNotified convertToEntity(NotifiedModel<?> model) {
        if (!(model instanceof TelegramNotifiedModel)) {
            throw new RuntimeException("Wrong model type: " + model.getClass().getSimpleName());
        }
        TelegramNotifiedModel telegramModel = (TelegramNotifiedModel) model;
        TelegramNotified notified;
        if (telegramModel.getId() != null) {
            notified = telegramNotifiedRepository.findById(telegramModel.getId()).orElseThrow(() -> new EntityNotFoundException(Notified.class, telegramModel.getId()));
        } else {
            notified = new TelegramNotified();
        }
        notified.setUserName(telegramModel.getUserName());
        convertToEntity(model, notified);

        return notified;
    }

    @Override
    protected NotificationType getType() {
        return NotificationType.TELEGRAM;
    }

    @Override
    public TelegramNotifiedModel convertToModel(Notified entity) {
        if (!(entity instanceof TelegramNotified)) {
            throw new RuntimeException("Wrong Entity type: " + entity.getNotificationType());
        }
        return toModel((TelegramNotified) entity);
    }

    @Override
    @NotNull
    public TelegramNotifiedModel toModel(TelegramNotified entity) {
        TelegramNotifiedModel notified = new TelegramNotifiedModel();
        notified.setUserName(entity.getUserName());
        toModel(notified, entity);
        return notified;
    }
}
