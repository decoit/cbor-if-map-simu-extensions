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
public class CBORImplementationNoDictTest {
	private final String administrativeDomain = "simu-adm";
	private final String name = "impl-name";
	private final String version = "impl-version";
	private final String localVersion = "impl-local-version";
	private final String platform = "impl-platform";

	
	@Test
	public void testCborSerialize() throws Exception {
		CBORImplementation instance = new CBORImplementation(administrativeDomain, name, version);

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
				+ "536368656D612F31D9A4106E696D706C656D656E746174696F6E867561646D696E6973747261746976652D646F6D61696E68"
				+ "73696D752D61646D646E616D6569696D706C2D6E616D656776657273696F6E6C696D706C2D76657273696F6E80");

		assertTrue("Byte array mismatch", Arrays.equals(expResult, bos.toByteArray()));
	}
	
	
	@Test
	public void testCborSerialize_full() throws Exception {
		CBORImplementation instance = new CBORImplementation(administrativeDomain, name, version);
		instance.setLocalVersion(localVersion);
		instance.setPlatform(platform);

		CborBuilder cb = new CborBuilder();
		ArrayBuilder ab = cb.addArray();

		instance.cborSerialize(ab);

		ab.end();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		CborEncoder ce = new CborEncoder(bos);
		ce.encode(cb.build());

		log.info("CBOR serialize (full):");
		log.info(DatatypeConverter.printHexBinary(bos.toByteArray()));

		byte[] expResult = DatatypeConverter.parseHexBinary("847822687474703A2F2F73696D752D70726F6A6563742E64652F584D4C"
				+ "536368656D612F31D9A4106E696D706C656D656E746174696F6E8A7561646D696E6973747261746976652D646F6D61696E68"
				+ "73696D752D61646D646E616D6569696D706C2D6E616D656776657273696F6E6C696D706C2D76657273696F6E6D6C6F63616C"
				+ "2D76657273696F6E72696D706C2D6C6F63616C2D76657273696F6E68706C6174666F726D6D696D706C2D706C6174666F726D"
				+ "80");

		assertTrue("Byte array mismatch", Arrays.equals(expResult, bos.toByteArray()));
	}
}
