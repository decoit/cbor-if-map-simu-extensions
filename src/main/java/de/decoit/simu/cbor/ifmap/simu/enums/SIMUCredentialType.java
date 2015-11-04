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
 * Enum to be used for the 'credential-type' attribute of SIMU metadata.
 *
 * @author Thomas Rix (rix@decoit.de)
 */
public enum SIMUCredentialType {
	PASSWORD("Password"),
	PUBLIC_KEY("Public Key"),
	BIOMETRIC("Biometric"),
	TOKEN("Token"),
	OTHER("Other");

	@Getter
	private final String xmlName;


	private SIMUCredentialType(final String s) {
		xmlName = s;
	}


	/**
	 * Lookup the matching enum constant for the specified XML name.
	 *
	 * @param name XML name
	 * @return Matching enum constant
	 */
	public static SIMUCredentialType fromXmlName(String name) {
		if(name == null) {
			throw new IllegalArgumentException("Null pointer for XML name provided");
		}
		
		switch(name) {
			case "Password":
				return SIMUCredentialType.PASSWORD;
			case "Public Key":
				return SIMUCredentialType.PUBLIC_KEY;
			case "Biometric":
				return SIMUCredentialType.BIOMETRIC;
			case "Token":
				return SIMUCredentialType.TOKEN;
			case "Other":
				return SIMUCredentialType.OTHER;
			default:
				throw new IllegalArgumentException("Unknown XML name provided");
		}
	}
}
