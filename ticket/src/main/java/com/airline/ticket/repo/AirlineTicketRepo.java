package com.airline.ticket.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.airline.ticket.entity.AirlineTicket;

@Repository
public interface AirlineTicketRepo extends JpaRepository<AirlineTicket, Integer> {

	@Query( value =  "select * from air_tick at  where customer_id is null and booked=0 limit 1 for update skip locked", nativeQuery = true)
	public List<AirlineTicket> getUnbooked( Integer customerId);
	
	public AirlineTicket findFirstById(Integer id);
}
