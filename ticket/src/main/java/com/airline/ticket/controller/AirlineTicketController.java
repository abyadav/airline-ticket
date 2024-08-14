package com.airline.ticket.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.airline.ticket.service.AirlineTicketService;
import com.zaxxer.hikari.HikariDataSource;

@RestController
@RequestMapping("/airline")
public class AirlineTicketController {

	@Autowired
	private AirlineTicketService airlineTicketService;
	

    @Autowired
    private HikariDataSource dataSource;

    public void printConnectionPoolStatus() {
        System.out.println("Maximum Pool Size: " + dataSource.getMaximumPoolSize());
        System.out.println("Active Connections: " + dataSource.getHikariPoolMXBean().getActiveConnections());
        System.out.println("Idle Connections: " + dataSource.getHikariPoolMXBean().getIdleConnections());
        System.out.println("Total Connections: " + dataSource.getHikariPoolMXBean().getTotalConnections());
        System.out.println("Threads Awaiting Connection: " + dataSource.getHikariPoolMXBean().getThreadsAwaitingConnection());
    }
	
	
	@RequestMapping(path = "/book", method = RequestMethod.POST)
	public void bookTickets() {
		long  startTime = System.currentTimeMillis();
	ExecutorService executorService = Executors.newFixedThreadPool(50);
		
		for(int i=0;i<10201;i++) {
			final Integer j = i;
			executorService.submit(()->airlineTicketService.bookTicket(j));
		}
	     executorService.shutdown();

	        try {
	            // Wait for all tasks to complete or timeout
	            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
	                executorService.shutdownNow();
	            }
	        } catch (InterruptedException e) {
	            executorService.shutdownNow();
	            Thread.currentThread().interrupt();
	        }

	        // Stop the timer
	        long endTime = System.currentTimeMillis();

	        // Calculate and print the execution time
	        long executionTime = endTime - startTime;
	        System.out.println("Overall execution time: " + executionTime + " ms");
	        printConnectionPoolStatus();
	}
}
