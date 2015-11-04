/*
 * No license defined yet.
 */
package de.decoit.simu.cbor.ifmap.simu.deserializer.metadata;

import co.nstant.in.cbor.CborDecoder;
import co.nstant.in.cbor.model.Array;
import co.nstant.in.cbor.model.DataItem;
import de.decoit.simu.cbor.ifmap.enums.IfMapCardinality;
import de.decoit.simu.cbor.ifmap.simu.enums.SIMUFileImportanceType;
import de.decoit.simu.cbor.ifmap.simu.enums.SIMUFileStatusType;
import de.decoit.simu.cbor.ifmap.simu.metadata.singlevalue.CBORFileStatus;
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
public class FileStatusDeserializerNoDictTest {
	private final String publisherId = "my-publisher-id";
	private final ZonedDateTime ifMapTimestamp = ZonedDateTime.parse("2011-12-03T10:15:30.123456+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	private final ZonedDateTime discoveredTime = ZonedDateTime.parse("2011-12-03T10:10:15+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	private final SIMUFileStatusType status = SIMUFileStatusType.NEW;
	private final SIMUFileImportanceType importance = SIMUFileImportanceType.CRITICAL;


	@Test
	public void testDeserialize() throws Exception {
		byte[] input = DatatypeConverter.parseHexBinary("847822687474703A2F2F73696D752D70726F6A6563742E64652F584D4C"
				+ "536368656D612F316B66696C652D737461747573887269666D61702D7075626C69736865722D69646F6D792D7075626C6973"
				+ "6865722D69646F69666D61702D74696D657374616D70C11A4ED9E8B2781869666D61702D74696D657374616D702D66726163"
				+ "74696F6EC482281A075BCA007169666D61702D63617264696E616C6974796B73696E676C6556616C75658CF6667374617475"
				+ "7380636E6577F66F646973636F76657265642D74696D6580C11A4ED9E777F66A696D706F7274616E63658068637269746963"
				+ "616C");

		ByteArrayInputStream bis = new ByteArrayInputStream(input);
		CborDecoder cd = new CborDecoder(bis);
		List<DataItem> diList = cd.decode();
		Array topLevelArray = (Array) diList.get(0);
		
		String dictPath = "<" + SimuNamespaces.SIMU + ">" + CBORFileStatus.XML_NAME;
		DictionarySimpleElement dse = DictionaryProvider.getInstance().findElementByPath(dictPath);
		
		CBORFileStatus result = FileStatusDeserializer
										.getInstance()
										.deserialize((Array)topLevelArray.getDataItems().get(2), 
													 topLevelArray.getDataItems().get(3),
													 dse);

		assertEquals(publisherId, result.getIfMapPublisherId());
		assertEquals(TimestampHelper.toUTC(ifMapTimestamp), result.getIfMapTimestamp());
		assertEquals(IfMapCardinality.SINGLE_VALUE, result.getIfMapCardinality());
		assertEquals(TimestampHelper.toUTC(discoveredTime), result.getDiscoveredTime());
		assertEquals(status, result.getStatus());
		assertEquals(importance, result.getImportance());
	}
}
