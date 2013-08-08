import java.util.ArrayList;
import java.util.List;

import es.moodbox.netmet.service.MeasurementService;
import es.moodbox.netmet.service.impl.MeasurementServiceImpl;
import junit.framework.TestCase;

public class TestMeasurementService extends TestCase{

	public void testMeasurement()throws InterruptedException{
		List<Integer> devicesId = new ArrayList<Integer>();
		for (int i = 0; i < 1000; i++) {
			devicesId.add(i);
		}

		MeasurementService serv = new MeasurementServiceImpl();
		serv.doMeasurements(devicesId);
	}

}