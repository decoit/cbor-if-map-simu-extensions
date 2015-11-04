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
package de.decoit.simu.cbor.ifmap.simu.identifier;

import co.nstant.in.cbor.CborBuilder;
import co.nstant.in.cbor.CborEncoder;
import co.nstant.in.cbor.builder.ArrayBuilder;
import java.io.ByteArrayOutputStream;
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
public class CBORFileNoDictTest {
	private final String administrativeDomain = "simu-adm";
	private final String path = "/etc/fstab";


	@Test
	public void testCborSerialize() throws Exception {
		CBORFile instance = new CBORFile(administrativeDomain, path);

		CborBuilder cb = new CborBuilder();
		ArrayBuilder ab = cb.addArray();

		instance.cborSerialize(ab);

		ab.end();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		CborEncoder ce = new CborEncoder(bos);
		ce.encode(cb.build());

		log.info("CBOR serialize:");
		log.info(DatatypeConverter.printHexBinary(bos.toByteArray()));

		byte[] expResult = DatatypeConverter.parseHexBinary("847822687474703A2F2F73696D752D70726F6A6563742E64652F584D4C"
				+ "536368656D612F31D9A4106466696C65847561646D696E6973747261746976652D646F6D61696E6873696D752D61646D6470"
				+ "6174686A2F6574632F667374616280");

		assertTrue("Byte array mismatch", Arrays.equals(expResult, bos.toByteArray()));
	}
}
