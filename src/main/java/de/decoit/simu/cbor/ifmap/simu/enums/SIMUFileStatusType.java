/*
 * No license defined yet.
 */
package de.decoit.simu.cbor.ifmap.simu.enums;

import lombok.Getter;



/**
 * Enum to be used for the 'status' attribute of SIMU file-status metadata.
 *
 * @author Thomas Rix (rix@decoit.de)
 */
public enum SIMUFileStatusType {
	NEW("new"),
	MODIFIED("modified"),
	DELETED("deleted"),
	EXISTING("existing");

	@Getter
	private final String xmlName;


	private SIMUFileStatusType(final String s) {
		xmlName = s;
	}


	/**
	 * Lookup the matching enum constant for the specified XML name.
	 *
	 * @param name XML name
	 * @return Matching enum constant
	 */
	public static SIMUFileStatusType fromXmlName(String name) {
		if(name == null) {
			throw new IllegalArgumentException("Null pointer for XML name provided");
		}

		switch(name) {
			case "new":
				return SIMUFileStatusType.NEW;
			case "modified":
				return SIMUFileStatusType.MODIFIED;
			case "deleted":
				return SIMUFileStatusType.DELETED;
			case "existing":
				return SIMUFileStatusType.EXISTING;
			default:
				throw new IllegalArgumentException("Unknown XML name provided");
		}
	}
}
