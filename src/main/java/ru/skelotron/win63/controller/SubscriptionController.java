package ru.skelotron.win63.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skelotron.win63.entity.CategoryEntity;
import ru.skelotron.win63.entity.EmailNotified;
import ru.skelotron.win63.entity.Notified;
import ru.skelotron.win63.entity.Subscription;
import ru.skelotron.win63.record.SubscriptionRecord;
import ru.skelotron.win63.repository.CategoryRepository;
import ru.skelotron.win63.repository.SubscriptionRepository;

import java.util.Iterator;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/add")
    public ResponseEntity<Void> subscribe(@RequestBody SubscriptionRecord record) {
        CategoryEntity category = categoryRepository.findByUrl(record.getCategoryUrl());
        Subscription subscription = subscriptionRepository.findByCategory(category);
        if (subscription == null) {
            subscription = new Subscription();
            subscription.setCategory( category );
        }

        Notified notified = new EmailNotified(record.getAddress(), record.getSubjectTemplate(), record.getTextTemplate());
        subscription.getNotifiedEntities().add( notified );
        subscriptionRepository.save(subscription);

        System.out.println(subscription);

        return ResponseEntity.ok().build();
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
