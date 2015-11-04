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
 * Java representation of the implementation extended identifier from the SIMU namespace.
 *
 * @author Thomas Rix (rix@decoit.de)
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Slf4j
public class CBORImplementation extends AbstractExtendedIdentifier {
	public static final String XML_NAME = "implementation";
	public static final String NAME = "name";
	public static final String VERSION = "version";
	public static final String LOCAL_VERSION = "local-version";
	public static final String PLATFORM = "platform";


	/**
	 * Create a new implementation extended identifier.
	 *
	 * @param administrativeDomain Implementation administrative domain
	 * @param name Implementation name
	 * @param version Implementation version
	 */
	public CBORImplementation(String administrativeDomain, String name, String version) {
		super(SimuNamespaces.SIMU, XML_NAME, administrativeDomain, new HashMap<>(), new HashMap<>());

		if(StringUtils.isBlank(name)) {
			throw new IllegalArgumentException("Name must not be blank");
		}

		if(StringUtils.isBlank(version)) {
			throw new IllegalArgumentException("Version must not be blank");
		}

		DataItem nameDi = new UnicodeString(name);
		this.attributes.put(NAME, nameDi);

		DataItem versionDi = new UnicodeString(version);
		this.attributes.put(VERSION, versionDi);
	}


	/**
	 * Set the local version string for this identifier.
	 * The local version may be null to remove this attribute from the identifier.
	 * If not null, the local version MUST NOT be empty or whitespace only.
	 *
	 * @param localVersion Local version string
	 */
	public void setLocalVersion(String localVersion) {
		if(StringUtils.isWhitespace(localVersion)) {
			throw new IllegalArgumentException("Local version must not be empty or whitespace only");
		}

		if(localVersion != null) {
			DataItem localVersionDi = new UnicodeString(localVersion);
			this.attributes.put(LOCAL_VERSION, localVersionDi);
		}
		else {
			this.attributes.remove(LOCAL_VERSION);
		}
	}


	/**
	 * Set the platform string for this identifier.
	 * The platform may be null to remove this attribute from the identifier.
	 * If not null, the platform MUST NOT be empty or whitespace only.
	 *
	 * @param platform Platform string
	 */
	public void setPlatform(String platform) {
		if(StringUtils.isWhitespace(platform)) {
			throw new IllegalArgumentException("Platform must not be empty or whitespace only");
		}

		if(platform != null) {
			DataItem platformDi = new UnicodeString(platform);
			this.attributes.put(PLATFORM, platformDi);
		}
		else {
			this.attributes.remove(PLATFORM);
		}
	}


	public String getName() {
		UnicodeString us = (UnicodeString) this.attributes.get(NAME);
		return us.getString();
	}


	public String getVersion() {
		UnicodeString us = (UnicodeString) this.attributes.get(VERSION);
		return us.getString();
	}


	public String getLocalVersion() {
		UnicodeString us = (UnicodeString) this.attributes.get(LOCAL_VERSION);

		if(us != null) {
			return us.getString();
		}
		else {
			return null;
		}
	}


	public String getPlatform() {
		UnicodeString us = (UnicodeString) this.attributes.get(PLATFORM);
		
		if(us != null) {
			return us.getString();
		}
		else {
			return null;
		}
	}
}
