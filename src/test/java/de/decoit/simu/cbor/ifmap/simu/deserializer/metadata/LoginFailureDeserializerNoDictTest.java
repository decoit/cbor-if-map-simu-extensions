/*
 * No license defined yet.
 */
package de.decoit.simu.cbor.ifmap.simu.deserializer.metadata;

import co.nstant.in.cbor.CborDecoder;
import co.nstant.in.cbor.model.Array;
import co.nstant.in.cbor.model.DataItem;
import de.decoit.simu.cbor.ifmap.enums.IfMapCardinality;
import de.decoit.simu.cbor.ifmap.simu.enums.SIMUCredentialType;
import de.decoit.simu.cbor.ifmap.simu.enums.SIMUReasonType;
import de.decoit.simu.cbor.ifmap.simu.metadata.singlevalue.CBORLoginFailure;
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
public class LoginFailureDeserializerNoDictTest {
	private final String publisherId = "my-publisher-id";
	private final ZonedDateTime ifMapTimestamp = ZonedDateTime.parse("2011-12-03T10:15:30.123456+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	private final SIMUCredentialType credentialType = SIMUCredentialType.PASSWORD;
	private final SIMUReasonType reason = SIMUReasonType.INVALID_CREDENTIALS;
	private final String otherCredentialTypeDefinition = "custom-credential-type";
	private final String otherReasonTypeDefinition = "custom-reason-type";


	@Test
	public void testDeserialize() throws Exception {
		byte[] input = DatatypeConverter.parseHexBinary("847822687474703A2F2F73696D752D70726F6A6563742E64652F584D4C"
				+ "536368656D612F316D6C6F67696E2D6661696C757265887269666D61702D7075626C69736865722D69646F6D792D7075626C"
				+ "69736865722D69646F69666D61702D74696D657374616D70C11A4ED9E8B2781869666D61702D74696D657374616D702D6672"
				+ "616374696F6EC482281A075BCA007169666D61702D63617264696E616C6974796B73696E676C6556616C756588F66F637265"
				+ "64656E7469616C2D74797065806850617373776F7264F666726561736F6E8073496E76616C69642043726564656E7469616C"
				+ "73");

		ByteArrayInputStream bis = new ByteArrayInputStream(input);
		CborDecoder cd = new CborDecoder(bis);
		List<DataItem> diList = cd.decode();
		Array topLevelArray = (Array) diList.get(0);
		
		String dictPath = "<" + SimuNamespaces.SIMU + ">" + CBORLoginFailure.XML_NAME;
		DictionarySimpleElement dse = DictionaryProvider.getInstance().findElementByPath(dictPath);
		
		CBORLoginFailure result = LoginFailureDeserializer
										.getInstance()
										.deserialize((Array)topLevelArray.getDataItems().get(2), 
													 topLevelArray.getDataItems().get(3),
													 dse);

		assertEquals(publisherId, result.getIfMapPublisherId());
		assertEquals(TimestampHelper.toUTC(ifMapTimestamp), result.getIfMapTimestamp());
		assertEquals(IfMapCardinality.SINGLE_VALUE, result.getIfMapCardinality());
		assertEquals(credentialType, result.getCredentialType());
		assertEquals(reason, result.getReason());
		assertNull(result.getOtherCredentialTypeDefinition());
		assertNull(result.getOtherReasonTypeDefinition());
	}


	@Test
	public void testDeserialize_full() throws Exception {
		byte[] input = DatatypeConverter.parseHexBinary("847822687474703A2F2F73696D752D70726F6A6563742E64652F584D4C"
				+ "536368656D612F316D6C6F67696E2D6661696C757265887269666D61702D7075626C69736865722D69646F6D792D7075626C"
				+ "69736865722D69646F69666D61702D74696D657374616D70C11A4ED9E8B2781869666D61702D74696D657374616D702D6672"
				+ "616374696F6EC482281A075BCA007169666D61702D63617264696E616C6974796B73696E676C6556616C756590F66F637265"
				+ "64656E7469616C2D7479706580654F74686572F678206F746865722D63726564656E7469616C2D747970652D646566696E69"
				+ "74696F6E8076637573746F6D2D63726564656E7469616C2D74797065F666726561736F6E80654F74686572F6781C6F746865"
				+ "722D726561736F6E2D747970652D646566696E6974696F6E8072637573746F6D2D726561736F6E2D74797065");

		ByteArrayInputStream bis = new ByteArrayInputStream(input);
		CborDecoder cd = new CborDecoder(bis);
		List<DataItem> diList = cd.decode();
		Array topLevelArray = (Array) diList.get(0);
		
		String dictPath = "<" + SimuNamespaces.SIMU + ">" + CBORLoginFailure.XML_NAME;
		DictionarySimpleElement dse = DictionaryProvider.getInstance().findElementByPath(dictPath);
		
		CBORLoginFailure result = LoginFailureDeserializer
										.getInstance()
										.deserialize((Array)topLevelArray.getDataItems().get(2), 
													 topLevelArray.getDataItems().get(3),
													 dse);

		assertEquals(publisherId, result.getIfMapPublisherId());
		assertEquals(TimestampHelper.toUTC(ifMapTimestamp), result.getIfMapTimestamp());
		assertEquals(IfMapCardinality.SINGLE_VALUE, result.getIfMapCardinality());
		assertEquals(SIMUCredentialType.OTHER, result.getCredentialType());
		assertEquals(SIMUReasonType.OTHER, result.getReason());
		assertEquals(otherCredentialTypeDefinition, result.getOtherCredentialTypeDefinition());
		assertEquals(otherReasonTypeDefinition, result.getOtherReasonTypeDefinition());
	}
}
