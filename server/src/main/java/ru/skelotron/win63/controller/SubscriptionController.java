package ru.skelotron.win63.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skelotron.win63.controller.converter.SubscriptionModelConverter;
import ru.skelotron.win63.controller.model.SubscriptionModel;
import ru.skelotron.win63.entity.CategoryEntity;
import ru.skelotron.win63.entity.EmailNotified;
import ru.skelotron.win63.entity.Notified;
import ru.skelotron.win63.entity.Subscription;
import ru.skelotron.win63.record.SubscriptionRecord;
import ru.skelotron.win63.record.Subscriptions;
import ru.skelotron.win63.repository.CategoryRepository;
import ru.skelotron.win63.repository.SubscriptionRepository;

import java.util.Iterator;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {
    private final SubscriptionRepository subscriptionRepository;
    private final CategoryRepository categoryRepository;
    private final SubscriptionModelConverter subscriptionModelConverter;

    @Autowired
    public SubscriptionController(SubscriptionRepository subscriptionRepository, CategoryRepository categoryRepository, SubscriptionModelConverter subscriptionModelConverter) {
        this.subscriptionRepository = subscriptionRepository;
        this.categoryRepository = categoryRepository;
        this.subscriptionModelConverter = subscriptionModelConverter;
    }

    @PostMapping("/add")
    public ResponseEntity<SubscriptionModel> subscribe(@RequestBody SubscriptionModel model) {
        Subscription subscription = subscriptionModelConverter.convertToEntity(model);
        subscriptionRepository.save(subscription);

        Subscription entity = subscriptionRepository.findById(subscription.getId()).orElseThrow(() -> new IllegalArgumentException("Can't find Subscription with id = " + subscription.getId()));
        SubscriptionModel savedModel = subscriptionModelConverter.convertToModel(entity);

        return ResponseEntity.ok(savedModel);
    }

    @PostMapping("/remove")
    public ResponseEntity<SubscriptionRecord> unsubscribe(@RequestBody SubscriptionRecord record) {
        // todo: rewrite
        CategoryEntity category = categoryRepository.findByUrl(record.getCategoryUrl());
        Subscription subscription = subscriptionRepository.findByCategory(category);
        if (subscription == null) {
            return ResponseEntity.ok().build();
        }

        for (Iterator<Notified> it = subscription.getNotifiedEntities().iterator(); it.hasNext(); ) {
            Notified notified = it.next();
            if (notified instanceof EmailNotified) {
                if (((EmailNotified) notified).getEmail().endsWith(record.getAddress())) {
                    it.remove();
                }
            }
        }
        subscriptionRepository.save(subscription);

        return ResponseEntity.ok(record);
    }

    @PostMapping("/{id}")
    public ResponseEntity<SubscriptionRecord> getSubscription(@PathVariable("id") Long id) {
        Subscription subscription = subscriptionRepository.findById(id).orElse(null);
        SubscriptionRecord record = new SubscriptionRecord();
        if (subscription != null) {
            Notified notified = subscription.getNotifiedEntities().iterator().next();
            record.setAddress(notified.getRecipient());
            if (notified instanceof EmailNotified) {
                EmailNotified emailNotified = (EmailNotified) notified;
                record.setSubjectTemplate(emailNotified.getSubjectTemplate());
                record.setTextTemplate(emailNotified.getTextTemplate());
            }
            record.setCategoryUrl(subscription.getCategory().getUrl());
        }
        return ResponseEntity.ok(record);
    }

    // todo: paging
    @GetMapping("/")
    public ResponseEntity<Subscriptions> getAll() {
        Subscriptions subscriptions = new Subscriptions();
        for (Subscription subscription : subscriptionRepository.findAll()) {
            subscriptions.getSubscriptions().add(subscriptionModelConverter.convertToModel(subscription));
        }
        return ResponseEntity.ok(subscriptions);
    }
}
