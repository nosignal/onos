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

 /*
      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                 Local Address (16 bytes)                      |
     ~                                                               ~
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |         Local Port            |        Remote Port            |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                    Sent OPEN Message                          |
     ~                                                               ~
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                  Received OPEN Message                        |
     ~                                                               ~
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                 Information (variable)                        |
     ~                                                               ~
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 */
package org.onosproject.bgpmonitoring.type;

import org.jboss.netty.buffer.ChannelBuffers;
import com.google.common.base.MoreObjects;
import org.onlab.packet.Deserializer;
import org.onosproject.bgpio.exceptions.BgpParseException;
import org.onosproject.bgpio.protocol.BgpMessage;
import org.onosproject.bgpio.protocol.ver4.BgpMessageVer4;
import org.onosproject.bgpio.types.BgpHeader;
import org.onosproject.bgpmonitoring.PeerUpNotificationMessage;
import org.onosproject.bgpmonitoring.BmpParseException;
import org.onosproject.bgpmonitoring.PerPeer;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkState;

/**
 * A message sent to indicate that a peering
 * session has come up.  The message includes information regarding
 * the data exchanged between the peers in their OPEN messages, as
 * well as information about the peering TCP session itself.  In
 * addition to being sent whenever a peer transitions to the
 * Established state, a Peer Up Notification is sent for each peer in
 * the Established state when the BMP session itself comes up.
 * <p>
 * Local Address: The local IP address associated with the peering
 * TCP session.  It is 4 bytes long if an IPv4 address is carried in
 * this field, as determined by the V flag (with the 12 most
 * significant bytes zero-filled) and 16 bytes long if an IPv6
 * address is carried in this field.
 * <p>
 * Local Port: The local port number associated with the peering TCP
 * session, or 0 if no TCP session actually exists (see Section 8.2).
 * <p>
 * Remote Port: The remote port number associated with the peering
 * TCP session, or 0 if no TCP session actually exists.
 * <p>
 * Sent OPEN Message: The full OPEN message transmitted by the
 * monitored router to its peer.
 * <p>
 * Received OPEN Message: The full OPEN message received by the
 * monitored router from its peer.
 * Information: Information about the peer, using the Information TLV
 * format.  Only the string type is defined in this
 * context; it may be repeated.  Inclusion of the Information field
 * is OPTIONAL.  Its presence or absence can be inferred by
 * inspection of the Message Length in the common header.
 */
public final class BmpPeerUpNotification extends PeerUpNotificationMessage {

    private static final Logger log = LoggerFactory.getLogger(BmpPeerUpNotification.class);

    private PerPeer perPeer;

    private InetAddress localAddress;

    private int localPort;

    private int remotePort;

    private BgpMessage sentOpenMsg;

    private BgpMessage receivedOpenMsg;

    private byte[] information;


