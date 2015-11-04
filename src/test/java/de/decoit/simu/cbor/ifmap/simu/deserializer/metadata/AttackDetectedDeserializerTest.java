/*
 * No license defined yet.
 */
package de.decoit.simu.cbor.ifmap.simu.deserializer.metadata;

import co.nstant.in.cbor.CborDecoder;
import co.nstant.in.cbor.model.Array;
import co.nstant.in.cbor.model.DataItem;
import de.decoit.simu.cbor.ifmap.enums.IfMapCardinality;
import de.decoit.simu.cbor.ifmap.simu.AbstractSimuTestBase;
import de.decoit.simu.cbor.ifmap.simu.metadata.multivalue.CBORAttackDetected;
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
public class AttackDetectedDeserializerTest extends AbstractSimuTestBase {
	private final String publisherId = "my-publisher-id";
	private final ZonedDateTime ifMapTimestamp = ZonedDateTime.parse("2011-12-03T10:15:30.123456+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	private final String type = "attack-type";
	private final String id = "attack-id";
	private final Double severityScore = 42.0;


	@Test
	public void testDeserialize() throws Exception {
		byte[] input = DatatypeConverter.parseHexBinary("84040888006F6D792D7075626C69736865722D696401C11A4ED9E8B202"
				+ "C482281A075BCA00030188F600806B61747461636B2D74797065F601806961747461636B2D6964");

		ByteArrayInputStream bis = new ByteArrayInputStream(input);
		CborDecoder cd = new CborDecoder(bis);
		List<DataItem> diList = cd.decode();
		Array topLevelArray = (Array) diList.get(0);
		
		String dictPath = "<" + SimuNamespaces.SIMU + ">" + CBORAttackDetected.XML_NAME;
		DictionarySimpleElement dse = DictionaryProvider.getInstance().findElementByPath(dictPath);
		
		CBORAttackDetected result = AttackDetectedDeserializer
											.getInstance()
											.deserialize((Array)topLevelArray.getDataItems().get(2), 
														 topLevelArray.getDataItems().get(3),
														 dse);

		assertEquals(publisherId, result.getIfMapPublisherId());
		assertEquals(TimestampHelper.toUTC(ifMapTimestamp), result.getIfMapTimestamp());
		assertEquals(IfMapCardinality.MULTI_VALUE, result.getIfMapCardinality());
		assertEquals(type, result.getType());
		assertEquals(id, result.getId());
		assertNull(result.getSeverityScore());
	}
	
	
	@Test
	public void testDeserialize_full() throws Exception {
		byte[] input = DatatypeConverter.parseHexBinary("84040888006F6D792D7075626C69736865722D696401C11A4ED9E8B202"
				+ "C482281A075BCA0003018CF600806B61747461636B2D74797065F601806961747461636B2D6964F60280FB40450000000000"
				+ "00");

		ByteArrayInputStream bis = new ByteArrayInputStream(input);
		CborDecoder cd = new CborDecoder(bis);
		List<DataItem> diList = cd.decode();
		Array topLevelArray = (Array) diList.get(0);
		
		String dictPath = "<" + SimuNamespaces.SIMU + ">" + CBORAttackDetected.XML_NAME;
		DictionarySimpleElement dse = DictionaryProvider.getInstance().findElementByPath(dictPath);
		
		CBORAttackDetected result = AttackDetectedDeserializer
											.getInstance()
											.deserialize((Array)topLevelArray.getDataItems().get(2), 
														 topLevelArray.getDataItems().get(3),
														 dse);

		assertEquals(publisherId, result.getIfMapPublisherId());
		assertEquals(TimestampHelper.toUTC(ifMapTimestamp), result.getIfMapTimestamp());
		assertEquals(IfMapCardinality.MULTI_VALUE, result.getIfMapCardinality());
		assertEquals(type, result.getType());
		assertEquals(id, result.getId());
		assertEquals(severityScore, result.getSeverityScore());
	}
}
