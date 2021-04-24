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
public class EmailNotifiedModel extends NotifiedModel {
    private String recipient;
    private String subject;
    private String message;

    @Override
    public NotificationType getType() {
        return NotificationType.EMAIL;
    }
}
