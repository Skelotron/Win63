package ru.skelotron.win63.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skelotron.win63.entity.*;
import ru.skelotron.win63.record.FilterRecord;
import ru.skelotron.win63.record.SubscriptionRecord;
import ru.skelotron.win63.repository.CategoryRepository;
import ru.skelotron.win63.repository.SubscriptionRepository;

import java.util.HashSet;
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
    public ResponseEntity<SubscriptionRecord> subscribe(@RequestBody SubscriptionRecord record) {
        CategoryEntity category = categoryRepository.findByUrl(record.getCategoryUrl());
        if (category == null) {
            return ResponseEntity.badRequest().build();
        }
        Subscription subscription = subscriptionRepository.findByCategory(category);
        if (subscription == null) {
            subscription = new Subscription();
            subscription.setCategory( category );
        }

        Set<Filter> filters = new HashSet<>();
        for (FilterRecord filterRecord : record.getFilters()) {
            Filter filter = new Filter(filterRecord.getType(), Item.ENTITY_NAME, filterRecord.getField(), filterRecord.getValue());
            filters.add(filter);
        }
        Notified notified = new EmailNotified(record.getAddress(), record.getSubjectTemplate(), record.getTextTemplate(), filters);
        if (!contains(subscription.getNotifiedEntities(), notified)) {
            subscription.getNotifiedEntities().add(notified);
            subscriptionRepository.save(subscription);
        }

        System.out.println(subscription);

        return ResponseEntity.ok(record);
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
    public ResponseEntity<SubscriptionRecord> unsubscribe(@RequestBody SubscriptionRecord record) {
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
}
