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
package de.decoit.simu.cbor.ifmap.simu.deserializer.metadata;

import co.nstant.in.cbor.CborDecoder;
import co.nstant.in.cbor.model.Array;
import co.nstant.in.cbor.model.DataItem;
import de.decoit.simu.cbor.ifmap.enums.IfMapCardinality;
import de.decoit.simu.cbor.ifmap.simu.AbstractSimuTestBase;
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
public class LoginFailureDeserializerTest extends AbstractSimuTestBase {
	private final String publisherId = "my-publisher-id";
	private final ZonedDateTime ifMapTimestamp = ZonedDateTime.parse("2011-12-03T10:15:30.123456+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	private final SIMUCredentialType credentialType = SIMUCredentialType.PASSWORD;
	private final SIMUReasonType reason = SIMUReasonType.INVALID_CREDENTIALS;
	private final String otherCredentialTypeDefinition = "custom-credential-type";
	private final String otherReasonTypeDefinition = "custom-reason-type";


	@Test
	public void testDeserialize() throws Exception {
		byte[] input = DatatypeConverter.parseHexBinary("84040A88006F6D792D7075626C69736865722D696401C11A4ED9E8B202"
				+ "C482281A075BCA00030088F6008000F6018001");

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
		byte[] input = DatatypeConverter.parseHexBinary("84040A88006F6D792D7075626C69736865722D696401C11A4ED9E8B202"
				+ "C482281A075BCA00030090F6008004F6028076637573746F6D2D63726564656E7469616C2D74797065F6018005F603807263"
				+ "7573746F6D2D726561736F6E2D74797065");

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
