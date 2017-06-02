package com.travelers.hackathon.data.DriverDataService.generate.sampleData;

public class GenerateDataForStartAndStop {

	public float[] velocityOverTime(int sampleTimeSeconds, int totalSeconds, float maxSpeedMetersPerSecond,
			float accelerationMetersPerSecondSecond, float decelerationMetersPerSecondSecond, float startVelocity,
			float stopVelocity) {

		int totalSamples = getTotalSamples(totalSeconds, sampleTimeSeconds);
		// We will return totalSamples + 1=> because of the t=0.

		/* Figure out how long we need to accelerate for */
		int secondsToAccelerate = getSecondsToDeltaV(startVelocity, maxSpeedMetersPerSecond,
				accelerationMetersPerSecondSecond);
		int samplesToAccelerate = getTotalSamples(secondsToAccelerate, sampleTimeSeconds);
		/* Figure out how long we need to decelerate for */
		int secondsToDecelerate = getSecondsToDeltaV(maxSpeedMetersPerSecond, stopVelocity,
				decelerationMetersPerSecondSecond);
		int samplesToDecelerate = getTotalSamples(secondsToDecelerate, sampleTimeSeconds);

		if (samplesToAccelerate + samplesToDecelerate > totalSamples) {
			throw new IndexOutOfBoundsException("We cants start and stop in this amount of time: "
					+ maxSpeedMetersPerSecond + "m/s in" + totalSeconds + "s");
		}

		/* Lets populate the list */
		float[] samples = new float[totalSamples];
		// /*Populate t=0 first. */
		// samples[0] = startVelocity;
		int sampleToStartStopping = totalSamples - samplesToDecelerate;
		float currentVelocity = startVelocity;
		for (int i = 0; i < totalSamples; i++) {
			if (i <= samplesToAccelerate) {
				currentVelocity += (accelerationMetersPerSecondSecond * sampleTimeSeconds);
				if (currentVelocity > maxSpeedMetersPerSecond) {
					currentVelocity = maxSpeedMetersPerSecond;
				}
			} else if (i >= sampleToStartStopping) {
				currentVelocity += (decelerationMetersPerSecondSecond * sampleTimeSeconds);
				if (currentVelocity < stopVelocity) {
					currentVelocity = stopVelocity;
				}
			} else {
				currentVelocity = maxSpeedMetersPerSecond;
			}
			samples[i] = currentVelocity;

		}

		return samples;
	}

	public int getTotalSamples(int totalSeconds, int sampleTimeSeconds) {
		int totalSamples = (totalSeconds / sampleTimeSeconds);
		if ((totalSeconds % sampleTimeSeconds) > 0) {
			totalSamples++;
		}
		return totalSamples;
	}

	public int getSecondsToDeltaV(float currentVelocity, float targetVelocity, float acceleration) {
		float deltaV = targetVelocity - currentVelocity;
		int seconds = (int) (deltaV / acceleration);
		if (deltaV % acceleration != 0) {
			seconds++;
		}
		return seconds;
	}

	/**
	 * Converts MPH to MPS
	 * 
	 * @param milesPerHour
	 */
	public static final float metersPerSecond(float milesPerHour) {
		return milesPerHour * 0.44704f;
	}

}