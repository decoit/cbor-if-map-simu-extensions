/*
 * No license defined yet.
 */
package de.decoit.simu.cbor.ifmap.simu;

import de.decoit.simu.cbor.xml.dictionary.DictionaryProvider;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import org.junit.AfterClass;
import org.junit.BeforeClass;



/**
 *
 * @author Thomas Rix (rix@decoit.de)
 */
public abstract class AbstractSimuTestBase {
	@BeforeClass
	public static void testClassSetup() throws URISyntaxException, IOException {
		URI dictFile = ClassLoader.getSystemResource("ifmap-base.dict").toURI();
		DictionaryProvider.getInstance().replaceDictionary(Paths.get(dictFile));

		URI dummyDictFile = ClassLoader.getSystemResource("ifmap-simu.dict").toURI();
		DictionaryProvider.getInstance().extendDictionary(Paths.get(dummyDictFile));
	}


	@AfterClass
	public static void testClassTearDown() {
		DictionaryProvider.getInstance().clear();
	}
}
