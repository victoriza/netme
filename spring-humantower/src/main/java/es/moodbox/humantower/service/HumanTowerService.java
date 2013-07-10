package es.moodbox.humantower.service;

import org.springframework.stereotype.Service;

@Service
public interface HumanTowerService {

	Double getHumanEdgeWeight(int level, int index);
	
	Double getHumanEdgeWeight(int level);
}
