package ru.skelotron.win63.email_sender;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;

public final class EmailMessageFactory {
    private static final EmailMessageFactory INSTANCE = new EmailMessageFactory();

    private EmailMessageFactory() {
    }

    public static EmailMessageFactory getInstance() {
        return INSTANCE;
    }

    public Message createMessage(Email email, Session session) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email.getFrom()));
            for (String recipient : email.getTo()) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            }
            for (String recipient : email.getCc()) {
                message.addRecipient(Message.RecipientType.CC, new InternetAddress(recipient));
            }
            for (String recipient : email.getBcc()) {
                message.addRecipient(Message.RecipientType.BCC, new InternetAddress(recipient));
            }
            message.setSubject(email.getSubject());
            message.setText(email.getText());

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(email.getText());

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            addAttachments(email, multipart);

            message.setContent(multipart);
            return message;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private void addAttachments(Email email, Multipart multipart) throws MessagingException {
        try {
            for (Attachment attachment : email.getAttachments()) {
                MimeBodyPart attachPart = new MimeBodyPart();
                File file = new File(attachment.getPath());
                attachPart.attachFile(file);
                multipart.addBodyPart(attachPart);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
