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
import org.jboss.netty.buffer.ChannelBuffers;

import org.onosproject.bgpio.exceptions.BgpParseException;
import org.onosproject.bgpio.protocol.BgpMessage;
import org.onosproject.bgpio.protocol.ver4.BgpMessageVer4;
import org.onosproject.bgpio.types.BgpHeader;
import org.onosproject.bgpmonitoring.RouteMonitoringMessage;
import org.onosproject.bgpmonitoring.BmpParseException;
import org.onosproject.bgpmonitoring.PerPeer;
import org.onlab.packet.Deserializer;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.nio.ByteBuffer;

import static com.google.common.base.Preconditions.checkState;

/**
 * Used to provide an initial dump of all routes received from a peer.
 * As well as an ongoing mechanism that sends the incremental routes
 * advertised and withdrawn by a peer to the monitoring station.
 * <p>
 * Route Monitoring messages are used for initial synchronization of the
 * ADJ-RIBs-In.  They are also used for ongoing monitoring of the
 * ADJ-RIB-In state.  Route monitoring messages are state-compressed.
 * <p>
 * Route Mirroring messages are used for verbatim duplication of
 * messages as received.  A possible use for mirroring is exact
 * mirroring of one or more monitored BGP sessions, without state
 * compression.  Another possible use is the mirroring of messages that
 * have been treated-as-withdraw [RFC7606], for debugging purposes.
 * Mirrored messages may be sampled, or may be lossless.  The Messages
 * Lost Information code is provided to allow losses to be indicated.
 * Following the common BMP header and per-peer header is a set of TLVs
 * that contain information about a message or set of messages.  Each
 * TLV comprises a 2-byte type code, a 2-byte length field, and a
 * variable-length value.  Inclusion of any given TLV is OPTIONAL;
 * however, at least one TLV SHOULD be included, otherwise there's no
 * point in sending the message.
 */
public final class BmpRouteMonitoringMessage extends RouteMonitoringMessage {


    private PerPeer perPeer;

    private BgpMessage bgpMessage;

    private BmpRouteMonitoringMessage(Builder builder) {
        this.perPeer = builder.perPeer;
        this.bgpMessage = builder.bgpMessage;
    }

    /**
     * Returns Bgp message.
     *
     * @return Bgp message
     */
    @Override
    public BgpMessage getBgpMessage() {
        return bgpMessage;
    }

    /**
     * Returns BMP Header of BMP Message.
     *
     * @return BMP Header of BMP Message
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
        BmpRouteMonitoringMessage that = (BmpRouteMonitoringMessage) o;
        return Objects.equals(perPeer, that.perPeer) &&
                Objects.equals(bgpMessage, that.bgpMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(perPeer, bgpMessage);
    }

    /**
     * Data deserializer function for BMP route monitoring.
     *
     * @return data deserializer function
     */
    public static Deserializer<BmpRouteMonitoringMessage> deserializer() {
        return (data, offset, length) -> {
            BiPredicate<ByteBuffer, Integer> isValidBuffer = (b, l)
                    -> b.hasRemaining() && b.remaining() >= l;

            ByteBuffer bb = ByteBuffer.wrap(data, offset, length);
            if (!isValidBuffer.test(bb, ROUTE_MONITORING_HEADER_MIN_LENGTH +
                    PerPeerPacket.PEER_HEADER_MIN_LENGTH)) {
                throw new BmpParseException("Invalid bmp route monitoring message buffer size.");
            }

            byte[] perPeerBytes = new byte[PerPeerPacket.PEER_HEADER_MIN_LENGTH];
            bb.get(perPeerBytes);

            byte[] routeBytes = new byte[bb.remaining()];
            bb.get(routeBytes);
            Builder builder = new Builder()
                    .perPeer(PerPeerPacket.deserializer().deserialize(perPeerBytes,
                            0, PerPeerPacket.PEER_HEADER_MIN_LENGTH));
            try {
                builder.bgpMessage(BgpMessageVer4.READER.readFrom(ChannelBuffers.wrappedBuffer(routeBytes),
                        new BgpHeader()));
            } catch (BgpParseException ex) {
                throw new BmpParseException(ex);
            }
            return builder.build();

        };
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .add("perPeer", perPeer)
                .add("bgpMessage", bgpMessage)
                .toString();
    }

    /**
     * Builder for BMP route monitoring message.
     */
    private static class Builder {

        private PerPeer perPeer;

        private BgpMessage bgpMessage;

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
         * Setter bpg message.
         *
         * @param bgpMessage bpg message.
         * @return this class builder.
         */
        public Builder bgpMessage(BgpMessage bgpMessage) {
            this.bgpMessage = bgpMessage;
            return this;
        }

        /**
         * Checks arguments for bgp route monitoring message.
         */
        private void checkArguments() {
            checkState(perPeer != null, "Invalid bmp route monitor per peer buffer.");
            checkState(bgpMessage != null, "Invalid bgp message.");
        }

        /**
         * Builds BMP route monitoring message.
         *
         * @return BMP route monitoring message.
         */
        public BmpRouteMonitoringMessage build() {
            checkArguments();
            return new BmpRouteMonitoringMessage(this);
        }

    }

}
