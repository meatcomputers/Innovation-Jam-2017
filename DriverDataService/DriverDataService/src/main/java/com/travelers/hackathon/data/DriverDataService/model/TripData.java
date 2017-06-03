package com.travelers.hackathon.data.DriverDataService.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonFormat;

@XmlRootElement
@Entity
@Table(name="tripData")
public class TripData {

	@Id
    @GeneratedValue
	private Long tripID; 
	
	private Integer driverId; 
	
	private Integer vehicleId; 

	@JsonFormat(pattern="MM/dd/yyyy kk:mm:ss", timezone = "GMT-4")
	private Date timeStamp; 
	
	private Float speed;
	
	private Boolean driverless;
	
	private Boolean rideShare; 

	public Long getTripID() {
		return tripID;
	}

	public void setTripID(Long tripID) {
		this.tripID = tripID;
	}

	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	public Integer getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Integer vehicleId) {
		this.vehicleId = vehicleId;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Float getSpeed() {
		return speed;
	}

	public void setSpeed(Float speed) {
		this.speed = speed;
	}

	public Boolean getDriverless() {
		return driverless;
	}

	public void setDriverless(Boolean driverless) {
		this.driverless = driverless;
	}

	public Boolean getRideShare() {
		return rideShare;
	}

	public void setRideShare(Boolean rideShare) {
		this.rideShare = rideShare;
	}
	
}
