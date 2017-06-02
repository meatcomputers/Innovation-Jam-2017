package com.travelers.hackathon.data.DriverDataService.generate.sampleData;

import java.util.ArrayList;
import java.util.List;

public class GenerateDataForTrip {

	private GenerateDataForStartAndStop startAndStop;

	private static final int SECONDS_PER_STOP = 45;

	public GenerateDataForTrip(GenerateDataForStartAndStop startAndStop) {
		super();
		this.startAndStop = startAndStop;
	}

	public float[] velocityOverTrip(int sampleTimeSeconds, int totalSeconds, float maxSpeedMetersPerSecond,
			float accelerationMetersPerSecondSecond, float decelerationMetersPerSecondSecond, int numberOfStops) {

		int numberOfSamples = startAndStop.getTotalSamples(totalSeconds, sampleTimeSeconds);
		int samplesPerStop = startAndStop.getTotalSamples(SECONDS_PER_STOP, sampleTimeSeconds);
		int samplesForTrips = numberOfSamples - (samplesPerStop * numberOfStops);
		int numberOfArrays = 1 + (numberOfStops * 2); // A trip is either a
														// round trip or a stop.
		List<float[]> trips = new ArrayList<float[]>(numberOfArrays);
		int samplesPerTrip = samplesForTrips / (numberOfStops + 1);
		int samplesToAddToFirstTrip = samplesForTrips % (numberOfStops + 1); // Add
																				// the
																				// remaining
																				// samples
																				// to
																				// the
																				// last
																				// trip.
		trips.add(startAndStop.velocityOverTime(sampleTimeSeconds,
				(samplesPerTrip + samplesToAddToFirstTrip) * sampleTimeSeconds, maxSpeedMetersPerSecond,
				accelerationMetersPerSecondSecond, decelerationMetersPerSecondSecond, 0.0f, 0.0f));

		for (int i = 0; i < numberOfStops; i++) {
			trips.add(getStopTimeSamples(samplesPerStop));
			trips.add(startAndStop.velocityOverTime(sampleTimeSeconds, samplesPerTrip * sampleTimeSeconds,
					maxSpeedMetersPerSecond, accelerationMetersPerSecondSecond, decelerationMetersPerSecondSecond, 0.0f,
					0.0f));
		}
		/* Combine all the data into a single array of data */
		float[] samples = new float[numberOfSamples];
		int currentIndex = 0;
		for (float[] nextSamples : trips) {
			for (float nextData : nextSamples) {
				samples[currentIndex++] = nextData;
			}
		}

		return samples;
	}

	/**
	 * Returns an array of '0' velocity for the number of samples in a standard
	 * stop.
	 * 
	 * @param samplesPerStop
	 * @return
	 */
	public float[] getStopTimeSamples(int samplesPerStop) {
		float[] stopTimes = new float[samplesPerStop];
		for (int i = 0; i < samplesPerStop; i++) {
			stopTimes[i] = 0.0f;
		}
		return stopTimes;
	}

}