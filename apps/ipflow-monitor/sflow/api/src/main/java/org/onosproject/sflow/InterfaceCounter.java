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
import static com.google.common.base.Preconditions.checkState;
import java.nio.ByteBuffer;
import org.onlab.packet.BasePacket;
import org.onlab.packet.Deserializer;

/**
 * Represents interface counters for network interfaces.
 */
public final class InterfaceCounter extends BasePacket {
    private int ifIndex;
    private int ifType;
    private long ifSpeed;
    private int ifDirection;
    private int ifStatus;
    private long ifInOctets;
    private int ifInUcastPkts;
    private int ifInMulticastPkts;
    private int ifInBroadcastPkts;
    private int ifInDiscards;
    private int ifInErrors;
    private int ifInUnknownProtos;
    private long ifOutOctets;
    private int ifOutUcastPkts;
    private int ifOutMulticastPkts;
    private int ifOutBroadcastPkts;
    private int ifOutDiscards;
    private int ifOutErrors;
    private int ifPromiscuousMode;

    private InterfaceCounter(Builder builder) {
        this.ifIndex = builder.ifIndex;
        this.ifType = builder.ifType;
        this.ifSpeed = builder.ifSpeed;
        this.ifDirection = builder.ifDirection;
        this.ifStatus = builder.ifStatus;
        this.ifInOctets = builder.ifInOctets;
        this.ifInUcastPkts = builder.ifInUcastPkts;
        this.ifInMulticastPkts = builder.ifInMulticastPkts;
        this.ifInBroadcastPkts = builder.ifInBroadcastPkts;
        this.ifInDiscards = builder.ifInDiscards;
        this.ifInErrors = builder.ifInErrors;
        this.ifInUnknownProtos = builder.ifInUnknownProtos;
        this.ifOutOctets = builder.ifOutOctets;
        this.ifOutUcastPkts = builder.ifOutUcastPkts;
        this.ifOutMulticastPkts = builder.ifOutMulticastPkts;
        this.ifOutBroadcastPkts = builder.ifOutBroadcastPkts;
        this.ifOutDiscards = builder.ifOutDiscards;
        this.ifOutErrors = builder.ifOutErrors;
        this.ifPromiscuousMode = builder.ifPromiscuousMode;
    }

    /**
     * Get interface index.
     *
     * @return interface index.
     */
    public long getIfIndex() {
        return ifIndex;
    }

    /**
     * Get interface type.
     *
     * @return interface type.
     */
    public int getIfType() {
        return ifType;
    }

    /**
     * Get interface speed.
     *
     * @return interface speed.
     */
    public long getIfSpeed() {
        return ifSpeed;
    }

    /**
     * Get interface flow direction.
     *
     * @return interface flow direction.
     */
    public int getIfDirection() {
        return ifDirection;
    }

    /**
     * Get interface status.
     *
     * @return interface status.
     */
    public int getIfStatus() {
        return ifStatus;
    }

    /**
     * Get interface ingress octets.
     *
     * @return interface ingress octets.
     */
    public long getIfInOctets() {
        return ifInOctets;
    }

    /**
     * Get interface ingress packets.
     *
     * @return interface ingress unicast packets.
     */
    public int getIfInUcastPkts() {
        return ifInUcastPkts;
    }

    /**
     * Get interface ingress multicast packets.
     *
     * @return interface ingress multicast packets.
     */
    public int getIfInMulticastPkts() {
        return ifInMulticastPkts;
    }

    /**
     * Get interface ingress broadcast packets.
     *
     * @return interface ingress broadcast packets.
     */
    public int getIfInBroadcastPkts() {
        return ifInBroadcastPkts;
    }

    /**
     * Get interface ingress discard packets.
     *
     * @return interface ingress discard packets.
     */
    public int getIfInDiscards() {
        return ifInDiscards;
    }

    /**
     * Get interface ingress error packets.
     *
     * @return interface ingress error packets.
     */
    public int getIfInErrors() {
        return ifInErrors;
    }

    /**
     * Get interface ingress unknow protocols.
     *
     * @return interface ingress unknow protocols.
     */
    public int getIfInUnknownProtos() {
        return ifInUnknownProtos;
    }

    /**
     * Get interface egress octets.
     *
     * @return interface egress octets.
     */
    public long getIfOutOctets() {
        return ifOutOctets;
    }

    /**
     * Get interface egress unicast packets.
     *
     * @return interface egress unicast packets.
     */
    public int getIfOutUcastPkts() {
        return ifOutUcastPkts;
    }

    /**
     * Get interface egress multicast packets.
     *
     * @return interface egress multicast packets.
     */
    public int getIfOutMulticastPkts() {
        return ifOutMulticastPkts;
    }

    /**
     * Get interface egress broadcast packets.
     *
     * @return interface egress broadcast packets.
     */
    public int getIfOutBroadcastPkts() {
        return ifOutBroadcastPkts;
    }

