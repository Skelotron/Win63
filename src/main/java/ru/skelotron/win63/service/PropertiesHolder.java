package ru.skelotron.win63.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class PropertiesHolder {
    private final MessageSource messageSource;

    @Autowired
    public PropertiesHolder(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getProperty(String name) {
        return messageSource.getMessage(name, new Object[] {}, Locale.getDefault());
    }
}
