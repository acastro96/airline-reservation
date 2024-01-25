package com.dhanush.airline.util;

import com.dhanush.airline.dao.FlightDao;
import com.dhanush.airline.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.time.LocalDate;
import java.util.Date;

public class DataLoader implements ApplicationRunner {

    @Autowired
    private FlightDao flightDao;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Fare fare = new Fare();
        fare.setFare(150);
        fare.setCurrency("USD");
        //fare.setFareId(1);

        AirlineInfo airlineInfo = new AirlineInfo();
        //airlineInfo.setAirlineId(1);
        airlineInfo.setAirlineLogo("Avianca");
        airlineInfo.setNameOfAirline("Avianca");


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
        flight.setOrigin("PUNE");

        flightDao.save(flight);

    }
}
