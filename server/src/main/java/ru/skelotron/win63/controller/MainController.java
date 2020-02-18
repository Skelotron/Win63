package ru.skelotron.win63.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.skelotron.win63.controller.model.CategoryModel;
import ru.skelotron.win63.controller.model.SubscriptionModel;
import ru.skelotron.win63.entity.*;
import ru.skelotron.win63.repository.CategoryRepository;
import ru.skelotron.win63.repository.SubscriptionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class MainController {
    private final CategoryRepository categoryRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public MainController(CategoryRepository categoryRepository, SubscriptionRepository subscriptionRepository) {
        this.categoryRepository = categoryRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<CategoryModel> categoryModelList = new ArrayList<>();
        for (CategoryEntity categoryEntity : categoryRepository.findAll()) {
            categoryModelList.add(new CategoryModel(categoryEntity.getName(), categoryEntity.getUrl()));
        }
        model.addAttribute("categories", categoryModelList);

        List<SubscriptionModel> subscriptionModelList = new ArrayList<>();
        for (Subscription subscription : subscriptionRepository.findAll()) {
            Set<Notified> notifiedEntities = subscription.getNotifiedEntities();
            List<EmailNotified> emailNotifiedEntities = notifiedEntities.stream().filter(notified -> notified.getNotificationType() == NotificationType.EMAIL)
                    .map(notified -> (EmailNotified) notified)
                    .collect(Collectors.toList());
            for (EmailNotified emailNotified : emailNotifiedEntities) {
                subscriptionModelList.add( new SubscriptionModel( subscription.getCategory().getName(), emailNotified.getEmail(), emailNotified.getTextTemplate() ) );
            }
        }
        model.addAttribute("subscriptions", subscriptionModelList);

        return "index";
    }
}
