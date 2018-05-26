package mplus.hackathon.service.impl;

import mplus.hackathon.config.Constants;
import mplus.hackathon.service.MailSenderService;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailSenderServiceImpl implements MailSenderService {


    @Override
    public void sendMail(String theme, String mailBody, String email) {

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.port", "465");
        properties.setProperty("mail.smtp.host", "smtp.gmail.com");
        properties.setProperty("mail.smtp.socketFactory.port", "465");
        properties.setProperty("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.allow8bitmime", "true");
        properties.setProperty("mail.smtps.allow8bitmime", "true");
        properties.setProperty("mail.mime.charset", "UTF-8");
        Session session = Session.getDefaultInstance(properties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Constants.EMAIL_LOGIN, Constants.EMAIL_PASSWORD);
                    }
                });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Constants.EMAIL_LOGIN));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(
                    email));
            message.setSubject(theme, "UTF-8");
            message.setText(Constants.MAIL_BODY.replace("~~~", mailBody));
            message.setContent(message, "text/plain; charset=UTF-8");


            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.getMimeMessage().setSubject(theme, "UTF-8");

            helper.setSubject(theme);
            helper.setText(mailBody, true);

            synchronized (this) {
                Transport.send(message);
            }
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }
}