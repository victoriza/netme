package es.moodbox.humantower.service.impl;

import java.util.HashMap;

import es.moodbox.humantower.service.HumanTowerService;


public class HumanTowerIterativeImpl implements HumanTowerService{

	private final static int HUMAN_WEIGTH = 50;

	private static final int INITIAL_KEY_LENGHT = 8;

	private static final String SEPARATOR = "-";
	
	private final static HashMap<String, Double> humanTower = new HashMap<String, Double>();
	
	static {
		humanTower.put(getKey(0, 0), Double.parseDouble("0.0"));
	}
	
	/**
	 * If not tell is it at the edge
	 * 
	 * @param level
	 * 
	 * @return weight over the shoulders
	 */
	public Double getHumanEdgeWeight(int level) {
		if (level == 0) {
			return Double.valueOf("0.0");
		}
		else {
			return (HUMAN_WEIGTH + getHumanEdgeWeight(level-1)) / 2;
		}
	}
	public Double getHumanEdgeWeight(int level, int index) {
		//i will be level
		//j will be index
		Double result;
		for (int i = 1; i <= level; i++){
			//System.out.println("At level= "+i);
			for (int j = 0; j <= i; j++) {
				//peso TOTAL de lo anterior es lo que soporto
				//edge
				if (j == 0) {
					result = ((HUMAN_WEIGTH + getSafeHumanWeight(i - 1, 0)) / 2);
				} else if (j == i) {
					result = ((HUMAN_WEIGTH + getSafeHumanWeight(i - 1, 0)) / 2);
				} else {
					result = HUMAN_WEIGTH + ((getSafeHumanWeight(i - 1, j - 1) + getSafeHumanWeight(i - 1, j)) / 2);
				}
				//System.out.println("���� Adding= ("+i+"," +j+") w: "+result);
				humanTower.put(getKey(i, j) , result);
				
				if (i == level && j == index) {
					return result;
				}
			}
		}
		return Double.valueOf("-1");
	}
	private static Double getSafeHumanWeight(int level, int index) {
		Double res = humanTower.get(getKey(level, index));
		if (res == null) {
			return Double.parseDouble("0.0");
		}
		return res;
	}
	
	
	private static String getKey(int level , int index) {
		StringBuffer key = new StringBuffer(INITIAL_KEY_LENGHT);
		key.append(level);
		key.append(SEPARATOR);
		key.append(index);
		return key.toString();
	}
}