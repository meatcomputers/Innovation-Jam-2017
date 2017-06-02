package com.travelers.hackathon.data.DriverDataService.generate.sampleData;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.travelers.hackathon.data.DriverDataService.controller.TripDataRepository;
import com.travelers.hackathon.data.DriverDataService.model.DriverProfile;
import com.travelers.hackathon.data.DriverDataService.model.TripData;
import com.travelers.hackathon.data.DriverDataService.model.TripProfile;

@Component
public class GenerateTripsForCustomers {
	
	@Autowired
	TripDataRepository tripDataRepository; 

	private static final int SAMPLE_TIME_SECONDS = 15;

	public static void main(String[] args) throws Exception {
		new GenerateTripsForCustomers().generateDataForDriverOne();
	}

	public void generateDataForDriverOne() throws Exception {
		DriverProfile driverProfile = new DriverProfile();
		driverProfile.setDriverId(1);
		driverProfile.setAcceleration(3.1f);
		driverProfile.setDeceleration(-2.4f);
		driverProfile.setPassengersPerDay(2);

		Calendar tripDate = Calendar.getInstance();
		tripDate.set(2020, Calendar.JUNE, 02, 8, 30, 0);
		TripProfile weekdayCommute = new TripProfile(driverProfile);
		weekdayCommute.setStartTime(tripDate.getTime());
		weekdayCommute.setNumberOfStops(5);
		weekdayCommute.setNumberOfUberPassengers(0);
		weekdayCommute.setMaxSpeedMilesPerHour(75);
		weekdayCommute.setAccelerationMetersPerSecondSecond(3.1f);
		weekdayCommute.setDecelerationMetersPerSecondSecond(-2.8f);
		weekdayCommute.setSampleTimeSeconds(SAMPLE_TIME_SECONDS);
		weekdayCommute.setTotalSeconds(25 * 60);

		TripProfile saturdayDrive = new TripProfile(driverProfile);
		saturdayDrive.setStartTime(tripDate.getTime());
		saturdayDrive.setNumberOfStops(3);
		saturdayDrive.setNumberOfUberPassengers(0);
		saturdayDrive.setMaxSpeedMilesPerHour(25);
		saturdayDrive.setAccelerationMetersPerSecondSecond(2.7f);
		saturdayDrive.setDecelerationMetersPerSecondSecond(-2.8f);
		saturdayDrive.setSampleTimeSeconds(SAMPLE_TIME_SECONDS);
		saturdayDrive.setTotalSeconds(5 * 60);

		FileWriter fw = new FileWriter("Data/DriverData_01.csv");
//		FileWriter fw = new FileWriter("Data/DriverData_01.json");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write("driverID, timestamp, velocity, driverless\n");
//		bw.write("data: {[\n");
		
		for (int j = 0; j < 4; j++) {
			for (int i = 0; i < 5; i++) {
				tripDate.set(Calendar.HOUR, 8);
				tripDate.set(Calendar.MINUTE, 30);
				weekdayCommute.setStartTime(tripDate.getTime());
				generateTripData(driverProfile, weekdayCommute, bw, .5f);

				tripDate.add(Calendar.HOUR, 9);
				weekdayCommute.setStartTime(tripDate.getTime());
				generateTripData(driverProfile, weekdayCommute, bw, .5f);

				tripDate.add(Calendar.DATE, 1);
			}

			tripDate.set(Calendar.HOUR, 11);
			tripDate.set(Calendar.MINUTE, 30);
			saturdayDrive.setStartTime(tripDate.getTime());
			generateTripData(driverProfile, saturdayDrive, bw, .5f);
			tripDate.add(Calendar.DATE, 1);

		}

		bw.write("]}\n");

	}

	public static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

	public void generateTripData(DriverProfile profile, TripProfile trip, BufferedWriter bw, float percentDriverless) throws Exception {
		float[] tripPoints = new GenerateDataForTrip(new GenerateDataForStartAndStop()).velocityOverTrip(
				trip.getSampleTimeSeconds(), trip.getTotalSeconds(), trip.getMaxSpeedMetersPerSecond(),
				trip.getAccelerationMetersPerSecondSecond(), trip.getDecelerationMetersPerSecondSecond(),
				trip.getNumberOfStops());

		int startDriverless = (int) (tripPoints.length * (percentDriverless / 2));
		int stopDriverless = (int) (tripPoints.length * (percentDriverless / 2) * 3);
		Date currentTime = trip.getStartTime();
		for (int i = 0; i < tripPoints.length; i++) {
			float tripPoint = tripPoints[i];
			currentTime = new Date(currentTime.getTime() + 1000 * trip.getSampleTimeSeconds());
			boolean driverless = ((startDriverless <= i) && (i <= stopDriverless)); 
			bw.write(
					profile.getDriverId() 
					+ ",1"
					+ "," + dateFormat.format(currentTime) 
					+ "," + tripPoint 
					+ "," + driverless
					+ "\n");
//			bw.write("{ driverId: " + profile.getDriverId() 
//				+ ", vehicleId: " + 1
//				+ ", timestamp: " + dateFormat.format(currentTime) 
//				+ ", velocity: " + tripPoint
//				+ ", driverless: " + driverless  
//				+ " }, \n");
			TripData tripData = new TripData(); 
			tripData.setDriverId(profile.getDriverId());
			tripData.setVehicleId(1);
			tripData.setSpeed(tripPoint);
			tripData.setTimeStamp(currentTime);
			tripData.setDriverless(driverless);
			tripDataRepository.save(tripData);
		}

	}
}