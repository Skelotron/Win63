package email_sender;

import ru.skelotron.win63.common.NotificationSender;
import ru.skelotron.win63.entity.entity.EmailNotified;
import ru.skelotron.win63.entity.entity.Item;
import ru.skelotron.win63.entity.entity.Notified;

import javax.mail.*;
import java.util.Collections;
import java.util.Properties;

public class EmailSender implements NotificationSender {
    private static final EmailSender INSTANCE = new EmailSender();
    private final Properties properties;

    private EmailSender() {
        this.properties = new Properties();
        initialize(new PropertiesSmtpSettings());
    }

    public static EmailSender getInstance() {
        return INSTANCE;
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
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(getUsername(), getPassword());
                }
            };
            return Session.getDefaultInstance(getProperties(), authenticator);
        } else {
            return Session.getDefaultInstance(getProperties());
        }
    }

    public void send(Email email) {
        Message message = EmailMessageFactory.getInstance().createMessage(email, createSession());
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void send(Notified notified, Iterable<Item> items) {
        if (notified instanceof EmailNotified) {
            for (Item item : items) {
                Email email = convertToEmail((EmailNotified) notified, item);
                send(email);
            }
        }
    }

    private Email convertToEmail(EmailNotified notified, Item item) {
        Email email = new Email();
        email.setTo(Collections.singletonList( notified.getEmail() ) );
        email.setSubject( new MessageProcessor(notified.getSubjectTemplate()).process(item) );
        email.setMessage( new MessageProcessor(notified.getTextTemplate()).process(item) );

        return email;
    }
}
