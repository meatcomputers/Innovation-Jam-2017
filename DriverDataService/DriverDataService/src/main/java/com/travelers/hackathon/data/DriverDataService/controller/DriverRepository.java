package com.travelers.hackathon.data.DriverDataService.controller;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.travelers.hackathon.data.DriverDataService.model.Driver;

@RepositoryRestResource(collectionResourceRel = "drivers", path = "drivers")
public interface DriverRepository extends PagingAndSortingRepository<Driver, Long> {
	@RestResource(path = "by-idCustomers")
	List<Driver> findByDriverId(@Param("driverId") int driverId);
}
