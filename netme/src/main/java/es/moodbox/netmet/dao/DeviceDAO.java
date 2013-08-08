package es.moodbox.netmet.dao;

import java.util.Date;
import java.util.List;

public interface DeviceDAO {

	public List<Integer> findAll();

	public void save(int deviceId, double measurement , Date date);
}