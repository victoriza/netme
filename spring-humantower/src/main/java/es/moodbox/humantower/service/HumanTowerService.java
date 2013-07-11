package es.moodbox.humantower.service;

import org.springframework.stereotype.Service;

@Service
public interface HumanTowerService {

	double getHumanEdgeWeight(int level, int index);
	
	double getHumanEdgeWeight(int level);
}
