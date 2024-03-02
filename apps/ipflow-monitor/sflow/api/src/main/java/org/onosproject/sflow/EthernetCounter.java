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

/**
 * Represents ethernet counters for network interfaces.
 */
public final class EthernetCounter {

    private InterfaceCounter generic;
    private int dot3StatsAlignmentErrors;
    private int dot3StatsFcsErrors;
    private int dot3StatsSingleCollisionFrames;
    private int dot3StatsMultipleCollisionFrames;
    private int dot3StatsSqeTestErrors;
    private int dot3StatsDeferredTransmissions;
    private int dot3StatsLateCollisions;
    private int dot3StatsExcessiveCollisions;
    private int dot3StatsInternalMacTransmitErrors;
    private int dot3StatsCarrierSenseErrors;
    private int dot3StatsFrameTooLongs;
    private int dot3StatsInternalMacReceiveErrors;
    private int dot3StatsSymbolErrors;

    private EthernetCounter(Builder builder) {
        this.generic = builder.generic;
        this.dot3StatsAlignmentErrors = builder.dot3StatsAlignmentErrors;
        this.dot3StatsFcsErrors = builder.dot3StatsFcsErrors;
        this.dot3StatsSingleCollisionFrames = builder.dot3StatsSingleCollisionFrames;
        this.dot3StatsMultipleCollisionFrames = builder.dot3StatsMultipleCollisionFrames;
        this.dot3StatsSqeTestErrors = builder.dot3StatsSqeTestErrors;
        this.dot3StatsDeferredTransmissions = builder.dot3StatsDeferredTransmissions;
        this.dot3StatsLateCollisions = builder.dot3StatsLateCollisions;
        this.dot3StatsExcessiveCollisions = builder.dot3StatsExcessiveCollisions;
        this.dot3StatsInternalMacTransmitErrors = builder.dot3StatsInternalMacTransmitErrors;
        this.dot3StatsCarrierSenseErrors = builder.dot3StatsCarrierSenseErrors;
        this.dot3StatsFrameTooLongs = builder.dot3StatsFrameTooLongs;
        this.dot3StatsInternalMacReceiveErrors = builder.dot3StatsInternalMacReceiveErrors;
        this.dot3StatsSymbolErrors = builder.dot3StatsSymbolErrors;
    }

    /**
     * Gets the generic interface counter.
     *
     * @return generic interface counter.
     */
    public InterfaceCounter getGeneric() {
        return generic;
    }

    /**
     * Gets the count of allignment errors.
     *
     * @return count of allignment errors.
     */
    public int getDot3StatsAlignmentErrors() {
        return dot3StatsAlignmentErrors;
    }

    /**
     * Gets the count of FCS errors.
     *
     * @return count of FCS errors.
     */
    public int getDot3StatsFcsErrors() {
        return dot3StatsFcsErrors;
    }

    /**
     * Gets the count of single collision frames.
     *
     * @return count of single collision frames.
     */
    public int getDot3StatsSingleCollisionFrames() {
        return dot3StatsSingleCollisionFrames;
    }

    /**
     * Gets the count of multi collision frames.
     *
     * @return count of multi collision frames.
     */
    public int getDot3StatsMultipleCollisionFrames() {
        return dot3StatsMultipleCollisionFrames;
    }

    /**
     * Gets the count of SQE test errors.
     *
     * @return count of SQE test errors.
     */
    public int getDot3StatsSqeTestErrors() {
        return dot3StatsSqeTestErrors;
    }

    /**
     * Gets the count of deferred transmissions.
     *
     * @return count of deferred transmissions.
     */
    public int getDot3StatsDeferredTransmissions() {
        return dot3StatsDeferredTransmissions;
    }

    /**
     * Gets the count of late collisions.
     *
     * @return count of late collisions.
     */
    public int getDot3StatsLateCollisions() {
        return dot3StatsLateCollisions;
    }

    /**
     * Gets the count of excessive collisions.
     *
     * @return count of excessive collisions.
     */
    public int getDot3StatsExcessiveCollisions() {
        return dot3StatsExcessiveCollisions;
    }

