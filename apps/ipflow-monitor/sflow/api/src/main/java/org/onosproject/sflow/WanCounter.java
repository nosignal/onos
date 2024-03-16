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
package org.onosproject.sflow;

import com.google.common.base.MoreObjects;
import org.onlab.packet.Deserializer;

/**
 * Represents WAN counters for network interfaces.
 */
public final class WanCounter extends CounterPacket {

    private InterfaceCounter generic;

    private WanCounter(Builder builder) {
        this.generic = builder.generic;
    }

    /**
     * Gets the generic interface counter.
     *
     * @return generic interface counter.
     */
    public InterfaceCounter getGeneric() {
        return generic;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .add("generic", generic)
                .toString();
    }

    /**
     * Deserializer function for sFlow interface WAN counter.
     *
     * @return deserializer function
     */
    public static Deserializer<WanCounter> deserializer() {
        return (data, offset, length) -> {

            if (length < InterfaceCounter.INTERFACE_COUNTER_LENGTH) {
                throw new IllegalStateException("Invalid interface WAN counter buffer size.");
            }

            Builder builder = new Builder();
            return builder.generic(InterfaceCounter.deserializer().deserialize(data,
                            offset, length))
                    .build();
        };
    }

    /**
     * Builder pattern to create an instance of InterfaceCounter.
     */
    private static class Builder {
        private InterfaceCounter generic;

        /**
         * Sets the generic interface counter.
         *
         * @param generic the generic interface counter.
         * @return this builder instance.
         */
        public Builder generic(InterfaceCounter generic) {
            this.generic = generic;
            return this;
        }

        /**
         * Builds an instance of WAN counter based on the configured parameters.
         *
         * @return an instance of WAN counter.
         */
        public WanCounter build() {
            return new WanCounter(this);
        }

    }
}
