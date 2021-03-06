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
package de.decoit.simu.cbor.ifmap.simu.metadata.singlevalue;

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
public class CBORServiceDiscoveredByTest extends AbstractSimuTestBase {
	private final String publisherId = "my-publisher-id";
	private final ZonedDateTime ifMapTimestamp = ZonedDateTime.parse("2011-12-03T10:15:30.123456+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	private final String publisherId2 = "my-publisher-id-2";
	private final ZonedDateTime ifMapTimestamp2 = ZonedDateTime.parse("2012-12-03T10:15:30.123456+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);

	
	@Test
	public void testConstructor() {
		CBORServiceDiscoveredBy instance = new CBORServiceDiscoveredBy(publisherId, ifMapTimestamp);
		
		assertEquals(SimuNamespaces.SIMU, instance.getNamespace());
		assertEquals(IfMapCardinality.SINGLE_VALUE, instance.getIfMapCardinality());
	}
	
	
	@Test
	public void testConstructor_ClientSide() {
		CBORServiceDiscoveredBy instance = new CBORServiceDiscoveredBy();
		
		assertNull(instance.getIfMapPublisherId());
		assertNull(instance.getIfMapTimestamp());
		assertEquals(SimuNamespaces.SIMU, instance.getNamespace());
		assertEquals(IfMapCardinality.SINGLE_VALUE, instance.getIfMapCardinality());
	}
	

	@Test
	public void testCborSerialize() throws Exception {
		CBORServiceDiscoveredBy instance = new CBORServiceDiscoveredBy(publisherId, ifMapTimestamp);

		CborBuilder cb = new CborBuilder();
		ArrayBuilder ab = cb.addArray();

		instance.cborSerialize(ab);

		ab.end();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		CborEncoder ce = new CborEncoder(bos);
		ce.encode(cb.build());

		log.info("CBOR serialize:");
		log.info(DatatypeConverter.printHexBinary(bos.toByteArray()));

		byte[] expResult = DatatypeConverter.parseHexBinary("84040C88006F6D792D7075626C69736865722D696401C11A4ED9E8B202"
				+ "C482281A075BCA00030080");

		assertTrue("Byte array mismatch", Arrays.equals(expResult, bos.toByteArray()));
	}


	@Test
	public void testEquals_False() {
		CBORServiceDiscoveredBy instance1 = new CBORServiceDiscoveredBy(publisherId, ifMapTimestamp);
		CBORServiceDiscoveredBy instance2 = new CBORServiceDiscoveredBy(publisherId2, ifMapTimestamp2);

		assertFalse(instance1.equals(instance2));
	}


	@Test
	public void testEquals_True() {
		CBORServiceDiscoveredBy instance1 = new CBORServiceDiscoveredBy(publisherId, ifMapTimestamp);
		CBORServiceDiscoveredBy instance2 = new CBORServiceDiscoveredBy(publisherId, ifMapTimestamp);

		assertTrue(instance1.equals(instance2));
	}


	@Test
	public void testEquals_OtherClass() {
		CBORServiceDiscoveredBy instance1 = new CBORServiceDiscoveredBy(publisherId, ifMapTimestamp);
		Integer instance2 = 42;

		assertFalse(instance1.equals(instance2));
	}
}
