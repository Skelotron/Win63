package ru.skelotron.win63.email_sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.mail.*;
import java.io.IOException;
import java.util.Properties;

@Component
@Slf4j
public class EmailSender {
    private final Properties properties;
    private final PropertiesSmtpSettings smtpSettings;

    @Autowired
    public EmailSender(PropertiesSmtpSettings smtpSettings) {
        this.properties = new Properties();
        this.smtpSettings = smtpSettings;
    }

    @PostConstruct
    public void initialize() {
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

    private boolean useAuth() {
        return smtpSettings.useAuth();
    }

    public Session createSession() {
        if (useAuth()) {
            Authenticator authenticator = new Authenticator() {
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(smtpSettings.getUsername(), smtpSettings.getPassword());
                }
            };
            return Session.getDefaultInstance(getProperties(), authenticator);
        } else {
            return Session.getDefaultInstance(getProperties());
        }
    }

    @SuppressWarnings("unused") // used by message broker
    private void send(Email email) {
        try {
            Message message = EmailMessageFactory.getInstance().createMessage(email, createSession());
            Transport.send(message);
        } catch (MessagingException | IOException e) {
            log.error("Exception on trying to send email to " + email.getTo());
        }
    }
}
