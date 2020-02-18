package ru.skelotron.win63.record;

import lombok.Getter;
import lombok.Setter;
import ru.skelotron.win63.entity.NotificationType;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class SubscriptionRecord implements Serializable {
    private String categoryUrl;
    private NotificationType type;
    private String address;
    private String subjectTemplate;
    private String textTemplate;
    private List<FilterRecord> filters;
}
