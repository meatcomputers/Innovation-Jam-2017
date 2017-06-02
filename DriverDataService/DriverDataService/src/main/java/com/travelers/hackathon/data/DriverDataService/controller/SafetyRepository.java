package com.travelers.hackathon.data.DriverDataService.controller;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.travelers.hackathon.data.DriverDataService.model.Safety;

@RepositoryRestResource(collectionResourceRel = "safeties", path = "safeties")
public interface SafetyRepository extends PagingAndSortingRepository<Safety, Long> {
	@RestResource(path = "by-safetyLevel")
	List<Safety> findBySafetyLevel(@Param("safetyLevel") Long safetyLevel);

}
