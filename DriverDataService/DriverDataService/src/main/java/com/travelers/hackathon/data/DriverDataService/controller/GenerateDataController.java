package com.travelers.hackathon.data.DriverDataService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.travelers.hackathon.data.DriverDataService.generate.sampleData.GenerateTripsForCustomers;

@RestController
public class GenerateDataController {
	
	@Autowired 
	GenerateTripsForCustomers generateTripsController; 

	@RequestMapping(value = "/generateFor/{customerId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, "application/hal+json" })
	public String generateDataForUser() {
		try {
//			generateTripsController.generateDataForDriverOne();
			return "success"; 
		} catch (Exception e) {
			e.printStackTrace();
			return "failure: " + e.getLocalizedMessage(); 
		}
	}

}
