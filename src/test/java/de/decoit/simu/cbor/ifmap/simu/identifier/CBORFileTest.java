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
import de.decoit.simu.cbor.ifmap.simu.util.SimuNamespaces;
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
public class CBORFileTest extends AbstractSimuTestBase {
	private final String administrativeDomain = "simu-adm";
	private final String path = "/etc/fstab";
	private final String path2 = "/etc/issue";


	@Test
	public void testConstructor() {
		CBORFile instance = new CBORFile(administrativeDomain, path);
		
		assertEquals(SimuNamespaces.SIMU, instance.getNamespace());
		assertEquals("file", instance.getElementName());
		assertEquals(path, instance.getPath());
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_null_String() {
		CBORFile instance = new CBORFile(null, path);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_EmptyString_String() {
		CBORFile instance = new CBORFile("", path);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_Whitespaces_String() {
		CBORFile instance = new CBORFile("   ", path);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_String_null() {
		CBORFile instance = new CBORFile(administrativeDomain, null);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_String_EmptyString() {
		CBORFile instance = new CBORFile(administrativeDomain, "");
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_String_Whitespaces() {
		CBORFile instance = new CBORFile(administrativeDomain, "   ");
	}

	
	@Test
	public void testCborSerialize() throws Exception {
		CBORFile instance = new CBORFile(administrativeDomain, path);

		CborBuilder cb = new CborBuilder();
		ArrayBuilder ab = cb.addArray();

		instance.cborSerialize(ab);

		ab.end();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		CborEncoder ce = new CborEncoder(bos);
		ce.encode(cb.build());

		log.info("CBOR serialize:");
		log.info(DatatypeConverter.printHexBinary(bos.toByteArray()));

		byte[] expResult = DatatypeConverter.parseHexBinary("8404D9A4100E84006873696D752D61646D016A2F6574632F6673746162"
				+ "80");

		assertTrue("Byte array mismatch", Arrays.equals(expResult, bos.toByteArray()));
	}

	
	@Test
	public void testEquals_False() throws Exception {
		CBORFile instance1 = new CBORFile(administrativeDomain, path);
		CBORFile instance2 = new CBORFile(administrativeDomain, path2);

		assertFalse(instance1.equals(instance2));
	}


	@Test
	public void testEquals_True() throws Exception {
		CBORFile instance1 = new CBORFile(administrativeDomain, path);
		CBORFile instance2 = new CBORFile(administrativeDomain, path);

		assertTrue(instance1.equals(instance2));
	}


	@Test
	public void testEquals_OtherClass() {
		CBORFile instance1 = new CBORFile(administrativeDomain, path);
		Integer instance2 = 42;

		assertFalse(instance1.equals(instance2));
	}
}
