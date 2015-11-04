/*
 * No license defined yet.
 */
package de.decoit.simu.cbor.ifmap.simu;

import de.decoit.simu.cbor.ifmap.CBORDeserializer;
import de.decoit.simu.cbor.ifmap.CBORSerializer;
import de.decoit.simu.cbor.ifmap.deserializer.ExtendedIdentifierDeserializerManager;
import de.decoit.simu.cbor.ifmap.deserializer.MetadataDeserializerManager;
import de.decoit.simu.cbor.ifmap.identifier.AbstractIdentifier;
import de.decoit.simu.cbor.ifmap.metadata.AbstractMetadata;
import de.decoit.simu.cbor.ifmap.response.CBORResponse;
import de.decoit.simu.cbor.ifmap.response.model.CBORPollResult;
import de.decoit.simu.cbor.ifmap.response.model.search.DeletePollSearchResult;
import de.decoit.simu.cbor.ifmap.response.model.search.SearchResultItem;
import de.decoit.simu.cbor.ifmap.simu.enums.SIMUCredentialType;
import de.decoit.simu.cbor.ifmap.simu.enums.SIMUFileImportanceType;
import de.decoit.simu.cbor.ifmap.simu.enums.SIMUFileStatusType;
import de.decoit.simu.cbor.ifmap.simu.enums.SIMUReasonType;
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
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Thomas Rix (rix@decoit.de)
 */
@Slf4j
public class InterOpTest extends AbstractSimuTestBase {
	@BeforeClass
	public static void setUp() {
		ExtendedIdentifierDeserializerManager.clearAllVendorDeserializers();
		MetadataDeserializerManager.clearAllVendorDeserializers();
		
		SimuNamespaceInitializer.init();
	}
	
	
	@AfterClass
	public static void tearDown() {
		ExtendedIdentifierDeserializerManager.clearAllVendorDeserializers();
		MetadataDeserializerManager.clearAllVendorDeserializers();
	}
	
	
	@Test
	public void interOpTest() throws Exception {
		AbstractIdentifier identifierA = new CBORFile("simu-adm", "/etc/fstab");
		AbstractIdentifier identifierB = new CBORImplementation("simu-adm", "openssh", "v0.6.7");
		AbstractIdentifier identifierC = new CBORService("simu-adm", "SSH", "openssh", 22);
		AbstractIdentifier identifierD = new CBORVulnerability("simu-adm", "CVE", "some-CVE-ID");
	
		AbstractMetadata m1 = new CBORAttackDetected("CVE", "some-CVE-ID");
		AbstractMetadata m2 = new CBORDeviceDiscoveredBy("device-discoverer");
		AbstractMetadata m3 = new CBORFileMonitored();
		AbstractMetadata m4 = new CBORFileStatus(SIMUFileStatusType.NEW, SIMUFileImportanceType.LOW, ZonedDateTime.now(
																									ZoneOffset.UTC));
		AbstractMetadata m5 = new CBORHopCount(42);
		AbstractMetadata m6 = new CBORIdentifiesAs();
		AbstractMetadata m7 = new CBORFileMonitored();
		AbstractMetadata m8 = new CBORImplementationVulnerability();
		AbstractMetadata m9 = new CBORLatency(42.0);
		AbstractMetadata m10 = new CBORLoginFailure(SIMUCredentialType.BIOMETRIC, SIMUReasonType.COMMUNICATION_FAILURE);
		AbstractMetadata m11 = new CBORLoginSuccess(SIMUCredentialType.BIOMETRIC);
		AbstractMetadata m12 = new CBORServiceDiscoveredBy();
		AbstractMetadata m13 = new CBORServiceImplementation();
		AbstractMetadata m14 = new CBORServiceIp();

		SearchResultItem sri1 = new SearchResultItem(identifierA, identifierB);
		sri1.addMetadata(m1);
		sri1.addMetadata(m2);
		sri1.addMetadata(m3);
		sri1.addMetadata(m4);
		sri1.addMetadata(m5);
		sri1.addMetadata(m6);
		sri1.addMetadata(m7);

		SearchResultItem sri2 = new SearchResultItem(identifierC, identifierD);
		sri2.addMetadata(m8);
		sri2.addMetadata(m9);
		sri2.addMetadata(m10);
		sri2.addMetadata(m11);
		sri2.addMetadata(m12);
		sri2.addMetadata(m13);
		sri2.addMetadata(m14);
		
		DeletePollSearchResult dpsr = new DeletePollSearchResult();
		dpsr.addSearchResultItem(sri1);
		dpsr.addSearchResultItem(sri2);
		
		CBORPollResult inputResult = new CBORPollResult();
		inputResult.addPollResult(dpsr);
		
		CBORResponse r = new CBORResponse(inputResult);
		
		byte[] cborBytes = CBORSerializer.serializeResponse(r);
		
		CBORResponse result = CBORDeserializer.deserializeResponse(cborBytes);
		
		assertEquals(r, result);
	}
}
