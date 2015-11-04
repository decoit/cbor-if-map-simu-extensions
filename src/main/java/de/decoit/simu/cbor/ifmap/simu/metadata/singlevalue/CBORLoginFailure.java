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
package de.decoit.simu.cbor.ifmap.simu.metadata.singlevalue;

import co.nstant.in.cbor.builder.ArrayBuilder;
import co.nstant.in.cbor.model.DataItem;
import co.nstant.in.cbor.model.SimpleValue;
import co.nstant.in.cbor.model.SimpleValueType;
import co.nstant.in.cbor.model.UnicodeString;
import de.decoit.simu.cbor.ifmap.exception.CBORSerializationException;
import de.decoit.simu.cbor.ifmap.metadata.singlevalue.AbstractSingleValueMetadata;
import de.decoit.simu.cbor.ifmap.simu.enums.SIMUCredentialType;
import de.decoit.simu.cbor.ifmap.simu.enums.SIMUReasonType;
import de.decoit.simu.cbor.ifmap.simu.util.SimuNamespaces;
import de.decoit.simu.cbor.xml.dictionary.DictionaryProvider;
import de.decoit.simu.cbor.xml.dictionary.DictionarySimpleElement;
import de.decoit.simu.cbor.xml.dictionary.exception.DictionaryPathException;
import java.time.ZonedDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;



