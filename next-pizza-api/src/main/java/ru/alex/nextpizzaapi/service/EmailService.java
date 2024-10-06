package ru.alex.nextpizzaapi.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import ru.alex.nextpizzaapi.dto.email.EmailDto;

import java.nio.charset.StandardCharsets;

@Service
public class EmailService {
    private final JavaMailSender sender;
    private final SpringTemplateEngine templateEngine;

    @Autowired
    public EmailService(JavaMailSender sender,
                        SpringTemplateEngine templateEngine) {
        this.sender = sender;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendEmail(EmailDto email) {
        MimeMessage message = sender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED,
                    StandardCharsets.UTF_8.name()
            );
            Context context = new Context();
            context.setVariables(email.context());
            String emailContent = templateEngine.process(email.templateLocation(), context);

            messageHelper.setTo(email.to());
            messageHelper.setSubject(email.subject());
            messageHelper.setText(emailContent, true);
            sender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
