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
import co.nstant.in.cbor.model.DoublePrecisionFloat;
import de.decoit.simu.cbor.ifmap.exception.CBORSerializationException;
import de.decoit.simu.cbor.ifmap.metadata.singlevalue.AbstractSingleValueMetadata;
import de.decoit.simu.cbor.ifmap.simu.util.SimuNamespaces;
import java.time.ZonedDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;



/**
 * Java representation of the latency metadata from the SIMU namespace.
 *
 * @author Thomas Rix (rix@decoit.de)
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Slf4j
public final class CBORLatency extends AbstractSingleValueMetadata {
	public static final String XML_NAME = "latency";

	@Getter
	private final Double latency;


	/**
	 * Create a new latency metadata.
	 * This constructor should be used for metadata that is sent from the SERVER to the CLIENT. It includes
	 * the attributes ifmap-publisher-id, ifmap-timestamp and ifmap-timestamp-fraction.
	 * The timestamps are stored with UTC timezone. If a timestamp with another timezone is
	 * passed into the constructor, it will be converted to UTC. The IF-MAP timestamp fraction
	 * element will be calculated from the provided timestamp.
	 *
	 * @param ifMapPublisherId IF-MAP client publisher ID
	 * @param ifMapTimestamp Timestamp for the metadata
	 * @param latency Latency in ms
	 */
	public CBORLatency(String ifMapPublisherId, ZonedDateTime ifMapTimestamp, double latency) {
		super(SimuNamespaces.SIMU, XML_NAME, ifMapPublisherId, ifMapTimestamp);

		if(latency < 0) {
			throw new IllegalArgumentException("Latency must not be less than 0");
		}

		this.latency = latency;
		
		this.nestedElementProvideParentBuilder = true;
	}
	
	
	/**
	 * Create a new latency metadata.
	 * This constructor should be used for metadata that is sent from the CLIENT to the SERVER. It does not include
	 * the attributes ifmap-publisher-id, ifmap-timestamp and ifmap-timestamp-fraction.
	 * The timestamps are stored with UTC timezone. If a timestamp with another timezone is
	 * passed into the constructor, it will be converted to UTC. The IF-MAP timestamp fraction
	 * element will be calculated from the provided timestamp.
	 *
	 * @param latency Latency in ms
	 */
	public CBORLatency(double latency) {
		super(SimuNamespaces.SIMU, XML_NAME);

		if(latency < 0) {
			throw new IllegalArgumentException("Latency must not be less than 0");
		}

		this.latency = latency;
		
		this.nestedElementProvideParentBuilder = true;
	}
	
	
	@Override
	protected void serializeNestedElements(final ArrayBuilder<?> builder) throws CBORSerializationException {
		try {
			DataItem valueDi = new DoublePrecisionFloat(this.latency);
			
			builder.add(valueDi);
		}
		catch(RuntimeException ex) {
			throw new CBORSerializationException("Error during serialization, see nested exception for details", ex);
		}
	}
}
