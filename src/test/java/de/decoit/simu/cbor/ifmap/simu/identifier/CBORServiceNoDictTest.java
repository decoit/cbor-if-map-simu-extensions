/*
 * No license defined yet.
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
public class CBORServiceNoDictTest {
	private final String administrativeDomain = "simu-adm";
	private final String name = "service-name";
	private final String type = "service-type";
	private final Integer port = 42;


	@Test
	public void testCborSerialize() throws Exception {
		CBORService instance = new CBORService(administrativeDomain, type, name, port);

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
				+ "536368656D612F31D9A4106773657276696365887561646D696E6973747261746976652D646F6D61696E6873696D752D6164"
				+ "6D64706F7274182A646E616D656C736572766963652D6E616D6564747970656C736572766963652D7479706580");

		assertTrue("Byte array mismatch", Arrays.equals(expResult, bos.toByteArray()));
	}
}
