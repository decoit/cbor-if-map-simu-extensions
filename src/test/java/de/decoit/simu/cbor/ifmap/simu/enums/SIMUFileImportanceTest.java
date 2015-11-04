/*
 * No license defined yet.
 */
package de.decoit.simu.cbor.ifmap.simu.enums;

import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author Thomas Rix (rix@decoit.de)
 */
public class SIMUFileImportanceTest {
	@Test
	public void testFromXmlName_Low() {
		SIMUFileImportanceType result = SIMUFileImportanceType.fromXmlName("low");
		assertEquals(SIMUFileImportanceType.LOW, result);
	}
	
	
	@Test
	public void testFromXmlName_Medium() {
		SIMUFileImportanceType result = SIMUFileImportanceType.fromXmlName("medium");
		assertEquals(SIMUFileImportanceType.MEDIUM, result);
	}
	
	
	@Test
	public void testFromXmlName_High() {
		SIMUFileImportanceType result = SIMUFileImportanceType.fromXmlName("high");
		assertEquals(SIMUFileImportanceType.HIGH, result);
	}
	
	
	@Test
	public void testFromXmlName_Critical() {
		SIMUFileImportanceType result = SIMUFileImportanceType.fromXmlName("critical");
		assertEquals(SIMUFileImportanceType.CRITICAL, result);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testFromXmlName_null() {
		SIMUFileImportanceType result = SIMUFileImportanceType.fromXmlName(null);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testFromXmlName_UnknownXmlName() {
		SIMUFileImportanceType result = SIMUFileImportanceType.fromXmlName("UnknownXmlName");
	}
}
