package ru.skelotron.win63.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skelotron.win63.entity.Subscription;
import ru.skelotron.win63.exception.EntityNotFoundException;
import ru.skelotron.win63.mvc.converter.SubscriptionModelConverter;
import ru.skelotron.win63.mvc.model.NotifiedModel;
import ru.skelotron.win63.mvc.model.SubscriptionModel;
import ru.skelotron.win63.repository.SubscriptionRepository;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController extends AbstractController<SubscriptionModelConverter, SubscriptionRepository, Subscription, SubscriptionModel> {

    @Autowired
    public SubscriptionController(SubscriptionRepository subscriptionRepository, SubscriptionModelConverter subscriptionModelConverter) {
        super(subscriptionModelConverter, subscriptionRepository);
    }

    @PostMapping("/")
    public ResponseEntity<SubscriptionModel> subscribe(@RequestBody SubscriptionModel model) {
        Subscription subscription = getConverter().convertToEntity(model);
        getRepository().save(subscription);

        Subscription entity = getRepository().findById(subscription.getId()).orElseThrow(() -> new EntityNotFoundException(Subscription.class, subscription.getId()));
        SubscriptionModel savedModel = getConverter().toModel(entity);

        return ResponseEntity.ok(savedModel);
    }

    @DeleteMapping("/")
    public ResponseEntity<SubscriptionModel> unsubscribe(@RequestBody SubscriptionModel model) {
        Subscription subscription = getRepository().getOne(model.getId());

        Set<Long> notifiedIds = model.getNotifiedList().stream().map(NotifiedModel::getId).collect(Collectors.toSet());
        subscription.getNotifiedEntities().removeIf(notified -> !notifiedIds.contains(notified.getId()));
        getRepository().save(subscription);

        return ResponseEntity.ok(model);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionModel> getSubscription(@PathVariable("id") Long id) {
        Subscription subscription = getRepository().findById(id).orElse(null);
        if (subscription != null) {
            SubscriptionModel model = getConverter().toModel(subscription);
            return ResponseEntity.ok(model);
        }
        throw new EntityNotFoundException(Subscription.class, id);
    }

    @GetMapping("/")
    public ResponseEntity<CollectionModel<SubscriptionModel>> getAll(Pageable page) {
        return ResponseEntity.ok(getAllRecordsHolder(page));
    }
}
