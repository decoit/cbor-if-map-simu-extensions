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
package de.decoit.simu.cbor.ifmap.simu.identifier;

import co.nstant.in.cbor.CborBuilder;
import co.nstant.in.cbor.CborEncoder;
import co.nstant.in.cbor.builder.ArrayBuilder;
import de.decoit.simu.cbor.ifmap.simu.AbstractSimuTestBase;
import java.io.ByteArrayOutputStream;
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
public class CBORServiceTest extends AbstractSimuTestBase {
	private final String administrativeDomain = "simu-adm";
	private final String name = "service-name";
	private final String type = "service-type";
	private final Integer port = 42;
	private final String name2 = "service-name-2";
	private final String type2 = "service-type-2";
	private final Integer port2 = 21;


	@Test
	public void testConstructor() {
		CBORService instance = new CBORService(administrativeDomain, type, name, port);
		
		assertEquals(type, instance.getType());
		assertEquals(name, instance.getName());
		assertEquals(port, instance.getPort());
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_null_String_String_Integer() {
		CBORService instance = new CBORService(null, type, name, port);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_EmptyString_String_String_Integer() {
		CBORService instance = new CBORService("", type, name, port);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_Whitespaces_String_String_Integer() {
		CBORService instance = new CBORService("   ", type, name, port);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_String_null_String_Integer() {
		CBORService instance = new CBORService(administrativeDomain, null, name, port);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_String_EmptyString_String_Integer() {
		CBORService instance = new CBORService(administrativeDomain, "", name, port);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_String_Whitespaces_String_Integer() {
		CBORService instance = new CBORService(administrativeDomain, "   ", name, port);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_String_String_null_Integer() {
		CBORService instance = new CBORService(administrativeDomain, type, null, port);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_String_String_EmptyString_Integer() {
		CBORService instance = new CBORService(administrativeDomain, type, "", port);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_String_String_Whitespaces_Integer() {
		CBORService instance = new CBORService(administrativeDomain, type, "   ", port);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_String_String_String_null() {
		CBORService instance = new CBORService(administrativeDomain, type, name, null);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_String_String_String_NegativeInteger() {
		CBORService instance = new CBORService(administrativeDomain, type, name, -42);
	}


	@Test
	public void testCborSerialize() throws Exception {
		CBORService instance = new CBORService(administrativeDomain, type, name, port);

		CborBuilder cb = new CborBuilder();
		ArrayBuilder ab = cb.addArray();

		instance.cborSerialize(ab);

		ab.end();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		CborEncoder ce = new CborEncoder(bos);
		ce.encode(cb.build());

		log.info("CBOR serialize:");
		log.info(DatatypeConverter.printHexBinary(bos.toByteArray()));

		byte[] expResult = DatatypeConverter.parseHexBinary("8404D9A4100088006873696D752D61646D03182A026C73657276696365"
				+ "2D6E616D65016C736572766963652D7479706580");

		assertTrue("Byte array mismatch", Arrays.equals(expResult, bos.toByteArray()));
	}
	
	
	@Test
	public void testEquals_False() throws Exception {
		CBORService instance1 = new CBORService(administrativeDomain, type, name, port);
		CBORService instance2 = new CBORService(administrativeDomain, type2, name2, port2);

		assertFalse(instance1.equals(instance2));
	}


	@Test
	public void testEquals_True() throws Exception {
		CBORService instance1 = new CBORService(administrativeDomain, type, name, port);
		CBORService instance2 = new CBORService(administrativeDomain, type, name, port);

		assertTrue(instance1.equals(instance2));
	}


	@Test
	public void testEquals_OtherClass() {
		CBORService instance1 = new CBORService(administrativeDomain, type, name, port);
		Integer instance2 = 42;

		assertFalse(instance1.equals(instance2));
	}
}
