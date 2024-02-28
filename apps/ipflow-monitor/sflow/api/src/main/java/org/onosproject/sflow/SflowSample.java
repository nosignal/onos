/*
 * Copyright 2024-present Open Networking Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onosproject.sflow;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.onlab.packet.BasePacket;
import org.onlab.packet.DeserializationException;
import org.onlab.packet.Deserializer;

/**
 * The sFlow Agent uses two forms of sampling.
 * statistical packet-based sampling of switched flows,
 * and time-based sampling of network interface statistics..
 * Ref : https://datatracker.ietf.org/doc/html/rfc3176
 */
public abstract class SflowSample extends BasePacket {

    int enterprise;

    Type type;

    int length;

    int sequenceNumber;

    int sourceId;

    int sourceIndex;

    int numberOfRecords;

    /**
     * Get sFlow agent enterprise id.
     *
     * @return agent enterprise.
     */
    public int getEnterprise() {
        return enterprise;
    }

    /**
     * Get sFlow sample type.
     *
     * @return sample type.
     */
    public Type getType() {
        return type;
    }

    /**
     * Get sFlow sample length.
     *
     * @return sample length.
     */
    public int getLength() {
        return length;
    }

    /**
     * Get sFlow sample sequence number.
     *
     * @return sequence number.
     */
    public int getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Get sFlow source id.
     *
     * @return source id.
     */
    public int getSourceId() {
        return sourceId;
    }

    /**
     * Get sFlow source index.
     *
     * @return source index.
     */
    public int getSourceIndex() {
        return sourceIndex;
    }

    /**
     * Get total number of sample records.
     *
     * @return total number of sample records.
     */
    public int getNumberOfRecords() {
        return numberOfRecords;
    }

    /**
     * Sample type.
     * Samples: Flow sample, Counter sample.
     */
    public enum Type {

        FLOW_DATA(1, FlowSample.deserializer()),
        COUNTER_DATA(2, CounterSample.deserializer());


        private final int sampleType;
        private final Deserializer deserializer;

        Type(int sampleType, Deserializer deserializer) {
            this.sampleType = sampleType;
            this.deserializer = deserializer;
        }

        private static Map<Integer, Type> parser = new ConcurrentHashMap<>();

        static {
            Arrays.stream(Type.values()).forEach(type -> parser.put(type.sampleType, type));
        }

        public static Type getType(int sampleType) throws DeserializationException {
            if ((sampleType < 1) || (sampleType > 2)) {
                throw new DeserializationException("Invalid sample type");
            }
            return Optional.of(sampleType)
                    .filter(id -> parser.containsKey(id))
                    .map(id -> parser.get(id))
                    .orElse(FLOW_DATA);
        }

        public Deserializer getDecoder() {
            return this.deserializer;
        }

    }

    @Override
    public byte[] serialize() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
