/*
 * No license defined yet.
 */
package de.decoit.simu.cbor.ifmap.simu.deserializer.identifier;

import co.nstant.in.cbor.model.Array;
import co.nstant.in.cbor.model.DataItem;
import de.decoit.simu.cbor.ifmap.deserializer.ExtendedIdentifierDeserializerManager;
import de.decoit.simu.cbor.ifmap.deserializer.vendor.VendorIdentifierDeserializer;
import de.decoit.simu.cbor.ifmap.exception.CBORDeserializationException;
import de.decoit.simu.cbor.ifmap.simu.identifier.CBORFile;
import de.decoit.simu.cbor.ifmap.simu.identifier.CBORService;
import de.decoit.simu.cbor.xml.dictionary.DictionarySimpleElement;
import java.util.List;
import lombok.extern.slf4j.Slf4j;



/**
 * Deserializer class to create {@link CBORService} instances from CBOR data.
 *
 * @author Thomas Rix (rix@decoit.de)
 */
@Slf4j
public class FileDeserializer implements VendorIdentifierDeserializer<CBORFile> {
	private static FileDeserializer instance;


	/**
	 * Get the singleton instance of this deserializer.
	 *
	 * @return Deserializer instance
	 */
	public static FileDeserializer getInstance() {
		if(instance == null) {
			instance = new FileDeserializer();
		}

		return instance;
	}


	/**
	 * Private constructor, this is a singleton class.
	 */
	private FileDeserializer() {}
	
	
	@Override
	public CBORFile deserialize(final Array attributes, 
								final Array nestedTags, 
								final DictionarySimpleElement elementDictEntry) throws CBORDeserializationException {
		if(log.isDebugEnabled()) {
			log.debug("Attributes array: " + attributes);
			log.debug("Nested tags array: " + nestedTags);
			log.debug("Dictionary entry: " + elementDictEntry);
		}

		// Initially define the required variables to build the target object
		String administrativeDomain = null;
		String path = null;

		// Get list of all attribute data items
		List<DataItem> attributesDataItems = attributes.getDataItems();

		// Iterate over the data items in steps of 2
		for(int i=0; i<attributesDataItems.size(); i=i+2) {
			// Get name and value data items
			DataItem attrName = attributesDataItems.get(i);
			DataItem attrValue = attributesDataItems.get(i+1);

			String attrNameStr = ExtendedIdentifierDeserializerManager.getAttributeXmlName(attrName, elementDictEntry);

			// Process the attribute value
			switch(attrNameStr) {
				case CBORFile.ADMINISTRATIVE_DOMAIN:
					administrativeDomain = ExtendedIdentifierDeserializerManager.processUnicodeStringItem(attrValue, true);
					break;
				case CBORFile.PATH:
					path = ExtendedIdentifierDeserializerManager.processUnicodeStringItem(attrValue, true);
					break;
			}
		}

		// Build return value object
		CBORFile rv = new CBORFile(administrativeDomain, path);

		return rv;
	}
}
