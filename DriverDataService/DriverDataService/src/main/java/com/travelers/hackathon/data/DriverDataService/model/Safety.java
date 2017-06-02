package com.travelers.hackathon.data.DriverDataService.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name="safety")
public class Safety {

	@Id
    @GeneratedValue
	private Long safetyLevel; 
	
	private Float maxAccel; 
	
	private Float maxDecel; 
	
	private Float maxSpeed; 
	
	private Float driverlessPct;

	public Long getSafetyLevel() {
		return safetyLevel;
	}

	public void setSafetyLevel(Long safetyLevel) {
		this.safetyLevel = safetyLevel;
	}

	public Float getMaxAccel() {
		return maxAccel;
	}

	public void setMaxAccel(Float maxAccel) {
		this.maxAccel = maxAccel;
	}

	public Float getMaxDecel() {
		return maxDecel;
	}

	public void setMaxDecel(Float maxDecel) {
		this.maxDecel = maxDecel;
	}

	public Float getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(Float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public Float getDriverlessPct() {
		return driverlessPct;
	}

	public void setDriverlessPct(Float driverlessPct) {
		this.driverlessPct = driverlessPct;
	} 
	
	
	
}
