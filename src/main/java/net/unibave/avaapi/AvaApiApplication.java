package net.unibave.avaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class AvaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AvaApiApplication.class, args);
	}

}
