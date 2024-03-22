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
import java.util.function.BiPredicate;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.onlab.packet.BasePacket;
import org.onlab.packet.Deserializer;
import org.onosproject.bgpmonitoring.PerPeer;
import org.onosproject.bgpmonitoring.PeerType;
import org.onosproject.bgpmonitoring.BmpParseException;

import static com.google.common.base.Preconditions.checkState;

/**
 * The per-peer header follows the common header for most BMP messages.
 * The rest of the data in a BMP message is dependent on the Message
 * Type field in the common header.
 * <p>
 * Peer Type (1 byte): Identifies the type of peer.  Currently, three
 * types of peers are identified:
 * <p>
 * *  Peer Type = 0: Global Instance Peer
 * *  Peer Type = 1: RD Instance Peer
 * *  Peer Type = 2: Local Instance Peer
 * <p>
 * o  Peer Flags (1 byte): These flags provide more information about
 * the peer.  The flags are defined as follows:
 * <p>
 * 0 1 2 3 4 5 6 7
 * +-+-+-+-+-+-+-+-+
 * |V|L|A| Reserved|
 * +-+-+-+-+-+-+-+-+
 * <p>
 * *  The V flag indicates that the Peer address is an IPv6 address.
 * For IPv4 peers, this is set to 0.
 * <p>
 * The L flag, if set to 1, indicates that the message reflects
 * the post-policy Adj-RIB-In (i.e., its path attributes reflect
 * the application of inbound policy).  It is set to 0 if the
 * message reflects the pre-policy Adj-RIB-In.  Locally sourced
 * routes also carry an L flag of 1.  See Section 5 for further
 * detail.  This flag has no significance when used with route
 * mirroring messages.
 * <p>
 * *  The A flag, if set to 1, indicates that the message is
 * formatted using the legacy 2-byte AS_PATH format.  If set to 0,
 * the message is formatted using the 4-byte AS_PATH format
 * [RFC6793].  A BMP speaker MAY choose to propagate the AS_PATH
 * information as received from its peer, or it MAY choose to
 * reformat all AS_PATH information into a 4-byte format
 * regardless of how it was received from the peer.  In the latter
 * case, AS4_PATH or AS4_AGGREGATOR path attributes SHOULD NOT be
 * sent in the BMP UPDATE message.  This flag has no significance
 * when used with route mirroring messages.
 * <p>
 * The remaining bits are reserved for future use.  They MUST be
 * transmitted as 0 and their values MUST be ignored on receipt.
 * <p>
 * Peer Distinguisher (8 bytes): Routers today can have multiple
 * instances (example: Layer 3 Virtual Private Networks (L3VPNs)
 * [RFC4364]).  This field is present to distinguish peers that
 * belong to one address domain from the other.
 * <p>
 * If the peer is a "Global Instance Peer", this field is zero-
 * filled.  If the peer is a "RD Instance Peer", it is set to the
 * route distinguisher of the particular instance the peer belongs
 * to.  If the peer is a "Local Instance Peer", it is set to a
 * unique, locally defined value.  In all cases, the effect is that
 * the combination of the Peer Type and Peer Distinguisher is
 * sufficient to disambiguate peers for which other identifying
 * information might overlap.
 * <p>
 * Peer Address: The remote IP address associated with the TCP
 * session over which the encapsulated PDU was received.  It is 4
 * bytes long if an IPv4 address is carried in this field (with the
 * 12 most significant bytes zero-filled) and 16 bytes long if an
 * IPv6 address is carried in this field.
 * <p>
 * Peer AS: The Autonomous System number of the peer from which the
 * encapsulated PDU was received.  If a 16-bit AS number is stored in
 * this field [RFC6793], it should be padded with zeroes in the 16
 * most significant bits.
 * <p>
 * Timestamp: The time when the encapsulated routes were received
 * (one may also think of this as the time when they were installed
 * in the Adj-RIB-In), expressed in seconds and microseconds since
 * midnight (zero hour), January 1, 1970 (UTC).  If zero, the time is
 * unavailable.  Precision of the timestamp is implementation-
 * dependent.
 */
public final class PerPeerPacket extends BasePacket implements PerPeer {

    /*
      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |   Peer Type   |  Peer Flags   |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |         Peer Distinguisher (present based on peer type)       |
     |                                                               |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                 Peer Address (16 bytes)                       |
     ~                                                               ~
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                           Peer AS                             |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                         Peer BGP ID                           |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                    Timestamp (seconds)                        |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                  Timestamp (microseconds)                     |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     */
    public static final int PEER_HEADER_MIN_LENGTH = 42;

    public static final int PEER_DISTINGUISHER = 8;
    public static final int IPV4_ADDRSZ = 4;
    public static final int IPV6_ADDRSZ = 16;

    private PeerType type;

    private byte flags;

    private byte[] peerDistinguisher;

    private InetAddress peerAddress;

    private int peerAs;

    private String peerBgpId;

    private int seconds;

    private int microseconds;

    private PerPeerPacket(Builder builder) {
        this.type = builder.type;
        this.flags = builder.flags;
        this.peerDistinguisher = builder.peerDistinguisher;
        this.peerAddress = builder.peerAddress;
        this.peerAs = builder.peerAs;
        this.peerBgpId = builder.peerBgpId;
        this.seconds = builder.seconds;
        this.microseconds = builder.microseconds;

    }

    @Override
    public PeerType getType() {
        return type;
    }

