package com.parkbenchapi.util;

import org.junit.Test;
import org.junit.Assert;



public class GeoUtilTest {
	
	private GeoUtil util = new GeoUtil();
	private double expected = 21384;
	private double marginOfError = 100;
	
	@Test
	public void testGetDistance2() {
		double actual = util.getDistance(-81.581212, 28.417663, -81.591313, 28.359719);
		System.out.println("Expected: " + expected);
		System.out.println("Actual: " + actual);
		Assert.assertTrue(actual < expected + marginOfError && actual > expected - marginOfError);
	}

}
