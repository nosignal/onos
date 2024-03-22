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
import org.onlab.packet.DeserializationException;
import org.onlab.packet.Deserializer;

import java.nio.ByteBuffer;
import java.util.function.BiPredicate;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.onosproject.bgpmonitoring.BmpMessage;
import org.onosproject.bgpmonitoring.BmpVersion;
import org.onosproject.bgpmonitoring.BmpPacket;
import org.onosproject.bgpmonitoring.BmpParseException;

import static com.google.common.base.Preconditions.checkState;

/**
 * The following common header appears in all BMP messages.  The rest of
 * the data in a BMP message is dependent on the Message Type field in
 * the common header.
 * <p>
 * Version (1 byte): Indicates the BMP version.  This is set to '3'
 * for all messages defined in this specification. ('1' and '2' were
 * used by draft versions of this document.)  Version 0 is reserved
 * and MUST NOT be sent.
 * <p>
 * Message Length (4 bytes): Length of the message in bytes
 * (including headers, data, and encapsulated messages, if any).
 * <p>
 * Message Type (1 byte): This identifies the type of the BMP
 * message.  A BMP implementation MUST ignore unrecognized message
 * types upon receipt.
 * <p>
 * Type = 0: Route Monitoring
 * Type = 1: Statistics Report
 * Type = 2: Peer Down Notification
 * Type = 3: Peer Up Notification
 * Type = 4: Initiation Message
 * Type = 5: Termination Message
 * Type = 6: Route Mirroring Message
 */
public final class BmpHeader extends BmpPacket {

    /*

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+
     |    Version    |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                        Message Length                         |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |   Msg. Type   |
     +---------------+

     */


    public static final int DEFAULT_HEADER_LENGTH = 6;
    public static final int DEFAULT_PACKET_MINIMUM_LENGTH = 4;

    private BmpVersion version;

    private BmpType type;

    private int length;

    private BmpMessage message;


    public enum BmpType {

        ROUTE_MONITORING(0, BmpRouteMirroring.deserializer()),

        STATISTICS_REPORT(1, BmpStatsReport.deserializer()),

        PEER_DOWN_NOTIFICATION(2, BmpPeerDownNotification.deserializer()),

        PEER_UP_NOTIFICATION(3, BmpPeerUpNotification.deserializer()),

        INITIATION_MESSAGE(4, BmpInitiationMessage.deserializer()),

        TERMINATION_MESSAGE(5, BmpTerminationMessage.deserializer()),

        ROUTE_MIRRORING_MESSAGE(6, BmpRouteMirroring.deserializer());

        private final int value;
        private final Deserializer<BmpMessage> deserializer;

        /**
         * Assign value with the value val as the types of BMP message.
         *
         * @param val          type of BMP message
         * @param deserializer deserializer
         */
        BmpType(int val, Deserializer deserializer) {
            this.value = val;
            this.deserializer = deserializer;
        }


        /**
         * Returns value as type of BMP message.
         *
         * @return value type of BMP message
         */
        public int getType() {
            return value;
        }

        private static Map<Integer, BmpType> parser = new ConcurrentHashMap<>();

        static {
            Arrays.stream(BmpType.values()).forEach(type -> parser.put(type.value, type));
        }

        public static BmpType getType(int type) throws DeserializationException {
            if (type > 6) {
                throw new DeserializationException("Invalid trap type");
            }
            return Optional.of(type)
                    .filter(id -> parser.containsKey(id))
                    .map(id -> parser.get(id))
                    .orElse(ROUTE_MONITORING);
        }

        public Deserializer<BmpMessage> getDecoder() {
            return this.deserializer;
        }

    }


    private BmpHeader(Builder builder) {
        this.version = builder.version;
        this.length = builder.length;
        this.type = builder.type;
        this.message = builder.message;
    }


    /**
     * Returns message length.
     *
     * @return message length
     */
    @Override
    public int getLength() {
        return this.length;
    }

    /**
     * Returns message version.
     *
     * @return message version
     */
    @Override
    public BmpVersion getVersion() {
        return this.version;
    }

    /**
     * Returns message type.
     *
     * @return message type
     */
    @Override
    public String getType() {
        return this.type.name();
    }

    /**
     * Returns message type.
     *
     * @return message type
     */
    @Override
    public BmpMessage getMessage() {
        return this.message;
    }


    /**
     * Read from channel buffer and Returns BMP header.
     *
     * @return Deserializer Deserializer
     */
    public static Deserializer<BmpHeader> deserializer() {
        return (data, offset, length) -> {
            BiPredicate<ByteBuffer, Integer> isValidBuffer = (b, l)
                    -> b.hasRemaining() && b.remaining() >= l;

            ByteBuffer bb = ByteBuffer.wrap(data, offset, length);

            if (!isValidBuffer.test(bb, DEFAULT_HEADER_LENGTH)) {
                throw new BmpParseException("Invalid bmp header buffer size.");
            }
            Builder builder = new Builder()
                    .version(BmpVersion.getVersion((int) bb.get()))
                    .length(bb.getInt())
                    .type(BmpType.getType((int) bb.get()));

            if (bb.remaining() != (builder.length - DEFAULT_HEADER_LENGTH)) {
                throw new BmpParseException("Invalid bmp header buffer size.");
            }

            byte[] msgBytes = new byte[bb.remaining()];
            bb.get(msgBytes);
            return builder.message(builder.type.getDecoder()
                            .deserialize(msgBytes, 0, (builder.length - DEFAULT_HEADER_LENGTH)))
                    .build();

        };
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .add("version", version)
                .add("type", type)
                .add("length", length)
                .toString();
    }

    /**
     * Builder for BMP header.
     */
    private static class Builder {

        private BmpVersion version;

        private BmpType type;

        private int length;

        private BmpMessage message;


        /**
         * Setter bmp version.
         *
         * @param version bmp version.
         * @return this class builder.
         */
        public Builder version(BmpVersion version) {
            this.version = version;
            return this;
        }


        /**
         * Setter bmp header length.
         *
         * @param length bmp header length.
         * @return this class builder.
         */
        public Builder length(int length) {
            this.length = length;
            return this;
        }

        /**
         * Setter bmp message type.
         *
         * @param type bmp message type.
         * @return this class builder.
         */
        public Builder type(BmpType type) {
            this.type = type;
            return this;
        }


        /**
         * Setter bmp message.
         *
         * @param message bmp message.
         * @return this class builder.
         */
        public Builder message(BmpMessage message) {
            this.message = message;
            return this;
        }


        /**
         * Checks arguments for bmp header.
         */
        private void checkArguments() {
            checkState(length != 0, "Invalid bmp header length.");
            checkState(type != null, "Invalid bmp message type.");
            checkState(message != null, "Invalid bmp message.");
        }

        /**
         * Builds BMP header.
         *
         * @return BMP header.
         */
        public BmpHeader build() {
            checkArguments();
            return new BmpHeader(this);
        }
    }
}
