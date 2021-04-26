package ru.skelotron.win63.mvc.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skelotron.win63.entity.NotificationType;
import ru.skelotron.win63.entity.Notified;
import ru.skelotron.win63.entity.Subscription;
import ru.skelotron.win63.exception.EntityNotFoundException;
import ru.skelotron.win63.mvc.model.NotifiedModel;
import ru.skelotron.win63.mvc.model.SubscriptionModel;
import ru.skelotron.win63.repository.SubscriptionRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Component
public class SubscriptionModelConverter implements ModelConverter<Subscription, SubscriptionModel> {
    private final CategoryModelConverter categoryModelConverter;
    private final NotifiedModelConverterFactory notifiedModelConverterFactory;
    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionModelConverter(CategoryModelConverter categoryModelConverter, NotifiedModelConverterFactory notifiedModelConverterFactory, SubscriptionRepository subscriptionRepository) {
        this.categoryModelConverter = categoryModelConverter;
        this.notifiedModelConverterFactory = notifiedModelConverterFactory;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public SubscriptionModel convertToModel(Subscription entity) {
        SubscriptionModel subscription = new SubscriptionModel();
        subscription.setId(entity.getId());
        subscription.setCategory( categoryModelConverter.convertToModel(entity.getCategory()) );
        subscription.setNotifiedList(new ArrayList<>());
        for (Notified notified : entity.getNotifiedEntities()) {
            subscription.getNotifiedList().add( getNotifiedConverter(notified.getNotificationType()).convertToModel(notified) );
        }
        return subscription;
    }

    private NotifiedModelConverter getNotifiedConverter(NotificationType type) {
        return notifiedModelConverterFactory.get(type);
    }

    @Override
    public Subscription convertToEntity(SubscriptionModel model) {
        Subscription subscription;
        if (model.getId() != null) {
            subscription = subscriptionRepository.findById(model.getId()).orElseThrow(() -> new EntityNotFoundException(Subscription.class, model.getId()));
        } else {
            subscription = new Subscription();
        }
        subscription.setCategory( categoryModelConverter.convertToEntity( model.getCategory() ) );

        Set<Notified> notifiedSet = new HashSet<>();
        for (NotifiedModel notifiedModel : model.getNotifiedList()) {
            Notified notified = getNotifiedConverter(notifiedModel.getType()).convertToEntity(notifiedModel);
            notified.getSubscriptions().add(subscription);
            notifiedSet.add(notified);
        }
        subscription.setNotifiedEntities(notifiedSet);
        return subscription;
    }
}
