/*
 * No license defined yet.
 */
package de.decoit.simu.cbor.ifmap.simu.deserializer.identifier;

import co.nstant.in.cbor.CborDecoder;
import co.nstant.in.cbor.model.Array;
import co.nstant.in.cbor.model.DataItem;
import de.decoit.simu.cbor.ifmap.simu.identifier.CBORImplementation;
import de.decoit.simu.cbor.ifmap.simu.util.SimuNamespaces;
import de.decoit.simu.cbor.xml.dictionary.DictionaryProvider;
import de.decoit.simu.cbor.xml.dictionary.DictionarySimpleElement;
import java.io.ByteArrayInputStream;
import java.util.List;
import javax.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author Thomas Rix (rix@decoit.de)
 */
@Slf4j
public class ImplementationDeserializerNoDictTest {
	private final String administrativeDomain = "simu-adm";
	private final String name = "impl-name";
	private final String version = "impl-version";
	private final String localVersion = "impl-local-version";
	private final String platform = "impl-platform";


	@Test
	public void testDeserialize() throws Exception {
		byte[] input = DatatypeConverter.parseHexBinary("847822687474703A2F2F73696D752D70726F6A6563742E64652F584D4C"
				+ "536368656D612F31D9A4106E696D706C656D656E746174696F6E867561646D696E6973747261746976652D646F6D61696E68"
				+ "73696D752D61646D646E616D6569696D706C2D6E616D656776657273696F6E6C696D706C2D76657273696F6E80");

		ByteArrayInputStream bis = new ByteArrayInputStream(input);
		CborDecoder cd = new CborDecoder(bis);
		List<DataItem> diList = cd.decode();
		Array topLevelArray = (Array) diList.get(0);
		
		String dictPath = "<" + SimuNamespaces.SIMU + ">" + CBORImplementation.XML_NAME;
		DictionarySimpleElement dse = DictionaryProvider.getInstance().findElementByPath(dictPath);
		
		CBORImplementation result = ImplementationDeserializer
										.getInstance()
										.deserialize((Array)topLevelArray.getDataItems().get(2), 
													 (Array)topLevelArray.getDataItems().get(3),
													 dse);

		assertEquals(administrativeDomain, result.getAdministrativeDomain());
		assertEquals(name, result.getName());
		assertEquals(version, result.getVersion());
		assertNull(result.getLocalVersion());
		assertNull(result.getPlatform());
	}


	@Test
	public void testDeserialize_full() throws Exception {
		byte[] input = DatatypeConverter.parseHexBinary("847822687474703A2F2F73696D752D70726F6A6563742E64652F584D4C"
				+ "536368656D612F31D9A4106E696D706C656D656E746174696F6E8A7561646D696E6973747261746976652D646F6D61696E68"
				+ "73696D752D61646D646E616D6569696D706C2D6E616D656776657273696F6E6C696D706C2D76657273696F6E6D6C6F63616C"
				+ "2D76657273696F6E72696D706C2D6C6F63616C2D76657273696F6E68706C6174666F726D6D696D706C2D706C6174666F726D"
				+ "80");

		ByteArrayInputStream bis = new ByteArrayInputStream(input);
		CborDecoder cd = new CborDecoder(bis);
		List<DataItem> diList = cd.decode();
		Array topLevelArray = (Array) diList.get(0);
		
		String dictPath = "<" + SimuNamespaces.SIMU + ">" + CBORImplementation.XML_NAME;
		DictionarySimpleElement dse = DictionaryProvider.getInstance().findElementByPath(dictPath);
		
		CBORImplementation result = ImplementationDeserializer
										.getInstance()
										.deserialize((Array)topLevelArray.getDataItems().get(2), 
													 (Array)topLevelArray.getDataItems().get(3),
													 dse);

		assertEquals(administrativeDomain, result.getAdministrativeDomain());
		assertEquals(name, result.getName());
		assertEquals(version, result.getVersion());
		assertEquals(localVersion, result.getLocalVersion());
		assertEquals(platform, result.getPlatform());
	}
}
