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
public class CBORDeviceDiscoveredByTest extends AbstractSimuTestBase {
	private final String publisherId = "my-publisher-id";
	private final ZonedDateTime ifMapTimestamp = ZonedDateTime.parse("2011-12-03T10:15:30.123456+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	private final String discoverer = "device-discoverer";
	private final String publisherId2 = "my-publisher-id-2";
	private final ZonedDateTime ifMapTimestamp2 = ZonedDateTime.parse("2012-12-03T10:15:30.123456+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	private final String discoverer2 = "device-discoverer-2";


	@Test
	public void testConstructor() throws Exception {
		CBORDeviceDiscoveredBy instance = new CBORDeviceDiscoveredBy(publisherId, ifMapTimestamp, discoverer);
		
		assertEquals(discoverer, instance.getDiscoverer());
		assertEquals(SimuNamespaces.SIMU, instance.getNamespace());
		assertEquals(IfMapCardinality.SINGLE_VALUE, instance.getIfMapCardinality());
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_null() throws Exception {
		CBORDeviceDiscoveredBy instance = new CBORDeviceDiscoveredBy(publisherId, ifMapTimestamp, null);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_EmptyString() throws Exception {
		CBORDeviceDiscoveredBy instance = new CBORDeviceDiscoveredBy(publisherId, ifMapTimestamp, "");
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_Whitespaces() throws Exception {
		CBORDeviceDiscoveredBy instance = new CBORDeviceDiscoveredBy(publisherId, ifMapTimestamp, "   ");
	}
	
	
	@Test
	public void testConstructor_ClientSide() throws Exception {
		CBORDeviceDiscoveredBy instance = new CBORDeviceDiscoveredBy(discoverer);
		
		assertEquals(discoverer, instance.getDiscoverer());
		assertNull(instance.getIfMapPublisherId());
		assertNull(instance.getIfMapTimestamp());
		assertEquals(SimuNamespaces.SIMU, instance.getNamespace());
		assertEquals(IfMapCardinality.SINGLE_VALUE, instance.getIfMapCardinality());
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_ClientSide_null() throws Exception {
		CBORDeviceDiscoveredBy instance = new CBORDeviceDiscoveredBy(null);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_ClientSide_EmptyString() throws Exception {
		CBORDeviceDiscoveredBy instance = new CBORDeviceDiscoveredBy("");
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_ClientSide_Whitespaces() throws Exception {
		CBORDeviceDiscoveredBy instance = new CBORDeviceDiscoveredBy("   ");
	}


	@Test
	public void testCborSerialize() throws Exception {
		CBORDeviceDiscoveredBy instance = new CBORDeviceDiscoveredBy(publisherId, ifMapTimestamp, discoverer);

		CborBuilder cb = new CborBuilder();
		ArrayBuilder ab = cb.addArray();

		instance.cborSerialize(ab);

		ab.end();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		CborEncoder ce = new CborEncoder(bos);
		ce.encode(cb.build());

		log.info("CBOR serialize:");
		log.info(DatatypeConverter.printHexBinary(bos.toByteArray()));

		byte[] expResult = DatatypeConverter.parseHexBinary("84040D88006F6D792D7075626C69736865722D696401C11A4ED9E8B202"
				+ "C482281A075BCA00030084F60080716465766963652D646973636F7665726572");

		assertTrue("Byte array mismatch", Arrays.equals(expResult, bos.toByteArray()));
	}


	@Test
	public void testEquals_False() {
		CBORDeviceDiscoveredBy instance1 = new CBORDeviceDiscoveredBy(publisherId, ifMapTimestamp, discoverer);
		CBORDeviceDiscoveredBy instance2 = new CBORDeviceDiscoveredBy(publisherId2, ifMapTimestamp2, discoverer2);

		assertFalse(instance1.equals(instance2));
	}


	@Test
	public void testEquals_True() {
		CBORDeviceDiscoveredBy instance1 = new CBORDeviceDiscoveredBy(publisherId, ifMapTimestamp, discoverer);
		CBORDeviceDiscoveredBy instance2 = new CBORDeviceDiscoveredBy(publisherId, ifMapTimestamp, discoverer);

		assertTrue(instance1.equals(instance2));
	}


	@Test
	public void testEquals_OtherClass() {
		CBORDeviceDiscoveredBy instance1 = new CBORDeviceDiscoveredBy(publisherId, ifMapTimestamp, discoverer);
		Integer instance2 = 42;

		assertFalse(instance1.equals(instance2));
	}
}
