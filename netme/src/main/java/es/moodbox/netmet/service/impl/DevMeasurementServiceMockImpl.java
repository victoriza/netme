package es.moodbox.netmet.service.impl;

import es.moodbox.netmet.service.DevMeasurementService;

public class DevMeasurementServiceMockImpl implements DevMeasurementService{

	private final static int MAX = 100;
	private final static int MIN = 0;

	public double obtainMeasurement(int deviceId)throws InterruptedException {
		long sleep = Math.round(Math.random() * ( MAX - MIN ));
		Thread.sleep(sleep);

		return Math.round(Math.random() * ( MIN - MAX ));
	}

}