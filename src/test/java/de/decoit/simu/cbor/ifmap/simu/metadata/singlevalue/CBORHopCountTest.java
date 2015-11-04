/*
 * No license defined yet.
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
public class CBORHopCountTest extends AbstractSimuTestBase {
	private final String publisherId = "my-publisher-id";
	private final ZonedDateTime ifMapTimestamp = ZonedDateTime.parse("2011-12-03T10:15:30.123456+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	private final Integer hopCount = 42;
	private final String publisherId2 = "my-publisher-id-2";
	private final ZonedDateTime ifMapTimestamp2 = ZonedDateTime.parse("2012-12-03T10:15:30.123456+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	private final Integer hopCount2 = 21;


	@Test
	public void testConstructor() {
		CBORHopCount instance = new CBORHopCount(publisherId, ifMapTimestamp, hopCount);
		
		assertEquals(hopCount, instance.getHopCount());
		assertEquals(SimuNamespaces.SIMU, instance.getNamespace());
		assertEquals(IfMapCardinality.SINGLE_VALUE, instance.getIfMapCardinality());
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_Null() {
		CBORHopCount instance = new CBORHopCount(publisherId, ifMapTimestamp, null);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_Zero() {
		CBORHopCount instance = new CBORHopCount(publisherId, ifMapTimestamp, 0);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_NegativeInteger() {
		CBORHopCount instance = new CBORHopCount(publisherId, ifMapTimestamp, -1);
	}
	
	
	@Test
	public void testConstructor_ClientSide() {
		CBORHopCount instance = new CBORHopCount(hopCount);
		
		assertEquals(hopCount, instance.getHopCount());
		assertNull(instance.getIfMapPublisherId());
		assertNull(instance.getIfMapTimestamp());
		assertEquals(SimuNamespaces.SIMU, instance.getNamespace());
		assertEquals(IfMapCardinality.SINGLE_VALUE, instance.getIfMapCardinality());
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_ClientSide_Null() {
		CBORHopCount instance = new CBORHopCount(null);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_ClientSide_Zero() {
		CBORHopCount instance = new CBORHopCount(0);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_ClientSide_NegativeInteger() {
		CBORHopCount instance = new CBORHopCount(-1);
	}


	@Test
	public void testCborSerialize() throws Exception {
		CBORHopCount instance = new CBORHopCount(publisherId, ifMapTimestamp, hopCount);
		
		CborBuilder cb = new CborBuilder();
		ArrayBuilder ab = cb.addArray();

		instance.cborSerialize(ab);

		ab.end();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		CborEncoder ce = new CborEncoder(bos);
		ce.encode(cb.build());

		log.info("CBOR serialize:");
		log.info(DatatypeConverter.printHexBinary(bos.toByteArray()));

		byte[] expResult = DatatypeConverter.parseHexBinary("84040488006F6D792D7075626C69736865722D696401C11A4ED9E8B202"
				+ "C482281A075BCA000300182A");

		assertTrue("Byte array mismatch", Arrays.equals(expResult, bos.toByteArray()));
	}


	@Test
	public void testEquals_False() {
		CBORHopCount instance1 = new CBORHopCount(publisherId, ifMapTimestamp, hopCount);
		CBORHopCount instance2 = new CBORHopCount(publisherId2, ifMapTimestamp2, hopCount2);

		assertFalse(instance1.equals(instance2));
	}


	@Test
	public void testEquals_True() {
		CBORHopCount instance1 = new CBORHopCount(publisherId, ifMapTimestamp, hopCount);
		CBORHopCount instance2 = new CBORHopCount(publisherId, ifMapTimestamp, hopCount);

		assertTrue(instance1.equals(instance2));
	}


	@Test
	public void testEquals_OtherClass() {
		CBORHopCount instance1 = new CBORHopCount(publisherId, ifMapTimestamp, hopCount);
		Integer instance2 = 42;

		assertFalse(instance1.equals(instance2));
	}
}