/**
 * Java representation of the login-failure metadata from the SIMU namespace.
 *
 * @author Thomas Rix (rix@decoit.de)
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Slf4j
public final class CBORLoginFailure extends AbstractSingleValueMetadata {
	public static final String XML_NAME = "login-failure";
	public static final String CREDENTIAL_TYPE = "credential-type";
	public static final String REASON = "reason";
	public static final String OTHER_CREDENTIAL_TYPE_DEFINITION = "other-credential-type-definition";
	public static final String OTHER_REASON_TYPE_DEFINITION = "other-reason-type-definition";

	@Getter
	private final SIMUCredentialType credentialType;
	@Getter
	private final SIMUReasonType reason;
	@Getter
	private final String otherCredentialTypeDefinition;
	@Getter
	private final String otherReasonTypeDefinition;


	/**
	 * Create a new login-failure metadata.
	 * This constructor does not work with credential and/or reason types OTHER because other type
	 * definitions are required in that case.
	 * 
	 * This constructor should be used for metadata that is sent from the SERVER to the CLIENT. It includes
	 * the attributes ifmap-publisher-id, ifmap-timestamp and ifmap-timestamp-fraction.
	 * The timestamps are stored with UTC timezone. If a timestamp with another timezone is
	 * passed into the constructor, it will be converted to UTC. The IF-MAP timestamp fraction
	 * element will be calculated from the provided timestamp.
	 *
	 * @param ifMapPublisherId IF-MAP client publisher ID
	 * @param ifMapTimestamp Timestamp for the metadata
	 * @param credentialType Type of the used credentials
	 * @param reason Reason of the failed login
	 */
	public CBORLoginFailure(String ifMapPublisherId, ZonedDateTime ifMapTimestamp, SIMUCredentialType credentialType, SIMUReasonType reason) {
		this(ifMapPublisherId, ifMapTimestamp, credentialType, null, reason, null);
	}


	/**
	 * Create a new login-failure metadata.
	 * This constructor allows usage of credential type OTHER if an additional other type definition
	 * is provided. Otherwise the other type definition may be null. Usage with reason type OTHER will fail.
	 * 
	 * This constructor should be used for metadata that is sent from the SERVER to the CLIENT. It includes
	 * the attributes ifmap-publisher-id, ifmap-timestamp and ifmap-timestamp-fraction.
	 * The timestamps are stored with UTC timezone. If a timestamp with another timezone is
	 * passed into the constructor, it will be converted to UTC. The IF-MAP timestamp fraction
	 * element will be calculated from the provided timestamp.
	 *
	 * @param ifMapPublisherId IF-MAP client publisher ID
	 * @param ifMapTimestamp Timestamp for the metadata
	 * @param credentialType Type of the used credentials
	 * @param otherCredentialTypeDefinition Additional credential type definition, may be null if applicable
	 * @param reason Reason of the failed login
	 */
	public CBORLoginFailure(String ifMapPublisherId, ZonedDateTime ifMapTimestamp, SIMUCredentialType credentialType, String otherCredentialTypeDefinition, SIMUReasonType reason) {
		this(ifMapPublisherId, ifMapTimestamp, credentialType, otherCredentialTypeDefinition, reason, null);
	}


	/**
	 * Create a new login-failure metadata.
	 * This constructor allows usage of reason type OTHER if an additional other type definition
	 * is provided. Otherwise the other type definition may be null. Usage with credential type OTHER will fail.
	 * 
	 * This constructor should be used for metadata that is sent from the SERVER to the CLIENT. It includes
	 * the attributes ifmap-publisher-id, ifmap-timestamp and ifmap-timestamp-fraction.
	 * The timestamps are stored with UTC timezone. If a timestamp with another timezone is
	 * passed into the constructor, it will be converted to UTC. The IF-MAP timestamp fraction
	 * element will be calculated from the provided timestamp.
	 *
	 * @param ifMapPublisherId IF-MAP client publisher ID
	 * @param ifMapTimestamp Timestamp for the metadata
	 * @param credentialType Type of the used credentials
	 * @param reason Reason of the failed login
	 * @param otherReasonTypeDefinition Additional reason type definition, may be null if applicable
	 */
	public CBORLoginFailure(String ifMapPublisherId, ZonedDateTime ifMapTimestamp, SIMUCredentialType credentialType, SIMUReasonType reason, String otherReasonTypeDefinition) {
		this(ifMapPublisherId, ifMapTimestamp, credentialType, null, reason, otherReasonTypeDefinition);
	}


	/**
	 * Create a new login-failure metadata.
	 * This constructor allows usage of both credential and reason type OTHER if additional other type definitions
	 * are provided. Otherwise the other type definitions may be null.
	 * 
	 * This constructor should be used for metadata that is sent from the SERVER to the CLIENT. It includes
	 * the attributes ifmap-publisher-id, ifmap-timestamp and ifmap-timestamp-fraction.
	 * The timestamps are stored with UTC timezone. If a timestamp with another timezone is
	 * passed into the constructor, it will be converted to UTC. The IF-MAP timestamp fraction
	 * element will be calculated from the provided timestamp.
	 *
	 * @param ifMapPublisherId IF-MAP client publisher ID
	 * @param ifMapTimestamp Timestamp for the metadata
	 * @param credentialType Type of the used credentials
	 * @param otherCredentialTypeDefinition Additional credential type definition, may be null if applicable
	 * @param reason Reason of the failed login
	 * @param otherReasonTypeDefinition Additional reason type definition, may be null if applicable
	 */
	public CBORLoginFailure(String ifMapPublisherId, ZonedDateTime ifMapTimestamp, SIMUCredentialType credentialType, String otherCredentialTypeDefinition, SIMUReasonType reason, String otherReasonTypeDefinition) {
		super(SimuNamespaces.SIMU, XML_NAME, ifMapPublisherId, ifMapTimestamp);

		if(credentialType == null) {
			throw new IllegalArgumentException("Credential type must not be null");
		}
		else if(credentialType == SIMUCredentialType.OTHER && StringUtils.isBlank(otherCredentialTypeDefinition)) {
			throw new IllegalArgumentException("Other credential type definition must not be blank if credential type is OTHER");
		}
		else if(credentialType != SIMUCredentialType.OTHER) {
			otherCredentialTypeDefinition = null;
		}

		if(reason == null) {
			throw new IllegalArgumentException("Reason must not be null");
		}
		else if(reason == SIMUReasonType.OTHER && StringUtils.isBlank(otherReasonTypeDefinition)) {
			throw new IllegalArgumentException("Other reason type definition must not be blank if reason is OTHER");
		}
		else if(reason != SIMUReasonType.OTHER) {
			otherReasonTypeDefinition = null;
		}

		this.credentialType = credentialType;
		this.reason = reason;
		this.otherCredentialTypeDefinition = otherCredentialTypeDefinition;
		this.otherReasonTypeDefinition = otherReasonTypeDefinition;
	}
	
	
	/**
	 * Create a new login-failure metadata.
	 * This constructor does not work with credential and/or reason types OTHER because other type
	 * definitions are required in that case.
	 * 
	 * This constructor should be used for metadata that is sent from the CLIENT to the SERVER. It does not include
	 * the attributes ifmap-publisher-id, ifmap-timestamp and ifmap-timestamp-fraction.
	 * The timestamps are stored with UTC timezone. If a timestamp with another timezone is
	 * passed into the constructor, it will be converted to UTC. The IF-MAP timestamp fraction
	 * element will be calculated from the provided timestamp.
	 *
	 * @param credentialType Type of the used credentials
	 * @param reason Reason of the failed login
	 */
	public CBORLoginFailure(SIMUCredentialType credentialType, SIMUReasonType reason) {
		this(credentialType, null, reason, null);
	}


	/**
	 * Create a new login-failure metadata.
	 * This constructor allows usage of credential type OTHER if an additional other type definition
	 * is provided. Otherwise the other type definition may be null. Usage with reason type OTHER will fail.
	 * 
	 * This constructor should be used for metadata that is sent from the CLIENT to the SERVER. It does not include
	 * the attributes ifmap-publisher-id, ifmap-timestamp and ifmap-timestamp-fraction.
	 * The timestamps are stored with UTC timezone. If a timestamp with another timezone is
	 * passed into the constructor, it will be converted to UTC. The IF-MAP timestamp fraction
	 * element will be calculated from the provided timestamp.
	 *
	 * @param credentialType Type of the used credentials
	 * @param otherCredentialTypeDefinition Additional credential type definition, may be null if applicable
	 * @param reason Reason of the failed login
	 */
	public CBORLoginFailure(SIMUCredentialType credentialType, String otherCredentialTypeDefinition, SIMUReasonType reason) {
		this(credentialType, otherCredentialTypeDefinition, reason, null);
	}


	/**
	 * Create a new login-failure metadata.
	 * This constructor allows usage of reason type OTHER if an additional other type definition
	 * is provided. Otherwise the other type definition may be null. Usage with credential type OTHER will fail.
	 * 
	 * This constructor should be used for metadata that is sent from the CLIENT to the SERVER. It does not include
	 * the attributes ifmap-publisher-id, ifmap-timestamp and ifmap-timestamp-fraction.
	 * The timestamps are stored with UTC timezone. If a timestamp with another timezone is
	 * passed into the constructor, it will be converted to UTC. The IF-MAP timestamp fraction
	 * element will be calculated from the provided timestamp.
	 *
	 * @param credentialType Type of the used credentials
	 * @param reason Reason of the failed login
	 * @param otherReasonTypeDefinition Additional reason type definition, may be null if applicable
	 */
	public CBORLoginFailure(SIMUCredentialType credentialType, SIMUReasonType reason, String otherReasonTypeDefinition) {
		this(credentialType, null, reason, otherReasonTypeDefinition);
	}


	/**
	 * Create a new login-failure metadata.
	 * This constructor allows usage of both credential and reason type OTHER if additional other type definitions
	 * are provided. Otherwise the other type definitions may be null.
	 * 
	 * This constructor should be used for metadata that is sent from the CLIENT to the SERVER. It does not include
	 * the attributes ifmap-publisher-id, ifmap-timestamp and ifmap-timestamp-fraction.
	 * The timestamps are stored with UTC timezone. If a timestamp with another timezone is
	 * passed into the constructor, it will be converted to UTC. The IF-MAP timestamp fraction
	 * element will be calculated from the provided timestamp.
	 *
	 * @param credentialType Type of the used credentials
	 * @param otherCredentialTypeDefinition Additional credential type definition, may be null if applicable
	 * @param reason Reason of the failed login
	 * @param otherReasonTypeDefinition Additional reason type definition, may be null if applicable
	 */
	public CBORLoginFailure(SIMUCredentialType credentialType, String otherCredentialTypeDefinition, SIMUReasonType reason, String otherReasonTypeDefinition) {
		super(SimuNamespaces.SIMU, XML_NAME);

		if(credentialType == null) {
			throw new IllegalArgumentException("Credential type must not be null");
		}
		else if(credentialType == SIMUCredentialType.OTHER && StringUtils.isBlank(otherCredentialTypeDefinition)) {
			throw new IllegalArgumentException("Other credential type definition must not be blank if credential type is OTHER");
		}
		else if(credentialType != SIMUCredentialType.OTHER) {
			otherCredentialTypeDefinition = null;
		}

		if(reason == null) {
			throw new IllegalArgumentException("Reason must not be null");
		}
		else if(reason == SIMUReasonType.OTHER && StringUtils.isBlank(otherReasonTypeDefinition)) {
			throw new IllegalArgumentException("Other reason type definition must not be blank if reason is OTHER");
		}
		else if(reason != SIMUReasonType.OTHER) {
			otherReasonTypeDefinition = null;
		}

		this.credentialType = credentialType;
		this.reason = reason;
		this.otherCredentialTypeDefinition = otherCredentialTypeDefinition;
		this.otherReasonTypeDefinition = otherReasonTypeDefinition;
	}
	
	
	@Override
	protected void serializeNestedElements(final ArrayBuilder<?> builder) throws CBORSerializationException {
		try {
			// Build dictionary path to this element
			StringBuilder dictPathSb = new StringBuilder("<");
			dictPathSb.append(this.namespace);
			dictPathSb.append(">");
			dictPathSb.append(this.elementName);

			// Get dictionary entry for this element
			DictionarySimpleElement elementEntry = DictionaryProvider.getInstance().findElementByPath(dictPathSb.toString());

			// Serialize 'credential-type'
			{
				DataItem cborName = this.getNestedElementNameMapping(CBORLoginFailure.CREDENTIAL_TYPE, elementEntry);

				builder.add(new SimpleValue(SimpleValueType.NULL));
				builder.add(cborName);
				builder.addArray();
				builder.add(this.getNestedElementEnumValueMapping(CBORLoginFailure.CREDENTIAL_TYPE, this.credentialType.getXmlName(), elementEntry));
			}
			
			// Serialize 'other-credential-type-definition'
			if(this.otherCredentialTypeDefinition != null) {
				DataItem cborName = this.getNestedElementNameMapping(CBORLoginFailure.OTHER_CREDENTIAL_TYPE_DEFINITION, elementEntry);

				builder.add(new SimpleValue(SimpleValueType.NULL));
				builder.add(cborName);
				builder.addArray();
				builder.add(new UnicodeString(this.otherCredentialTypeDefinition));
			}
			
			// Serialize 'reason'
			{
				DataItem cborName = this.getNestedElementNameMapping(CBORLoginFailure.REASON, elementEntry);

				builder.add(new SimpleValue(SimpleValueType.NULL));
				builder.add(cborName);
				builder.addArray();
				builder.add(this.getNestedElementEnumValueMapping(CBORLoginFailure.REASON, this.reason.getXmlName(), elementEntry));
			}
			
			// Serialize 'other-reason-type-definition'
			if(this.otherReasonTypeDefinition != null) {
				DataItem cborName = this.getNestedElementNameMapping(CBORLoginFailure.OTHER_REASON_TYPE_DEFINITION, elementEntry);

				builder.add(new SimpleValue(SimpleValueType.NULL));
				builder.add(cborName);
				builder.addArray();
				builder.add(new UnicodeString(this.otherReasonTypeDefinition));
			}
		}
		catch(DictionaryPathException | RuntimeException ex) {
			throw new CBORSerializationException("Error during serialization, see nested exception for details", ex);
		}
	}
}
