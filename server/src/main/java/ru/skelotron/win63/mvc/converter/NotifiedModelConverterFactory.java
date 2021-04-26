package ru.skelotron.win63.mvc.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skelotron.win63.entity.NotificationType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotifiedModelConverterFactory {
    private final Map<NotificationType, NotifiedModelConverter> notifiedModelConverters = new HashMap<>();

    @Autowired
    public void setNotifiedModelConverters(@SuppressWarnings("TypeMayBeWeakened") List<NotifiedModelConverter> notifiedModelConverters) {
        for (NotifiedModelConverter converter : notifiedModelConverters) {
            this.notifiedModelConverters.put(converter.getType(), converter);
        }
    }

    public NotifiedModelConverter get(NotificationType type) {
        NotifiedModelConverter converter = notifiedModelConverters.get(type);
        if (converter == null) {
            throw new IllegalStateException("There is no ModelConverter for notificationType = " + type);
        }
        return converter;
    }
}
