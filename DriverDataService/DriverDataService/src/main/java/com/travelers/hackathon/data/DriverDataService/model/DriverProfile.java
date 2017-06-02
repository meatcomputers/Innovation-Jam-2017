package com.travelers.hackathon.data.DriverDataService.model;

import java.util.Date;

public class DriverProfile {

	private int driverId;

	private int passengersPerDay;

	private float acceleration;

	private float deceleration;

	private Date[] tripStartTimes;

	public int getDriverId() {
		return driverId;
	}

	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}

	public int getPassengersPerDay() {
		return passengersPerDay;
	}

	public void setPassengersPerDay(int passengersPerDay) {
		this.passengersPerDay = passengersPerDay;
	}

	public float getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(float acceleration) {
		this.acceleration = acceleration;
	}

	public float getDeceleration() {
		return deceleration;
	}

	public void setDeceleration(float deceleration) {
		this.deceleration = deceleration;
	}

	public Date[] getTripStartTimes() {
		return tripStartTimes;
	}

	public void setTripStartTimes(Date[] tripStartTimes) {
		this.tripStartTimes = tripStartTimes;
	}
}