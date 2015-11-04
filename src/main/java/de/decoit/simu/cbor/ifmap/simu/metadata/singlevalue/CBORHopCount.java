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
import co.nstant.in.cbor.model.UnsignedInteger;
import de.decoit.simu.cbor.ifmap.exception.CBORSerializationException;
import de.decoit.simu.cbor.ifmap.metadata.singlevalue.AbstractSingleValueMetadata;
import de.decoit.simu.cbor.ifmap.simu.util.SimuNamespaces;
import java.time.ZonedDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;



/**
 * Java representation of the hop-count metadata from the SIMU namespace.
 *
 * @author Thomas Rix (rix@decoit.de)
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Slf4j
public final class CBORHopCount extends AbstractSingleValueMetadata {
	public static final String XML_NAME = "hop-count";

	@Getter
	private final Integer hopCount;


	/**
	 * Create a new hop-count metadata.
	 * This constructor should be used for metadata that is sent from the SERVER to the CLIENT. It includes
	 * the attributes ifmap-publisher-id, ifmap-timestamp and ifmap-timestamp-fraction.
	 * The timestamps are stored with UTC timezone. If a timestamp with another timezone is
	 * passed into the constructor, it will be converted to UTC. The IF-MAP timestamp fraction
	 * element will be calculated from the provided timestamp.
	 *
	 * @param ifMapPublisherId IF-MAP client publisher ID
	 * @param ifMapTimestamp Timestamp for the metadata
	 * @param hopCount Hop count
	 */
	public CBORHopCount(String ifMapPublisherId, ZonedDateTime ifMapTimestamp, Integer hopCount) {
		super(SimuNamespaces.SIMU, XML_NAME, ifMapPublisherId, ifMapTimestamp);

		if(hopCount == null) {
			throw new IllegalArgumentException("Hop count must not be null");
		}
		else if(hopCount < 1) {
			throw new IllegalArgumentException("Hop count must be greater than 0");
		}

		this.hopCount = hopCount;
		
		this.nestedElementProvideParentBuilder = true;
	}
	
	
	/**
	 * Create a new hop-count metadata.
	 * This constructor should be used for metadata that is sent from the CLIENT to the SERVER. It does not include
	 * the attributes ifmap-publisher-id, ifmap-timestamp and ifmap-timestamp-fraction.
	 * The timestamps are stored with UTC timezone. If a timestamp with another timezone is
	 * passed into the constructor, it will be converted to UTC. The IF-MAP timestamp fraction
	 * element will be calculated from the provided timestamp.
	 *
	 * @param hopCount Hop count
	 */
	public CBORHopCount(Integer hopCount) {
		super(SimuNamespaces.SIMU, XML_NAME);

		if(hopCount == null) {
			throw new IllegalArgumentException("Hop count must not be null");
		}
		else if(hopCount < 1) {
			throw new IllegalArgumentException("Hop count must be greater than 0");
		}

		this.hopCount = hopCount;
		
		this.nestedElementProvideParentBuilder = true;
	}
	
	
	@Override
	protected void serializeNestedElements(final ArrayBuilder<?> builder) throws CBORSerializationException {
		try {
			DataItem valueDi = new UnsignedInteger(this.hopCount);
			
			builder.add(valueDi);
		}
		catch(RuntimeException ex) {
			throw new CBORSerializationException("Error during serialization, see nested exception for details", ex);
		}
	}
}
