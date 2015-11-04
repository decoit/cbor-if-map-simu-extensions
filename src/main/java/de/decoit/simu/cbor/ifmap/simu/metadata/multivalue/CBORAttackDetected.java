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
package de.decoit.simu.cbor.ifmap.simu.metadata.multivalue;

import co.nstant.in.cbor.builder.ArrayBuilder;
import co.nstant.in.cbor.model.DataItem;
import co.nstant.in.cbor.model.DoublePrecisionFloat;
import co.nstant.in.cbor.model.SimpleValue;
import co.nstant.in.cbor.model.SimpleValueType;
import co.nstant.in.cbor.model.UnicodeString;
import de.decoit.simu.cbor.ifmap.exception.CBORSerializationException;
import de.decoit.simu.cbor.ifmap.metadata.multivalue.AbstractMultiValueMetadata;
import de.decoit.simu.cbor.ifmap.simu.util.SimuNamespaces;
import de.decoit.simu.cbor.xml.dictionary.DictionaryProvider;
import de.decoit.simu.cbor.xml.dictionary.DictionarySimpleElement;
import de.decoit.simu.cbor.xml.dictionary.exception.DictionaryPathException;
import java.time.ZonedDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * Java representation of the attack-detected metadata from the SIMU namespace.
 * 
 * @author Thomas Rix (rix@decoit.de)
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Slf4j
public class CBORAttackDetected extends AbstractMultiValueMetadata {
	public static final String XML_NAME = "attack-detected";
	public static final String TYPE = "type";
	public static final String ID = "id";
	public static final String SEVERITY_SCORE = "severity-score";
	
	@Getter
	private final String type;
	@Getter
	private final String id;
	@Getter
	private Double severityScore;
	
	
	/**
	 * Create a new attack-detected metadata.
	 * This constructor should be used for metadata that is sent from the SERVER to the CLIENT. It includes
	 * the attributes ifmap-publisher-id, ifmap-timestamp and ifmap-timestamp-fraction.
	 * The timestamps are stored with UTC timezone. If a timestamp with another timezone is
	 * passed into the constructor, it will be converted to UTC. The IF-MAP timestamp fraction
	 * element will be calculated from the provided timestamp.
	 *
	 * @param ifMapPublisherId IF-MAP client publisher ID
	 * @param ifMapTimestamp Timestamp for the metadata
	 * @param type Attack type
	 * @param id Attack ID
	 */
	public CBORAttackDetected(String ifMapPublisherId, ZonedDateTime ifMapTimestamp, String type, String id) {
		super(SimuNamespaces.SIMU, XML_NAME, ifMapPublisherId, ifMapTimestamp);

		if(StringUtils.isBlank(type)) {
			throw new IllegalArgumentException("Type may not be blank");
		}
		
		if(StringUtils.isBlank(id)) {
			throw new IllegalArgumentException("ID may not be blank");
		}

		this.type = type;
		this.id = id;
		this.severityScore = null;
	}
	
	
	/**
	 * Create a new attack-detected metadata.
	 * This constructor should be used for metadata that is sent from the CLIENT to the SERVER. It does not include
	 * the attributes ifmap-publisher-id, ifmap-timestamp and ifmap-timestamp-fraction.
	 * The timestamps are stored with UTC timezone. If a timestamp with another timezone is
	 * passed into the constructor, it will be converted to UTC. The IF-MAP timestamp fraction
	 * element will be calculated from the provided timestamp.
	 *
	 * @param type Attack type
	 * @param id Attack ID
	 */
	public CBORAttackDetected(String type, String id) {
		super(SimuNamespaces.SIMU, XML_NAME);

		if(StringUtils.isBlank(type)) {
			throw new IllegalArgumentException("Type may not be blank");
		}
		
		if(StringUtils.isBlank(id)) {
			throw new IllegalArgumentException("ID may not be blank");
		}

		this.type = type;
		this.id = id;
		this.severityScore = null;
	}
	
	
	/**
	 * Set the severity score of the detected attack.
	 * The parameter may be null to remove this property from the metadata.
	 * 
	 * @param score Severity score
	 */
	public void setSeverityScore(Double score) {
		this.severityScore = score;
	}
	
	
	@Override
	protected void serializeNestedElements(final ArrayBuilder<?> builder) throws CBORSerializationException {
		try {
			// Build dictionary path to this element
			StringBuilder dictPathSb = new StringBuilder("<");
			dictPathSb.append(this.namespace);
			dictPathSb.append(">");
			dictPathSb.append(this.elementName);

			// Get dictionary entry for this element
			DictionarySimpleElement elementEntry = DictionaryProvider.getInstance().findElementByPath(dictPathSb.toString());

			// Serialize 'type'
			{
				DataItem cborName = this.getNestedElementNameMapping(CBORAttackDetected.TYPE, elementEntry);

				builder.add(new SimpleValue(SimpleValueType.NULL));
				builder.add(cborName);
				builder.addArray();
				builder.add(new UnicodeString(this.type));
			}
			
			// Serialize 'id'
			{
				DataItem cborName = this.getNestedElementNameMapping(CBORAttackDetected.ID, elementEntry);

				builder.add(new SimpleValue(SimpleValueType.NULL));
				builder.add(cborName);
				builder.addArray();
				builder.add(new UnicodeString(this.id));
			}
			
			// Serialize 'severity-score'
			if(this.severityScore != null) {
				DataItem cborName = this.getNestedElementNameMapping(CBORAttackDetected.SEVERITY_SCORE, elementEntry);

				builder.add(new SimpleValue(SimpleValueType.NULL));
				builder.add(cborName);
				builder.addArray();
				builder.add(new DoublePrecisionFloat(this.severityScore));
			}
		}
		catch(DictionaryPathException | RuntimeException ex) {
			throw new CBORSerializationException("Error during serialization, see nested exception for details", ex);
		}
	}
}
