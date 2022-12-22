package net.unibave.avaapi.services;

import net.unibave.avaapi.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    private final SpringTemplateEngine templateEngine;
    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(SpringTemplateEngine templateEngine, JavaMailSender mailSender) {
        this.templateEngine = templateEngine;
        this.mailSender = mailSender;
    }

    @Async
    public void sendConfirmEmail(User user) throws MessagingException {
        Map<String, Object> data = new HashMap<>();
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        data.put("userName", user.getName());

        Context context = new Context();
        context.setVariables(data);

        String html = templateEngine.process("confirm-email.html", context);

        helper.setTo(user.getEmail());
        helper.setText(html, true);
        helper.setSubject("Confirmação de cadastro");
        helper.setFrom("<Unibave.net> notreply@unibave.net");

        mailSender.send(message);
    }
}
