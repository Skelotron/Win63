package ru.skelotron.win63.mvc.model;

import java.io.Serializable;
import java.util.List;

public interface ModelListHolder<M extends AbstractModel> extends Serializable {
    List<M> getModels();
    void setModels(List<M> models);
}
