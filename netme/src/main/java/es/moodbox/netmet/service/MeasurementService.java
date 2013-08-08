package es.moodbox.netmet.service;

import java.util.List;

public interface MeasurementService {

	public void doMeasurements(List<Integer> devicesId) throws InterruptedException;

}