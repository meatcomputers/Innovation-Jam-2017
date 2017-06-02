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
@Table(name="collision")
public class Collision {

	@Id
    @GeneratedValue
	private Long collisionID;
	
	private String policy; 
	
	private Long driverId; 
	
	private Long vehicleId; 

	@JsonFormat(pattern="MM/dd/yyyy kk:mm:ss")
	private Date timestamp;

	public Long getCollisionID() {
		return collisionID;
	}

	public void setCollisionID(Long collisionID) {
		this.collisionID = collisionID;
	}

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	public Long getDriverId() {
		return driverId;
	}

	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}

	public Long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	} 
}
