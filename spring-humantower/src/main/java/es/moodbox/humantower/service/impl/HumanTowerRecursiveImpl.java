package es.moodbox.humantower.service.impl;

import es.moodbox.humantower.service.HumanTowerService;

public class HumanTowerRecursiveImpl implements HumanTowerService{

	private static final int HUMAN_WEIGHT = 50;
	
	public HumanTowerRecursiveImpl() {}

	public Double getHumanEdgeWeight(int level, int index) {
		if (level == 0) {
			return Double.valueOf("0.0");
		} else {
			return (getHumanWeight(level -1,index-1) + getHumanWeight(level-1 , index) + getHumanEdgeWeight(level - 1, index -1) + getHumanEdgeWeight(level - 1, index)) / 2;
		}
	}
	
	private int getHumanWeight(int level, int index) {
		if (index < 0 || index > level) {
			return 0;
		}
		return HUMAN_WEIGHT;
	}

	public Double getHumanEdgeWeight(int level) {
		if (level == 0) {
			return Double.valueOf("0.0");
		} else {
			return (HUMAN_WEIGHT/2) + (getHumanEdgeWeight(level-1) /2);
		}
	}
}