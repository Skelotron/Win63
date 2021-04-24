package ru.skelotron.win63.mvc.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skelotron.win63.entity.EmailNotified;
import ru.skelotron.win63.entity.NotificationType;
import ru.skelotron.win63.entity.Notified;
import ru.skelotron.win63.exception.EntityNotFoundException;
import ru.skelotron.win63.mvc.model.EmailNotifiedModel;
import ru.skelotron.win63.mvc.model.NotifiedModel;
import ru.skelotron.win63.repository.EmailNotifiedRepository;

@Component
public class EmailNotifiedModelConverter extends NotifiedModelConverter implements ModelConverter<Notified, NotifiedModel> {
    private final EmailNotifiedRepository emailNotifiedRepository;

    @Autowired
    public EmailNotifiedModelConverter(FilterModelConverter filterModelConverter, EmailNotifiedRepository emailNotifiedRepository) {
        super(filterModelConverter);
        this.emailNotifiedRepository = emailNotifiedRepository;
    }

    @Override
    public EmailNotifiedModel convertToModel(Notified entity) {
        if (!(entity instanceof EmailNotified)) {
            throw new RuntimeException("Wrong Entity type: " + entity.getNotificationType());
        }

        EmailNotified emailNotified = (EmailNotified) entity;
        EmailNotifiedModel notified = new EmailNotifiedModel();
        notified.setRecipient(emailNotified.getRecipient());
        notified.setSubject( emailNotified.getSubjectTemplate() );
        notified.setMessage( emailNotified.getTextTemplate() );
        convertToModel(notified, emailNotified);
        return notified;
    }

    @Override
    public EmailNotified convertToEntity(NotifiedModel model) {
        if (!(model instanceof EmailNotifiedModel)) {
            throw new RuntimeException("Wrong model type: " + model.getClass().getSimpleName());
        }
        EmailNotifiedModel emailModel = (EmailNotifiedModel) model;
        EmailNotified notified;
        if (emailModel.getId() != null) {
            notified = emailNotifiedRepository.findById(emailModel.getId()).orElseThrow(() -> new EntityNotFoundException(Notified.class, emailModel.getId()));
        } else {
            notified = new EmailNotified();
        }
        notified.setEmail(emailModel.getRecipient());
        notified.setSubjectTemplate(emailModel.getSubject());
        notified.setTextTemplate(emailModel.getMessage());
        convertToEntity(model, notified);

        return notified;
    }

    @Override
    protected NotificationType getType() {
        return NotificationType.EMAIL;
    }
}
