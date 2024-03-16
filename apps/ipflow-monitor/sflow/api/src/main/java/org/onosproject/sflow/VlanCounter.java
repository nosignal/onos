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

import com.google.common.base.MoreObjects;
import java.nio.ByteBuffer;
import org.onlab.packet.BasePacket;
import org.onlab.packet.Deserializer;

import java.util.function.BiPredicate;

/**
 * Represents VLAN counters for network interfaces.
 */
public final class VlanCounter extends BasePacket {

    public static final int VLAN_COUNTER_LENGTH = 28;

    private int vlanId;
    private long octets;
    private int ucastPkts;
    private int multicastPkts;
    private int broadcastPkts;
    private int discards;

    private VlanCounter(Builder builder) {
        this.vlanId = builder.vlanId;
        this.octets = builder.octets;
        this.ucastPkts = builder.ucastPkts;
        this.multicastPkts = builder.multicastPkts;
        this.broadcastPkts = builder.broadcastPkts;
        this.discards = builder.discards;
    }


    /**
     * Gets the VLAN ID.
     *
     * @return the VLAN ID
     */
    public int getVlanId() {
        return vlanId;
    }

    /**
     * Gets the count of octets.
     *
     * @return the count of octets
     */
    public long getOctets() {
        return octets;
    }

    /**
     * Gets the count of unicast packets.
     *
     * @return the count of unicast packets
     */
    public int getUcastPkts() {
        return ucastPkts;
    }

    /**
     * Gets the count of multicast packets.
     *
     * @return the count of multicast packets
     */
    public int getMulticastPkts() {
        return multicastPkts;
    }

    /**
     * Gets the count of broadcast packets.
     *
     * @return the count of broadcast packets
     */
    public int getBroadcastPkts() {
        return broadcastPkts;
    }

    /**
     * Gets the count of discards.
     *
     * @return the count of discards
     */
    public int getDiscards() {
        return discards;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .add("vlanId", vlanId)
                .add("octets", octets)
                .add("ucastPkts", ucastPkts)
                .add("multicastPkts", multicastPkts)
                .add("broadcastPkts", broadcastPkts)
                .add("discards", discards)
                .toString();
    }

    /**
     * Deserializer function for sFlow interface vlan counter.
     *
     * @return deserializer function
     */
    public static Deserializer<VlanCounter> deserializer() {
        return (data, offset, length) -> {

            BiPredicate<ByteBuffer, Integer> isValidBuffer = (b, l)
                    -> b.hasRemaining() && b.remaining() >= l;

            ByteBuffer bb = ByteBuffer.wrap(data, offset, length);
            if (!isValidBuffer.test(bb, VLAN_COUNTER_LENGTH)) {
                throw new IllegalStateException("Invalid interface vlan counter buffer size.");
            }

            Builder builder = new Builder();
            return builder.vlanId(bb.getInt())
                    .octets(bb.getLong())
                    .ucastPkts(bb.getInt())
                    .multicastPkts(bb.getInt())
                    .broadcastPkts(bb.getInt())
                    .discards(bb.getInt())
                    .build();
        };
    }

    @Override
    public byte[] serialize() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Builder pattern to create an instance of VlanCounter.
     */
    public static class Builder {
        private int vlanId;
        private long octets;
        private int ucastPkts;
        private int multicastPkts;
        private int broadcastPkts;
        private int discards;


        /**
         * Sets the VLAN ID.
         *
         * @param vlanId the VLAN ID to set
         * @return this builder instance
         */
        public Builder vlanId(int vlanId) {
            this.vlanId = vlanId;
            return this;
        }

        /**
         * Sets the count of octets.
         *
         * @param octets the count of octets to set
         * @return this builder instance
         */
        public Builder octets(long octets) {
            this.octets = octets;
            return this;
        }

        /**
         * Sets the count of unicast packets.
         *
         * @param ucastPkts the count of unicast packets
         * @return this builder instance
         */
        public Builder ucastPkts(int ucastPkts) {
            this.ucastPkts = ucastPkts;
            return this;
        }

        /**
         * Sets the count of multicast packets.
         *
         * @param multicastPkts the count of multicast packets
         * @return this builder instance
         */
        public Builder multicastPkts(int multicastPkts) {
            this.multicastPkts = multicastPkts;
            return this;
        }

        /**
         * Sets the count of broadcast packets.
         *
         * @param broadcastPkts the count of broadcast packets
         * @return this builder instance
         */
        public Builder broadcastPkts(int broadcastPkts) {
            this.broadcastPkts = broadcastPkts;
            return this;
        }

        /**
         * Sets the count of discards.
         *
         * @param discards the count of discards
         * @return this builder instance
         */
        public Builder discards(int discards) {
            this.discards = discards;
            return this;
        }

        /**
         * Builds an instance of VlanCounter based on the configured parameters.
         *
         * @return an instance of VlanCounter
         */
        public VlanCounter build() {
            return new VlanCounter(this);
        }
    }
}
