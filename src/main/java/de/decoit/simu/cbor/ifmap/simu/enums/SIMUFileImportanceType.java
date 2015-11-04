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

import lombok.Getter;



/**
 * Enum to be used for the 'importance' attribute of SIMU file-status metadata.
 *
 * @author Thomas Rix (rix@decoit.de)
 */
public enum SIMUFileImportanceType {
	LOW("low"),
	MEDIUM("medium"),
	HIGH("high"),
	CRITICAL("critical");

	@Getter
	private final String xmlName;


	private SIMUFileImportanceType(final String s) {
		xmlName = s;
	}

	
	/**
	 * Lookup the matching enum constant for the specified XML name.
	 *
	 * @param name XML name
	 * @return Matching enum constant
	 */
	public static SIMUFileImportanceType fromXmlName(String name) {
		if(name == null) {
			throw new IllegalArgumentException("Null pointer for XML name provided");
		}

		switch(name) {
			case "low":
				return SIMUFileImportanceType.LOW;
			case "medium":
				return SIMUFileImportanceType.MEDIUM;
			case "high":
				return SIMUFileImportanceType.HIGH;
			case "critical":
				return SIMUFileImportanceType.CRITICAL;
			default:
				throw new IllegalArgumentException("Unknown XML name provided");
		}
	}
}
