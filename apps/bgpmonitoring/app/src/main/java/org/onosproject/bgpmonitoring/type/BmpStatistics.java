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

package org.onosproject.bgpmonitoring.type;


import com.google.common.base.MoreObjects;

import java.nio.ByteBuffer;

import org.onlab.packet.BasePacket;
import org.onlab.packet.Deserializer;

import java.util.function.BiPredicate;

import org.onosproject.bgpmonitoring.BmpStats;
import org.onosproject.bgpmonitoring.StatsType;
import org.onosproject.bgpmonitoring.BmpParseException;

import static com.google.common.base.Preconditions.checkState;
import static org.onosproject.bgpmonitoring.StatsType.PREFIXES_REJECTED;
import static org.onosproject.bgpmonitoring.StatsType.DUPLICATE_PREFIX;
import static org.onosproject.bgpmonitoring.StatsType.DUPLICATE_WITHDRAW;
import static org.onosproject.bgpmonitoring.StatsType.CLUSTER_LIST;
import static org.onosproject.bgpmonitoring.StatsType.AS_PATH;
import static org.onosproject.bgpmonitoring.StatsType.ORIGINATOR_ID;
import static org.onosproject.bgpmonitoring.StatsType.AS_CONFED;
import static org.onosproject.bgpmonitoring.StatsType.ADJ_RIB_IN;
import static org.onosproject.bgpmonitoring.StatsType.LOC_RIB;
import static org.onosproject.bgpmonitoring.StatsType.ADJ_RIB_IN_AFI_SAFI;
import static org.onosproject.bgpmonitoring.StatsType.LOC_RIB_AFI_SAFI;
import static org.onosproject.bgpmonitoring.StatsType.UPDATES_SUBJECTED_WITHDRAW;
import static org.onosproject.bgpmonitoring.StatsType.PREFIXES_SUBJECTED_WITHDRAW;
import static org.onosproject.bgpmonitoring.StatsType.DUPLICATE_UPDATE_MESSAGES;
import static org.onosproject.bgpmonitoring.StatsType.JNX_ADJ_RIB_IN;

public final class BmpStatistics extends BasePacket implements BmpStats {

    public static final int STATISTICS_HEADER_MIN_LENGTH = 4;

    private StatsType statsType;

    private int statLen;

    private long value;

    private BmpStatistics(Builder builder) {
        this.statsType = builder.statsType;
        this.statLen = builder.statLen;
        this.value = builder.value;
    }

    /**
     * Returns BMP stats type.
     *
     * @return BMP stats type
     */
    public StatsType getStatsType() {
        return statsType;
    }

    /**
     * Returns BMP stats length.
     *
     * @return BMP stats length
     */
    public int getStatLen() {
        return statLen;
    }

    /**
     * Returns BMP stats value.
     *
     * @return BMP stats value
     */
    public long getValue() {
        return value;
    }

    /**
     * Data deserializer function for BMP statistics.
     *
     * @return data deserializer function
     */
    public static Deserializer<BmpStatistics> deserializer() {
        return (data, offset, length) -> {
            BiPredicate<ByteBuffer, Integer> isValidBuffer = (b, l)
                    -> b.hasRemaining() && b.remaining() >= l;

            ByteBuffer bb = ByteBuffer.wrap(data, offset, length);
            if (!isValidBuffer.test(bb, STATISTICS_HEADER_MIN_LENGTH)) {
                throw new BmpParseException("Invalid BMP statistics buffer size.");
            }

            return new Builder().statsType(StatsType.getType((int) bb.getShort()))
                    .statLen((int) bb.getShort())
                    .value(bb)
                    .build();
        };
    }

    @Override
    public byte[] serialize() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toString() {

        return MoreObjects.toStringHelper(getClass())
                .add("statsType", statsType)
                .add("statLen", statLen)
                .add("value", value)
                .toString();
    }

    /**
     * Builder for BMP statistics.
     */
    private static class Builder {

        private StatsType statsType;

        private int statLen;

        private long value;

        /**
         * Setter type of bmp stats.
         *
         * @param statsType type of bmp stats.
         * @return this class builder.
         */
        public Builder statsType(StatsType statsType) {
            this.statsType = statsType;
            return this;
        }

        /**
         * Setter length of bmp stats.
         *
         * @param statLen length of bmp stats.
         * @return this class builder.
         */
        public Builder statLen(int statLen) {
            this.statLen = statLen;
            return this;
        }

        /**
         * Setter value for bmp stats.
         *
         * @param bb byte buffer.
         * @return this class builder.
         */
        public Builder value(ByteBuffer bb) {
            switch (statsType) {
                case PREFIXES_REJECTED:
                case DUPLICATE_PREFIX:
                case DUPLICATE_WITHDRAW:
                case CLUSTER_LIST:
                case AS_PATH:
                case ORIGINATOR_ID:
                case AS_CONFED:
                case UPDATES_SUBJECTED_WITHDRAW:
                case PREFIXES_SUBJECTED_WITHDRAW:
                case DUPLICATE_UPDATE_MESSAGES:
                case JNX_ADJ_RIB_IN:
                    this.value = bb.getInt();
                    break;
                case ADJ_RIB_IN:
                case LOC_RIB:
                case ADJ_RIB_IN_AFI_SAFI:
                case LOC_RIB_AFI_SAFI:
                    this.value = bb.getLong();
                    break;
                default:
                    this.value = bb.getInt();

            }
            return this;
        }

        /**
         * Checks arguments for BMP statistics.
         */
        private void checkArguments() {
            checkState(statsType != null, "Invalid bmp statistics type.");
        }

        /**
         * Builds BMP statistics counter.
         *
         * @return BMP statistics counter.
         */
        public BmpStatistics build() {
            checkArguments();
            return new BmpStatistics(this);
        }
    }
}