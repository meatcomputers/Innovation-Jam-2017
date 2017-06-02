package com.travelers.hackathon.data.DriverDataService.controller;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.travelers.hackathon.data.DriverDataService.model.TripData;

public interface TripDataRepository extends PagingAndSortingRepository<TripData, Long> {

	@RestResource(path = "by-tripID")
	List<TripData> findByTripID(@Param("tripID") Long tripID);
	
	@RestResource(path = "by-driverIdAndTimestampBetween")
	List<TripData> findByDriverIdAndTimeStampBetween(
			@Param("driverId") Integer driverId, 
			@Param("startTime") @JsonFormat(pattern="MM/dd/yyyy kk:mm:ss") Date startTime, 
			@Param("stopTime") @JsonFormat(pattern="MM/dd/yyyy kk:mm:ss") Date stopTime);
	
	@RestResource(path = "by-driverIdAndTimestampAfter")
	List<TripData> findByDriverIdAndTimeStampAfter(
			@Param("driverId") Integer driverId, 
			@Param("timeStamp") @JsonFormat(pattern="MM/dd/yyyy kk:mm:ss") Date timeStamp);
}
