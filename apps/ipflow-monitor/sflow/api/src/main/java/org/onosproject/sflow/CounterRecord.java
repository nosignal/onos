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

import com.google.common.base.MoreObjects;

import static com.google.common.base.Preconditions.checkState;

/**
 * The sFlow interface counter record.
 */
public final class CounterRecord extends BasePacket {

    private Type type;

    private int length;

    private CounterPacket counterPacket;


    private CounterRecord(Builder builder) {
        this.type = builder.type;
        this.length = builder.length;
        this.counterPacket = builder.counterPacket;
    }

    /**
     * Get sFlow counter type.
     *
     * @return counter type.
     */
    public Type getType() {
        return type;
    }

    /**
     * Get sFlow counter record lenght.
     *
     * @return counter record length.
     */
    public int getLength() {
        return length;
    }

    /**
     * Get sFlow counter packet.
     *
     * @return counter packet.
     */
    public CounterPacket getCounterPacket() {
        return counterPacket;
    }


    /**
     * Interface counter type.
     * Counter type : Generic, Ethernet, Token ring, Fddi, Vg, Wan, Vlan.
     */
    public enum Type {

        GENERIC(1, InterfaceCounter.deserializer()),
        ETHERNET(2, EthernetCounter.deserializer()),
        TOKENRING(3, TokenRingCounter.deserializer()),
        FDDI(4, InterfaceCounter.deserializer()),
        VG(5, VgCounter.deserializer()),
        WAN(6, InterfaceCounter.deserializer()),
        VLAN(7, VlanCounter.deserializer());


        private final int counterType;
        private final Deserializer deserializer;

        Type(int counterType, Deserializer deserializer) {
            this.counterType = counterType;
            this.deserializer = deserializer;
        }

        private static Map<Integer, Type> parser = new ConcurrentHashMap<>();

        static {
            Arrays.stream(Type.values()).forEach(type -> parser.put(type.counterType, type));
        }

        public static Type getType(int ctype) throws DeserializationException {
            if ((ctype < 1) || (ctype > 7)) {
                throw new DeserializationException("Invalid counter type");
            }
            return Optional.of(ctype)
                    .filter(id -> parser.containsKey(id))
                    .map(id -> parser.get(id))
                    .orElse(GENERIC);
        }

        public Deserializer getDecoder() {
            return this.deserializer;
        }

    }

    /**
     * Data deserializer function for flow interface counter record.
     *
     * @return data deserializer function
     */
    public static Deserializer<CounterSample> deserializer() {
        return (data, offset, length) -> {
            return null;
        };
    }

    @Override
    public byte[] serialize() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .add("type", type)
                .add("length", length)
                .add("counterPacket", counterPacket)
                .toString();
    }

    /**
     * Builder for sFlow interface counter record.
     */
    private static class Builder {

        private Type type;

        private int length;

        private CounterPacket counterPacket;

        /**
         * Setter sFlow counter record length.
         *
         * @param length of the counter record.
         * @return this class builder.
         */
        public Builder length(int length) {
            this.length = length;
            return this;
        }

        /**
         * Setter sFlow counter type.
         *
         * @param type sFlow counter type.
         * @return this class builder.
         */
        public Builder type(Type type) {
            this.type = type;
            return this;
        }

        /**
         * Setter sFlow flow counter packet.
         *
         * @param sequenceNumber flow counter packet.
         * @return this class builder.
         */
        public Builder counterPacket(CounterPacket counterPacket) {
            this.counterPacket = counterPacket;
            return this;
        }

        /**
         * Checks arguments for sFlow sample interface counter.
         */
        private void checkArguments() {
            checkState(type != null, "Invalid counter type.");
            checkState(length != 0, "Invalid counter record length.");
        }

        /**
         * Builds sFlow interface counter record.
         *
         * @return interface counter record.
         */
        public CounterRecord build() {
            checkArguments();
            return new CounterRecord(this);
        }
    }
}