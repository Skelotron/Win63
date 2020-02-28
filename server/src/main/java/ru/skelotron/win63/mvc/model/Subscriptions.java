package ru.skelotron.win63.mvc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Subscriptions implements ModelListHolder<SubscriptionModel> {
    private List<SubscriptionModel> subscriptions;

    @Override
    public List<SubscriptionModel> getModels() {
        return subscriptions;
    }

    @Override
    public void setModels(List<SubscriptionModel> models) {
        this.subscriptions = models;
    }
}
