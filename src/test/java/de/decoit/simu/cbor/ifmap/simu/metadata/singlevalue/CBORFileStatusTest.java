/*
 * No license defined yet.
 */
package de.decoit.simu.cbor.ifmap.simu.metadata.singlevalue;

import co.nstant.in.cbor.CborBuilder;
import co.nstant.in.cbor.CborEncoder;
import co.nstant.in.cbor.builder.ArrayBuilder;
import de.decoit.simu.cbor.ifmap.enums.IfMapCardinality;
import de.decoit.simu.cbor.ifmap.simu.AbstractSimuTestBase;
import de.decoit.simu.cbor.ifmap.simu.enums.SIMUFileImportanceType;
import de.decoit.simu.cbor.ifmap.simu.enums.SIMUFileStatusType;
import de.decoit.simu.cbor.ifmap.simu.util.SimuNamespaces;
import de.decoit.simu.cbor.ifmap.util.TimestampHelper;
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
public class CBORFileStatusTest extends AbstractSimuTestBase {
	private final String publisherId = "my-publisher-id";
	private final ZonedDateTime ifMapTimestamp = ZonedDateTime.parse("2011-12-03T10:15:30.123456+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	private final ZonedDateTime discoveredTime = ZonedDateTime.parse("2011-12-03T10:10:15+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	private final SIMUFileStatusType status = SIMUFileStatusType.NEW;
	private final SIMUFileImportanceType importance = SIMUFileImportanceType.CRITICAL;
	private final String publisherId2 = "my-publisher-id-2";
	private final ZonedDateTime ifMapTimestamp2 = ZonedDateTime.parse("2012-12-03T10:15:30.123456+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	private final ZonedDateTime discoveredTime2 = ZonedDateTime.parse("2012-12-03T10:10:15+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	private final SIMUFileStatusType status2 = SIMUFileStatusType.MODIFIED;
	private final SIMUFileImportanceType importance2 = SIMUFileImportanceType.MEDIUM;

	
	@Test
	public void testConstructor() {
		CBORFileStatus instance = new CBORFileStatus(publisherId, ifMapTimestamp, status, importance, discoveredTime);
		
		assertEquals(SimuNamespaces.SIMU, instance.getNamespace());
		assertEquals(IfMapCardinality.SINGLE_VALUE, instance.getIfMapCardinality());
		assertEquals(status, instance.getStatus());
		assertEquals(importance, instance.getImportance());
		assertEquals(TimestampHelper.toUTC(discoveredTime), instance.getDiscoveredTime());
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_null_Importance_ZonedDateTime() {
		CBORFileStatus instance = new CBORFileStatus(publisherId, ifMapTimestamp, null, importance, discoveredTime);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_Status_null_ZonedDateTime() {
		CBORFileStatus instance = new CBORFileStatus(publisherId, ifMapTimestamp, status, null, discoveredTime);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_Status_Importance_null() {
		CBORFileStatus instance = new CBORFileStatus(publisherId, ifMapTimestamp, status, importance, null);
	}
	
	
	@Test
	public void testConstructor_ClientSide() {
		CBORFileStatus instance = new CBORFileStatus(status, importance, discoveredTime);
		
		assertNull(instance.getIfMapPublisherId());
		assertNull(instance.getIfMapTimestamp());
		assertEquals(SimuNamespaces.SIMU, instance.getNamespace());
		assertEquals(IfMapCardinality.SINGLE_VALUE, instance.getIfMapCardinality());
		assertEquals(status, instance.getStatus());
		assertEquals(importance, instance.getImportance());
		assertEquals(TimestampHelper.toUTC(discoveredTime), instance.getDiscoveredTime());
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_ClientSide_null_Importance_ZonedDateTime() {
		CBORFileStatus instance = new CBORFileStatus(null, importance, discoveredTime);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_ClientSide_Status_null_ZonedDateTime() {
		CBORFileStatus instance = new CBORFileStatus(status, null, discoveredTime);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_ClientSide_Status_Importance_null() {
		CBORFileStatus instance = new CBORFileStatus(status, importance, null);
	}
	

	@Test
	public void testCborSerialize() throws Exception {
		CBORFileStatus instance = new CBORFileStatus(publisherId, ifMapTimestamp, status, importance, discoveredTime);

		CborBuilder cb = new CborBuilder();
		ArrayBuilder ab = cb.addArray();

		instance.cborSerialize(ab);

		ab.end();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		CborEncoder ce = new CborEncoder(bos);
		ce.encode(cb.build());

		log.info("CBOR serialize:");
		log.info(DatatypeConverter.printHexBinary(bos.toByteArray()));

		byte[] expResult = DatatypeConverter.parseHexBinary("84041088006F6D792D7075626C69736865722D696401C11A4ED9E8B202"
				+ "C482281A075BCA0003008CF6008000F60180C11A4ED9E777F6028003");

		assertTrue("Byte array mismatch", Arrays.equals(expResult, bos.toByteArray()));
	}


	@Test
	public void testEquals_False() {
		CBORFileStatus instance1 = new CBORFileStatus(publisherId, ifMapTimestamp, status, importance, discoveredTime);
		CBORFileStatus instance2 = new CBORFileStatus(publisherId2, ifMapTimestamp2, status2, importance2, discoveredTime2);

		assertFalse(instance1.equals(instance2));
	}


	@Test
	public void testEquals_True() {
		CBORFileStatus instance1 = new CBORFileStatus(publisherId, ifMapTimestamp, status, importance, discoveredTime);
		CBORFileStatus instance2 = new CBORFileStatus(publisherId, ifMapTimestamp, status, importance, discoveredTime);

		assertTrue(instance1.equals(instance2));
	}


	@Test
	public void testEquals_OtherClass() {
		CBORFileStatus instance1 = new CBORFileStatus(publisherId, ifMapTimestamp, status, importance, discoveredTime);
		Integer instance2 = 42;

		assertFalse(instance1.equals(instance2));
	}
}
