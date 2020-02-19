package ru.skelotron.win63.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.skelotron.win63.controller.converter.SubscriptionModelConverter;
import ru.skelotron.win63.controller.model.SubscriptionModel;
import ru.skelotron.win63.entity.*;
import ru.skelotron.win63.repository.SubscriptionRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionModelConverter subscriptionModelConverter;

    @Autowired
    public MainController(SubscriptionRepository subscriptionRepository, SubscriptionModelConverter subscriptionModelConverter) {
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionModelConverter = subscriptionModelConverter;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<SubscriptionModel> subscriptionModelList = new ArrayList<>();
        for (Subscription subscription : subscriptionRepository.findAll()) {
            subscriptionModelList.add( subscriptionModelConverter.convertToModel(subscription) );
        }
        model.addAttribute("subscriptions", subscriptionModelList);

        return "index";
    }
}
