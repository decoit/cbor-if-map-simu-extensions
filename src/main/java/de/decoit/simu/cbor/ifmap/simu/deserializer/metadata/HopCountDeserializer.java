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
import de.decoit.simu.cbor.ifmap.simu.metadata.singlevalue.CBORHopCount;
import de.decoit.simu.cbor.ifmap.util.TimestampHelper;
import de.decoit.simu.cbor.xml.dictionary.DictionarySimpleElement;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;



/**
 *
 * @author Thomas Rix (rix@decoit.de)
 */
@Slf4j
public class HopCountDeserializer implements VendorMetadataDeserializer<CBORHopCount> {
	private static HopCountDeserializer instance;


	/**
	 * Get the singleton instance of this deserializer.
	 *
	 * @return Deserializer instance
	 */
	public static HopCountDeserializer getInstance() {
		if(instance == null) {
			instance = new HopCountDeserializer();
		}

		return instance;
	}


	/**
	 * Private constructor, this is a singleton class.
	 */
	private HopCountDeserializer() {}
	
	
	@Override
	public CBORHopCount deserialize(final Array attributes, 
									final DataItem nestedDataItem,
									final DictionarySimpleElement elementDictEntry) throws CBORDeserializationException {
		if(log.isDebugEnabled()) {
			log.debug("Attributes array: " + attributes);
			log.debug("Nested data item: " + nestedDataItem);
			log.debug("Dictionary entry: " + elementDictEntry);
		}
		
		// Initially define the required variables to build the target object
		String publisherId = null;
		ZonedDateTime timestamp = null;
		Integer hopCount = null;
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
				case CBORHopCount.IFMAP_PUBLISHER_ID:
					publisherId = MetadataDeserializerManager.processUnicodeStringItem(attrValue, true);
					break;
				case CBORHopCount.IFMAP_TIMESTAMP:
					timestampDi = attrValue;
					break;
				case CBORHopCount.IFMAP_TIMESTAMP_FRACTION:
					timestampFractionDi = attrValue;
			}
		}
		
		// Process nested hop count value
		if(nestedDataItem.getMajorType() != MajorType.UNSIGNED_INTEGER) {
			throw new CBORDeserializationException("Expected nested UnsignedInteger, found " + nestedDataItem.getMajorType());
		}
		hopCount = MetadataDeserializerManager.processUnsignedIntegerItem(nestedDataItem, true).intValueExact();
		

		if(timestampDi != null) {
			timestamp = TimestampHelper.fromEpochTimeDataItem(timestampDi, timestampFractionDi);
		}

		// Build return value object
		CBORHopCount rv;
		if(publisherId != null && timestamp != null) {
			rv = new CBORHopCount(publisherId, timestamp, hopCount);
		}
		else {
			rv = new CBORHopCount(hopCount);
		}

		return rv;
	}
}
