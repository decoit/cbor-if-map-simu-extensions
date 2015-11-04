/*
 * No license defined yet.
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
public class CBORImplementationTest extends AbstractSimuTestBase {
	private final String administrativeDomain = "simu-adm";
	private final String name = "impl-name";
	private final String version = "impl-version";
	private final String localVersion = "impl-local-version";
	private final String platform = "impl-platform";
	private final String name2 = "impl-name-2";
	private final String version2 = "impl-version-2";
	private final String localVersion2 = "impl-local-version-2";
	private final String platform2 = "impl-platform-2";


	@Test
	public void testConstructor() {
		CBORImplementation instance = new CBORImplementation(administrativeDomain, name, version);
		
		assertEquals(SimuNamespaces.SIMU, instance.getNamespace());
		assertEquals("implementation", instance.getElementName());
		assertEquals(name, instance.getName());
		assertEquals(version, instance.getVersion());
		assertNull(instance.getLocalVersion());
		assertNull(instance.getPlatform());
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_null_String_String() {
		CBORImplementation instance = new CBORImplementation(null, name, version);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_EmptyString_String_String() {
		CBORImplementation instance = new CBORImplementation("", name, version);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_Whitespaces_String_String() {
		CBORImplementation instance = new CBORImplementation("   ", name, version);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_String_null_String() {
		CBORImplementation instance = new CBORImplementation(administrativeDomain, null, version);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_String_EmptyString_String() {
		CBORImplementation instance = new CBORImplementation(administrativeDomain, "", version);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_String_Whitespaces_String() {
		CBORImplementation instance = new CBORImplementation(administrativeDomain, "   ", version);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_String_String_null() {
		CBORImplementation instance = new CBORImplementation(administrativeDomain, name, null);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_String_String_EmptyString() {
		CBORImplementation instance = new CBORImplementation(administrativeDomain, name, "");
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_String_String_Whitespaces() {
		CBORImplementation instance = new CBORImplementation(administrativeDomain, name, "   ");
	}


	@Test
	public void testSetLocalVersion() {
		CBORImplementation instance = new CBORImplementation(administrativeDomain, name, version);
		instance.setLocalVersion(localVersion);
		
		assertEquals(localVersion, instance.getLocalVersion());
	}


	@Test
	public void testSetLocalVersion_null() {
		CBORImplementation instance = new CBORImplementation(administrativeDomain, name, version);
		instance.setLocalVersion(localVersion);
		instance.setLocalVersion(null);
		
		assertNull(instance.getLocalVersion());
	}


	@Test(expected = IllegalArgumentException.class)
	public void testSetLocalVersion_EmptyString() {
		CBORImplementation instance = new CBORImplementation(administrativeDomain, name, version);
		instance.setLocalVersion("");
	}


	@Test(expected = IllegalArgumentException.class)
	public void testSetLocalVersion_Whitespaces() {
		CBORImplementation instance = new CBORImplementation(administrativeDomain, name, version);
		instance.setLocalVersion("   ");
	}


	@Test
	public void testSetPlatform() {
		CBORImplementation instance = new CBORImplementation(administrativeDomain, name, version);
		instance.setPlatform(platform);
		
		assertEquals(platform, instance.getPlatform());
	}


	@Test
	public void testSetPlatform_null() {
		CBORImplementation instance = new CBORImplementation(administrativeDomain, name, version);
		instance.setPlatform(platform);
		instance.setPlatform(null);
		
		assertNull(instance.getPlatform());
	}


	@Test(expected = IllegalArgumentException.class)
	public void testSetPlatform_EmptyString() {
		CBORImplementation instance = new CBORImplementation(administrativeDomain, name, version);
		instance.setPlatform("");
	}


	@Test(expected = IllegalArgumentException.class)
	public void testSetPlatform_Whitespaces() {
		CBORImplementation instance = new CBORImplementation(administrativeDomain, name, version);
		instance.setPlatform("   ");
	}
	
	
	@Test
	public void testCborSerialize() throws Exception {
		CBORImplementation instance = new CBORImplementation(administrativeDomain, name, version);

		CborBuilder cb = new CborBuilder();
		ArrayBuilder ab = cb.addArray();

		instance.cborSerialize(ab);

		ab.end();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		CborEncoder ce = new CborEncoder(bos);
		ce.encode(cb.build());

		log.info("CBOR serialize:");
		log.info(DatatypeConverter.printHexBinary(bos.toByteArray()));

		byte[] expResult = DatatypeConverter.parseHexBinary("8404D9A4100186006873696D752D61646D0169696D706C2D6E616D6502"
				+ "6C696D706C2D76657273696F6E80");

		assertTrue("Byte array mismatch", Arrays.equals(expResult, bos.toByteArray()));
	}
	
	
	@Test
	public void testCborSerialize_full() throws Exception {
		CBORImplementation instance = new CBORImplementation(administrativeDomain, name, version);
		instance.setLocalVersion(localVersion);
		instance.setPlatform(platform);

		CborBuilder cb = new CborBuilder();
		ArrayBuilder ab = cb.addArray();

		instance.cborSerialize(ab);

		ab.end();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		CborEncoder ce = new CborEncoder(bos);
		ce.encode(cb.build());

		log.info("CBOR serialize (full):");
		log.info(DatatypeConverter.printHexBinary(bos.toByteArray()));

		byte[] expResult = DatatypeConverter.parseHexBinary("8404D9A410018A006873696D752D61646D0169696D706C2D6E616D6502"
				+ "6C696D706C2D76657273696F6E0372696D706C2D6C6F63616C2D76657273696F6E046D696D706C2D706C6174666F726D80");

		assertTrue("Byte array mismatch", Arrays.equals(expResult, bos.toByteArray()));
	}

	
	@Test
	public void testEquals_False() throws Exception {
		CBORImplementation instance1 = new CBORImplementation(administrativeDomain, name, version);
		CBORImplementation instance2 = new CBORImplementation(administrativeDomain, name2, version2);

		assertFalse(instance1.equals(instance2));
	}


	@Test
	public void testEquals_True() throws Exception {
		CBORImplementation instance1 = new CBORImplementation(administrativeDomain, name, version);
		CBORImplementation instance2 = new CBORImplementation(administrativeDomain, name, version);

		assertTrue(instance1.equals(instance2));
	}


	@Test
	public void testEquals_False_full() throws Exception {
		CBORImplementation instance1 = new CBORImplementation(administrativeDomain, name, version);
		instance1.setLocalVersion(localVersion);
		instance1.setPlatform(platform);

		CBORImplementation instance2 = new CBORImplementation(administrativeDomain, name2, version2);
		instance2.setLocalVersion(localVersion2);
		instance2.setPlatform(platform2);

		assertFalse(instance1.equals(instance2));
	}


	@Test
	public void testEquals_True_full() throws Exception {
		CBORImplementation instance1 = new CBORImplementation(administrativeDomain, name, version);
		instance1.setLocalVersion(localVersion);
		instance1.setPlatform(platform);

		CBORImplementation instance2 = new CBORImplementation(administrativeDomain, name, version);
		instance2.setLocalVersion(localVersion);
		instance2.setPlatform(platform);

		assertTrue(instance1.equals(instance2));
	}


	@Test
	public void testEquals_OtherClass() {
		CBORImplementation instance1 = new CBORImplementation(administrativeDomain, name, version);
		Integer instance2 = 42;

		assertFalse(instance1.equals(instance2));
	}
}
