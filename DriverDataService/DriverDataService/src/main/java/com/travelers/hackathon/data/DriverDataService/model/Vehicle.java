package com.travelers.hackathon.data.DriverDataService.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name="vehicle")
public class Vehicle {

	@Id
    @GeneratedValue
	private Long vehicleId; 
	
	private String make; 
	
	private String model; 
	
	private Float premiumFactor;

	public Long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Float getPremiumFactor() {
		return premiumFactor;
	}

	public void setPremiumFactor(Float premiumFactor) {
		this.premiumFactor = premiumFactor;
	} 
	
}
