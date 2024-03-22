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
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.function.BiPredicate;
import java.nio.ByteBuffer;

import org.onosproject.bgpmonitoring.StatsMessage;
import org.onosproject.bgpmonitoring.BmpStats;
import org.onosproject.bgpmonitoring.PerPeer;
import org.onosproject.bgpmonitoring.BmpParseException;
import org.onlab.packet.DeserializationException;
import org.onlab.packet.Deserializer;

import static com.google.common.base.Preconditions.checkState;

/**
 * An ongoing dump of statistics that can be used
 * by the monitoring station as a high-level indication of the
 * activity going on in the router.
 * <p>
 * These messages contain information that could be used by the
 * monitoring station to observe interesting events that occur on the
 * router.
 * <p>
 * Transmission of SR messages could be timer triggered or event driven
 * (for example, when a significant event occurs or a threshold is
 * reached).  This specification does not impose any timing restrictions
 * on when and on what event these reports have to be transmitted.  It
 * is left to the implementation to determine transmission timings --
 * however, configuration control should be provided of the timer and/or
 * threshold values.  This document only specifies the form and content
 * of SR messages.
 * <p>
 * Following the common BMP header and per-peer header is a 4-byte field
 * that indicates the number of counters in the stats message where each
 * counter is encoded as a TLV.
 * <p>
 * Stat Type = 1: (32-bit Counter) Number of (known) duplicate prefix
 * advertisements.
 * <p>
 * Stat Type = 2: (32-bit Counter) Number of (known) duplicate
 * withdraws.
 * <p>
 * Stat Type = 3: (32-bit Counter) Number of updates invalidated due
 * to CLUSTER_LIST loop.
 * <p>
 * Stat Type = 4: (32-bit Counter) Number of updates invalidated due
 * to AS_PATH loop.
 * <p>
 * Stat Type = 5: (32-bit Counter) Number of updates invalidated due
 * to ORIGINATOR_ID.
 * <p>
 * Stat Type = 6: (32-bit Counter) Number of updates invalidated due
 * to AS_CONFED loop.
 * <p>
 * Stat Type = 7: (64-bit Gauge) Number of routes in Adj-RIBs-In.
 * <p>
 * Stat Type = 8: (64-bit Gauge) Number of routes in Loc-RIB.
 * <p>
 * Stat Type = 9: Number of routes in per-AFI/SAFI Adj-RIB-In.  The
 * value is structured as: 2-byte Address Family Identifier (AFI),
 * 1-byte Subsequent Address Family Identifier (SAFI), followed by a
 * 64-bit Gauge.
 * <p>
 * Stat Type = 10: Number of routes in per-AFI/SAFI Loc-RIB.  The
 * value is structured as: 2-byte AFI, 1-byte SAFI, followed by a
 * 64-bit Gauge.
 * <p>
 * Stat Type = 11: (32-bit Counter) Number of updates subjected to
 * treat-as-withdraw treatment [RFC7606].
 * <p>
 * Stat Type = 12: (32-bit Counter) Number of prefixes subjected to
 * treat-as-withdraw treatment [RFC7606].
 * <p>
 * Stat Type = 13: (32-bit Counter) Number of duplicate update
 * messages received.
 * <p>
 * Although the current specification only specifies 4-byte counters and
 * 8-byte gauges as "Stat Data", this does not preclude future versions
 * from incorporating more complex TLV-type "Stat Data" (for example,
 * one that can carry prefix-specific data).  SR messages are optional.
 * However, if an SR message is transmitted, at least one statistic MUST
 * be carried in it.
 */
public final class BmpStatsReport extends StatsMessage {


/*
      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                        Stats Count                            |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

     Each counter is encoded as follows:

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |         Stat Type             |          Stat Len             |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                        Stat Data                              |
     ~                                                               ~
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 */

    private PerPeer perPeer;
    private int statsCount;
    private List<BmpStats> stats;

    private BmpStatsReport(Builder builder) {
        this.perPeer = builder.perPeer;
        this.statsCount = builder.statsCount;
        this.stats = builder.stats;

    }

    /**
     * Returns number of BMP statistics records.
     *
     * @return number of BMP statistics records
     */
    public int getStatsCount() {
        return statsCount;
    }

    /**
     * Returns BGP statistics.
     *
     * @return BGP statistics
     */
    public List<BmpStats> getStats() {
        return stats;
    }

