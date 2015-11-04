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
