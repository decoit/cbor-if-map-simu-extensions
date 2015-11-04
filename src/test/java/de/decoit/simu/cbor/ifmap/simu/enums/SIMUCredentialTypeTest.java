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
public class SIMUCredentialTypeTest {
	@Test
	public void testFromXmlName_Password() {
		SIMUCredentialType result = SIMUCredentialType.fromXmlName("Password");
		assertEquals(SIMUCredentialType.PASSWORD, result);
	}


	@Test
	public void testFromXmlName_PublicKey() {
		SIMUCredentialType result = SIMUCredentialType.fromXmlName("Public Key");
		assertEquals(SIMUCredentialType.PUBLIC_KEY, result);
	}
	
	
	@Test
	public void testFromXmlName_Biometric() {
		SIMUCredentialType result = SIMUCredentialType.fromXmlName("Biometric");
		assertEquals(SIMUCredentialType.BIOMETRIC, result);
	}
	
	
	@Test
	public void testFromXmlName_Token() {
		SIMUCredentialType result = SIMUCredentialType.fromXmlName("Token");
		assertEquals(SIMUCredentialType.TOKEN, result);
	}
	
	
	@Test
	public void testFromXmlName_Other() {
		SIMUCredentialType result = SIMUCredentialType.fromXmlName("Other");
		assertEquals(SIMUCredentialType.OTHER, result);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testFromXmlName_null() {
		SIMUCredentialType result = SIMUCredentialType.fromXmlName(null);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testFromXmlName_UnknownXmlName() {
		SIMUCredentialType result = SIMUCredentialType.fromXmlName("UnknownXmlName");
	}
}