    /**
     * Get interface egress discard packets.
     *
     * @return interface egress discard packets.
     */
    public int getIfOutDiscards() {
        return ifOutDiscards;
    }

    /**
     * Get interface egress error packets.
     *
     * @return interface egress error packets.
     */
    public int getIfOutErrors() {
        return ifOutErrors;
    }

    /**
     * Get interface promiscuous mode.
     *
     * @return interface promiscuous mode.
     */
    public int getIfPromiscuousMode() {
        return ifPromiscuousMode;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + this.ifIndex;
        hash = 59 * hash + this.ifType;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final InterfaceCounter other = (InterfaceCounter) obj;
        if (this.ifIndex != other.ifIndex) {
            return false;
        }
        if (this.ifType != other.ifType) {
            return false;
        }
        return this.ifSpeed != other.ifSpeed;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .add("ifIndex", ifIndex)
                .add("ifType", ifType)
                .add("ifSpeed", ifSpeed)
                .add("ifDirection", ifDirection)
                .add("ifStatus", ifStatus)
                .add("ifInOctets", ifInOctets)
                .add("ifInUcastPkts", ifInUcastPkts)
                .add("ifInMulticastPkts", ifInMulticastPkts)
                .add("ifInBroadcastPkts", ifInBroadcastPkts)
                .add("ifInDiscards", ifInDiscards)
                .add("ifInErrors", ifInErrors)
                .add("ifInUnknownProtos", ifInUnknownProtos)
                .add("ifOutOctets", ifOutOctets)
                .add("ifOutUcastPkts", ifOutUcastPkts)
                .add("ifOutMulticastPkts", ifOutMulticastPkts)
                .add("ifOutBroadcastPkts", ifOutBroadcastPkts)
                .add("ifOutDiscards", ifOutDiscards)
                .add("ifOutErrors", ifOutErrors)
                .add("ifPromiscuousMode", ifPromiscuousMode)
                .toString();
    }

    /**
     * Deserializer function for sFlow packets.
     *
     * @return deserializer function
     */
    public static Deserializer<InterfaceCounter> deserializer() {
        return (data, offset, length) -> {
            ByteBuffer bb = ByteBuffer.wrap(data, offset, length);
            Builder builder = new Builder();
            return builder.ifIndex(bb.getShort())
                    .ifType(bb.getShort())
                    .ifSpeed(bb.getInt())
                    .ifStatus(bb.getShort())
                    .ifInOctets(bb.getLong())
                    .ifInUcastPkts(bb.getInt())
                    .ifInMulticastPkts(bb.getInt())
                    .ifInBroadcastPkts(bb.getInt())
                    .ifInDiscards(bb.getInt())
                    .ifInErrors(bb.getInt())
                    .ifInUnknownProtos(bb.getInt())
                    .ifOutOctets(bb.getLong())
                    .ifOutUcastPkts(bb.getInt())
                    .ifOutMulticastPkts(bb.getInt())
                    .ifOutBroadcastPkts(bb.getInt())
                    .ifOutDiscards(bb.getInt())
                    .ifOutErrors(bb.getInt())
                    .ifPromiscuousMode(bb.getInt())
                    .build();
        };
    }

    @Override
    public byte[] serialize() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Builder pattern to create an instance of InterfaceCounter.
     */
    private static class Builder {

        private int ifIndex;
        private int ifType;
        private long ifSpeed;
        private int ifDirection;
        private int ifStatus;
        private long ifInOctets;
        private int ifInUcastPkts;
        private int ifInMulticastPkts;
        private int ifInBroadcastPkts;
        private int ifInDiscards;
        private int ifInErrors;
        private int ifInUnknownProtos;
        private long ifOutOctets;
        private int ifOutUcastPkts;
        private int ifOutMulticastPkts;
        private int ifOutBroadcastPkts;
        private int ifOutDiscards;
        private int ifOutErrors;
        private int ifPromiscuousMode;


        /**
         * Sets the interface index.
         *
         * @param ifIndex the interface index.
         * @return this builder instance.
         */
        public Builder ifIndex(int ifIndex) {
            this.ifIndex = ifIndex;
            return this;
        }

        /**
         * Sets the interface type.
         *
         * @param ifType the interface type.
         * @return this builder instance.
         */
        public Builder ifType(int ifType) {
            this.ifType = ifType;
            return this;
        }

        /**
         * Sets the interface speed.
         *
         * @param ifSpeed the interface speed.
         * @return this builder instance.
         */
        public Builder ifSpeed(long ifSpeed) {
            this.ifSpeed = ifSpeed;
            return this;
        }

        /**
         * Sets interface flow direction.
         *
         * @param ifDirection interface flow direction.
         * @return this builder instance.
         */
        public Builder ifDirection(int ifDirection) {
            this.ifDirection = ifDirection;
            return this;
        }

