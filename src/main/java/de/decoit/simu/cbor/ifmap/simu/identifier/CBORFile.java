/*
 * No license defined yet.
 */
package de.decoit.simu.cbor.ifmap.simu.identifier;

import co.nstant.in.cbor.model.DataItem;
import co.nstant.in.cbor.model.UnicodeString;
import de.decoit.simu.cbor.ifmap.identifier.extended.AbstractExtendedIdentifier;
import de.decoit.simu.cbor.ifmap.simu.util.SimuNamespaces;
import java.util.HashMap;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * Java representation of the file extended identifier from the SIMU namespace.
 * 
 * @author Thomas Rix (rix@decoit.de)
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Slf4j
public class CBORFile extends AbstractExtendedIdentifier {
	public static final String XML_NAME = "file";
	public static final String PATH = "path";
	
	
	/**
	 * Create a new implementation extended identifier.
	 *
	 * @param administrativeDomain File administrative domain
	 * @param path File path
	 */
	public CBORFile(String administrativeDomain, String path) {
		super(SimuNamespaces.SIMU, XML_NAME, administrativeDomain, new HashMap<>(), new HashMap<>());

		if(StringUtils.isBlank(path)) {
			throw new IllegalArgumentException("Path must not be blank");
		}

		DataItem pathDi = new UnicodeString(path);
		this.attributes.put(PATH, pathDi);
	}
	
	
	public String getPath() {
		UnicodeString us = (UnicodeString) this.attributes.get(PATH);
		return us.getString();
	}
}
