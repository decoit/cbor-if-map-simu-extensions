/*
 * No license defined yet.
 */
package de.decoit.simu.cbor.ifmap.simu;

import de.decoit.simu.cbor.ifmap.deserializer.ExtendedIdentifierDeserializerManager;
import de.decoit.simu.cbor.ifmap.deserializer.MetadataDeserializerManager;
import de.decoit.simu.cbor.ifmap.simu.identifier.CBORFile;
import de.decoit.simu.cbor.ifmap.simu.identifier.CBORImplementation;
import de.decoit.simu.cbor.ifmap.simu.identifier.CBORService;
import de.decoit.simu.cbor.ifmap.simu.identifier.CBORVulnerability;
import de.decoit.simu.cbor.ifmap.simu.metadata.multivalue.CBORAttackDetected;
import de.decoit.simu.cbor.ifmap.simu.metadata.singlevalue.CBORDeviceDiscoveredBy;
import de.decoit.simu.cbor.ifmap.simu.metadata.singlevalue.CBORFileMonitored;
import de.decoit.simu.cbor.ifmap.simu.metadata.singlevalue.CBORFileStatus;
import de.decoit.simu.cbor.ifmap.simu.metadata.singlevalue.CBORHopCount;
import de.decoit.simu.cbor.ifmap.simu.metadata.singlevalue.CBORIdentifiesAs;
import de.decoit.simu.cbor.ifmap.simu.metadata.singlevalue.CBORImplementationVulnerability;
import de.decoit.simu.cbor.ifmap.simu.metadata.singlevalue.CBORLatency;
import de.decoit.simu.cbor.ifmap.simu.metadata.singlevalue.CBORLoginFailure;
import de.decoit.simu.cbor.ifmap.simu.metadata.singlevalue.CBORLoginSuccess;
import de.decoit.simu.cbor.ifmap.simu.metadata.singlevalue.CBORServiceDiscoveredBy;
import de.decoit.simu.cbor.ifmap.simu.metadata.singlevalue.CBORServiceImplementation;
import de.decoit.simu.cbor.ifmap.simu.metadata.singlevalue.CBORServiceIp;
import de.decoit.simu.cbor.ifmap.simu.util.SimuNamespaces;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;


/**
 *
 * @author Thomas Rix (rix@decoit.de)
 */
public class SimuNamespaceInitializerTest {
	@BeforeClass
	public static void setUp() {
		ExtendedIdentifierDeserializerManager.clearAllVendorDeserializers();
		MetadataDeserializerManager.clearAllVendorDeserializers();
	}
	
	
	@AfterClass
	public static void tearDown() {
		ExtendedIdentifierDeserializerManager.clearAllVendorDeserializers();
		MetadataDeserializerManager.clearAllVendorDeserializers();
	}