    /**
     * Gets the count of internal mac transmit errors.
     *
     * @return count of internal mac transmit errors.
     */
    public int getDot3StatsInternalMacTransmitErrors() {
        return dot3StatsInternalMacTransmitErrors;
    }

    /**
     * Gets the count of carrier sense errors.
     *
     * @return count of carrier sense errors.
     */
    public int getDot3StatsCarrierSenseErrors() {
        return dot3StatsCarrierSenseErrors;
    }

    /**
     * Gets the count of frame too longs error.
     *
     * @return count of frame too longs error.
     */
    public int getDot3StatsFrameTooLongs() {
        return dot3StatsFrameTooLongs;
    }

    /**
     * Gets the count of internal mac receive errors.
     *
     * @return count of internal mac receive errors.
     */
    public int getDot3StatsInternalMacReceiveErrors() {
        return dot3StatsInternalMacReceiveErrors;
    }

    /**
     * Gets the count of symbol errors.
     *
     * @return count of symbol errors.
     */
    public int getDot3StatsSymbolErrors() {
        return dot3StatsSymbolErrors;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .add("generic", generic)
                .add("dot3StatsAlignmentErrors", dot3StatsAlignmentErrors)
                .add("dot3StatsFcsErrors", dot3StatsFcsErrors)
                .add("dot3StatsSingleCollisionFrames", dot3StatsSingleCollisionFrames)
                .add("dot3StatsMultipleCollisionFrames", dot3StatsMultipleCollisionFrames)
                .add("dot3StatsSqeTestErrors", dot3StatsSqeTestErrors)
                .add("dot3StatsDeferredTransmissions", dot3StatsDeferredTransmissions)
                .add("dot3StatsLateCollisions", dot3StatsLateCollisions)
                .add("dot3StatsExcessiveCollisions", dot3StatsExcessiveCollisions)
                .add("dot3StatsInternalMacTransmitErrors", dot3StatsInternalMacTransmitErrors)
                .add("dot3StatsCarrierSenseErrors", dot3StatsCarrierSenseErrors)
                .add("dot3StatsFrameTooLongs", dot3StatsFrameTooLongs)
                .add("dot3StatsInternalMacReceiveErrors", dot3StatsInternalMacReceiveErrors)
                .add("dot3StatsSymbolErrors", dot3StatsSymbolErrors)
                .toString();
    }

    /**
     * Builder pattern to create an instance of InterfaceCounter.
     */
    private static class Builder {
        private InterfaceCounter generic;
        private int dot3StatsAlignmentErrors;
        private int dot3StatsFcsErrors;
        private int dot3StatsSingleCollisionFrames;
        private int dot3StatsMultipleCollisionFrames;
        private int dot3StatsSqeTestErrors;
        private int dot3StatsDeferredTransmissions;
        private int dot3StatsLateCollisions;
        private int dot3StatsExcessiveCollisions;
        private int dot3StatsInternalMacTransmitErrors;
        private int dot3StatsCarrierSenseErrors;
        private int dot3StatsFrameTooLongs;
        private int dot3StatsInternalMacReceiveErrors;
        private int dot3StatsSymbolErrors;

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
         * Sets the count of allignment errors.
         *
         * @param dot3StatsAlignmentErrors the count of allignment errors.
         * @return this builder instance.
         */
        public Builder dot3StatsAlignmentErrors(int dot3StatsAlignmentErrors) {
            this.dot3StatsAlignmentErrors = dot3StatsAlignmentErrors;
            return this;
        }

        /**
         * Sets the count of FCS errors.
         *
         * @param dot3StatsFCSErrors the count of FCS errors.
         * @return this builder instance.
         */
        public Builder dot3StatsFcsErrors(int dot3StatsFcsErrors) {
            this.dot3StatsFcsErrors = dot3StatsFcsErrors;
            return this;
        }

        /**
         * Sets the count of single collision frames.
         *
         * @param dot3StatsSingleCollisionFrames the count of single collision frames.
         * @return this builder instance.
         */
        public Builder dot3StatsSingleCollisionFrames(int dot3StatsSingleCollisionFrames) {
            this.dot3StatsSingleCollisionFrames = dot3StatsSingleCollisionFrames;
            return this;
        }

