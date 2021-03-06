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
package de.decoit.simu.cbor.ifmap.simu.metadata.multivalue;

import co.nstant.in.cbor.CborBuilder;
import co.nstant.in.cbor.CborEncoder;
import co.nstant.in.cbor.builder.ArrayBuilder;
import java.io.ByteArrayOutputStream;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import javax.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Thomas Rix (rix@decoit.de)
 */
@Slf4j
public class CBORAttackDetectedNoDictTest {
	private final String publisherId = "my-publisher-id";
	private final ZonedDateTime ifMapTimestamp = ZonedDateTime.parse("2011-12-03T10:15:30.123456+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	private final String type = "attack-type";
	private final String id = "attack-id";
	private final Double severityScore = 42.0;
	
	
	@Test
	public void testCborSerialize() throws Exception {
		CBORAttackDetected instance = new CBORAttackDetected(publisherId, ifMapTimestamp, type, id);

		CborBuilder cb = new CborBuilder();
		ArrayBuilder ab = cb.addArray();

		instance.cborSerialize(ab);

		ab.end();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		CborEncoder ce = new CborEncoder(bos);
		ce.encode(cb.build());

		log.info("CBOR serialize:");
		log.info(DatatypeConverter.printHexBinary(bos.toByteArray()));

		byte[] expResult = DatatypeConverter.parseHexBinary("847822687474703A2F2F73696D752D70726F6A6563742E64652F584D4C"
				+ "536368656D612F316F61747461636B2D6465746563746564887269666D61702D7075626C69736865722D69646F6D792D7075"
				+ "626C69736865722D69646F69666D61702D74696D657374616D70C11A4ED9E8B2781869666D61702D74696D657374616D702D"
				+ "6672616374696F6EC482281A075BCA007169666D61702D63617264696E616C6974796A6D756C746956616C756588F6647479"
				+ "7065806B61747461636B2D74797065F6626964806961747461636B2D6964");

		assertTrue("Byte array mismatch", Arrays.equals(expResult, bos.toByteArray()));
	}
	
	
	@Test
	public void testCborSerialize_full() throws Exception {
		CBORAttackDetected instance = new CBORAttackDetected(publisherId, ifMapTimestamp, type, id);
		instance.setSeverityScore(severityScore);

		CborBuilder cb = new CborBuilder();
		ArrayBuilder ab = cb.addArray();

		instance.cborSerialize(ab);

		ab.end();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		CborEncoder ce = new CborEncoder(bos);
		ce.encode(cb.build());

		log.info("CBOR serialize:");
		log.info(DatatypeConverter.printHexBinary(bos.toByteArray()));

		byte[] expResult = DatatypeConverter.parseHexBinary("847822687474703A2F2F73696D752D70726F6A6563742E64652F584D4C"
				+ "536368656D612F316F61747461636B2D6465746563746564887269666D61702D7075626C69736865722D69646F6D792D7075"
				+ "626C69736865722D69646F69666D61702D74696D657374616D70C11A4ED9E8B2781869666D61702D74696D657374616D702D"
				+ "6672616374696F6EC482281A075BCA007169666D61702D63617264696E616C6974796A6D756C746956616C75658CF6647479"
				+ "7065806B61747461636B2D74797065F6626964806961747461636B2D6964F66E73657665726974792D73636F726580FB4045"
				+ "000000000000");

		assertTrue("Byte array mismatch", Arrays.equals(expResult, bos.toByteArray()));
	}
}
