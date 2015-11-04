/*
 * No license defined yet.
 */
package de.decoit.simu.cbor.ifmap.simu.deserializer.metadata;

import co.nstant.in.cbor.CborDecoder;
import co.nstant.in.cbor.model.Array;
import co.nstant.in.cbor.model.DataItem;
import de.decoit.simu.cbor.ifmap.enums.IfMapCardinality;
import de.decoit.simu.cbor.ifmap.simu.metadata.singlevalue.CBORDeviceDiscoveredBy;
import de.decoit.simu.cbor.ifmap.simu.util.SimuNamespaces;
import de.decoit.simu.cbor.ifmap.util.TimestampHelper;
import de.decoit.simu.cbor.xml.dictionary.DictionaryProvider;
import de.decoit.simu.cbor.xml.dictionary.DictionarySimpleElement;
import java.io.ByteArrayInputStream;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author Thomas Rix (rix@decoit.de)
 */
@Slf4j
public class DeviceDiscoveredByDeserializerNoDictTest {
	private final String publisherId = "my-publisher-id";
	private final ZonedDateTime ifMapTimestamp = ZonedDateTime.parse("2011-12-03T10:15:30.123456+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	private final String discoverer = "device-discoverer";


	@Test
	public void testDeserialize() throws Exception {
		byte[] input = DatatypeConverter.parseHexBinary("847822687474703A2F2F73696D752D70726F6A6563742E64652F584D4C"
				+ "536368656D612F31746465766963652D646973636F76657265642D6279887269666D61702D7075626C69736865722D69646F"
				+ "6D792D7075626C69736865722D69646F69666D61702D74696D657374616D70C11A4ED9E8B2781869666D61702D74696D6573"
				+ "74616D702D6672616374696F6EC482281A075BCA007169666D61702D63617264696E616C6974796B73696E676C6556616C75"
				+ "6584F66A646973636F766572657280716465766963652D646973636F7665726572");

		ByteArrayInputStream bis = new ByteArrayInputStream(input);
		CborDecoder cd = new CborDecoder(bis);
		List<DataItem> diList = cd.decode();
		Array topLevelArray = (Array) diList.get(0);
		
		String dictPath = "<" + SimuNamespaces.SIMU + ">" + CBORDeviceDiscoveredBy.XML_NAME;
		DictionarySimpleElement dse = DictionaryProvider.getInstance().findElementByPath(dictPath);
		
		CBORDeviceDiscoveredBy result = DeviceDiscoveredByDeserializer
											.getInstance()
											.deserialize((Array)topLevelArray.getDataItems().get(2), 
														 topLevelArray.getDataItems().get(3),
														 dse);

		assertEquals(publisherId, result.getIfMapPublisherId());
		assertEquals(TimestampHelper.toUTC(ifMapTimestamp), result.getIfMapTimestamp());
		assertEquals(IfMapCardinality.SINGLE_VALUE, result.getIfMapCardinality());
		assertEquals(discoverer, result.getDiscoverer());
	}
}