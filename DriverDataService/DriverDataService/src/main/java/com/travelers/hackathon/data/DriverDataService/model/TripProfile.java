package com.travelers.hackathon.data.DriverDataService.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TripProfile {

	private Date startTime;
	private int sampleTimeSeconds;
	private int totalSeconds;
	private float maxSpeedMetersPerSecond;
	private float accelerationMetersPerSecondSecond;
	private float decelerationMetersPerSecondSecond;
	private int numberOfStops;
	private int numberOfUberPassengers;
	private List<int[]> selfDriveStartAndStops;
	boolean isRideShare; 

	public TripProfile(DriverProfile driverProfile) {
		super();
		this.accelerationMetersPerSecondSecond = driverProfile.getAcceleration() * getAccelerationRandomModifier();
		this.decelerationMetersPerSecondSecond = driverProfile.getDeceleration() * getAccelerationRandomModifier();
	}

	/**
	 * Returns an acceleration modifier that is between -.3 and .3
	 * 
	 * @return
	 */
	private float getAccelerationRandomModifier() {
		return (float) ((Math.random() - .5) * 0.3d);
	}

	public TripProfile() {
		super();
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public int getSampleTimeSeconds() {
		return sampleTimeSeconds;
	}

	public void setSampleTimeSeconds(int sampleTimeSeconds) {
		this.sampleTimeSeconds = sampleTimeSeconds;
	}

	public int getTotalSeconds() {
		return totalSeconds;
	}

	public void setTotalSeconds(int totalSeconds) {
		this.totalSeconds = totalSeconds;
	}

	public float getMaxSpeedMetersPerSecond() {
		return maxSpeedMetersPerSecond;
	}

	public void setMaxSpeedMetersPerSecond(float maxSpeedMetersPerSecond) {
		this.maxSpeedMetersPerSecond = maxSpeedMetersPerSecond;
	}

	public float getAccelerationMetersPerSecondSecond() {
		return accelerationMetersPerSecondSecond;
	}

	public void setAccelerationMetersPerSecondSecond(float accelerationMetersPerSecondSecond) {
		this.accelerationMetersPerSecondSecond = accelerationMetersPerSecondSecond;
	}

	public float getDecelerationMetersPerSecondSecond() {
		return decelerationMetersPerSecondSecond;
	}

	public void setDecelerationMetersPerSecondSecond(float decelerationMetersPerSecondSecond) {
		this.decelerationMetersPerSecondSecond = decelerationMetersPerSecondSecond;
	}

	public int getNumberOfStops() {
		return numberOfStops;
	}

	public void setNumberOfStops(int numberOfStops) {
		this.numberOfStops = numberOfStops;
	}

	public int getNumberOfUberPassengers() {
		return numberOfUberPassengers;
	}

	public void setNumberOfUberPassengers(int numberOfUberPassengers) {
		this.numberOfUberPassengers = numberOfUberPassengers;
	}

	public void setMaxSpeedMilesPerHour(float maxSpeedMilesPerHour) {
		this.setMaxSpeedMetersPerSecond(maxSpeedMilesPerHour * 0.44704f);
	}

	public List<int[]> getSelfDriveStartAndStops() {
		if (selfDriveStartAndStops != null) {
			selfDriveStartAndStops = new ArrayList<int[]>(2);
		}
		return selfDriveStartAndStops;
	}

	public boolean isRideShare() {
		return isRideShare;
	}

	public void setRideShare(boolean isRideShare) {
		this.isRideShare = isRideShare;
	}
}