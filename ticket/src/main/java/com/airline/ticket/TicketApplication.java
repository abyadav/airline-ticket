package com.airline.ticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)  // Uses CGLIB proxies

public class TicketApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketApplication.class, args);
	}

}
