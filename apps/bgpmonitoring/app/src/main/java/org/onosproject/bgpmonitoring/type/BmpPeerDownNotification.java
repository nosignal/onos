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
import org.onosproject.bgpio.exceptions.BgpParseException;
import org.onosproject.bgpio.protocol.BgpMessage;
import org.onosproject.bgpio.protocol.ver4.BgpMessageVer4;
import org.onosproject.bgpio.types.BgpHeader;
import org.onosproject.bgpmonitoring.PeerDownNotificationMessage;
import org.onosproject.bgpmonitoring.PerPeer;
import org.onosproject.bgpmonitoring.BmpParseException;
import org.onosproject.bgpmonitoring.PeerDownReason;
import org.jboss.netty.buffer.ChannelBuffers;
import org.onlab.packet.Deserializer;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.nio.ByteBuffer;

import static com.google.common.base.Preconditions.checkState;

/**
 * A message sent to indicate that a peering
 * session has gone down with information indicating the reason for
 * the session disconnect.
 * <p>
 * This message is used to indicate that a peering session was terminated.
 * <p>
 * Reason 1: The local system closed the session.  Following the
 * Reason is a BGP PDU containing a BGP NOTIFICATION message that
 * would have been sent to the peer.
 * <p>
 * Reason 3: The remote system closed the session with a notification
 * message.  Following the Reason is a BGP PDU containing the BGP
 * NOTIFICATION message as received from the peer.
 * <p>
 * A Peer Down message implicitly withdraws all routes that were
 * associated with the peer in question.  A BMP implementation MAY omit
 * sending explicit withdraws for such routes.
 */
public final class BmpPeerDownNotification extends PeerDownNotificationMessage {

/*
      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+
     |    Reason     |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |            Data (present if Reason = 1, 2 or 3)               |
     ~                                                               ~
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 */


    private PerPeer perPeer;

    private PeerDownReason reason;

    private BgpMessage bgpMessage;

    private BmpPeerDownNotification(Builder builder) {
        this.perPeer = builder.perPeer;
        this.bgpMessage = builder.bgpMessage;
        this.reason = builder.reason;
    }

    /**
     * Returns BMP peer down reason.
     *
     * @return BMP peer down reason
     */
    @Override
    public PeerDownReason getReason() {
        return reason;
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
        BmpPeerDownNotification that = (BmpPeerDownNotification) o;
        return Objects.equals(perPeer, that.perPeer) &&
                reason == that.reason &&
                Objects.equals(bgpMessage, that.bgpMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(perPeer, reason, bgpMessage);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .add("perPeer", perPeer)
                .add("reason", reason)
                .add("bpgMessage", bgpMessage)
                .toString();
    }

    /**
     * Data deserializer function for flow interface counter record.
     *
     * @return data deserializer function
     */
    public static Deserializer<BmpPeerDownNotification> deserializer() {
        return (data, offset, length) -> {
            BiPredicate<ByteBuffer, Integer> isValidBuffer = (b, l)
                    -> b.hasRemaining() && b.remaining() >= l;

            ByteBuffer bb = ByteBuffer.wrap(data, offset, length);
            if (!isValidBuffer.test(bb, PEERDOWN_NOTIFICATION_HEADER_MIN_LENGTH +
                    PerPeerPacket.PEER_HEADER_MIN_LENGTH)) {
                throw new BmpParseException("Invalid bmp peer down notification message buffer size");
            }
            byte[] perPeerBytes = new byte[PerPeerPacket.PEER_HEADER_MIN_LENGTH];
            bb.get(perPeerBytes);

            Builder builder = new Builder()
                    .perPeer(PerPeerPacket.deserializer().deserialize(perPeerBytes,
                            0, PerPeerPacket.PEER_HEADER_MIN_LENGTH))
                    .reason(PeerDownReason.getType((int) bb.get()));

            if (bb.remaining() <= 0) {
                throw new BmpParseException("Invalid bmp peer down notification message buffer size");
            }

            if (builder.reason == PeerDownReason.LOCAL_SYSTEM_CLOSED_SESSION_WITH_NOTIFICATION ||
                    builder.reason == PeerDownReason.REMOTE_SYSTEM_CLOSED_SESSION_WITH_NOTIFICATION) {
                byte[] routeBytes = new byte[bb.remaining()];
                bb.get(routeBytes);
                try {
                    builder.bgpMessage(BgpMessageVer4.READER.readFrom(ChannelBuffers.wrappedBuffer(routeBytes),
                            new BgpHeader()));
                } catch (BgpParseException ex) {
                    throw new BmpParseException(ex);
                }
                return builder.build();
            }
            throw new BmpParseException("Not supported peer down reason");
        };
    }


    /**
     * Builder for BMP peer down notification message.
     */
    private static class Builder {

        private PerPeer perPeer;

        private PeerDownReason reason;

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
         * Setter bgp peer down reason.
         *
         * @param reason bgp peer down reason.
         * @return this class builder.
         */
        public Builder reason(PeerDownReason reason) {
            this.reason = reason;
            return this;
        }

        /**
         * Setter bpg message.
         *
         * @param bgpMessage bgp message.
         * @return this class builder.
         */
        public Builder bgpMessage(BgpMessage bgpMessage) {
            this.bgpMessage = bgpMessage;
            return this;
        }

        /**
         * Checks arguments for bmp peer down notification message.
         */
        private void checkArguments() {
            checkState(perPeer != null, "Invalid bgp peer down message per peer buffer.");
            checkState(bgpMessage != null, "Invalid bgp message.");
        }

        /**
         * Builds bmp peer down notification message.
         *
         * @return bmp peer down notification message.
         */
        public BmpPeerDownNotification build() {
            checkArguments();
            return new BmpPeerDownNotification(this);
        }

    }
}
