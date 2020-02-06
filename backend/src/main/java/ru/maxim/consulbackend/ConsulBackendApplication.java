package ru.maxim.consulbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ConsulBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsulBackendApplication.class, args);
	}

}
