/*
 * No license defined yet.
 */
package de.decoit.simu.cbor.ifmap.simu.deserializer.identifier;

import co.nstant.in.cbor.CborDecoder;
import co.nstant.in.cbor.model.Array;
import co.nstant.in.cbor.model.DataItem;
import de.decoit.simu.cbor.ifmap.simu.AbstractSimuTestBase;
import de.decoit.simu.cbor.ifmap.simu.identifier.CBORFile;
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
public class FileDeserializerTest extends AbstractSimuTestBase {
	private final String administrativeDomain = "simu-adm";
	private final String path = "/etc/fstab";
	
	
	@Test
	public void testDeserialize() throws Exception {
		byte[] input = DatatypeConverter.parseHexBinary("8404D9A4100E84006873696D752D61646D016A2F6574632F6673746162"
				+ "80");

		ByteArrayInputStream bis = new ByteArrayInputStream(input);
		CborDecoder cd = new CborDecoder(bis);
		List<DataItem> diList = cd.decode();
		Array topLevelArray = (Array) diList.get(0);
		
		String dictPath = "<" + SimuNamespaces.SIMU + ">" + CBORFile.XML_NAME;
		DictionarySimpleElement dse = DictionaryProvider.getInstance().findElementByPath(dictPath);
		
		CBORFile result = FileDeserializer
								.getInstance()
								.deserialize((Array)topLevelArray.getDataItems().get(2), 
											 (Array)topLevelArray.getDataItems().get(3),
											 dse);

		assertEquals(administrativeDomain, result.getAdministrativeDomain());
		assertEquals(path, result.getPath());
	}
}
