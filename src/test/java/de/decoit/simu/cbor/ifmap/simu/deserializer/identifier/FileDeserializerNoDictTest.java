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
package de.decoit.simu.cbor.ifmap.simu.deserializer.identifier;

import co.nstant.in.cbor.CborDecoder;
import co.nstant.in.cbor.model.Array;
import co.nstant.in.cbor.model.DataItem;
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
public class FileDeserializerNoDictTest {
	private final String administrativeDomain = "simu-adm";
	private final String path = "/etc/fstab";


	@Test
	public void testDeserialize() throws Exception {
		byte[] input = DatatypeConverter.parseHexBinary("847822687474703A2F2F73696D752D70726F6A6563742E64652F584D4C"
				+ "536368656D612F31D9A4106466696C65847561646D696E6973747261746976652D646F6D61696E6873696D752D61646D6470"
				+ "6174686A2F6574632F667374616280");

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
