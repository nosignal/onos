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


import org.jboss.netty.buffer.ChannelBuffers;
import com.google.common.base.MoreObjects;
import org.onlab.packet.Deserializer;
import org.onosproject.bgpio.exceptions.BgpParseException;
import org.onosproject.bgpio.protocol.BgpMessage;
import org.onosproject.bgpio.protocol.ver4.BgpMessageVer4;
import org.onosproject.bgpio.types.BgpHeader;
import org.onosproject.bgpmonitoring.RouteMirroringMessage;
import org.onosproject.bgpmonitoring.PerPeer;
import org.onosproject.bgpmonitoring.MirroringType;
import org.onosproject.bgpmonitoring.BmpParseException;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.nio.ByteBuffer;

import static com.google.common.base.Preconditions.checkState;

/**
 * Route Mirroring messages are used for verbatim duplication of
 * messages as received.  A possible use for mirroring is exact
 * mirroring of one or more monitored BGP sessions, without state
 * compression.  Another possible use is the mirroring of messages that
 * have been treated-as-withdraw [RFC7606], for debugging purposes.
 * Mirrored messages may be sampled, or may be lossless.  The Messages
 * Lost Information code is provided to allow losses to be indicated.
 * <p>
 * Following the common BMP header and per-peer header is a set of TLVs
 * that contain information about a message or set of messages.  Each
 * TLV comprises a 2-byte type code, a 2-byte length field, and a
 * variable-length value.  Inclusion of any given TLV is OPTIONAL;
 * however, at least one TLV SHOULD be included, otherwise there's no
 * point in sending the message.  Defined TLVs are as follows:
 * <p>
 * Type = 0: BGP Message.  A BGP PDU.  This PDU may or may not be an
 * Update message.  If the BGP Message TLV occurs in the Route
 * Mirroring message, it MUST occur last in the list of TLVs.
 * <p>
 * Type = 1: Information.  A 2-byte code that provides information
 * about the mirrored message or message stream.  Defined codes are:
 */
public final class BmpRouteMirroring extends RouteMirroringMessage {

/*
      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |          Mirroring Type      |       Mirroring Length         |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                Route Mirroring (variable)                     |
     ~                                                               ~
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 */


    private PerPeer perPeer;

    private MirroringType mirroringType;

    private int mirroringLength;

    private BgpMessage bgpMessage;

    private BmpRouteMirroring(Builder builder) {
        this.perPeer = builder.perPeer;
        this.mirroringType = builder.mirroringType;
        this.mirroringLength = builder.mirroringLength;
        this.bgpMessage = builder.bgpMessage;
    }

    /**
     * Returns Bgp route mirroring type.
     *
     * @return Bgp route mirroring type
     */
    @Override
    public MirroringType getMirroringType() {
        return mirroringType;
    }

    /**
     * Returns Bgp route mirroring length.
     *
     * @return Bgp route mirroring length
     */
    @Override
    public int getMirroringLength() {
        return mirroringLength;
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
        BmpRouteMirroring that = (BmpRouteMirroring) o;
        return mirroringType == that.mirroringType &&
                mirroringLength == that.mirroringLength &&
                Objects.equals(perPeer, that.perPeer) &&
                Objects.equals(bgpMessage, that.bgpMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(perPeer, mirroringType, mirroringLength, bgpMessage);
    }


    /**
     * Data deserializer function for bmp route mirroring message.
     *
     * @return data deserializer function
     */
    public static Deserializer<BmpRouteMirroring> deserializer() {
        return (data, offset, length) -> {
            BiPredicate<ByteBuffer, Integer> isValidBuffer = (b, l)
                    -> b.hasRemaining() && b.remaining() >= l;

            ByteBuffer bb = ByteBuffer.wrap(data, offset, length);
            if (!isValidBuffer.test(bb, ROUTE_MIRRORING_HEADER_MIN_LENGTH +
                    PerPeerPacket.PEER_HEADER_MIN_LENGTH)) {
                throw new BmpParseException("Invalid bmp route mirroring message buffer size.");
            }
            byte[] perPeerBytes = new byte[PerPeerPacket.PEER_HEADER_MIN_LENGTH];
            bb.get(perPeerBytes);

            Builder builder = new Builder()
                    .perPeer(PerPeerPacket.deserializer().deserialize(perPeerBytes,
                            0, PerPeerPacket.PEER_HEADER_MIN_LENGTH))
                    .mirroringType(MirroringType.getType((int) bb.getShort()))
                    .mirroringLength((int) bb.getShort());
            if (builder.mirroringType != MirroringType.BGP_MESSAGE) {
                throw new BmpParseException("Not supported mirroring type");
            }

            byte[] routeBytes = new byte[bb.remaining()];
            bb.get(routeBytes);
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
                .add("mirroringType", mirroringType)
                .add("mirroringLength", mirroringLength)
                .add("bgpMessage", bgpMessage)
                .toString();
    }

    /**
     * Builder for BMP route mirroring message.
     */
    private static class Builder {

        private PerPeer perPeer;

        private MirroringType mirroringType;

        private int mirroringLength;

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
         * Setter bmp mirroring type.
         *
         * @param mirroringType bmp mirroring type.
         * @return this class builder.
         */
        public Builder mirroringType(MirroringType mirroringType) {
            this.mirroringType = mirroringType;
            return this;
        }

        /**
         * Setter bmp mirroring length.
         *
         * @param mirroringLength bmp mirroring length.
         * @return this class builder.
         */
        public Builder mirroringLength(int mirroringLength) {
            this.mirroringLength = mirroringLength;
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
         * Checks arguments for bgp route mirroring message.
         */
        private void checkArguments() {
            checkState(perPeer != null, "Invalid bmp route mirroring per peer buffer.");
            checkState(bgpMessage != null, "Invalid bgp message.");
        }

        /**
         * Builds BMP route mirroring message.
         *
         * @return BMP route mirroring message.
         */
        public BmpRouteMirroring build() {
            checkArguments();
            return new BmpRouteMirroring(this);
        }
    }
}
