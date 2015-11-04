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
import de.decoit.simu.cbor.ifmap.enums.IfMapCardinality;
import de.decoit.simu.cbor.ifmap.simu.AbstractSimuTestBase;
import de.decoit.simu.cbor.ifmap.simu.util.SimuNamespaces;
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
public class CBORAttackDetectedTest extends AbstractSimuTestBase {
	private final String publisherId = "my-publisher-id";
	private final ZonedDateTime ifMapTimestamp = ZonedDateTime.parse("2011-12-03T10:15:30.123456+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	private final String type = "attack-type";
	private final String id = "attack-id";
	private final Double severityScore = 42.0;
	private final String publisherId2 = "my-publisher-id-2";
	private final ZonedDateTime ifMapTimestamp2 = ZonedDateTime.parse("2012-12-03T10:15:30.123456+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	private final Double severityScore2 = 21.0;
	private final String type2 = "attack-type-2";
	private final String id2 = "attack-id-2";
	
	
	@Test
	public void testConstructor() {
		CBORAttackDetected instance = new CBORAttackDetected(publisherId, ifMapTimestamp, type, id);
		
		assertEquals(SimuNamespaces.SIMU, instance.getNamespace());
		assertEquals(IfMapCardinality.MULTI_VALUE, instance.getIfMapCardinality());
		assertEquals(type, instance.getType());
		assertEquals(id, instance.getId());
		assertNull(instance.getSeverityScore());
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_null_String() {
		CBORAttackDetected instance = new CBORAttackDetected(publisherId, ifMapTimestamp, null, id);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_EmptyString_String() {
		CBORAttackDetected instance = new CBORAttackDetected(publisherId, ifMapTimestamp, "", id);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_Whitespaces_String() {
		CBORAttackDetected instance = new CBORAttackDetected(publisherId, ifMapTimestamp, "   ", id);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_String_Null() {
		CBORAttackDetected instance = new CBORAttackDetected(publisherId, ifMapTimestamp, type, null);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_String_EmptyString() {
		CBORAttackDetected instance = new CBORAttackDetected(publisherId, ifMapTimestamp, type, "");
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_String_Whitespaces() {
		CBORAttackDetected instance = new CBORAttackDetected(publisherId, ifMapTimestamp, type, "   ");
	}
	
	
	@Test
	public void testConstructor_ClientSide() {
		CBORAttackDetected instance = new CBORAttackDetected(type, id);
		
		assertEquals(SimuNamespaces.SIMU, instance.getNamespace());
		assertEquals(IfMapCardinality.MULTI_VALUE, instance.getIfMapCardinality());
		assertNull(instance.getIfMapPublisherId());
		assertNull(instance.getIfMapTimestamp());
		assertEquals(type, instance.getType());
		assertEquals(id, instance.getId());
		assertNull(instance.getSeverityScore());
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_ClientSide_null_String() {
		CBORAttackDetected instance = new CBORAttackDetected(null, id);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_ClientSide_EmptyString_String() {
		CBORAttackDetected instance = new CBORAttackDetected("", id);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_ClientSide_Whitespaces_String() {
		CBORAttackDetected instance = new CBORAttackDetected("   ", id);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_ClientSide_String_null() {
		CBORAttackDetected instance = new CBORAttackDetected(type, null);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_ClientSide_String_EmptyString() {
		CBORAttackDetected instance = new CBORAttackDetected(type, "");
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_ClientSide_String_Whitespaces() {
		CBORAttackDetected instance = new CBORAttackDetected(type, "   ");
	}
	
	
	@Test
	public void testSetSeverityScore() {
		CBORAttackDetected instance = new CBORAttackDetected(publisherId, ifMapTimestamp, type, id);
		instance.setSeverityScore(severityScore);
		
		assertEquals(severityScore, instance.getSeverityScore());
	}
	
	
	@Test
	public void testSetSeverityScore_null() {
		CBORAttackDetected instance = new CBORAttackDetected(publisherId, ifMapTimestamp, type, id);
		instance.setSeverityScore(severityScore);
		instance.setSeverityScore(null);
		
		assertNull(instance.getSeverityScore());
	}
	
	
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

		byte[] expResult = DatatypeConverter.parseHexBinary("84040888006F6D792D7075626C69736865722D696401C11A4ED9E8B202"
				+ "C482281A075BCA00030188F600806B61747461636B2D74797065F601806961747461636B2D6964");

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

		byte[] expResult = DatatypeConverter.parseHexBinary("84040888006F6D792D7075626C69736865722D696401C11A4ED9E8B202"
				+ "C482281A075BCA0003018CF600806B61747461636B2D74797065F601806961747461636B2D6964F60280FB40450000000000"
				+ "00");

		assertTrue("Byte array mismatch", Arrays.equals(expResult, bos.toByteArray()));
	}
	
	
	@Test
	public void testEquals_False() {
		CBORAttackDetected instance1 = new CBORAttackDetected(publisherId, ifMapTimestamp, type, id);
		CBORAttackDetected instance2 = new CBORAttackDetected(publisherId2, ifMapTimestamp2, type2, id2);

		assertFalse(instance1.equals(instance2));
	}


	@Test
	public void testEquals_True() {
		CBORAttackDetected instance1 = new CBORAttackDetected(publisherId, ifMapTimestamp, type, id);
		CBORAttackDetected instance2 = new CBORAttackDetected(publisherId, ifMapTimestamp, type, id);

		assertTrue(instance1.equals(instance2));
	}
	
	
	@Test
	public void testEquals_full_False() {
		CBORAttackDetected instance1 = new CBORAttackDetected(publisherId, ifMapTimestamp, type, id);
		instance1.setSeverityScore(severityScore);
		CBORAttackDetected instance2 = new CBORAttackDetected(publisherId2, ifMapTimestamp2, type2, id2);
		instance2.setSeverityScore(severityScore2);

		assertFalse(instance1.equals(instance2));
	}


	@Test
	public void testEquals_full_True() {
		CBORAttackDetected instance1 = new CBORAttackDetected(publisherId, ifMapTimestamp, type, id);
		instance1.setSeverityScore(severityScore);
		CBORAttackDetected instance2 = new CBORAttackDetected(publisherId, ifMapTimestamp, type, id);
		instance2.setSeverityScore(severityScore);

		assertTrue(instance1.equals(instance2));
	}


	@Test
	public void testEquals_OtherClass() {
		CBORAttackDetected instance1 = new CBORAttackDetected(publisherId, ifMapTimestamp, type, id);
		Integer instance2 = 42;

		assertFalse(instance1.equals(instance2));
	}
}
