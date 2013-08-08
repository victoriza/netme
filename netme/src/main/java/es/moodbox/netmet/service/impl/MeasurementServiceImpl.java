package es.moodbox.netmet.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import es.moodbox.netmet.dao.DeviceDAO;
import es.moodbox.netmet.dao.impl.DeviceDAOImpl;
import es.moodbox.netmet.service.DevMeasurementService;
import es.moodbox.netmet.service.MeasurementService;

public class MeasurementServiceImpl implements MeasurementService {

	private final static DevMeasurementService devMeasurement = new DevMeasurementServiceMockImpl();

	private final static DeviceDAO deviceDAO = new DeviceDAOImpl();

	//TODO: get the value from the params
	private static int MAX_EXECUTORS = 4;

	//TODO: Define max waiting time with bussines
	private final static int MAX_WAITING_TIME = 10;

	private static final ExecutorService mExecutor = Executors.newFixedThreadPool(MAX_EXECUTORS);

	/**
	 * Does the measurement of given devices in a non blocking way.
	 * It waits a fix amount of time for completition
	 *
	 * @param devicesId all the devices Id to do the measurement
	 * @throws InterruptedException if the method is not able to finish
	 * all the measurements in a fixed amount of time
	 *
	 * @author Víctor Suárez
	 */
	public void doMeasurements(List<Integer> devicesId) throws InterruptedException{
		Date now = new Date();
		for (Integer aux : devicesId) {
			mExecutor.execute(new MeasureReader(aux , now));
		}
		// the executor won t accept more new threads
		// and will finish ALL existing in queue
		mExecutor.shutdown();

		//Wait until the max allowed time
		mExecutor.awaitTermination(MAX_WAITING_TIME, TimeUnit.SECONDS);
	}

	/**
	 * MeasureReader simply calls doMeasurement and when ready storeMeasurement
	 * in a non bloking way
	 *
	 * @throws InterruptedException if the device is not measured
	 * @author Víctor Suárez
	 */
	public class MeasureReader implements Runnable {

		private final int deviceId;
		private final Date mDate;

		MeasureReader(int devId , Date date) {
			this.deviceId = devId;
			this.mDate = date;
		}
		public void run() {
			try {
				deviceDAO.save(deviceId , devMeasurement.obtainMeasurement(deviceId) , mDate);
			} catch (InterruptedException e) {
				//TODO:
				//@somebody ask to remove the device from the next measurement
			}
		}
	}
}