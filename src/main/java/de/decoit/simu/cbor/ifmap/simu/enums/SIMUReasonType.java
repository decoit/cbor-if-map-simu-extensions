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
 * Enum to be used for the 'reason' attribute of SIMU metadata.
 *
 * @author Thomas Rix (rix@decoit.de)
 */
public enum SIMUReasonType {
	INVALID_ACCOUNT("Invalid Account"),
	INVALID_CREDENTIALS("Invalid Credentials"),
	EXPIRED_CREDENTIALS("Expired Credentials"),
	COMMUNICATION_FAILURE("Communication Failure"),
	UNKNOWN("Unknown"),
	OTHER("Other");

	@Getter
	private final String xmlName;


	private SIMUReasonType(final String s) {
		xmlName = s;
	}

	
	/**
	 * Lookup the matching enum constant for the specified XML name.
	 *
	 * @param name XML name
	 * @return Matching enum constant
	 */
	public static SIMUReasonType fromXmlName(String name) {
		if(name == null) {
			throw new IllegalArgumentException("Null pointer for XML name provided");
		}
		
		switch(name) {
			case "Invalid Account":
				return SIMUReasonType.INVALID_ACCOUNT;
			case "Invalid Credentials":
				return SIMUReasonType.INVALID_CREDENTIALS;
			case "Expired Credentials":
				return SIMUReasonType.EXPIRED_CREDENTIALS;
			case "Communication Failure":
				return SIMUReasonType.COMMUNICATION_FAILURE;
			case "Unknown":
				return SIMUReasonType.UNKNOWN;
			case "Other":
				return SIMUReasonType.OTHER;
			default:
				throw new IllegalArgumentException("Unknown XML name provided");
		}
	}
}
