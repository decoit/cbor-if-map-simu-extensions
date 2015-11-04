/*
 * No license defined yet.
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
