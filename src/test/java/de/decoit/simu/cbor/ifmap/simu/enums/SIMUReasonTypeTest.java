/* 
 * Copyright 2015 DECOIT GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