    private BmpPeerUpNotification(Builder builder) {
        this.perPeer = builder.perPeer;
        this.localAddress = builder.localAddress;
        this.localPort = builder.localPort;
        this.remotePort = builder.remotePort;
        this.sentOpenMsg = builder.sentOpenMsg;
        this.receivedOpenMsg = builder.receivedOpenMsg;
        this.information = builder.information;


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

    /**
     * Returns local ip address.
     *
     * @return local ip address
     */
    public InetAddress getLocalAddress() {
        return localAddress;
    }

    /**
     * Returns local port number.
     *
     * @return local port number
     */
    public int getLocalPort() {
        return localPort;
    }

    /**
     * Returns remote port number.
     *
     * @return remote port number
     */
    public int getRemotePort() {
        return remotePort;
    }

    /**
     * Returns Bgp sent open message.
     *
     * @return Bgp sent open message
     */
    public BgpMessage getSentOpenMsg() {
        return sentOpenMsg;
    }

    /**
     * Returns Bgp received open message.
     *
     * @return Bgp received open message
     */
    public BgpMessage getReceivedOpenMsg() {
        return receivedOpenMsg;
    }

    /**
     * Returns BMP peer information.
     *
     * @return BMP peer information
     */
    public byte[] getInformation() {
        return information;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BmpPeerUpNotification that = (BmpPeerUpNotification) o;
        return localPort == that.localPort &&
                remotePort == that.remotePort &&
                Objects.equals(localAddress, that.localAddress) &&
                Objects.equals(sentOpenMsg, that.sentOpenMsg) &&
                Objects.equals(receivedOpenMsg, that.receivedOpenMsg) &&
                Arrays.equals(information, that.information) &&
                Objects.equals(perPeer, that.perPeer);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(localAddress, localPort, remotePort, sentOpenMsg,
                receivedOpenMsg, perPeer);
        result = 31 * result + Arrays.hashCode(information);
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .add("perPeer", perPeer)
                .add("localAddress", localAddress)
                .add("localPort", localPort)
                .add("remotePort", remotePort)
                .add("sentOpenMsg", sentOpenMsg)
                .add("receivedOpenMsg", receivedOpenMsg)
                .add("information", Arrays.toString(information))
                .toString();
    }

    /**
     * Data deserializer function for BMP peer up notification message.
     *
     * @return data deserializer function
     */
    public static Deserializer<BmpPeerUpNotification> deserializer() {
        return (data, offset, length) -> {
            BiPredicate<ByteBuffer, Integer> isValidBuffer = (b, l)
                    -> b.hasRemaining() && b.remaining() >= l;

            ByteBuffer bb = ByteBuffer.wrap(data, offset, length);
            if (!isValidBuffer.test(bb, PEERUP_NOTIFICATION_HEADER_MIN_LENGTH +
                    PerPeerPacket.PEER_HEADER_MIN_LENGTH)) {
                throw new BmpParseException("Invalid bmp peer up notification message buffer size.");
            }
            byte[] perPeerBytes = new byte[PerPeerPacket.PEER_HEADER_MIN_LENGTH];
            bb.get(perPeerBytes);

            Builder builder = new Builder()
                    .perPeer(PerPeerPacket.deserializer().deserialize(perPeerBytes,
                            0, PerPeerPacket.PEER_HEADER_MIN_LENGTH));


            if (builder.perPeer.isIpv6()) {
                builder.localAddress(PerPeerPacket.toInetAddress(IPV6_ADDRS, bb));
            } else {
                bb.position(bb.position() + (IPV6_ADDRS - IPV4_ADDRS));
                builder.localAddress(PerPeerPacket.toInetAddress(IPV4_ADDRS, bb));
            }

            builder.localPort(bb.getShort())
                    .remotePort(bb.getShort());


            if (bb.remaining() < (PADDING_BYTES + BGP_LENGTH_FIELD)) {
                throw new BmpParseException("Invalid bmp peer up notification message buffer size.");
            }

            bb.position(bb.position() + PADDING_BYTES);
            int msgLength = bb.getShort();
            bb.position(bb.position() - (PADDING_BYTES + BGP_LENGTH_FIELD));

            if (bb.remaining() < msgLength) {
                throw new BmpParseException("Not enough readable bytes");
            }
            byte[] routeBytes = new byte[msgLength];
            bb.get(routeBytes);
            try {
                builder.sentOpenMsg(BgpMessageVer4.READER.readFrom(ChannelBuffers.wrappedBuffer(routeBytes),
                        new BgpHeader()));
            } catch (BgpParseException ex) {
                throw new BmpParseException(ex);
            }

            if (bb.remaining() < (PADDING_BYTES + BGP_LENGTH_FIELD)) {
                throw new BmpParseException("Not enough readable bytes");
            }

            bb.position(bb.position() + PADDING_BYTES);
            msgLength = bb.getShort();
            bb.position(bb.position() - (PADDING_BYTES + BGP_LENGTH_FIELD));

            if (bb.remaining() < msgLength) {
                throw new BmpParseException("Not enough readable bytes");
            }
            routeBytes = new byte[msgLength];
            bb.get(routeBytes);
            try {
                builder.receivedOpenMsg(BgpMessageVer4.READER.readFrom(ChannelBuffers.wrappedBuffer(routeBytes),
                        new BgpHeader()));
            } catch (BgpParseException ex) {
                throw new BmpParseException(ex);
            }

            if (bb.remaining() > 0) {
                byte[] information = new byte[bb.remaining()];
                bb.get(information);
                builder.information(information);

            }

            return builder.build();

        };
    }


    /**
     * Builder for BMP peer up notification message.
     */
    private static class Builder {


        private PerPeer perPeer;

        private InetAddress localAddress;

        private int localPort;

        private int remotePort;

        private BgpMessage sentOpenMsg;

        private BgpMessage receivedOpenMsg;

        private byte[] information;

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
         * Setter bgp local address.
         *
         * @param localAddress bgp local address.
         * @return this class builder.
         */
        public Builder localAddress(InetAddress localAddress) {
            this.localAddress = localAddress;
            return this;
        }


        /**
         * Setter bgp local port.
         *
         * @param localPort bgp local port.
         * @return this class builder.
         */
        public Builder localPort(int localPort) {
            this.localPort = localPort;
            return this;
        }

        /**
         * Setter bgp remote port.
         *
         * @param remotePort bgp remote port.
         * @return this class builder.
         */
        public Builder remotePort(int remotePort) {
            this.remotePort = remotePort;
            return this;
        }

        /**
         * Setter bgp send open message.
         *
         * @param sentOpenMsg bgp send open message.
         * @return this class builder.
         */
        public Builder sentOpenMsg(BgpMessage sentOpenMsg) {
            this.sentOpenMsg = sentOpenMsg;
            return this;
        }

        /**
         * Setter bgp receive open message.
         *
         * @param receivedOpenMsg bgp receive open message.
         * @return this class builder.
         */
        public Builder receivedOpenMsg(BgpMessage receivedOpenMsg) {
            this.receivedOpenMsg = receivedOpenMsg;
            return this;
        }

        /**
         * Setter bgp information message.
         *
         * @param information bgp information message.
         * @return this class builder.
         */
        public Builder information(byte[] information) {
            this.information = information;
            return this;
        }

        /**
         * Checks arguments for bmp peer up notification.
         */
        private void checkArguments() {
            checkState(perPeer != null, "Invalid bmp per peer in peer up notification message.");
            checkState(sentOpenMsg != null, "Invalid bgp send open message.");
            checkState(receivedOpenMsg != null, "Invalid bgp receive open message.");
        }

        /**
         * Builds BMP peer up notification message.
         *
         * @return BMP peer up notification message.
         */
        public BmpPeerUpNotification build() {
            checkArguments();
            return new BmpPeerUpNotification(this);
        }
    }
}
