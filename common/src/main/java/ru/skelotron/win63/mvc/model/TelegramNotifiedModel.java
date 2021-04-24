package ru.skelotron.win63.mvc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skelotron.win63.entity.NotificationType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TelegramNotifiedModel extends NotifiedModel {
    private String userName;

    @Override
    public NotificationType getType() {
        return NotificationType.TELEGRAM;
    }
}
