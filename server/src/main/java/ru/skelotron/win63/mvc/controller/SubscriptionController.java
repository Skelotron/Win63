package ru.skelotron.win63.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skelotron.win63.mvc.converter.SubscriptionModelConverter;
import ru.skelotron.win63.mvc.model.ModelListHolder;
import ru.skelotron.win63.mvc.model.SubscriptionModel;
import ru.skelotron.win63.entity.CategoryEntity;
import ru.skelotron.win63.entity.EmailNotified;
import ru.skelotron.win63.entity.Notified;
import ru.skelotron.win63.entity.Subscription;
import ru.skelotron.win63.exception.EntityNotFoundException;
import ru.skelotron.win63.record.SubscriptionRecord;
import ru.skelotron.win63.mvc.model.Subscriptions;
import ru.skelotron.win63.repository.CategoryRepository;
import ru.skelotron.win63.repository.SubscriptionRepository;

import java.util.Iterator;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController extends AbstractController<SubscriptionModelConverter, SubscriptionRepository, Subscription, SubscriptionModel> {
    private final CategoryRepository categoryRepository;

    @Autowired
    public SubscriptionController(SubscriptionRepository subscriptionRepository, CategoryRepository categoryRepository, SubscriptionModelConverter subscriptionModelConverter) {
        super(subscriptionModelConverter, subscriptionRepository);
        this.categoryRepository = categoryRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<SubscriptionModel> subscribe(@RequestBody SubscriptionModel model) {
        Subscription subscription = getConverter().convertToEntity(model);
        getRepository().save(subscription);

        Subscription entity = getRepository().findById(subscription.getId()).orElseThrow(() -> new EntityNotFoundException(Subscription.class, subscription.getId()));
        SubscriptionModel savedModel = getConverter().convertToModel(entity);

        return ResponseEntity.ok(savedModel);
    }

    @PostMapping("/remove")
    public ResponseEntity<SubscriptionRecord> unsubscribe(@RequestBody SubscriptionRecord record) {
        // todo: rewrite
        CategoryEntity category = categoryRepository.findByUrl(record.getCategoryUrl());
        Subscription subscription = getRepository().findByCategory(category);
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
        getRepository().save(subscription);

        return ResponseEntity.ok(record);
    }

    @PostMapping("/{id}")
    public ResponseEntity<SubscriptionRecord> getSubscription(@PathVariable("id") Long id) {
        Subscription subscription = getRepository().findById(id).orElse(null);
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
    public ResponseEntity<ModelListHolder<SubscriptionModel>> getAll() {
        return ResponseEntity.ok(getAllRecordsHolder());
    }

    @Override
    protected ModelListHolder<SubscriptionModel> createModelListHolder() {
        return new Subscriptions();
    }
}
