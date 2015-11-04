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
import de.decoit.simu.cbor.ifmap.simu.enums.SIMUCredentialType;
import de.decoit.simu.cbor.ifmap.simu.enums.SIMUReasonType;
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
public class CBORLoginFailureTest extends AbstractSimuTestBase {
	private final String publisherId = "my-publisher-id";
	private final ZonedDateTime ifMapTimestamp = ZonedDateTime.parse("2011-12-03T10:15:30.123456+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	private final SIMUCredentialType credentialType = SIMUCredentialType.PASSWORD;
	private final SIMUReasonType reason = SIMUReasonType.INVALID_CREDENTIALS;
	private final String otherCredentialTypeDefinition = "custom-credential-type";
	private final String otherReasonTypeDefinition = "custom-reason-type";
	private final String publisherId2 = "my-publisher-id-2";
	private final ZonedDateTime ifMapTimestamp2 = ZonedDateTime.parse("2012-12-03T10:15:30.123456+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	private final SIMUCredentialType credentialType2 = SIMUCredentialType.BIOMETRIC;
	private final SIMUReasonType reason2 = SIMUReasonType.COMMUNICATION_FAILURE;
	private final String otherCredentialTypeDefinition2 = "custom-credential-type-2";
	private final String otherReasonTypeDefinition2 = "custom-reason-type-2";


	@Test
	public void testConstructor() {
		CBORLoginFailure instance = new CBORLoginFailure(publisherId, ifMapTimestamp, credentialType, reason);
		
		assertEquals(SimuNamespaces.SIMU, instance.getNamespace());
		assertEquals(IfMapCardinality.SINGLE_VALUE, instance.getIfMapCardinality());
		assertEquals(SIMUCredentialType.PASSWORD, instance.getCredentialType());
		assertEquals(SIMUReasonType.INVALID_CREDENTIALS, instance.getReason());
		assertNull(instance.getOtherCredentialTypeDefinition());
		assertNull(instance.getOtherReasonTypeDefinition());
	}


	@Test
	public void testConstructor_UnnecessaryCredentialType() {
		CBORLoginFailure instance = new CBORLoginFailure(publisherId, ifMapTimestamp, credentialType, otherCredentialTypeDefinition, reason);
		
		assertEquals(SimuNamespaces.SIMU, instance.getNamespace());
		assertEquals(IfMapCardinality.SINGLE_VALUE, instance.getIfMapCardinality());
		assertEquals(SIMUCredentialType.PASSWORD, instance.getCredentialType());
		assertEquals(SIMUReasonType.INVALID_CREDENTIALS, instance.getReason());
		assertNull(instance.getOtherCredentialTypeDefinition());
		assertNull(instance.getOtherReasonTypeDefinition());
	}


	@Test
	public void testConstructor_OtherCredentialType_TypeDef() {
		CBORLoginFailure instance = new CBORLoginFailure(publisherId, ifMapTimestamp, SIMUCredentialType.OTHER, otherCredentialTypeDefinition, reason);
		
		assertEquals(SimuNamespaces.SIMU, instance.getNamespace());
		assertEquals(IfMapCardinality.SINGLE_VALUE, instance.getIfMapCardinality());
		assertEquals(SIMUCredentialType.OTHER, instance.getCredentialType());
		assertEquals(SIMUReasonType.INVALID_CREDENTIALS, instance.getReason());
		assertEquals(otherCredentialTypeDefinition, instance.getOtherCredentialTypeDefinition());
		assertNull(instance.getOtherReasonTypeDefinition());
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_OtherCredentialType_NoTypeDef() {
		CBORLoginFailure instance = new CBORLoginFailure(publisherId, ifMapTimestamp, SIMUCredentialType.OTHER, reason);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_OtherCredentialType_NullTypeDef() {
		CBORLoginFailure instance = new CBORLoginFailure(publisherId, ifMapTimestamp, SIMUCredentialType.OTHER, null, reason);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_OtherCredentialType_EmptyTypeDef() {
		CBORLoginFailure instance = new CBORLoginFailure(publisherId, ifMapTimestamp, SIMUCredentialType.OTHER, "", reason);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_OtherCredentialType_WhitespacesTypeDef() {
		CBORLoginFailure instance = new CBORLoginFailure(publisherId, ifMapTimestamp, SIMUCredentialType.OTHER, "   ", reason);
	}
	
	
	@Test
	public void testConstructor_UnnecessaryReasonType() {
		CBORLoginFailure instance = new CBORLoginFailure(publisherId, ifMapTimestamp, credentialType, reason, otherReasonTypeDefinition);
		
		assertEquals(SimuNamespaces.SIMU, instance.getNamespace());
		assertEquals(IfMapCardinality.SINGLE_VALUE, instance.getIfMapCardinality());
		assertEquals(SIMUCredentialType.PASSWORD, instance.getCredentialType());
		assertEquals(SIMUReasonType.INVALID_CREDENTIALS, instance.getReason());
		assertNull(instance.getOtherCredentialTypeDefinition());
		assertNull(instance.getOtherReasonTypeDefinition());
	}


	@Test
	public void testConstructor_OtherReasonType_TypeDef() {
		CBORLoginFailure instance = new CBORLoginFailure(publisherId, ifMapTimestamp, credentialType, SIMUReasonType.OTHER, otherReasonTypeDefinition);
		
		assertEquals(SimuNamespaces.SIMU, instance.getNamespace());
		assertEquals(IfMapCardinality.SINGLE_VALUE, instance.getIfMapCardinality());
		assertEquals(SIMUCredentialType.PASSWORD, instance.getCredentialType());
		assertEquals(SIMUReasonType.OTHER, instance.getReason());
		assertNull(instance.getOtherCredentialTypeDefinition());
		assertEquals(otherReasonTypeDefinition, instance.getOtherReasonTypeDefinition());
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_OtherReasonType_NoTypeDef() {
		CBORLoginFailure instance = new CBORLoginFailure(publisherId, ifMapTimestamp, credentialType, SIMUReasonType.OTHER);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_OtherReasonType_NullTypeDef() {
		CBORLoginFailure instance = new CBORLoginFailure(publisherId, ifMapTimestamp, credentialType, SIMUReasonType.OTHER, null);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_OtherReasonType_EmptyTypeDef() {
		CBORLoginFailure instance = new CBORLoginFailure(publisherId, ifMapTimestamp, credentialType, SIMUReasonType.OTHER, "");
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_OtherReasonType_WhitespacesTypeDef() {
		CBORLoginFailure instance = new CBORLoginFailure(publisherId, ifMapTimestamp, credentialType, SIMUReasonType.OTHER, "   ");
	}


	@Test
	public void testConstructor_OtherCredentialType_TypeDef_OtherReasonType_TypeDef() {
		CBORLoginFailure instance = new CBORLoginFailure(publisherId, ifMapTimestamp, SIMUCredentialType.OTHER, otherCredentialTypeDefinition, SIMUReasonType.OTHER, otherReasonTypeDefinition);
		
		assertEquals(SimuNamespaces.SIMU, instance.getNamespace());
		assertEquals(IfMapCardinality.SINGLE_VALUE, instance.getIfMapCardinality());
		assertEquals(SIMUCredentialType.OTHER, instance.getCredentialType());
		assertEquals(SIMUReasonType.OTHER, instance.getReason());
		assertEquals(otherCredentialTypeDefinition, instance.getOtherCredentialTypeDefinition());
		assertEquals(otherReasonTypeDefinition, instance.getOtherReasonTypeDefinition());
	}
	
	
	@Test
	public void testConstructor_ClientSide() {
		CBORLoginFailure instance = new CBORLoginFailure(credentialType, reason);
		
		assertEquals(SimuNamespaces.SIMU, instance.getNamespace());
		assertEquals(IfMapCardinality.SINGLE_VALUE, instance.getIfMapCardinality());
		assertNull(instance.getIfMapPublisherId());
		assertNull(instance.getIfMapTimestamp());
		assertEquals(SIMUCredentialType.PASSWORD, instance.getCredentialType());
		assertEquals(SIMUReasonType.INVALID_CREDENTIALS, instance.getReason());
		assertNull(instance.getOtherCredentialTypeDefinition());
		assertNull(instance.getOtherReasonTypeDefinition());
	}


	@Test
	public void testConstructor_ClientSide_UnnecessaryCredentialType() {
		CBORLoginFailure instance = new CBORLoginFailure(credentialType, otherCredentialTypeDefinition, reason);
		
		assertEquals(SimuNamespaces.SIMU, instance.getNamespace());
		assertEquals(IfMapCardinality.SINGLE_VALUE, instance.getIfMapCardinality());
		assertNull(instance.getIfMapPublisherId());
		assertNull(instance.getIfMapTimestamp());
		assertEquals(SIMUCredentialType.PASSWORD, instance.getCredentialType());
		assertEquals(SIMUReasonType.INVALID_CREDENTIALS, instance.getReason());
		assertNull(instance.getOtherCredentialTypeDefinition());
		assertNull(instance.getOtherReasonTypeDefinition());
	}


	@Test
	public void testConstructor_ClientSide_OtherCredentialType_TypeDef() {
		CBORLoginFailure instance = new CBORLoginFailure(SIMUCredentialType.OTHER, otherCredentialTypeDefinition, reason);
		
		assertEquals(SimuNamespaces.SIMU, instance.getNamespace());
		assertEquals(IfMapCardinality.SINGLE_VALUE, instance.getIfMapCardinality());
		assertNull(instance.getIfMapPublisherId());
		assertNull(instance.getIfMapTimestamp());
		assertEquals(SIMUCredentialType.OTHER, instance.getCredentialType());
		assertEquals(SIMUReasonType.INVALID_CREDENTIALS, instance.getReason());
		assertEquals(otherCredentialTypeDefinition, instance.getOtherCredentialTypeDefinition());
		assertNull(instance.getOtherReasonTypeDefinition());
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_ClientSide_OtherCredentialType_NoTypeDef() {
		CBORLoginFailure instance = new CBORLoginFailure(SIMUCredentialType.OTHER, reason);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_ClientSide_OtherCredentialType_NullTypeDef() {
		CBORLoginFailure instance = new CBORLoginFailure(SIMUCredentialType.OTHER, null, reason);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_ClientSide_OtherCredentialType_EmptyTypeDef() {
		CBORLoginFailure instance = new CBORLoginFailure(SIMUCredentialType.OTHER, "", reason);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_ClientSide_OtherCredentialType_WhitespacesTypeDef() {
		CBORLoginFailure instance = new CBORLoginFailure(SIMUCredentialType.OTHER, "   ", reason);
	}
	
	
	@Test
	public void testConstructor_ClientSide_UnnecessaryReasonType() {
		CBORLoginFailure instance = new CBORLoginFailure(credentialType, reason, otherReasonTypeDefinition);
		
		assertEquals(SimuNamespaces.SIMU, instance.getNamespace());
		assertEquals(IfMapCardinality.SINGLE_VALUE, instance.getIfMapCardinality());
		assertNull(instance.getIfMapPublisherId());
		assertNull(instance.getIfMapTimestamp());
		assertEquals(SIMUCredentialType.PASSWORD, instance.getCredentialType());
		assertEquals(SIMUReasonType.INVALID_CREDENTIALS, instance.getReason());
		assertNull(instance.getOtherCredentialTypeDefinition());
		assertNull(instance.getOtherReasonTypeDefinition());
	}


	@Test
	public void testConstructor_ClientSide_OtherReasonType_TypeDef() {
		CBORLoginFailure instance = new CBORLoginFailure(credentialType, SIMUReasonType.OTHER, otherReasonTypeDefinition);
		
		assertEquals(SimuNamespaces.SIMU, instance.getNamespace());
		assertEquals(IfMapCardinality.SINGLE_VALUE, instance.getIfMapCardinality());
		assertNull(instance.getIfMapPublisherId());
		assertNull(instance.getIfMapTimestamp());
		assertEquals(SIMUCredentialType.PASSWORD, instance.getCredentialType());
		assertEquals(SIMUReasonType.OTHER, instance.getReason());
		assertNull(instance.getOtherCredentialTypeDefinition());
		assertEquals(otherReasonTypeDefinition, instance.getOtherReasonTypeDefinition());
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_ClientSide_OtherReasonType_NoTypeDef() {
		CBORLoginFailure instance = new CBORLoginFailure(credentialType, SIMUReasonType.OTHER);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_ClientSide_OtherReasonType_NullTypeDef() {
		CBORLoginFailure instance = new CBORLoginFailure(credentialType, SIMUReasonType.OTHER, null);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_ClientSide_OtherReasonType_EmptyTypeDef() {
		CBORLoginFailure instance = new CBORLoginFailure(credentialType, SIMUReasonType.OTHER, "");
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_ClientSide_OtherReasonType_WhitespacesTypeDef() {
		CBORLoginFailure instance = new CBORLoginFailure(credentialType, SIMUReasonType.OTHER, "   ");
	}


	@Test
	public void testConstructor_ClientSide_OtherCredentialType_TypeDef_OtherReasonType_TypeDef() {
		CBORLoginFailure instance = new CBORLoginFailure(SIMUCredentialType.OTHER, otherCredentialTypeDefinition, SIMUReasonType.OTHER, otherReasonTypeDefinition);
		
		assertEquals(SimuNamespaces.SIMU, instance.getNamespace());
		assertEquals(IfMapCardinality.SINGLE_VALUE, instance.getIfMapCardinality());
		assertNull(instance.getIfMapPublisherId());
		assertNull(instance.getIfMapTimestamp());
		assertEquals(SIMUCredentialType.OTHER, instance.getCredentialType());
		assertEquals(SIMUReasonType.OTHER, instance.getReason());
		assertEquals(otherCredentialTypeDefinition, instance.getOtherCredentialTypeDefinition());
		assertEquals(otherReasonTypeDefinition, instance.getOtherReasonTypeDefinition());
	}


	@Test
	public void testCborSerialize() throws Exception {
		CBORLoginFailure instance = new CBORLoginFailure(publisherId, ifMapTimestamp, credentialType, reason);

		CborBuilder cb = new CborBuilder();
		ArrayBuilder ab = cb.addArray();

		instance.cborSerialize(ab);

		ab.end();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		CborEncoder ce = new CborEncoder(bos);
		ce.encode(cb.build());

		log.info("CBOR serialize:");
		log.info(DatatypeConverter.printHexBinary(bos.toByteArray()));

		byte[] expResult = DatatypeConverter.parseHexBinary("84040A88006F6D792D7075626C69736865722D696401C11A4ED9E8B202"
				+ "C482281A075BCA00030088F6008000F6018001");

		assertTrue("Byte array mismatch", Arrays.equals(expResult, bos.toByteArray()));
	}


	@Test
	public void testCborSerialize_full() throws Exception {
		CBORLoginFailure instance = new CBORLoginFailure(publisherId, ifMapTimestamp, SIMUCredentialType.OTHER, otherCredentialTypeDefinition, SIMUReasonType.OTHER, otherReasonTypeDefinition);

		CborBuilder cb = new CborBuilder();
		ArrayBuilder ab = cb.addArray();

		instance.cborSerialize(ab);

		ab.end();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		CborEncoder ce = new CborEncoder(bos);
		ce.encode(cb.build());

		log.info("CBOR serialize (full):");
		log.info(DatatypeConverter.printHexBinary(bos.toByteArray()));

		byte[] expResult = DatatypeConverter.parseHexBinary("84040A88006F6D792D7075626C69736865722D696401C11A4ED9E8B202"
				+ "C482281A075BCA00030090F6008004F6028076637573746F6D2D63726564656E7469616C2D74797065F6018005F603807263"
				+ "7573746F6D2D726561736F6E2D74797065");

		assertTrue("Byte array mismatch", Arrays.equals(expResult, bos.toByteArray()));
	}


	@Test
	public void testEquals_False() {
		CBORLoginFailure instance1 = new CBORLoginFailure(publisherId, ifMapTimestamp, credentialType, reason);
		CBORLoginFailure instance2 = new CBORLoginFailure(publisherId2, ifMapTimestamp2, credentialType2, reason2);

		assertFalse(instance1.equals(instance2));
	}


	@Test
	public void testEquals_True() {
		CBORLoginFailure instance1 = new CBORLoginFailure(publisherId, ifMapTimestamp, credentialType, reason);
		CBORLoginFailure instance2 = new CBORLoginFailure(publisherId, ifMapTimestamp, credentialType, reason);

		assertTrue(instance1.equals(instance2));
	}


	@Test
	public void testEquals_False_full() {
		CBORLoginFailure instance1 = new CBORLoginFailure(publisherId, ifMapTimestamp, SIMUCredentialType.OTHER, otherCredentialTypeDefinition, SIMUReasonType.OTHER, otherReasonTypeDefinition);
		CBORLoginFailure instance2 = new CBORLoginFailure(publisherId2, ifMapTimestamp2, SIMUCredentialType.OTHER, otherCredentialTypeDefinition2, SIMUReasonType.OTHER, otherReasonTypeDefinition2);

		assertFalse(instance1.equals(instance2));
	}


	@Test
	public void testEquals_True_full() {
		CBORLoginFailure instance1 = new CBORLoginFailure(publisherId, ifMapTimestamp, SIMUCredentialType.OTHER, otherCredentialTypeDefinition, SIMUReasonType.OTHER, otherReasonTypeDefinition);
		CBORLoginFailure instance2 = new CBORLoginFailure(publisherId, ifMapTimestamp, SIMUCredentialType.OTHER, otherCredentialTypeDefinition, SIMUReasonType.OTHER, otherReasonTypeDefinition);

		assertTrue(instance1.equals(instance2));
	}


	@Test
	public void testEquals_OtherClass() {
		CBORLoginFailure instance1 = new CBORLoginFailure(publisherId, ifMapTimestamp, credentialType, reason);
		Integer instance2 = 42;

		assertFalse(instance1.equals(instance2));
	}
}
