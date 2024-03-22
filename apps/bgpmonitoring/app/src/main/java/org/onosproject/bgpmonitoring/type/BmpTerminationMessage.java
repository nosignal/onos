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
import org.onosproject.bgpmonitoring.TerminationMessage;
import org.onosproject.bgpmonitoring.BmpParseException;
import org.onosproject.bgpmonitoring.TerminationType;

import java.nio.charset.StandardCharsets;
import java.nio.ByteBuffer;

import org.onlab.packet.Deserializer;

import java.util.function.BiPredicate;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkState;

/**
 * A means for the monitored router to inform the
 * monitoring station of why it is closing a BMP session.
 * The termination message provides a way for a monitored router to
 * indicate why it is terminating a session.  Although use of this
 * message is RECOMMENDED, a monitoring station must always be prepared
 * for the session to terminate with no message.  Once the router has
 * sent a termination message, it MUST close the TCP session without
 * sending any further messages.  Likewise, the monitoring station MUST
 * close the TCP session after receiving a termination message.
 * <p>
 * The termination message consists of the common BMP header followed by
 * one or more TLVs containing information about the reason for the
 * termination, as follows:
 * Type = 0: String.  The Information field contains a free-form
 * UTF-8 string whose length is given by the Information Length
 * field.  Inclusion of this TLV is optional.  It MAY be used to
 * provide further detail for any of the defined reasons.
 * Multiple String TLVs MAY be included in the message.
 */
public final class BmpTerminationMessage extends TerminationMessage {

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


    private TerminationType terminationType;

    private int informationLength;

    private String information;

    public BmpTerminationMessage(Builder builder) {
        this.terminationType = builder.terminationType;
        this.informationLength = builder.informationLength;
        this.information = builder.information;
    }

    /**
     * Returns BMP session termination type.
     *
     * @return BMP session termination type
     */
    @Override
    public TerminationType getTerminationType() {
        return terminationType;
    }

    /**
     * Returns BMP session termination message length.
     *
     * @return BMP session termination message length
     */
    @Override
    public int getInformationLength() {
        return informationLength;
    }

    /**
     * Returns BMP session termination message.
     *
     * @return BMP session termination message
     */
    @Override
    public String getInformation() {
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
        BmpTerminationMessage that = (BmpTerminationMessage) o;
        return informationLength == that.informationLength &&
                terminationType == that.terminationType;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(terminationType, informationLength, information);
        result = 31 * result;
        return result;
    }

    /**
     * Data deserializer function for BMP termination message.
     *
     * @return data deserializer function
     */
    public static Deserializer<BmpTerminationMessage> deserializer() {
        return (data, offset, length) -> {
            BiPredicate<ByteBuffer, Integer> isValidBuffer = (b, l)
                    -> b.hasRemaining() && b.remaining() >= l;

            ByteBuffer bb = ByteBuffer.wrap(data, offset, length);
            if (!isValidBuffer.test(bb, TERMINATION_HEADER_MIN_LENGTH)) {
                throw new BmpParseException("Invalid bmp termination message record buffer size.");
            }
            Builder builder = new Builder();
            builder.terminationType(TerminationType.getType(bb.getInt()))
                    .informationLength(bb.getInt());
            if (builder.terminationType != TerminationType.UTF8_STRING) {
                throw new BmpParseException("Not supported termination type");
            }

            byte[] information = new byte[builder.informationLength];
            bb.get(information);

            return builder.information(new String(information, StandardCharsets.UTF_8))
                    .build();
        };
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .add("terminationType", terminationType)
                .add("informationLength", informationLength)
                .add("information", information)
                .toString();
    }

    /**
     * Builder for BMP termination message.
     */
    private static class Builder {

        private TerminationType terminationType;

        private int informationLength;

        private String information;


        /**
         * Setter bmp termination type.
         *
         * @param terminationType bmp termination type.
         * @return this class builder.
         */
        public Builder terminationType(TerminationType terminationType) {
            this.terminationType = terminationType;
            return this;
        }

        /**
         * Setter bmp termination message length.
         *
         * @param informationLength bmp termination message length.
         * @return this class builder.
         */
        public Builder informationLength(int informationLength) {
            this.informationLength = informationLength;
            return this;
        }

        /**
         * Setter bmp termination message.
         *
         * @param information bmp termination message.
         * @return this class builder.
         */
        public Builder information(String information) {
            this.information = information;
            return this;
        }

        /**
         * Checks arguments for bmp termination message.
         */
        private void checkArguments() {
            checkState(terminationType != null, "Invalid bmp termination type.");
            checkState(informationLength != 0, "Invalid bmp termination message length.");
        }

        /**
         * Builds bmp termination message.
         *
         * @return bmp termination message object.
         */
        public BmpTerminationMessage build() {
            checkArguments();
            return new BmpTerminationMessage(this);
        }
    }
}
