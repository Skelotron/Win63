package ru.skelotron.win63.mvc.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skelotron.win63.entity.NotificationType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeName("TELEGRAM")
public class TelegramNotifiedModel extends NotifiedModel<TelegramNotifiedModel> {
    private String userName;

    @Override
    public NotificationType getType() {
        return NotificationType.TELEGRAM;
    }
}