        /**
         * Sets interface status.
         *
         * @param ifStatus interface status.
         * @return this builder instance.
         */
        public Builder ifStatus(int ifStatus) {
            this.ifStatus = ifStatus;
            return this;
        }

        /**
         * Sets the count of ingress octets.
         *
         * @param ifInOctets the count of ingress octets.
         * @return this builder instance.
         */
        public Builder ifInOctets(long ifInOctets) {
            this.ifInOctets = ifInOctets;
            return this;
        }

        /**
         * Sets the count of ingress unicast packets.
         *
         * @param ifInUcastPkts the count of ingress packets.
         * @return this builder instance.
         */
        public Builder ifInUcastPkts(int ifInUcastPkts) {
            this.ifInUcastPkts = ifInUcastPkts;
            return this;
        }

        /**
         * Sets the count of ingress multicast packets.
         *
         * @param ifInMulticastPkts the count of ingress multicast packets.
         * @return this builder instance.
         */
        public Builder ifInMulticastPkts(int ifInMulticastPkts) {
            this.ifInMulticastPkts = ifInMulticastPkts;
            return this;
        }

        /**
         * Sets the count of ingress broadcast packets.
         *
         * @param ifInBroadcastPkts the count of ingress broadcast packets.
         * @return this builder instance.
         */
        public Builder ifInBroadcastPkts(int ifInBroadcastPkts) {
            this.ifInBroadcastPkts = ifInBroadcastPkts;
            return this;
        }

        /**
         * Sets the count of ingress discards.
         *
         * @param ifInDiscards the count of ingress discards.
         * @return this builder instance.
         */
        public Builder ifInDiscards(int ifInDiscards) {
            this.ifInDiscards = ifInDiscards;
            return this;
        }

        /**
         * Sets the count of ingress errors.
         *
         * @param ifInErrors the count of ingress errors.
         * @return this builder instance.
         */
        public Builder ifInErrors(int ifInErrors) {
            this.ifInErrors = ifInErrors;
            return this;
        }

        /**
         * Sets the count of unknown protocol.
         *
         * @param ifInUnknownProtos the count of unknown protocol.
         * @return this builder instance.
         */
        public Builder ifInUnknownProtos(int ifInUnknownProtos) {
            this.ifInUnknownProtos = ifInUnknownProtos;
            return this;
        }

        /**
         * Sets the count of egress octets.
         *
         * @param ifOutOctets the count of egress octets.
         * @return this builder instance.
         */
        public Builder ifOutOctets(long ifOutOctets) {
            this.ifOutOctets = ifOutOctets;
            return this;
        }

        /**
         * Sets the count of egress unicast packets.
         *
         * @param ifOutUcastPkts the count of egress packets.
         * @return this builder instance.
         */
        public Builder ifOutUcastPkts(int ifOutUcastPkts) {
            this.ifOutUcastPkts = ifOutUcastPkts;
            return this;
        }

        /**
         * Sets the count of egress multicast packets.
         *
         * @param ifOutMulticastPkts the count of egress multicast packets.
         * @return this builder instance.
         */
        public Builder ifOutMulticastPkts(int ifOutMulticastPkts) {
            this.ifOutMulticastPkts = ifOutMulticastPkts;
            return this;
        }

        /**
         * Sets the count of egress broadcast packets.
         *
         * @param ifOutBroadcastPkts the count of egress broadcast packets.
         * @return this builder instance.
         */
        public Builder ifOutBroadcastPkts(int ifOutBroadcastPkts) {
            this.ifOutBroadcastPkts = ifOutBroadcastPkts;
            return this;
        }

        /**
         * Sets the count of egress discards.
         *
         * @param ifOutDiscards the count of egress discards.
         * @return this builder instance.
         */
        public Builder ifOutDiscards(int ifOutDiscards) {
            this.ifOutDiscards = ifOutDiscards;
            return this;
        }

        /**
         * Sets the count of egress errors.
         *
         * @param ifOutErrors the count of egress errors.
         * @return this builder instance.
         */
        public Builder ifOutErrors(int ifOutErrors) {
            this.ifOutErrors = ifOutErrors;
            return this;
        }

        /**
         * Sets the interface promiscuous mode.
         *
         * @param ifPromiscuousMode the interface promiscuous mode.
         * @return this builder instance.
         */
        public Builder ifPromiscuousMode(int ifPromiscuousMode) {
            this.ifPromiscuousMode = ifPromiscuousMode;
            return this;
        }

        /**
         * Checks arguments for sFlow sample flow.
         */
        private void checkArguments() {
            checkState(ifIndex != 0, "Invalid interface index.");
            checkState(ifType != 0, "Invalid interface type.");
            checkState(ifSpeed != 0, "Invalid interface speed.");
        }

        /**
         * Builds an instance of InterfaceCounter based on the configured parameters.
         *
         * @return an instance of InterfaceCounter.
         */
        public InterfaceCounter build() {
            checkArguments();
            return new InterfaceCounter(this);
        }
    }
}
