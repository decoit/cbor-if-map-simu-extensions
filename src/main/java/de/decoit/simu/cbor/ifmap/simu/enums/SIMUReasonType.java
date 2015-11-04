/*
 * No license defined yet.
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
