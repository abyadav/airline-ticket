package com.airline.ticket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.airline.ticket.entity.AirlineTicket;
import com.airline.ticket.repo.AirlineTicketRepo;

@Service("airlineTicketService")
public class AirlineTicketServiceImpl implements AirlineTicketService {

	@Autowired
	private AirlineTicketRepo airlineTicketRepo;

	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	@Override
	public void bookTicket(Integer customerId) {
		try {
			List<AirlineTicket> ab = airlineTicketRepo.getUnbooked(100);
			AirlineTicket a = ab.get(0);
			a.setBooked(1);
			a.setCustomerId(customerId);
			a.setUpdatedCount(a.getUpdatedCount() + 1);
			airlineTicketRepo.saveAndFlush(a);
		} catch (Exception e) {

			System.out.println("Exception: ");
			System.out.println(e);
		}
	}

}