    @Override
    public byte getFlag() {
        return flags;
    }

    @Override
    public byte[] getPeerDistinguisher() {
        return peerDistinguisher;
    }

    @Override
    public InetAddress getIntAddress() {
        return peerAddress;
    }

    @Override
    public int getPeerAs() {
        return peerAs;
    }

    @Override
    public String getPeerBgpId() {
        return peerBgpId;
    }

    @Override
    public int getSeconds() {
        return seconds;
    }

    @Override
    public int getMicroseconds() {
        return microseconds;
    }

    @Override
    public boolean isIpv6() {
        return ((flags & 0x80) != 0x00);
    }

    /**
     * Data deserializer function for BMP per peer header.
     *
     * @return data deserializer function
     */
    public static Deserializer<PerPeerPacket> deserializer() {
        return (data, offset, length) -> {
            BiPredicate<ByteBuffer, Integer> isValidBuffer = (b, l)
                    -> b.hasRemaining() && b.remaining() >= l;

            ByteBuffer bb = ByteBuffer.wrap(data, offset, length);
            if (!isValidBuffer.test(bb, PEER_HEADER_MIN_LENGTH)) {
                throw new BmpParseException("Invalid bmp per peer header buffer size.");
            }
            Builder builder = new Builder();

            return builder.type(PeerType.getType((int) bb.get()))
                    .flags(bb.get())
                    .peerDistinguisher(bb)
                    .peerAddress(bb)
                    .peerAs(bb.getInt())
                    .peerBgpId(bb.getInt())
                    .seconds(bb.getInt())
                    .microseconds(bb.getInt())
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
                .add("flags", flags)
                .add("type", type)
                .add("peerAddress", peerAddress.getHostAddress())
                .add("peerAs", peerAs)
                .add("seconds", seconds)
                .add("microseconds", microseconds)
                .toString();
    }

    public static InetAddress toInetAddress(int length, ByteBuffer bb) {
        byte[] address = new byte[length];
        bb.get(address);

        InetAddress ipAddress = null;
        try {
            ipAddress = InetAddress.getByAddress(address);
        } catch (UnknownHostException ex) {
            throw new BmpParseException(ex);
        }

        return ipAddress;
    }

    /**
     * Builder for BMP per peer header.
     */
    private static class Builder {

        private PeerType type;

        private byte flags;

        private byte[] peerDistinguisher;

        private InetAddress peerAddress;

        private int peerAs;

        private String peerBgpId;

        private int seconds;

        private int microseconds;


        /**
         * Setter bmp per peer header.
         *
         * @param perPeer bmp per peer header.
         * @return this class builder.
         */
        public Builder type(PeerType type) {
            this.type = type;
            return this;
        }

        /**
         * Setter bmp per peer header flag.
         *
         * @param flags bmp peer per header flag.
         * @return this class builder.
         */
        public Builder flags(byte flags) {
            this.flags = flags;
            return this;
        }

        /**
         * Setter bgp peer distinguisher.
         *
         * @param bb byte buffer.
         * @return this class builder.
         */
        public Builder peerDistinguisher(ByteBuffer bb) {
            this.peerDistinguisher = new byte[PEER_DISTINGUISHER];
            bb.get(peerDistinguisher);
            return this;
        }

        /**
         * Setter bgp peer address.
         *
         * @param bb byte buffer.
         * @return this class builder.
         */
        public Builder peerAddress(ByteBuffer bb) {
            if ((flags & 0x80) != 0x00) {
                this.peerAddress = toInetAddress(IPV6_ADDRSZ, bb);
            } else {
                bb.position(bb.position() + (IPV6_ADDRSZ - IPV4_ADDRSZ));
                this.peerAddress = toInetAddress(IPV4_ADDRSZ, bb);
            }
            return this;
        }

        /**
         * Setter bgp peer As.
         *
         * @param peerAs peer As.
         * @return this class builder.
         */
        public Builder peerAs(int peerAs) {
            this.peerAs = peerAs;
            return this;
        }

        /**
         * Setter bgp router id.
         *
         * @param id bgp router id.
         * @return this class builder.
         */
        public Builder peerBgpId(int id) {
            StringBuilder sb = new StringBuilder();
            int result = 0;
            for (int i = 0; i < 4; ++i) {
                result = id >> (3 - i) * 8 & 0xff;
                sb.append(result);
                if (i != 3) {
                    sb.append(".");
                }
            }
            this.peerBgpId = sb.toString();
            return this;
        }

        /**
         * Setter bmp per peer message sent time in seconds.
         *
         * @param seconds bmp per peer message sent time in seconds.
         * @return this class builder.
         */
        public Builder seconds(int seconds) {
            this.seconds = seconds;
            return this;
        }

        /**
         * Setter bmp per peer message sent time in micro seconds.
         *
         * @param microseconds bmp per peer message sent time in micro seconds.
         * @return this class builder.
         */
        public Builder microseconds(int microseconds) {
            this.microseconds = microseconds;
            return this;
        }

        /**
         * Checks arguments for bmp per peer header.
         */
        private void checkArguments() {
            checkState(type != null, "Invalid bmp per peer type.");
            checkState(peerAddress != null, "Invalid bmp per peer address.");
        }

        /**
         * Builds bmp per peer header.
         *
         * @return bmp per peer header.
         */
        public PerPeerPacket build() {
            checkArguments();
            return new PerPeerPacket(this);
        }

    }

}

