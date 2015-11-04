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
public class SIMUReasonTypeTest {
	@Test
	public void testFromXmlName_InvalidAccount() {
		SIMUReasonType result = SIMUReasonType.fromXmlName("Invalid Account");
		assertEquals(SIMUReasonType.INVALID_ACCOUNT, result);
	}


	@Test
	public void testFromXmlName_InvalidCredentials() {
		SIMUReasonType result = SIMUReasonType.fromXmlName("Invalid Credentials");
		assertEquals(SIMUReasonType.INVALID_CREDENTIALS, result);
	}
	
	
	@Test
	public void testFromXmlName_ExpiredCredentials() {
		SIMUReasonType result = SIMUReasonType.fromXmlName("Expired Credentials");
		assertEquals(SIMUReasonType.EXPIRED_CREDENTIALS, result);
	}
	
	
	@Test
	public void testFromXmlName_CommunicationFailure() {
		SIMUReasonType result = SIMUReasonType.fromXmlName("Communication Failure");
		assertEquals(SIMUReasonType.COMMUNICATION_FAILURE, result);
	}
	
	
	@Test
	public void testFromXmlName_Unknown() {
		SIMUReasonType result = SIMUReasonType.fromXmlName("Unknown");
		assertEquals(SIMUReasonType.UNKNOWN, result);
	}
	
	
	@Test
	public void testFromXmlName_Other() {
		SIMUReasonType result = SIMUReasonType.fromXmlName("Other");
		assertEquals(SIMUReasonType.OTHER, result);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testFromXmlName_null() {
		SIMUReasonType result = SIMUReasonType.fromXmlName(null);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testFromXmlName_UnknownXmlName() {
		SIMUReasonType result = SIMUReasonType.fromXmlName("UnknownXmlName");
	}
}
