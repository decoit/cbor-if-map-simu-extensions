/*
 * No license defined yet.
 */
package de.decoit.simu.cbor.ifmap.simu.metadata.singlevalue;

import co.nstant.in.cbor.CborBuilder;
import co.nstant.in.cbor.CborEncoder;
import co.nstant.in.cbor.builder.ArrayBuilder;
import de.decoit.simu.cbor.ifmap.enums.IfMapCardinality;
import de.decoit.simu.cbor.ifmap.simu.AbstractSimuTestBase;
import de.decoit.simu.cbor.ifmap.simu.enums.SIMUCredentialType;
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
public class CBORLoginSuccessTest extends AbstractSimuTestBase {
	private final String publisherId = "my-publisher-id";
	private final ZonedDateTime ifMapTimestamp = ZonedDateTime.parse("2011-12-03T10:15:30.123456+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	private final SIMUCredentialType credentialType = SIMUCredentialType.PASSWORD;
	private final String otherCredentialTypeDefinition = "custom-credential-type";
	private final String publisherId2 = "my-publisher-id-2";
	private final ZonedDateTime ifMapTimestamp2 = ZonedDateTime.parse("2012-12-03T10:15:30.123456+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	private final SIMUCredentialType credentialType2 = SIMUCredentialType.BIOMETRIC;
	private final String otherCredentialTypeDefinition2 = "custom-credential-type-2";


	@Test
	public void testConstructor() {
		CBORLoginSuccess instance = new CBORLoginSuccess(publisherId, ifMapTimestamp, credentialType);
		
		assertEquals(SimuNamespaces.SIMU, instance.getNamespace());
		assertEquals(IfMapCardinality.SINGLE_VALUE, instance.getIfMapCardinality());
		assertEquals(SIMUCredentialType.PASSWORD, instance.getCredentialType());
		assertNull(instance.getOtherCredentialTypeDefinition());
	}


	@Test
	public void testConstructor_UnnecessaryCredentialType() {
		CBORLoginSuccess instance = new CBORLoginSuccess(publisherId, ifMapTimestamp, credentialType, otherCredentialTypeDefinition);
		
		assertEquals(SimuNamespaces.SIMU, instance.getNamespace());
		assertEquals(IfMapCardinality.SINGLE_VALUE, instance.getIfMapCardinality());
		assertEquals(SIMUCredentialType.PASSWORD, instance.getCredentialType());
		assertNull(instance.getOtherCredentialTypeDefinition());
	}

	
	@Test
	public void testConstructor_OtherCredentialType_TypeDef() {
		CBORLoginSuccess instance = new CBORLoginSuccess(publisherId, ifMapTimestamp, SIMUCredentialType.OTHER, otherCredentialTypeDefinition);
		
		assertEquals(SimuNamespaces.SIMU, instance.getNamespace());
		assertEquals(IfMapCardinality.SINGLE_VALUE, instance.getIfMapCardinality());
		assertEquals(SIMUCredentialType.OTHER, instance.getCredentialType());
		assertEquals(otherCredentialTypeDefinition, instance.getOtherCredentialTypeDefinition());
	}
	

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_OtherCredentialType_NoTypeDef() {
		CBORLoginSuccess instance = new CBORLoginSuccess(publisherId, ifMapTimestamp, SIMUCredentialType.OTHER);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_OtherCredentialType_NullTypeDef() {
		CBORLoginSuccess instance = new CBORLoginSuccess(publisherId, ifMapTimestamp, SIMUCredentialType.OTHER, null);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_OtherCredentialType_EmptyTypeDef() {
		CBORLoginSuccess instance = new CBORLoginSuccess(publisherId, ifMapTimestamp, SIMUCredentialType.OTHER, "");
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_OtherCredentialType_WhitespacesTypeDef() {
		CBORLoginSuccess instance = new CBORLoginSuccess(publisherId, ifMapTimestamp, SIMUCredentialType.OTHER, "   ");
	}
	
	
	@Test
	public void testConstructor_ClientSide() {
		CBORLoginSuccess instance = new CBORLoginSuccess(credentialType);
		
		assertEquals(SimuNamespaces.SIMU, instance.getNamespace());
		assertEquals(IfMapCardinality.SINGLE_VALUE, instance.getIfMapCardinality());
		assertNull(instance.getIfMapPublisherId());
		assertNull(instance.getIfMapTimestamp());
		assertEquals(SIMUCredentialType.PASSWORD, instance.getCredentialType());
		assertNull(instance.getOtherCredentialTypeDefinition());
	}


	@Test
	public void testConstructor_ClientSide_UnnecessaryCredentialType() {
		CBORLoginSuccess instance = new CBORLoginSuccess(credentialType, otherCredentialTypeDefinition);
		
		assertEquals(SimuNamespaces.SIMU, instance.getNamespace());
		assertEquals(IfMapCardinality.SINGLE_VALUE, instance.getIfMapCardinality());
		assertNull(instance.getIfMapPublisherId());
		assertNull(instance.getIfMapTimestamp());
		assertEquals(SIMUCredentialType.PASSWORD, instance.getCredentialType());
		assertNull(instance.getOtherCredentialTypeDefinition());
	}

	
	@Test
	public void testConstructor_ClientSide_OtherCredentialType_TypeDef() {
		CBORLoginSuccess instance = new CBORLoginSuccess(SIMUCredentialType.OTHER, otherCredentialTypeDefinition);
		
		assertEquals(SimuNamespaces.SIMU, instance.getNamespace());
		assertEquals(IfMapCardinality.SINGLE_VALUE, instance.getIfMapCardinality());
		assertNull(instance.getIfMapPublisherId());
		assertNull(instance.getIfMapTimestamp());
		assertEquals(SIMUCredentialType.OTHER, instance.getCredentialType());
		assertEquals(otherCredentialTypeDefinition, instance.getOtherCredentialTypeDefinition());
	}
	

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_ClientSide_OtherCredentialType_NoTypeDef() {
		CBORLoginSuccess instance = new CBORLoginSuccess(SIMUCredentialType.OTHER);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_ClientSide_OtherCredentialType_NullTypeDef() {
		CBORLoginSuccess instance = new CBORLoginSuccess(SIMUCredentialType.OTHER, null);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_ClientSide_OtherCredentialType_EmptyTypeDef() {
		CBORLoginSuccess instance = new CBORLoginSuccess(SIMUCredentialType.OTHER, "");
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_ClientSide_OtherCredentialType_WhitespacesTypeDef() {
		CBORLoginSuccess instance = new CBORLoginSuccess(SIMUCredentialType.OTHER, "   ");
	}


	@Test
	public void testCborSerialize() throws Exception {
		CBORLoginSuccess instance = new CBORLoginSuccess(publisherId, ifMapTimestamp, credentialType);

		CborBuilder cb = new CborBuilder();
		ArrayBuilder ab = cb.addArray();

		instance.cborSerialize(ab);

		ab.end();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		CborEncoder ce = new CborEncoder(bos);
		ce.encode(cb.build());

		log.info("CBOR serialize:");
		log.info(DatatypeConverter.printHexBinary(bos.toByteArray()));

		byte[] expResult = DatatypeConverter.parseHexBinary("84040B88006F6D792D7075626C69736865722D696401C11A4ED9E8B202"
				+ "C482281A075BCA00030084F6008000");

		assertTrue("Byte array mismatch", Arrays.equals(expResult, bos.toByteArray()));
	}


	@Test
	public void testCborSerialize_full() throws Exception {
		CBORLoginSuccess instance = new CBORLoginSuccess(publisherId, ifMapTimestamp, SIMUCredentialType.OTHER, otherCredentialTypeDefinition);

		CborBuilder cb = new CborBuilder();
		ArrayBuilder ab = cb.addArray();

		instance.cborSerialize(ab);

		ab.end();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		CborEncoder ce = new CborEncoder(bos);
		ce.encode(cb.build());

		log.info("CBOR serialize (full):");
		log.info(DatatypeConverter.printHexBinary(bos.toByteArray()));

		byte[] expResult = DatatypeConverter.parseHexBinary("84040B88006F6D792D7075626C69736865722D696401C11A4ED9E8B202"
				+ "C482281A075BCA00030088F6008004F6018076637573746F6D2D63726564656E7469616C2D74797065");

		assertTrue("Byte array mismatch", Arrays.equals(expResult, bos.toByteArray()));
	}


	@Test
	public void testEquals_False() {
		CBORLoginSuccess instance1 = new CBORLoginSuccess(publisherId, ifMapTimestamp, credentialType);
		CBORLoginSuccess instance2 = new CBORLoginSuccess(publisherId2, ifMapTimestamp2, credentialType2);

		assertFalse(instance1.equals(instance2));
	}


	@Test
	public void testEquals_True() {
		CBORLoginSuccess instance1 = new CBORLoginSuccess(publisherId, ifMapTimestamp, credentialType);
		CBORLoginSuccess instance2 = new CBORLoginSuccess(publisherId, ifMapTimestamp, credentialType);

		assertTrue(instance1.equals(instance2));
	}


	@Test
	public void testEquals_False_full() {
		CBORLoginSuccess instance1 = new CBORLoginSuccess(publisherId, ifMapTimestamp, SIMUCredentialType.OTHER, otherCredentialTypeDefinition);
		CBORLoginSuccess instance2 = new CBORLoginSuccess(publisherId2, ifMapTimestamp2, SIMUCredentialType.OTHER, otherCredentialTypeDefinition2);

		assertFalse(instance1.equals(instance2));
	}


	@Test
	public void testEquals_True_full() {
		CBORLoginSuccess instance1 = new CBORLoginSuccess(publisherId, ifMapTimestamp, SIMUCredentialType.OTHER, otherCredentialTypeDefinition);
		CBORLoginSuccess instance2 = new CBORLoginSuccess(publisherId, ifMapTimestamp, SIMUCredentialType.OTHER, otherCredentialTypeDefinition);

		assertTrue(instance1.equals(instance2));
	}


	@Test
	public void testEquals_OtherClass() {
		CBORLoginSuccess instance1 = new CBORLoginSuccess(publisherId, ifMapTimestamp, credentialType);
		Integer instance2 = 42;

		assertFalse(instance1.equals(instance2));
	}
}
