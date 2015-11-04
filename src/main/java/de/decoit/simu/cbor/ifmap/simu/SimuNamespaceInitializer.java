/*
 * No license defined yet.
 */
package de.decoit.simu.cbor.ifmap.simu;

import de.decoit.simu.cbor.ifmap.deserializer.ExtendedIdentifierDeserializerManager;
import de.decoit.simu.cbor.ifmap.deserializer.MetadataDeserializerManager;
import de.decoit.simu.cbor.ifmap.metadata.singlevalue.CBORDiscoveredBy;
import de.decoit.simu.cbor.ifmap.simu.deserializer.identifier.FileDeserializer;
import de.decoit.simu.cbor.ifmap.simu.deserializer.identifier.ImplementationDeserializer;
import de.decoit.simu.cbor.ifmap.simu.deserializer.identifier.ServiceDeserializer;
import de.decoit.simu.cbor.ifmap.simu.deserializer.identifier.VulnerabilityDeserializer;
import de.decoit.simu.cbor.ifmap.simu.deserializer.metadata.AttackDetectedDeserializer;
import de.decoit.simu.cbor.ifmap.simu.deserializer.metadata.DeviceDiscoveredByDeserializer;
import de.decoit.simu.cbor.ifmap.simu.deserializer.metadata.FileMonitoredDeserializer;
import de.decoit.simu.cbor.ifmap.simu.deserializer.metadata.FileStatusDeserializer;
import de.decoit.simu.cbor.ifmap.simu.deserializer.metadata.HopCountDeserializer;
import de.decoit.simu.cbor.ifmap.simu.deserializer.metadata.IdentifiesAsDeserializer;
import de.decoit.simu.cbor.ifmap.simu.deserializer.metadata.ImplementationVulnerabilityDeserializer;
import de.decoit.simu.cbor.ifmap.simu.deserializer.metadata.LatencyDeserializer;
import de.decoit.simu.cbor.ifmap.simu.deserializer.metadata.LoginFailureDeserializer;
import de.decoit.simu.cbor.ifmap.simu.deserializer.metadata.LoginSuccessDeserializer;
import de.decoit.simu.cbor.ifmap.simu.deserializer.metadata.ServiceDiscoveredByDeserializer;
import de.decoit.simu.cbor.ifmap.simu.deserializer.metadata.ServiceImplementationDeserializer;
import de.decoit.simu.cbor.ifmap.simu.deserializer.metadata.ServiceIpDeserializer;
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



/**
 * Initializer class to register deserializers for the SIMU namespace.
 * The class features a single static init() method that registers all deserializers required
 * for usage of the SIMU namespace identifiers and metadata.
 *
 * @author Thomas Rix (rix@decoit.de)
 */
public class SimuNamespaceInitializer {
	private static boolean initialized = false;


	/**
	 * Register all deserializers.
	 * MUST be called before using any of the classes in this namespace package.
	 */
	public static void init() {
		// Extended identifiers
		ExtendedIdentifierDeserializerManager.registerVendorDeserializer(ImplementationDeserializer.getInstance(), 
																  CBORImplementation.class, 
																  SimuNamespaces.SIMU, 
																  CBORImplementation.XML_NAME);
		
		ExtendedIdentifierDeserializerManager.registerVendorDeserializer(ServiceDeserializer.getInstance(), 
																  CBORService.class,
																  SimuNamespaces.SIMU,
																  CBORService.XML_NAME);
		
		ExtendedIdentifierDeserializerManager.registerVendorDeserializer(VulnerabilityDeserializer.getInstance(),
																  CBORVulnerability.class,
																  SimuNamespaces.SIMU,
																  CBORVulnerability.XML_NAME);
		
		ExtendedIdentifierDeserializerManager.registerVendorDeserializer(FileDeserializer.getInstance(),
																  CBORFile.class,
																  SimuNamespaces.SIMU,
																  CBORFile.XML_NAME);

		// Multi-value metadata
		MetadataDeserializerManager.registerVendorDeserializer(AttackDetectedDeserializer.getInstance(), 
														CBORAttackDetected.class,
														SimuNamespaces.SIMU,
														CBORAttackDetected.XML_NAME);

		// Single-value metadata
		MetadataDeserializerManager.registerVendorDeserializer(DeviceDiscoveredByDeserializer.getInstance(), 
														CBORDeviceDiscoveredBy.class,
														SimuNamespaces.SIMU,
														CBORDeviceDiscoveredBy.XML_NAME);
		
		MetadataDeserializerManager.registerVendorDeserializer(FileMonitoredDeserializer.getInstance(), 
														CBORFileMonitored.class,
														SimuNamespaces.SIMU,
														CBORFileMonitored.XML_NAME);
		
		MetadataDeserializerManager.registerVendorDeserializer(FileStatusDeserializer.getInstance(), 
														CBORFileStatus.class,
														SimuNamespaces.SIMU,
														CBORFileStatus.XML_NAME);
		
		MetadataDeserializerManager.registerVendorDeserializer(HopCountDeserializer.getInstance(), 
														CBORHopCount.class,
														SimuNamespaces.SIMU,
														CBORHopCount.XML_NAME);
		
		MetadataDeserializerManager.registerVendorDeserializer(IdentifiesAsDeserializer.getInstance(),
														CBORIdentifiesAs.class,
														SimuNamespaces.SIMU,
														CBORIdentifiesAs.XML_NAME);
		
		MetadataDeserializerManager.registerVendorDeserializer(ImplementationVulnerabilityDeserializer.getInstance(),
														CBORImplementationVulnerability.class,
														SimuNamespaces.SIMU,
														CBORImplementationVulnerability.XML_NAME);
		
		MetadataDeserializerManager.registerVendorDeserializer(LatencyDeserializer.getInstance(), 
														CBORLatency.class,
														SimuNamespaces.SIMU,
														CBORLatency.XML_NAME);
		
		MetadataDeserializerManager.registerVendorDeserializer(LoginFailureDeserializer.getInstance(), 
														CBORLoginFailure.class,
														SimuNamespaces.SIMU,
														CBORLoginFailure.XML_NAME);
		
		MetadataDeserializerManager.registerVendorDeserializer(LoginSuccessDeserializer.getInstance(), 
														CBORLoginSuccess.class,
														SimuNamespaces.SIMU,
														CBORLoginSuccess.XML_NAME);
		
		MetadataDeserializerManager.registerVendorDeserializer(ServiceDiscoveredByDeserializer.getInstance(), 
														CBORServiceDiscoveredBy.class,
														SimuNamespaces.SIMU,
														CBORServiceDiscoveredBy.XML_NAME);
		
		MetadataDeserializerManager.registerVendorDeserializer(ServiceImplementationDeserializer.getInstance(), 
														CBORServiceImplementation.class,
														SimuNamespaces.SIMU,
														CBORServiceImplementation.XML_NAME);
		
		MetadataDeserializerManager.registerVendorDeserializer(ServiceIpDeserializer.getInstance(),
														CBORServiceIp.class,
														SimuNamespaces.SIMU,
														CBORServiceIp.XML_NAME);

		initialized = true;
	}


	/**
	 * Check if init() was called.
	 *
	 * @return true if init() was called, false otherwise
	 */
	public static boolean isInitialized() {
		return initialized;
	}
}
