package ru.skelotron.win63.mvc.converter;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
public class SubscriptionModelConverter extends RepresentationModelAssemblerSupport<Subscription, SubscriptionModel> implements ModelConverter<Subscription, SubscriptionModel> {
    private final CategoryModelConverter categoryModelConverter;
    private final NotifiedModelConverterFactory notifiedModelConverterFactory;
    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionModelConverter(CategoryModelConverter categoryModelConverter, NotifiedModelConverterFactory notifiedModelConverterFactory, SubscriptionRepository subscriptionRepository) {
        super(Subscription.class, SubscriptionModel.class);
        this.categoryModelConverter = categoryModelConverter;
        this.notifiedModelConverterFactory = notifiedModelConverterFactory;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    @NotNull
    public SubscriptionModel toModel(Subscription entity) {
        SubscriptionModel subscription = new SubscriptionModel();
        subscription.setId(entity.getId());
        subscription.setCategory( categoryModelConverter.toModel(entity.getCategory()) );
        subscription.setNotifiedList(new ArrayList<>());
        for (Notified notified : entity.getNotifiedEntities()) {
            subscription.getNotifiedList().add( getNotifiedConverter(notified.getNotificationType()).convertToModel(notified) );
        }
        subscription.add(WebMvcLinkBuilder.linkTo(getControllerClass()).slash(entity.getId()).withSelfRel());
        return subscription;
    }

    @SuppressWarnings("unchecked")
    private NotifiedModelConverter<? extends Notified, ? extends NotifiedModel<?>> getNotifiedConverter(NotificationType type) {
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
        for (NotifiedModel<?> notifiedModel : model.getNotifiedList()) {
            Notified notified = getNotifiedConverter(notifiedModel.getType()).convertToEntity(notifiedModel);
            notified.getSubscriptions().add(subscription);
            notifiedSet.add(notified);
        }
        subscription.setNotifiedEntities(notifiedSet);
        return subscription;
    }
}
