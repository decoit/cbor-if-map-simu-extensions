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
public class SIMUFileStatusTypeTest {
	@Test
	public void testFromXmlName_New() {
		SIMUFileStatusType result = SIMUFileStatusType.fromXmlName("new");
		assertEquals(SIMUFileStatusType.NEW, result);
	}
	
	
	@Test
	public void testFromXmlName_Modified() {
		SIMUFileStatusType result = SIMUFileStatusType.fromXmlName("modified");
		assertEquals(SIMUFileStatusType.MODIFIED, result);
	}
	
	
	@Test
	public void testFromXmlName_Deleted() {
		SIMUFileStatusType result = SIMUFileStatusType.fromXmlName("deleted");
		assertEquals(SIMUFileStatusType.DELETED, result);
	}
	
	
	@Test
	public void testFromXmlName_Existing() {
		SIMUFileStatusType result = SIMUFileStatusType.fromXmlName("existing");
		assertEquals(SIMUFileStatusType.EXISTING, result);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testFromXmlName_null() {
		SIMUFileStatusType result = SIMUFileStatusType.fromXmlName(null);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testFromXmlName_UnknownXmlName() {
		SIMUFileStatusType result = SIMUFileStatusType.fromXmlName("UnknownXmlName");
	}
}
