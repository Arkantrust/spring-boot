package me.arkantrust.springtemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringApp {

	public static void main(String[] args) {
		SpringApplication.run(SpringApp.class, args);
		System.out.println("\nRunning at https://127.0.0.1:8080\n");
	}

}
