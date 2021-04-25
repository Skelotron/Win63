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
public class Synchronizations<T extends SynchronizationModel> implements ModelListHolder<T> {
    @JsonProperty("synchronizations")
    private List<T> models;

    @Override
    public List<T> getModels() {
        return models;
    }

    @Override
    public void setModels(List<T> models) {
        this.models = models;
    }
}
