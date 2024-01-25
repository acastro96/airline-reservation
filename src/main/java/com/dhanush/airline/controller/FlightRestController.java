package com.dhanush.airline.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import com.dhanush.airline.entity.*;
import com.dhanush.airline.service.AirlineInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dhanush.airline.service.FlightService;
import com.dhanush.airline.vo.SearchCriteria;

import javax.annotation.PostConstruct;

@RestController
public class FlightRestController {

	@Autowired
	private FlightService flightService;

	@Autowired
	private AirlineInfoService airlineInfoService;

	@PostConstruct
	public void saveInitial(){
		Fare fare = new Fare();
		fare.setFare(150);
		fare.setCurrency("USD");

		AirlineInfo airlineInfo = new AirlineInfo();
		airlineInfo.setAirlineLogo("Avianca");
		airlineInfo.setNameOfAirline("Avianca");

		airlineInfoService.save(airlineInfo);


		FlightInfo flightInfo = new FlightInfo();
		flightInfo.setFlightNumber("CDA321");
		flightInfo.setFlightType("Direct");
		flightInfo.setNumberofSeats(32);
		flightInfo.setAirlineInfo(airlineInfo);

		Inventory inventory = new Inventory();
		inventory.setCount(3);


		Flight flight = new Flight();
		flight.setDestination("MUMBAI");
		flight.setFlightDate(LocalDate.of(2024, 1, 29));
		flight.setDuration("3H");
		flight.setFare(fare);
		flight.setFlightNumber("CDA321");
		flight.setFlightInfo(flightInfo);
		flight.setInventory(inventory);
		flight.setFlightTime(LocalTime.of(8, 0));
		flight.setOrigin("PUNE");


		FlightInfo flightInfo2 = new FlightInfo();
		flightInfo2.setFlightNumber("CDA321");
		flightInfo2.setFlightType("Direct");
		flightInfo2.setNumberofSeats(32);
		flightInfo2.setAirlineInfo(airlineInfo);

		Inventory inventory2 = new Inventory();
		inventory2.setCount(3);


		Flight flight2 = new Flight();
		flight2.setDestination("PUNE");
		flight2.setFlightDate(LocalDate.of(2024, 1, 29));
		flight2.setDuration("3H");
		flight2.setFare(fare);
		flight2.setFlightNumber("CDA321");
		flight2.setFlightInfo(flightInfo);
		flight2.setInventory(inventory);
		flight2.setFlightTime(LocalTime.of(6, 30));
		flight2.setOrigin("MUMBAI");

		flightService.saveAll(Arrays.asList(flight, flight2));
	}

	@RequestMapping(value = "/searchFlights", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public List<Flight> searchFlights(@RequestBody SearchCriteria searchcriteria) {
		LocalDate flightDate = Instant.ofEpochMilli(searchcriteria.getFlightDate().getTime())
				.atZone(ZoneId.systemDefault()).toLocalDate();
		List<Flight> flights = flightService.getFlightsByOriginAndDestinationAndFlightDateOrderByFareFare(
				searchcriteria.getOrigin(), searchcriteria.getDestination(), flightDate, searchcriteria.getCount());
		return flights;
	}

	@RequestMapping(value = "/getFlightByNumberFlightDateFlightTime", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public Flight getFlightByNumberFlightDateFlightTime(@RequestBody Flight flight) {
		System.out.println(flight.getFlightNumber());
		System.out.println(flight.getFlightDate());
		System.out.println(flight.getFlightTime());
		return flightService.findByFlightNumberAndFlightDateAndFlightTime(flight.getFlightNumber(),
				flight.getFlightDate(), flight.getFlightTime());
	}

}
