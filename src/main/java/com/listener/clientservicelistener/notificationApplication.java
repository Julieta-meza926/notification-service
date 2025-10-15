package com.listener.clientservicelistener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class notificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(notificationApplication.class, args);
	}
}
