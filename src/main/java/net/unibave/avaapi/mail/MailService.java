package net.unibave.avaapi.mail;

import io.pebbletemplates.pebble.PebbleEngine;
import net.unibave.avaapi.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class MailService {

    private final JavaMailSender mailSender;
    private final PebbleEngine pebbleEngine;

    @Autowired
    public MailService(JavaMailSender mailSender, PebbleEngine pebbleEngine) {
        this.mailSender = mailSender;
        this.pebbleEngine = pebbleEngine;
    }

    public void sendMail(String to, String subject, String body) {
        final var message = mailSender.createMimeMessage();
        try {
            final var helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setFrom("<Unibave.net> notreply@ava.unibave.net");
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

        var template = pebbleEngine.getTemplate("confirm-email");

        Map<String, Object> context = new HashMap<>();
        context.put("userName", user.getName());
        Writer writer = new StringWriter();

        try {
            template.evaluate(writer, context);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        helper.setTo(user.getEmail());
        helper.setText(writer.toString(), true);
        helper.setSubject("Confirmação de cadastro");
        helper.setFrom("<Unibave.net> notreply@ava.unibave.net");

        mailSender.send(message);
    }
}
