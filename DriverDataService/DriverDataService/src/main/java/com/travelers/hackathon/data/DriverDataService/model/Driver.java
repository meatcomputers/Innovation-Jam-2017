package com.travelers.hackathon.data.DriverDataService.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name="driver")
public class Driver {

	@Id
    @GeneratedValue
	private Long driverId;
	
	private String firstName; 
	
	private String lastName; 
	
	private Integer safetyLevel;

	public Long getDriverId() {
		return driverId;
	}

	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getSafetyLevel() {
		return safetyLevel;
	}

	public void setSafetyLevel(Integer safetyLevel) {
		this.safetyLevel = safetyLevel;
	}

}
