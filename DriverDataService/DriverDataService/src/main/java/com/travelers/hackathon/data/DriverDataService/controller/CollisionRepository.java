package com.travelers.hackathon.data.DriverDataService.controller;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.travelers.hackathon.data.DriverDataService.model.Collision;
import com.travelers.hackathon.data.DriverDataService.model.TripData;

public interface CollisionRepository extends PagingAndSortingRepository<Collision, Long>  {

	@RestResource(path = "by-collisionID")
	List<TripData> findByCollisionID(@Param("collisionID") Long collisionID);
	
	@RestResource(path = "by-driverId")
	List<TripData> findByDriverId(@Param("driverId") Long driverId);
	
	@RestResource(path = "by-vehicleId")
	List<TripData> findByVehicleId(@Param("vehicleId") Long vehicleId);
}
