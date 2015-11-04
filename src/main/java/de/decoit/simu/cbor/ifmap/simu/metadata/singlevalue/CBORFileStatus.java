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
package de.decoit.simu.cbor.ifmap.simu.metadata.singlevalue;

import co.nstant.in.cbor.builder.ArrayBuilder;
import co.nstant.in.cbor.model.DataItem;
import co.nstant.in.cbor.model.SimpleValue;
import co.nstant.in.cbor.model.SimpleValueType;
import de.decoit.simu.cbor.ifmap.exception.CBORSerializationException;
import de.decoit.simu.cbor.ifmap.metadata.singlevalue.AbstractSingleValueMetadata;
import de.decoit.simu.cbor.ifmap.simu.enums.SIMUFileImportanceType;
import de.decoit.simu.cbor.ifmap.simu.enums.SIMUFileStatusType;
import de.decoit.simu.cbor.ifmap.simu.util.SimuNamespaces;
import de.decoit.simu.cbor.ifmap.util.TimestampHelper;
import de.decoit.simu.cbor.xml.dictionary.DictionaryProvider;
import de.decoit.simu.cbor.xml.dictionary.DictionarySimpleElement;
import de.decoit.simu.cbor.xml.dictionary.exception.DictionaryPathException;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;



/**
 * Java representation of the file-status metadata from the SIMU namespace.
 *
 * @author Thomas Rix (rix@decoit.de)
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Slf4j
public final class CBORFileStatus extends AbstractSingleValueMetadata {
	public static final String XML_NAME = "file-status";
	public static final String STATUS = "status";
	public static final String DISCOVERED_TIME = "discovered-time";
	public static final String IMPORTANCE = "importance";
	
	@Getter
	private final SIMUFileStatusType status;
	@Getter
	private final SIMUFileImportanceType importance;
	@Getter
	private final ZonedDateTime discoveredTime;


	/**
	 * Create a new file-status metadata.
	 * This constructor should be used for metadata that is sent from the SERVER to the CLIENT. It includes
	 * the attributes ifmap-publisher-id, ifmap-timestamp and ifmap-timestamp-fraction.
	 * The timestamp is stored with UTC timezone. If a timestamp with another timezone is
	 * passed into the constructor, it will be converted to UTC. The IF-MAP timestamp fraction
	 * element will be calculated from the provided timestamp.
	 *
	 * @param ifMapPublisherId IF-MAP client publisher ID
	 * @param ifMapTimestamp Timestamp for the metadata
	 * @param status Status of the monitored file
	 * @param importance Importance of the monitored file
	 * @param discoveredTime Time the last status change was discovered
	 */
	public CBORFileStatus(String ifMapPublisherId, ZonedDateTime ifMapTimestamp, SIMUFileStatusType status, SIMUFileImportanceType importance, ZonedDateTime discoveredTime) {
		super(SimuNamespaces.SIMU, XML_NAME, ifMapPublisherId, ifMapTimestamp);
		
		if(status == null) {
			throw new IllegalArgumentException("Status must not be null");
		}
		
		if(importance == null) {
			throw new IllegalArgumentException("Importance must not be null");
		}
		
		if(discoveredTime == null) {
			throw new IllegalArgumentException("Discovered time must not be null");
		}
		
		this.status = status;
		this.importance = importance;
		this.discoveredTime = TimestampHelper.toUTC(discoveredTime).truncatedTo(ChronoUnit.SECONDS);
	}
	
	
	/**
	 * Create a new file-status metadata.
	 * This constructor should be used for metadata that is sent from the CLIENT to the SERVER. It does not include
	 * the attributes ifmap-publisher-id, ifmap-timestamp and ifmap-timestamp-fraction.
	 * The timestamp is stored with UTC timezone. If a timestamp with another timezone is
	 * passed into the constructor, it will be converted to UTC. The IF-MAP timestamp fraction
	 * element will be calculated from the provided timestamp.
	 * 
	 * @param status Status of the monitored file
	 * @param importance Importance of the monitored file
	 * @param discoveredTime Time the last status change was discovered
	 */
	public CBORFileStatus(SIMUFileStatusType status, SIMUFileImportanceType importance, ZonedDateTime discoveredTime) {
		super(SimuNamespaces.SIMU, XML_NAME);
		
		if(status == null) {
			throw new IllegalArgumentException("Status must not be null");
		}
		
		if(importance == null) {
			throw new IllegalArgumentException("Importance must not be null");
		}
		
		if(discoveredTime == null) {
			throw new IllegalArgumentException("Discovered time must not be null");
		}
		
		this.status = status;
		this.importance = importance;
		this.discoveredTime = TimestampHelper.toUTC(discoveredTime).truncatedTo(ChronoUnit.SECONDS);
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

			// Serialize 'status'
			{
				DataItem cborName = this.getNestedElementNameMapping(CBORFileStatus.STATUS, elementEntry);

				builder.add(new SimpleValue(SimpleValueType.NULL));
				builder.add(cborName);
				builder.addArray();
				builder.add(this.getNestedElementEnumValueMapping(CBORFileStatus.STATUS, this.status.getXmlName(), elementEntry));
			}
			
			// Serialize 'discovered-time'
			{
				DataItem cborName = this.getNestedElementNameMapping(CBORFileStatus.DISCOVERED_TIME, elementEntry);

				builder.add(new SimpleValue(SimpleValueType.NULL));
				builder.add(cborName);
				builder.addArray();
				builder.add(TimestampHelper.toEpochTimeDataItem(this.discoveredTime));
			}
			
			// Serialize 'importance'
			{
				DataItem cborName = this.getNestedElementNameMapping(CBORFileStatus.IMPORTANCE, elementEntry);

				builder.add(new SimpleValue(SimpleValueType.NULL));
				builder.add(cborName);
				builder.addArray();
				builder.add(this.getNestedElementEnumValueMapping(CBORFileStatus.IMPORTANCE, this.importance.getXmlName(), elementEntry));
			}
		}
		catch(DictionaryPathException | RuntimeException ex) {
			throw new CBORSerializationException("Error during serialization, see nested exception for details", ex);
		}
	}
}
