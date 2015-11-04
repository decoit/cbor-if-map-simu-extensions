/*
 * No license defined yet.
 */
package de.decoit.simu.cbor.ifmap.simu.deserializer.metadata;

import co.nstant.in.cbor.model.Array;
import co.nstant.in.cbor.model.DataItem;
import co.nstant.in.cbor.model.MajorType;
import de.decoit.simu.cbor.ifmap.deserializer.MetadataDeserializerManager;
import de.decoit.simu.cbor.ifmap.deserializer.vendor.VendorMetadataDeserializer;
import de.decoit.simu.cbor.ifmap.exception.CBORDeserializationException;
import de.decoit.simu.cbor.ifmap.simu.enums.SIMUCredentialType;
import de.decoit.simu.cbor.ifmap.simu.enums.SIMUReasonType;
import de.decoit.simu.cbor.ifmap.simu.metadata.singlevalue.CBORLoginFailure;
import de.decoit.simu.cbor.ifmap.util.TimestampHelper;
import de.decoit.simu.cbor.xml.dictionary.DictionarySimpleElement;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;



/**
 * Deserializer class to create {@link CBORLoginFailure} instances from CBOR data.
 *
 * @author Thomas Rix (rix@decoit.de)
 */
@Slf4j
public class LoginFailureDeserializer implements VendorMetadataDeserializer<CBORLoginFailure> {
	private static LoginFailureDeserializer instance;


	/**
	 * Get the singleton instance of this deserializer.
	 *
	 * @return Deserializer instance
	 */
	public static LoginFailureDeserializer getInstance() {
		if(instance == null) {
			instance = new LoginFailureDeserializer();
		}

		return instance;
	}


	/**
	 * Private constructor, this is a singleton class.
	 */
	private LoginFailureDeserializer() {}
	
	
	@Override
	public CBORLoginFailure deserialize(final Array attributes, 
										final DataItem nestedDataItem,
										final DictionarySimpleElement elementDictEntry) throws CBORDeserializationException {
		if(log.isDebugEnabled()) {
			log.debug("Attributes array: " + attributes);
			log.debug("Nested data item: " + nestedDataItem);
			log.debug("Dictionary entry: " + elementDictEntry);
		}
		
		if(nestedDataItem.getMajorType() != MajorType.ARRAY) {
			throw new CBORDeserializationException("Expected nested Array, found " + nestedDataItem.getMajorType());
		}
		Array nestedTags = (Array) nestedDataItem;
		
		// Initially define the required variables to build the target object
		String publisherId = null;
		ZonedDateTime timestamp = null;
		SIMUCredentialType credentialType = null;
		SIMUReasonType reason = null;
		String otherCredentialTypeDefinition = null;
		String otherReasonTypeDefinition = null;
		DataItem timestampDi = null;
		DataItem timestampFractionDi = null;

		// Get list of all attribute data items
		List<DataItem> attributesDataItems = attributes.getDataItems();

		// Iterate over the data items in steps of 2
		for(int i=0; i<attributesDataItems.size(); i=i+2) {
			// Get name and value data items
			DataItem attrName = attributesDataItems.get(i);
			DataItem attrValue = attributesDataItems.get(i+1);

			String attrNameStr = MetadataDeserializerManager.getAttributeXmlName(attrName, elementDictEntry);

			// Process the attribute value
			switch(attrNameStr) {
				case CBORLoginFailure.IFMAP_PUBLISHER_ID:
					publisherId = MetadataDeserializerManager.processUnicodeStringItem(attrValue, true);
					break;
				case CBORLoginFailure.IFMAP_TIMESTAMP:
					timestampDi = attrValue;
					break;
				case CBORLoginFailure.IFMAP_TIMESTAMP_FRACTION:
					timestampFractionDi = attrValue;
			}
		}
		
		// Get list of all nested tags data items
		List<DataItem> nestedTagsDataItems = nestedTags.getDataItems();

		// Iterate over the data items in steps of 4
		for(int i=0; i<nestedTagsDataItems.size(); i=i+4) {
			// Get namespace, name and nested tag/value data items (index i and i+1)
			// The attributes array is ignore because a IF-MAP 'capability' has no attributes on nested tags
			// No further nested elements are expected, only a value should be present (index i+3)
			DataItem ntNamespace = nestedTagsDataItems.get(i);
			DataItem ntName = nestedTagsDataItems.get(i+1);
			DataItem ntNestedValue = nestedTagsDataItems.get(i+3);

			// The namespace should be of simple type NULL, no namespace is expected to be found here
			if(!MetadataDeserializerManager.isSimpleValueNull(ntNamespace)) {
				throw new CBORDeserializationException("Unexpected nested element with namespace found inside 'login-failure' element");
			}

			String nestedTagName = MetadataDeserializerManager.getNestedTagXmlName(ntName, elementDictEntry);

			// Process the nested element value
			switch(nestedTagName) {
				case CBORLoginFailure.CREDENTIAL_TYPE:
					credentialType = SIMUCredentialType.fromXmlName(MetadataDeserializerManager.getNestedTagEnumValueXmlName(
																			ntName, ntNestedValue, elementDictEntry));
					break;
				case CBORLoginFailure.OTHER_CREDENTIAL_TYPE_DEFINITION:
					otherCredentialTypeDefinition = MetadataDeserializerManager.processUnicodeStringItem(ntNestedValue, true);
					break;
				case CBORLoginFailure.REASON:
					reason = SIMUReasonType.fromXmlName(MetadataDeserializerManager.getNestedTagEnumValueXmlName(
																			ntName, ntNestedValue, elementDictEntry));
					break;
				case CBORLoginFailure.OTHER_REASON_TYPE_DEFINITION:
					otherReasonTypeDefinition = MetadataDeserializerManager.processUnicodeStringItem(ntNestedValue, true);
					break;
			}
		}

		if(timestampDi != null) {
			timestamp = TimestampHelper.fromEpochTimeDataItem(timestampDi, timestampFractionDi);
		}

		// Build return value object
		CBORLoginFailure rv;
		if(publisherId != null && timestamp != null) {
			rv = new CBORLoginFailure(publisherId, timestamp, credentialType, otherCredentialTypeDefinition, reason, otherReasonTypeDefinition);
		}
		else {
			rv = new CBORLoginFailure(credentialType, otherCredentialTypeDefinition, reason, otherReasonTypeDefinition);
		}

		return rv;
	}
}