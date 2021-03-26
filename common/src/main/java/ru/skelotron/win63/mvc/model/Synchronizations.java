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
public class Synchronizations<T extends SynchronizationModel> implements ModelListHolder<T> {
    private List<T> synchronizations;

    @Override
    public List<T> getModels() {
        return synchronizations;
    }

    @Override
    public void setModels(List<T> models) {
        this.synchronizations = models;
    }
}