    /**
     * Returns BMP Peer Header of BMP Message.
     *
     * @return BMP Peer Header of BMP Message
     */
    @Override
    public PerPeer getPerPeer() {
        return perPeer;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BmpStatsReport that = (BmpStatsReport) o;
        return statsCount == that.statsCount &&
                Objects.equals(stats, that.stats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statsCount, stats);
    }


    /**
     * Data deserializer function for BMP statistics.
     *
     * @return data deserializer function
     */
    public static Deserializer<BmpStatsReport> deserializer() {
        return (data, offset, length) -> {
            BiPredicate<ByteBuffer, Integer> isValidBuffer = (b, l)
                    -> b.hasRemaining() && b.remaining() >= l;

            ByteBuffer bb = ByteBuffer.wrap(data, offset, length);
            if (!isValidBuffer.test(bb,
                    STATS_REPORT_HEADER_MIN_LENGTH +
                            PerPeerPacket.PEER_HEADER_MIN_LENGTH +
                            BmpStatistics.STATISTICS_HEADER_MIN_LENGTH)) {
                throw new BmpParseException("Invalid bmp statistics message buffer size.");
            }


            byte[] perPeer = new byte[PerPeerPacket.PEER_HEADER_MIN_LENGTH];
            bb.get(perPeer);

            Builder builder = new Builder()
                    .perPeer(PerPeerPacket.deserializer().deserialize(perPeer,
                            0, PerPeerPacket.PEER_HEADER_MIN_LENGTH))
                    .statsCount(bb.getInt());

            IntStream.range(0, builder.statsCount)
                    .forEach(index -> {
                        if (bb.hasRemaining()) {
                            int statsType = bb.getShort();
                            int newLength = bb.getShort();
                            bb.position(bb.position() - BmpStatistics.STATISTICS_HEADER_MIN_LENGTH);
                            int statsLength = newLength + BmpStatistics.STATISTICS_HEADER_MIN_LENGTH;
                            if (bb.remaining() < statsLength) {
                                throw new BmpParseException("Invalid bmp statistics message buffer size.");
                            }
                            byte[] statsBytes = new byte[statsLength];
                            bb.get(statsBytes);
                            try {
                                builder.stats((BmpStats) BmpStatistics.deserializer()
                                        .deserialize(statsBytes, 0, statsLength));
                            } catch (DeserializationException ex) {
                                throw new BmpParseException(ex);
                            }
                        }
                    });

            return builder.build();
        };
    }


    @Override
    public String toString() {

        return MoreObjects.toStringHelper(getClass())
                .add("perPeer", perPeer)
                .add("statsCount", statsCount)
                .add("stats", stats)
                .toString();
    }

    /**
     * Builder for BMP statistics message.
     */
    private static class Builder {

        private PerPeer perPeer;

        private int statsCount;

        private List<BmpStats> stats = Lists.newArrayList();


        /**
         * Setter bmp per peer header.
         *
         * @param perPeer bmp per peer header.
         * @return this class builder.
         */
        public Builder perPeer(PerPeer perPeer) {
            this.perPeer = perPeer;
            return this;
        }


        /**
         * Setter bmp statistics record count.
         *
         * @param statsCount bmp statistics record count.
         * @return this class builder.
         */
        public Builder statsCount(int statsCount) {
            this.statsCount = statsCount;
            return this;
        }

        /**
         * Setter bmp statistics records.
         *
         * @param stats bmp statistics records.
         * @return this class builder.
         */
        public Builder stats(List<BmpStats> stats) {
            this.stats = stats;
            return this;
        }

        /**
         * Setter bmp statistics record.
         *
         * @param stats bmp statistics record.
         * @return this class builder.
         */
        public Builder stats(BmpStats stats) {
            this.stats.add(stats);
            return this;
        }

        /**
         * Checks arguments for bmp statistics record.
         */
        private void checkArguments() {
            checkState(perPeer != null, "Invalid bmp statistics per peer buffer.");
            checkState(statsCount != 0, "Invalid bmp statistics records.");
        }

        /**
         * Builds BMP statistics message.
         *
         * @return BMP statistics message.
         */
        public BmpStatsReport build() {
            checkArguments();
            return new BmpStatsReport(this);
        }
    }

}
