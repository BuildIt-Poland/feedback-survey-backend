package com.buildit.email;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.RawMessage;
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

public class EmailSender {

    private static final Logger LOGGER = LogManager.getLogger(EmailSender.class);

    private final EmailDao emailDao;

    public EmailSender(EmailDao emailDao) {
        this.emailDao = emailDao;
    }

    /**
     * Send email.
     *
     * @return true if mail is correctly generated.
     */
    public boolean sendMail(File file, Map<String, String> parameters) {
        try {
            Optional<Email> emailOptional = emailDao.getEmailConfiguration();
            if (emailOptional.isPresent()) {
                Email email = emailOptional.get();
                send(email, file, parameters);
                return true;
            }
        } catch (Exception ex) {
            LOGGER.error("The email wasn't sending. Error message: " + ex.getMessage());
        }
        return false;
    }

    private void send(Email email, File file, Map<String, String> parameters) throws MessagingException, IOException {
        SendRawEmailRequest rawEmailRequest = prepareRawEmail(email, file, parameters);

        AmazonSimpleEmailService client =
                AmazonSimpleEmailServiceClientBuilder.standard()
                        .build();
        client.sendRawEmail(rawEmailRequest);
    }

    private SendRawEmailRequest prepareRawEmail(Email email, File file, Map<String, String> parameters) throws MessagingException, IOException {
        Session session = Session.getDefaultInstance(new Properties());

        MimeMessage message = new MimeMessage(session);
        message.setSubject(formatText(email.getSubject(), parameters), "UTF-8");
        message.setFrom(new InternetAddress(email.getSender()));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getRecipients()));
        message.setContent(prepareMessage(email, file, parameters));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        message.writeTo(outputStream);
        RawMessage rawMessage = new RawMessage(ByteBuffer.wrap(outputStream.toByteArray()));

        return new SendRawEmailRequest(rawMessage);
    }

    private MimeMultipart prepareMessage(Email email, File file, Map<String, String> parameters) throws MessagingException {
        MimeMultipart msg = new MimeMultipart("mixed");
        msg.addBodyPart(prepareMessageBody(email, parameters));
        msg.addBodyPart(prepareAttachment(file));
        return msg;
    }

    private MimeBodyPart prepareMessageBody(Email email, Map<String, String> parameters) throws MessagingException {
        MimeMultipart msgBody = new MimeMultipart("alternative");

        // Define the text part.
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setContent(formatText(email.getBodyText(), parameters), "text/plain; charset=UTF-8");

        // Define the HTML part.
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(formatText(email.getBodyHtml(), parameters), "text/html; charset=UTF-8");

        msgBody.addBodyPart(textPart);
        msgBody.addBodyPart(htmlPart);

        MimeBodyPart wrap = new MimeBodyPart();
        wrap.setContent(msgBody);

        return wrap;
    }

    private MimeBodyPart prepareAttachment(File file) throws MessagingException {
        MimeBodyPart attachment = new MimeBodyPart();
        DataSource fds = new FileDataSource(file);
        attachment.setDataHandler(new DataHandler(fds));
        attachment.setFileName(file.getName());
        return attachment;
    }

    private String formatText(String text, Map<String, String> parameters) {
        for (String param : parameters.keySet()) {
            text = text.replace("{" + param + "}", parameters.get(param));
        }
        return text;
    }
}
