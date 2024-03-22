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
import org.onosproject.bgpmonitoring.InitiationMessage;
import org.onosproject.bgpmonitoring.BmpParseException;
import org.onosproject.bgpmonitoring.BmpMsg;

import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.nio.ByteBuffer;

import org.onlab.packet.Deserializer;

/**
 * A means for the monitored router to inform the
 * monitoring station of its vendor, software version, and so on.
 * <p>
 * The initiation message provides a means for the monitored router to.
 * inform the monitoring station of its vendor, software version, and so
 * on.  An initiation message MUST be sent as the first message after
 * the TCP session comes up.  An initiation message MAY be sent at any
 * point thereafter, if warranted by a change on the monitored router.
 * <p>
 * The initiation message consists of the common BMP header followed by
 * two or more Information TLVs containing information
 * about the monitored router.  The sysDescr and sysName Information
 * TLVs MUST be sent, any others are optional.  The string TLV MAY be
 * included multiple times.
 */
public final class BmpInitiationMessage extends InitiationMessage {

    /*

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |          Information Type     |       Information Length      |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                 Information (variable)                        |
     ~                                                               ~
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

     */


    private List<BmpMsg> initiationMeassages;


    private BmpInitiationMessage(Builder builder) {
        this.initiationMeassages = builder.initiationMeassages;
    }

    /**
     * Returns BMP initiation message.
     *
     * @return BMP initiation message
     */
    public List<BmpMsg> getInitiationMeassages() {
        return initiationMeassages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BmpInitiationMessage that = (BmpInitiationMessage) o;
        return Objects.equals(initiationMeassages, that.initiationMeassages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(initiationMeassages);
    }


    /**
     * Data deserializer function for BMP initiation message.
     *
     * @return data deserializer function
     */
    public static Deserializer<BmpInitiationMessage> deserializer() {
        return (data, offset, length) -> {
            BiPredicate<ByteBuffer, Integer> isValidBuffer = (b, l)
                    -> b.hasRemaining() && b.remaining() >= l;

            ByteBuffer bb = ByteBuffer.wrap(data, offset, length);
            if (!isValidBuffer.test(bb, INIT_MSG_NOTIFICATION_HEADER_MIN_LENGTH)) {
                throw new BmpParseException("Invalid bmp initiation message buffer size.");
            }
            Builder builder = new Builder();
            while (bb.hasRemaining() && bb.remaining() > INIT_MSG_NOTIFICATION_HEADER_MIN_LENGTH) {
                int msgtype = bb.getShort();
                int msgLength = bb.getShort();
                bb.position(bb.position() - INIT_MSG_NOTIFICATION_HEADER_MIN_LENGTH);
                if (bb.remaining() < msgLength) {
                    break;
                }
                byte[] msgBytes = new byte[msgLength];
                bb.get(msgBytes);

                builder.initiationMeassages(BmpInitMsg.deserializer()
                        .deserialize(msgBytes, 0, msgLength));
            }

            return builder.build();
        };
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .add("initiationMeassages", initiationMeassages)
                .toString();
    }

    /**
     * Builder for BMP initiation message.
     */
    private static class Builder {

        private List<BmpMsg> initiationMeassages;

        /**
         * Setter bmp initiation message.
         *
         * @param msg bmp initiation message.
         * @return this class builder.
         */
        public Builder initiationMeassages(BmpMsg msg) {
            this.initiationMeassages.add(msg);
            return this;
        }

        /**
         * Builds bmp initiation message.
         *
         * @return bmp initiation message.
         */
        public BmpInitiationMessage build() {
            return new BmpInitiationMessage(this);
        }
    }
}
