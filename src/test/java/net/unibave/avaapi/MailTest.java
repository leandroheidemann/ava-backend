package net.unibave.avaapi;

import net.unibave.avaapi.mail.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MailTest {

    @Autowired
    private MailService mailSender;

    @Test
    void testSendMail() {
        mailSender.sendMail("email@test.com", "Test", "Test send mail");
    }
}
