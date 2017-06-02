package com.travelers.hackathon.data.DriverDataService.controller;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.travelers.hackathon.data.DriverDataService.model.Safety;
import com.travelers.hackathon.data.DriverDataService.model.Vehicle;

public interface VehicleRepository extends PagingAndSortingRepository<Vehicle, Long> {
	@RestResource(path = "by-vehicleId")
	List<Safety> findByVehicleId(@Param("vehicleId") Long vehicleId);
	
}
