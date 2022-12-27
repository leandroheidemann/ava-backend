package net.unibave.avaapi.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class QueueListiner {
    @RabbitListener(queues = "test")
    public void getString(String message) throws InterruptedException {
        Thread.sleep(5000);

        System.out.println(message);
    }
}
