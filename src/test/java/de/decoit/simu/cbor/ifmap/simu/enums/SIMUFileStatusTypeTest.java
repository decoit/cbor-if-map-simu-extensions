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
