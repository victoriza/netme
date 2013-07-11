package es.moodbox.humantower.service;

import org.springframework.stereotype.Service;

/**
 *  Provides the Human Tower solution for edges and level/index positions
 *	where the level is the pyramid level and the function return the total weight that the human on
 *	the edge of that level has on his back
 * 
 * @author victor
 *
 */
@Service
public interface HumanTowerService {

	double getHumanEdgeWeight(int level, int index);
	
	double getHumanEdgeWeight(int level);
}
