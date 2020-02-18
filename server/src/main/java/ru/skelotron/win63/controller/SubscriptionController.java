package ru.skelotron.win63.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skelotron.win63.entity.*;
import ru.skelotron.win63.record.SubscriptionRecord;
import ru.skelotron.win63.repository.CategoryRepository;
import ru.skelotron.win63.repository.SubscriptionRepository;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {
    private final SubscriptionRepository subscriptionRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public SubscriptionController(SubscriptionRepository subscriptionRepository, CategoryRepository categoryRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.categoryRepository = categoryRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<Void> subscribe(@RequestBody SubscriptionRecord record) {
        CategoryEntity category = categoryRepository.findByUrl(record.getCategoryUrl());
        if (category == null) {
            return ResponseEntity.badRequest().build();
        }
        Subscription subscription = subscriptionRepository.findByCategory(category);
        if (subscription == null) {
            subscription = new Subscription();
            subscription.setCategory( category );
        }

        Notified notified = new EmailNotified(record.getAddress(), record.getSubjectTemplate(), record.getTextTemplate());
        if (!contains(subscription.getNotifiedEntities(), notified)) {
            subscription.getNotifiedEntities().add(notified);
            subscriptionRepository.save(subscription);
        }

        System.out.println(subscription);

        return ResponseEntity.ok().build();
    }

    private boolean contains(Set<Notified> notifiedEntities, Notified notified) {
        for (Notified entity : notifiedEntities) {
            if (Objects.equals(entity.getNotificationType(), notified.getNotificationType())
                    && Objects.equals(entity.getRecipient(), notified.getRecipient())) {
                return true;
            }
        }
        return false;
    }

    @PostMapping("/remove")
    public ResponseEntity<Void> unsubscribe(@RequestBody SubscriptionRecord record) {
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

        return ResponseEntity.ok().build();
    }
}
