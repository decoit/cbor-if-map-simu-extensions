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
public class CBORLatencyTest extends AbstractSimuTestBase {
	private final String publisherId = "my-publisher-id";
	private final ZonedDateTime ifMapTimestamp = ZonedDateTime.parse("2011-12-03T10:15:30.123456+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	private final Double latency = 42.0;
	private final String publisherId2 = "my-publisher-id-2";
	private final ZonedDateTime ifMapTimestamp2 = ZonedDateTime.parse("2012-12-03T10:15:30.123456+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	private final Double latency2 = 21.0;

	
	@Test
	public void testConstructor() {
		CBORLatency instance = new CBORLatency(publisherId, ifMapTimestamp, latency);
		
		assertEquals(latency, instance.getLatency());
		assertEquals(SimuNamespaces.SIMU, instance.getNamespace());
		assertEquals(IfMapCardinality.SINGLE_VALUE, instance.getIfMapCardinality());
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_NegativeDouble() {
		CBORLatency instance = new CBORLatency(publisherId, ifMapTimestamp, -0.1);
	}
	
	
	@Test
	public void testConstructor_ClientSide() {
		CBORLatency instance = new CBORLatency(latency);
		
		assertEquals(latency, instance.getLatency());
		assertNull(instance.getIfMapPublisherId());
		assertNull(instance.getIfMapTimestamp());
		assertEquals(SimuNamespaces.SIMU, instance.getNamespace());
		assertEquals(IfMapCardinality.SINGLE_VALUE, instance.getIfMapCardinality());
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_ClientSide_NegativeDouble() {
		CBORLatency instance = new CBORLatency(-0.1);
	}


	@Test
	public void testCborSerialize() throws Exception {
		CBORLatency instance = new CBORLatency(publisherId, ifMapTimestamp, latency);

		CborBuilder cb = new CborBuilder();
		ArrayBuilder ab = cb.addArray();

		instance.cborSerialize(ab);

		ab.end();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		CborEncoder ce = new CborEncoder(bos);
		ce.encode(cb.build());

		log.info("CBOR serialize:");
		log.info(DatatypeConverter.printHexBinary(bos.toByteArray()));

		byte[] expResult = DatatypeConverter.parseHexBinary("84040388006F6D792D7075626C69736865722D696401C11A4ED9E8B202"
				+ "C482281A075BCA000300FB4045000000000000");

		assertTrue("Byte array mismatch", Arrays.equals(expResult, bos.toByteArray()));
	}


	@Test
	public void testEquals_False() {
		CBORLatency instance1 = new CBORLatency(publisherId, ifMapTimestamp, latency);
		CBORLatency instance2 = new CBORLatency(publisherId2, ifMapTimestamp2, latency2);

		assertFalse(instance1.equals(instance2));
	}


	@Test
	public void testEquals_True() {
		CBORLatency instance1 = new CBORLatency(publisherId, ifMapTimestamp, latency);
		CBORLatency instance2 = new CBORLatency(publisherId, ifMapTimestamp, latency);

		assertTrue(instance1.equals(instance2));
	}


	@Test
	public void testEquals_OtherClass() {
		CBORLatency instance1 = new CBORLatency(publisherId, ifMapTimestamp, latency);
		Integer instance2 = 42;

		assertFalse(instance1.equals(instance2));
	}
}
