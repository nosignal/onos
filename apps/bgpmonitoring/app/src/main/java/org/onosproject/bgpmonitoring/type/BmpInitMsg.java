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

import java.nio.charset.StandardCharsets;
import java.nio.ByteBuffer;

import org.onlab.packet.BasePacket;
import org.onlab.packet.Deserializer;

import java.util.function.BiPredicate;

import org.onosproject.bgpmonitoring.BmpMsg;
import org.onosproject.bgpmonitoring.BmpParseException;


public final class BmpInitMsg extends BasePacket implements BmpMsg {

    public static final int MESSAGE_HEADER_MIN_LENGTH = 4;

    private short type;

    private short length;

    private String data;

    private BmpInitMsg(Builder builder) {
        this.type = builder.type;
        this.length = builder.length;
        this.data = builder.data;
    }

    /**
     * Returns initiation message type.
     *
     * @return initiation message type
     */
    @Override
    public short getType() {
        return type;
    }

    /**
     * Returns initiation message length.
     *
     * @return initiation message length
     */
    @Override
    public short getLength() {
        return length;
    }

    /**
     * Returns initiation message data.
     *
     * @return initiation message data
     */
    @Override
    public String getData() {
        return data;
    }

    /**
     * Data deserializer function for BMP initiation message.
     *
     * @return data deserializer function
     */
    public static Deserializer<BmpInitMsg> deserializer() {
        return (data, offset, length) -> {
            BiPredicate<ByteBuffer, Integer> isValidBuffer = (b, l)
                    -> b.hasRemaining() && b.remaining() >= l;

            ByteBuffer bb = ByteBuffer.wrap(data, offset, length);
            if (!isValidBuffer.test(bb, MESSAGE_HEADER_MIN_LENGTH)) {
                throw new BmpParseException("Invalid bmp initiation message buffer size.");
            }
            return new Builder().type(bb.getShort())
                    .length(bb.getShort())
                    .data(bb)
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
                .add("type", type)
                .add("length", length)
                .add("data", data)
                .toString();
    }

    /**
     * Builder for BMP initiation message.
     */
    private static class Builder {

        private short type;

        private short length;

        private String data;

        /**
         * Setter bmp initiation message type.
         *
         * @param type bmp initiation message type.
         * @return this class builder.
         */
        public Builder type(short type) {
            this.type = type;
            return this;
        }

        /**
         * Setter bmp initiation message length.
         *
         * @param length bmp initiation message length.
         * @return this class builder.
         */
        public Builder length(short length) {
            this.length = length;
            return this;
        }

        /**
         * Setter bmp initiation message.
         *
         * @param bb byte buffer.
         * @return this class builder.
         */
        public Builder data(ByteBuffer bb) {
            if (length != 0 && bb.remaining() > length) {
                throw new BmpParseException("Not enough readable bytes");
            }
            byte[] dataBytes = new byte[length];
            bb.get(dataBytes);
            switch (type) {
                case 0:
                    this.data = new String(dataBytes, StandardCharsets.UTF_8);
                    break;
                case 1:
                case 2:
                    this.data = new String(dataBytes, StandardCharsets.US_ASCII);
                    break;
                default:
                    this.data = new String(dataBytes, StandardCharsets.UTF_16);
            }
            return this;
        }


        /**
         * Builds bmp initiation message.
         *
         * @return bmp initiation message.
         */
        public BmpInitMsg build() {
            return new BmpInitMsg(this);
        }
    }
}