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
