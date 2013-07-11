package es.moodbox.test;

import junit.framework.Assert;
import es.moodbox.humantower.service.HumanTowerService;
import es.moodbox.humantower.service.impl.HumanTowerIterativeImpl;

public class Test {

	private final static HumanTowerService hts = new HumanTowerIterativeImpl(); 
	
    @org.junit.Test
    public void checkHumanTowerEdge() {
            	
    	
    	Assert.assertEquals(new Double("0"), new Double(hts.getHumanEdgeWeight(0)));
    	Assert.assertEquals(new Double("25"), hts.getHumanEdgeWeight(1));
    	Assert.assertEquals(new Double("37.5"), hts.getHumanEdgeWeight(2));
    	Assert.assertEquals(new Double("43.75"), hts.getHumanEdgeWeight(3));
    	Assert.assertEquals(new Double("46.875"), hts.getHumanEdgeWeight(4));
    	Assert.assertEquals(new Double("48.4375"), hts.getHumanEdgeWeight(5));
    	Assert.assertEquals(new Double("49.21875"), hts.getHumanEdgeWeight(6));
    	Assert.assertEquals(new Double("49.609375"), hts.getHumanEdgeWeight(7));
    	Assert.assertEquals(new Double("49.8046875"), hts.getHumanEdgeWeight(8));
    	Assert.assertEquals(new Double("49.90234375"), hts.getHumanEdgeWeight(9));
    	Assert.assertEquals(new Double("49.951171875"), hts.getHumanEdgeWeight(10));
    	
    	Assert.assertEquals(new Double("50"), hts.getHumanEdgeWeight(60));
    }
    
    @org.junit.Test
    public void checkHumanTowerIterative() {
    	Assert.assertEquals(new Double("0"), hts.getHumanEdgeWeight(0));
    	
    	Assert.assertEquals(new Double("25.0"), hts.getHumanEdgeWeight(1,0));
    	Assert.assertEquals(new Double("25.0"), hts.getHumanEdgeWeight(1,0));
    	
    	Assert.assertEquals(new Double("37.5"), hts.getHumanEdgeWeight(2,0));
    	Assert.assertEquals(new Double("75.0"), hts.getHumanEdgeWeight(2,1));
    	Assert.assertEquals(new Double("37.5"), hts.getHumanEdgeWeight(2,2));
    	
    	Assert.assertEquals(new Double("43.75"), hts.getHumanEdgeWeight(3,0));
    	Assert.assertEquals(new Double("106.25"), hts.getHumanEdgeWeight(3,1));
    	Assert.assertEquals(new Double("106.25"), hts.getHumanEdgeWeight(3,2));
    	Assert.assertEquals(new Double("43.75"), hts.getHumanEdgeWeight(3,3));
    	
    	Assert.assertEquals(new Double("46.875"), hts.getHumanEdgeWeight(4,0));
    	Assert.assertEquals(new Double("125.0"), hts.getHumanEdgeWeight(4,1));
    	Assert.assertEquals(new Double("156.25"), hts.getHumanEdgeWeight(4,2));
    	Assert.assertEquals(new Double("125.0"), hts.getHumanEdgeWeight(4,3));
    	Assert.assertEquals(new Double("46.875"), hts.getHumanEdgeWeight(4,4));
    	
    	
    	
    	
    }
}