	@Test
	public void testInit() {
		SimuNamespaceInitializer.init();

		assertTrue(SimuNamespaceInitializer.isInitialized());
		
		assertTrue(ExtendedIdentifierDeserializerManager.hasVendorDeserializer(CBORFile.class));
		assertTrue(ExtendedIdentifierDeserializerManager.hasVendorDeserializer(CBORImplementation.class));
		assertTrue(ExtendedIdentifierDeserializerManager.hasVendorDeserializer(CBORService.class));
		assertTrue(ExtendedIdentifierDeserializerManager.hasVendorDeserializer(CBORVulnerability.class));
		
		assertNotNull(ExtendedIdentifierDeserializerManager.resolveTargetClass(SimuNamespaces.SIMU, CBORFile.XML_NAME));
		assertNotNull(ExtendedIdentifierDeserializerManager.resolveTargetClass(SimuNamespaces.SIMU, CBORImplementation.XML_NAME));
		assertNotNull(ExtendedIdentifierDeserializerManager.resolveTargetClass(SimuNamespaces.SIMU, CBORService.XML_NAME));
		assertNotNull(ExtendedIdentifierDeserializerManager.resolveTargetClass(SimuNamespaces.SIMU, CBORVulnerability.XML_NAME));
		
		assertTrue(MetadataDeserializerManager.hasVendorDeserializer(CBORAttackDetected.class));
		assertTrue(MetadataDeserializerManager.hasVendorDeserializer(CBORDeviceDiscoveredBy.class));
		assertTrue(MetadataDeserializerManager.hasVendorDeserializer(CBORFileMonitored.class));
		assertTrue(MetadataDeserializerManager.hasVendorDeserializer(CBORFileStatus.class));
		assertTrue(MetadataDeserializerManager.hasVendorDeserializer(CBORHopCount.class));
		assertTrue(MetadataDeserializerManager.hasVendorDeserializer(CBORIdentifiesAs.class));
		assertTrue(MetadataDeserializerManager.hasVendorDeserializer(CBORImplementationVulnerability.class));
		assertTrue(MetadataDeserializerManager.hasVendorDeserializer(CBORLatency.class));
		assertTrue(MetadataDeserializerManager.hasVendorDeserializer(CBORLoginFailure.class));
		assertTrue(MetadataDeserializerManager.hasVendorDeserializer(CBORLoginSuccess.class));
		assertTrue(MetadataDeserializerManager.hasVendorDeserializer(CBORServiceDiscoveredBy.class));
		assertTrue(MetadataDeserializerManager.hasVendorDeserializer(CBORServiceImplementation.class));
		assertTrue(MetadataDeserializerManager.hasVendorDeserializer(CBORServiceIp.class));
		
		assertNotNull(MetadataDeserializerManager.resolveTargetClass(SimuNamespaces.SIMU, CBORAttackDetected.XML_NAME));
		assertNotNull(MetadataDeserializerManager.resolveTargetClass(SimuNamespaces.SIMU, CBORDeviceDiscoveredBy.XML_NAME));
		assertNotNull(MetadataDeserializerManager.resolveTargetClass(SimuNamespaces.SIMU, CBORFileMonitored.XML_NAME));
		assertNotNull(MetadataDeserializerManager.resolveTargetClass(SimuNamespaces.SIMU, CBORFileStatus.XML_NAME));
		assertNotNull(MetadataDeserializerManager.resolveTargetClass(SimuNamespaces.SIMU, CBORHopCount.XML_NAME));
		assertNotNull(MetadataDeserializerManager.resolveTargetClass(SimuNamespaces.SIMU, CBORIdentifiesAs.XML_NAME));
		assertNotNull(MetadataDeserializerManager.resolveTargetClass(SimuNamespaces.SIMU, CBORImplementationVulnerability.XML_NAME));
		assertNotNull(MetadataDeserializerManager.resolveTargetClass(SimuNamespaces.SIMU, CBORLatency.XML_NAME));
		assertNotNull(MetadataDeserializerManager.resolveTargetClass(SimuNamespaces.SIMU, CBORLoginFailure.XML_NAME));
		assertNotNull(MetadataDeserializerManager.resolveTargetClass(SimuNamespaces.SIMU, CBORLoginSuccess.XML_NAME));
		assertNotNull(MetadataDeserializerManager.resolveTargetClass(SimuNamespaces.SIMU, CBORServiceDiscoveredBy.XML_NAME));
		assertNotNull(MetadataDeserializerManager.resolveTargetClass(SimuNamespaces.SIMU, CBORServiceImplementation.XML_NAME));
		assertNotNull(MetadataDeserializerManager.resolveTargetClass(SimuNamespaces.SIMU, CBORServiceIp.XML_NAME));
	}


	@Test
	public void makeJaCoCoHappy() {
		SimuNamespaceInitializerExt instance = new SimuNamespaceInitializerExt();
	}


	/**
	 * Dummy implementation to make JaCoCo happy for 100% code coverage.
	 */
	private class SimuNamespaceInitializerExt extends SimuNamespaceInitializer {

	}
}
