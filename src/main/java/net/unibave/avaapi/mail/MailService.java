package net.unibave.avaapi.mail;

import net.unibave.avaapi.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class MailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Autowired
    public MailService(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void sendMail(String to, String subject, String body) {
        final var message = mailSender.createMimeMessage();
        try {
            final var helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setFrom("notreply@ava.unibave.net");
            helper.setSubject(subject);
            helper.setText(body);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

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
