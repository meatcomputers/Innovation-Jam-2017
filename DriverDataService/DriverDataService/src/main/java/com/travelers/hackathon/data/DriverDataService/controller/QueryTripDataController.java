package com.travelers.hackathon.data.DriverDataService.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.TemporalType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.travelers.hackathon.data.DriverDataService.model.TripData;

@RestController
public class QueryTripDataController {
	
	@Autowired
	TripDataRepository repository; 

	@RequestMapping(value = "/findTripDataByDriverIdForPreviousMinutes", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, "application/hal+json" })
	public List<TripData> getTripDataForPreviousMinutes(
				@Param("driverId") Integer driverId, 
				@Param("endTime") @JsonFormat(pattern="MM/dd/yyyy kk:mm:ss") Date endTime, 
				@Param("numMinutes") int numMinutes
			) 
	{
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy kk:mm:ss"); 
		System.out.println("Getting data before " + format.format(endTime));
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(endTime);
		cal.add(Calendar.MINUTE, (numMinutes * -1)); 
		System.out.println("Getting data after" + format.format(cal.getTime()));
		List<TripData> ret = repository.findByDriverIdAndTimeStampBetween(driverId, cal.getTime(), endTime);
		System.out.println("The first result is id: " + ret.get(0).getTripID() + " - " + format.format(ret.get(0).getTimeStamp()));
		return ret; 
	}
	
}
