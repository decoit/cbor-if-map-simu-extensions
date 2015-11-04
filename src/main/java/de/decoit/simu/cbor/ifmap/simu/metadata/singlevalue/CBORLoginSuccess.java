/*
 * No license defined yet.
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
 * Java representation of the login-success metadata from the SIMU namespace.
 *
 * @author Thomas Rix (rix@decoit.de)
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Slf4j
public class CBORLoginSuccess extends AbstractSingleValueMetadata {
	public static final String XML_NAME = "login-success";
	public static final String CREDENTIAL_TYPE = "credential-type";
	public static final String OTHER_CREDENTIAL_TYPE_DEFINITION = "other-credential-type-definition";

	@Getter
	private final SIMUCredentialType credentialType;
	@Getter
	private final String otherCredentialTypeDefinition;


	/**
	 * Create a new login-success metadata.
	 * This constructor does not work with credential type OTHER because an additional other type
	 * definition is required in that case.
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
	 */
	public CBORLoginSuccess(String ifMapPublisherId, ZonedDateTime ifMapTimestamp, SIMUCredentialType credentialType) {
		this(ifMapPublisherId, ifMapTimestamp, credentialType, null);
	}


	/**
	 * Create a new login-success metadata.
	 * This constructor allows usage of credential type OTHER if an additional other type definition
	 * is provided. Otherwise the other type definition may be null.
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
	 */
	public CBORLoginSuccess(String ifMapPublisherId, ZonedDateTime ifMapTimestamp, SIMUCredentialType credentialType, String otherCredentialTypeDefinition) {
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

		this.credentialType = credentialType;
		this.otherCredentialTypeDefinition = otherCredentialTypeDefinition;
	}
	
	
	/**
	 * Create a new login-success metadata.
	 * This constructor does not work with credential type OTHER because an additional other type
	 * definition is required in that case.
	 * 
	 * This constructor should be used for metadata that is sent from the CLIENT to the SERVER. It does not include
	 * the attributes ifmap-publisher-id, ifmap-timestamp and ifmap-timestamp-fraction.
	 * The timestamps are stored with UTC timezone. If a timestamp with another timezone is
	 * passed into the constructor, it will be converted to UTC. The IF-MAP timestamp fraction
	 * element will be calculated from the provided timestamp.
	 *
	 * @param credentialType Type of the used credentials
	 */
	public CBORLoginSuccess(SIMUCredentialType credentialType) {
		this(credentialType, null);
	}


	/**
	 * Create a new login-success metadata.
	 * This constructor allows usage of credential type OTHER if an additional other type definition
	 * is provided. Otherwise the other type definition may be null.
	 * 
	 * This constructor should be used for metadata that is sent from the CLIENT to the SERVER. It does not include
	 * the attributes ifmap-publisher-id, ifmap-timestamp and ifmap-timestamp-fraction.
	 * The timestamps are stored with UTC timezone. If a timestamp with another timezone is
	 * passed into the constructor, it will be converted to UTC. The IF-MAP timestamp fraction
	 * element will be calculated from the provided timestamp.
	 *
	 * @param credentialType Type of the used credentials
	 * @param otherCredentialTypeDefinition Additional credential type definition, may be null if applicable
	 */
	public CBORLoginSuccess(SIMUCredentialType credentialType, String otherCredentialTypeDefinition) {
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

		this.credentialType = credentialType;
		this.otherCredentialTypeDefinition = otherCredentialTypeDefinition;
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
				DataItem cborName = this.getNestedElementNameMapping(CBORLoginSuccess.CREDENTIAL_TYPE, elementEntry);

				builder.add(new SimpleValue(SimpleValueType.NULL));
				builder.add(cborName);
				builder.addArray();
				builder.add(this.getNestedElementEnumValueMapping(CBORLoginSuccess.CREDENTIAL_TYPE, this.credentialType.getXmlName(), elementEntry));
			}
			
			// Serialize 'other-credential-type-definition'
			if(this.otherCredentialTypeDefinition != null) {
				DataItem cborName = this.getNestedElementNameMapping(CBORLoginSuccess.OTHER_CREDENTIAL_TYPE_DEFINITION, elementEntry);

				builder.add(new SimpleValue(SimpleValueType.NULL));
				builder.add(cborName);
				builder.addArray();
				builder.add(new UnicodeString(this.otherCredentialTypeDefinition));
			}
		}
		catch(DictionaryPathException | RuntimeException ex) {
			throw new CBORSerializationException("Error during serialization, see nested exception for details", ex);
		}
	}
}