        /**
         * Sets the count of multi collision frames.
         *
         * @param dot3StatsMultipleCollisionFrames the count of multi collision frames.
         * @return this builder instance.
         */
        public Builder dot3StatsMultipleCollisionFrames(int dot3StatsMultipleCollisionFrames) {
            this.dot3StatsMultipleCollisionFrames = dot3StatsMultipleCollisionFrames;
            return this;
        }

        /**
         * Sets the count of SQE test errors.
         *
         * @param dot3StatsSQETestErrors the count of SQE test errors.
         * @return this builder instance.
         */
        public Builder dot3StatsSqeTestErrors(int dot3StatsSqeTestErrors) {
            this.dot3StatsSqeTestErrors = dot3StatsSqeTestErrors;
            return this;
        }

        /**
         * Sets the count of deferred transmissions.
         *
         * @param dot3StatsDeferredTransmissions the count of deferred transmissions.
         * @return this builder instance.
         */
        public Builder dot3StatsDeferredTransmissions(int dot3StatsDeferredTransmissions) {
            this.dot3StatsDeferredTransmissions = dot3StatsDeferredTransmissions;
            return this;
        }

        /**
         * Sets the count of late collisions.
         *
         * @param dot3StatsLateCollisions the count of late collisions.
         * @return this builder instance.
         */
        public Builder dot3StatsLateCollisions(int dot3StatsLateCollisions) {
            this.dot3StatsLateCollisions = dot3StatsLateCollisions;
            return this;
        }

        /**
         * Sets the count of excessive collisions.
         *
         * @param dot3StatsExcessiveCollisions the count of excessive collisions.
         * @return this builder instance.
         */
        public Builder dot3StatsExcessiveCollisions(int dot3StatsExcessiveCollisions) {
            this.dot3StatsExcessiveCollisions = dot3StatsExcessiveCollisions;
            return this;
        }

        /**
         * Sets the count of internal mac transmit errors.
         *
         * @param dot3StatsInternalMacTransmitErrors the count of internal mac transmit errors.
         * @return this builder instance.
         */
        public Builder dot3StatsInternalMacTransmitErrors(int dot3StatsInternalMacTransmitErrors) {
            this.dot3StatsInternalMacTransmitErrors = dot3StatsInternalMacTransmitErrors;
            return this;
        }

        /**
         * Sets the count of carrier sense errors.
         *
         * @param dot3StatsCarrierSenseErrors the count of carrier sense errors.
         * @return this builder instance.
         */
        public Builder dot3StatsCarrierSenseErrors(int dot3StatsCarrierSenseErrors) {
            this.dot3StatsCarrierSenseErrors = dot3StatsCarrierSenseErrors;
            return this;
        }

        /**
         * Sets the count of frame too longs error.
         *
         * @param dot3StatsFrameTooLongs the count of frame too longs error.
         * @return this builder instance.
         */
        public Builder dot3StatsFrameTooLongs(int dot3StatsFrameTooLongs) {
            this.dot3StatsFrameTooLongs = dot3StatsFrameTooLongs;
            return this;
        }

        /**
         * Sets the count of internal mac receive errors.
         *
         * @param dot3StatsInternalMacReceiveErrors the count of internal mac receive errors.
         * @return this builder instance.
         */
        public Builder dot3StatsInternalMacReceiveErrors(int dot3StatsInternalMacReceiveErrors) {
            this.dot3StatsInternalMacReceiveErrors = dot3StatsInternalMacReceiveErrors;
            return this;
        }

        /**
         * Sets the count of symbol errors.
         *
         * @param dot3StatsSymbolErrors the count of symbol errors.
         * @return this builder instance.
         */
        public Builder dot3StatsSymbolErrors(int dot3StatsSymbolErrors) {
            this.dot3StatsSymbolErrors = dot3StatsSymbolErrors;
            return this;
        }

        /**
         * Builds an instance of EthernetCounter based on the configured parameters.
         *
         * @return an instance of EnternetCounter.
         */
        public EthernetCounter build() {
            return new EthernetCounter(this);
        }
    }
}
