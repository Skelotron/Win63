package ru.skelotron.win63.email_sender;

import org.springframework.stereotype.Component;

import javax.mail.*;
import java.util.Properties;

@Component
public class EmailSender {
    private final Properties properties;

    public EmailSender() {
        this.properties = new Properties();
        initialize(new PropertiesSmtpSettings());
    }

    public void initialize(SmtpSettings smtpSettings) {
        properties.put("mail.smtp.host", smtpSettings.getHost());
        properties.put("mail.smtp.port", smtpSettings.getPort());
        properties.put("mail.smtp.auth", smtpSettings.useAuth());
        properties.put("mail.smtp.starttls.enable", smtpSettings.isStartTlsEnabled());
        properties.put("mail.user", smtpSettings.getUsername());
        properties.put("mail.password", smtpSettings.getPassword());
    }

    private Properties getProperties() {
        return properties;
    }

    private String getUsername() {
        return getProperties().getProperty("mail.user");
    }

    private String getPassword() {
        return getProperties().getProperty("mail.password");
    }

    private boolean useAuth() {
        return Boolean.TRUE.toString().equalsIgnoreCase(getProperties().getProperty("mail.smtp.auth"));
    }

    public Session createSession() {
        if (useAuth()) {
            Authenticator authenticator = new Authenticator() {
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(getUsername(), getPassword());
                }
            };
            return Session.getDefaultInstance(getProperties(), authenticator);
        } else {
            return Session.getDefaultInstance(getProperties());
        }
    }

    private void send(Email email) {
        Message message = EmailMessageFactory.getInstance().createMessage(email, createSession());
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
