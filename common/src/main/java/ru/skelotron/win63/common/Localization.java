package ru.skelotron.win63.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Localization {
    private final MessageSource messageSource;

    @Autowired
    public Localization(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String name) {
        return messageSource.getMessage(name, new Object[] {}, Locale.getDefault());
    }

    public String getMessage(String name, String... keys) {
        return messageSource.getMessage(name, keys, Locale.getDefault());
    }
}
