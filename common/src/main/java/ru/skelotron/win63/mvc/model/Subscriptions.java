package ru.skelotron.win63.mvc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("subscriptions")
    private List<SubscriptionModel> models;

    @Override
    public List<SubscriptionModel> getModels() {
        return models;
    }

    @Override
    public void setModels(List<SubscriptionModel> models) {
        this.models = models;
    }
}
