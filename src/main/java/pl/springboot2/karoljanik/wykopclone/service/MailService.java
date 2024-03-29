package pl.springboot2.karoljanik.wykopclone.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.springboot2.karoljanik.wykopclone.exceptions.WykopCloneException;
import pl.springboot2.karoljanik.wykopclone.model.AuthorizationMail;

@Service
public class MailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);
    private final JavaMailSender javaMailSender;
    private final MailContentBuilder mailContentBuilder;

    @Autowired
    public MailService(JavaMailSender javaMailSender, MailContentBuilder mailContentBuilder) {
        this.javaMailSender = javaMailSender;
        this.mailContentBuilder = mailContentBuilder;
    }

    @Async
    public void sendMail(AuthorizationMail authorizationMail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("wykopclone@email.com");
        message.setTo(authorizationMail.getTo());
        message.setSubject(authorizationMail.getSubject());
//        message.setText(mailContentBuilder.build(authorizationMail.getText()));
        message.setText(authorizationMail.getText());
        try {
            javaMailSender.send(message);
            LOGGER.info("Activation mail sent!");
        } catch (MailException e) {
            LOGGER.error("Exception while sending mail",e);
            throw new WykopCloneException("Exception occurred while sending mail to " + authorizationMail.getTo());
        }
    }

}



