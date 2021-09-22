package ru.skelotron.win63.mvc.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import ru.skelotron.win63.entity.NotificationType;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(EmailNotifiedModel.class),
        @JsonSubTypes.Type(TelegramNotifiedModel.class)
})
public abstract class NotifiedModel<T extends RepresentationModel<? extends T>> extends RepresentationModel<T> {
    private Long id;
    private List<FilterModel> filters;

    public abstract NotificationType getType();
}
