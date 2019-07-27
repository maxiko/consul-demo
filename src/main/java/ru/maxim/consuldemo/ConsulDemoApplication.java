package ru.maxim.consuldemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ConsulDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsulDemoApplication.class, args);
	}

}
