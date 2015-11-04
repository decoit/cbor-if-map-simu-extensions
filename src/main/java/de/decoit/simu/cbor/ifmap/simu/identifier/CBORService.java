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
import co.nstant.in.cbor.model.UnsignedInteger;
import de.decoit.simu.cbor.ifmap.identifier.extended.AbstractExtendedIdentifier;
import de.decoit.simu.cbor.ifmap.simu.util.SimuNamespaces;
import java.util.HashMap;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;



/**
 * Java representation of the service extended identifier from the SIMU namespace.
 *
 * @author Thomas Rix (rix@decoit.de)
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Slf4j
public final class CBORService extends AbstractExtendedIdentifier {
	public static final String XML_NAME = "service";
	public static final String TYPE = "type";
	public static final String NAME = "name";
	public static final String PORT = "port";


	/**
	 * Create a new service extended identifier.
	 * The service port number must be 0 or greater.
	 *
	 * @param administrativeDomain Service administrative domain
	 * @param type Service type
	 * @param name Service name
	 * @param port Service port
	 */
	public CBORService(String administrativeDomain, String type, String name, Integer port) {
		super(SimuNamespaces.SIMU, XML_NAME, administrativeDomain, new HashMap<>(), new HashMap<>());

		if(StringUtils.isBlank(type)) {
			throw new IllegalArgumentException("Type must not be blank");
		}

		if(StringUtils.isBlank(name)) {
			throw new IllegalArgumentException("Name must not be blank");
		}

		if(port == null) {
			throw new IllegalArgumentException("Port must not be null");
		}
		else if(port < 0) {
			throw new IllegalArgumentException("Port must be 0 or greater");
		}

		DataItem typeDi = new UnicodeString(type);
		this.attributes.put(TYPE, typeDi);

		DataItem nameDi = new UnicodeString(name);
		this.attributes.put(NAME, nameDi);

		DataItem portDi = new UnsignedInteger(port);
		this.attributes.put(PORT, portDi);
	}


	public String getType() {
		UnicodeString us = (UnicodeString) this.attributes.get(TYPE);
		return us.getString();
	}


	public String getName() {
		UnicodeString us = (UnicodeString) this.attributes.get(NAME);
		return us.getString();
	}


	public Integer getPort() {
		UnsignedInteger ui = (UnsignedInteger) this.attributes.get(PORT);
		return ui.getValue().intValueExact();
	}
}
