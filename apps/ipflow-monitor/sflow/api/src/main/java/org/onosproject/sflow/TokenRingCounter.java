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
 * Represents Token Ring counters for network interfaces.
 */
public final class TokenRingCounter {

    private InterfaceCounter generic;
    private int dot5StatsLineErrors;
    private int dot5StatsBurstErrors;
    private int dot5StatsAcErrors;
    private int dot5StatsAbortTransErrors;
    private int dot5StatsInternalErrors;
    private int dot5StatsLostFrameErrors;
    private int dot5StatsReceiveCongestions;
    private int dot5StatsFrameCopiedErrors;
    private int dot5StatsTokenErrors;
    private int dot5StatsSoftErrors;
    private int dot5StatsHardErrors;
    private int dot5StatsSignalLoss;
    private int dot5StatsTransmitBeacons;
    private int dot5StatsRecoverys;
    private int dot5StatsLobeWires;
    private int dot5StatsRemoves;
    private int dot5StatsSingles;
    private int dot5StatsFreqErrors;

    private TokenRingCounter(Builder builder) {
        this.generic = builder.generic;
        this.dot5StatsLineErrors = builder.dot5StatsLineErrors;
        this.dot5StatsBurstErrors = builder.dot5StatsBurstErrors;
        this.dot5StatsAcErrors = builder.dot5StatsAcErrors;
        this.dot5StatsAbortTransErrors = builder.dot5StatsAbortTransErrors;
        this.dot5StatsInternalErrors = builder.dot5StatsInternalErrors;
        this.dot5StatsLostFrameErrors = builder.dot5StatsLostFrameErrors;
        this.dot5StatsReceiveCongestions = builder.dot5StatsReceiveCongestions;
        this.dot5StatsFrameCopiedErrors = builder.dot5StatsFrameCopiedErrors;
        this.dot5StatsTokenErrors = builder.dot5StatsTokenErrors;
        this.dot5StatsSoftErrors = builder.dot5StatsSoftErrors;
        this.dot5StatsHardErrors = builder.dot5StatsHardErrors;
        this.dot5StatsSignalLoss = builder.dot5StatsSignalLoss;
        this.dot5StatsTransmitBeacons = builder.dot5StatsTransmitBeacons;
        this.dot5StatsRecoverys = builder.dot5StatsRecoverys;
        this.dot5StatsLobeWires = builder.dot5StatsLobeWires;
        this.dot5StatsRemoves = builder.dot5StatsRemoves;
        this.dot5StatsSingles = builder.dot5StatsSingles;
        this.dot5StatsFreqErrors = builder.dot5StatsFreqErrors;
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
     * Gets the count of line errors.
     *
     * @return count of line errors.
     */
    public int getDot5StatsLineErrors() {
        return dot5StatsLineErrors;
    }

    /**
     * Gets the count of burst errors.
     *
     * @return count of burst errors.
     */
    public int getDot5StatsBurstErrors() {
        return dot5StatsBurstErrors;
    }

    /**
     * Gets the count of AC errors.
     *
     * @return count of AC errors.
     */
    public int getDot5StatsAcErrors() {
        return dot5StatsAcErrors;
    }

    /**
     * Gets the count of abort transmit errors.
     *
     * @return count of abort transmit errors.
     */
    public int getDot5StatsAbortTransErrors() {
        return dot5StatsAbortTransErrors;
    }

    /**
     * Gets the count of internal errors.
     *
     * @return count of internal errors.
     */
    public int getDot5StatsInternalErrors() {
        return dot5StatsInternalErrors;
    }

    /**
     * Gets the count of lost frame errors.
     *
     * @return count of lost frame errors.
     */
    public int getDot5StatsLostFrameErrors() {
        return dot5StatsLostFrameErrors;
    }

    /**
     * Gets the count of receive congestion errors.
     *
     * @return count of receive congestion errors.
     */
    public int getDot5StatsReceiveCongestions() {
        return dot5StatsReceiveCongestions;
    }

    /**
     * Gets the count of frame copied errors.
     *
     * @return count of frame copied errors.
     */
    public int getDot5StatsFrameCopiedErrors() {
        return dot5StatsFrameCopiedErrors;
    }

    /**
     * Gets the count of token errors.
     *
     * @return count of token errors.
     */
    public int getDot5StatsTokenErrors() {
        return dot5StatsTokenErrors;
    }

    /**
     * Gets the count of soft errors.
     *
     * @return count of soft errors.
     */
    public int getDot5StatsSoftErrors() {
        return dot5StatsSoftErrors;
    }

    /**
     * Gets the count of hard errors.
     *
     * @return count of hard errors.
     */
    public int getDot5StatsHardErrors() {
        return dot5StatsHardErrors;
    }

    /**
     * Gets the count of signal loss errors.
     *
     * @return count of signal loss errors.
     */
    public int getDot5StatsSignalLoss() {
        return dot5StatsSignalLoss;
    }

    /**
     * Gets the count of transmit beacons errors.
     *
     * @return count of transmit beacons errors.
     */
    public int getDot5StatsTransmitBeacons() {
        return dot5StatsTransmitBeacons;
    }

    /**
     * Gets the count of recovery.
     *
     * @return count of recovery.
     */
    public int getDot5StatsRecoverys() {
        return dot5StatsRecoverys;
    }

    /**
     * Gets the count of lobe wires errors.
     *
     * @return count of lobe wires errors.
     */
    public int getDot5StatsLobeWires() {
        return dot5StatsLobeWires;
    }

    /**
     * Gets the count of removes.
     *
     * @return count of removes.
     */
    public int getDot5StatsRemoves() {
        return dot5StatsRemoves;
    }

    /**
     * Gets the count of singles.
     *
     * @return count of singles.
     */
    public int getDot5StatsSingles() {
        return dot5StatsSingles;
    }

    /**
     * Gets the count of frequency errors.
     *
     * @return count of frequency errors.
     */
    public int getDot5StatsFreqErrors() {
        return dot5StatsFreqErrors;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .add("generic", generic)
                .add("dot5StatsLineErrors", dot5StatsLineErrors)
                .add("dot5StatsBurstErrors", dot5StatsBurstErrors)
                .add("dot5StatsAcErrors", dot5StatsAcErrors)
                .add("dot5StatsAbortTransErrors", dot5StatsAbortTransErrors)
                .add("dot5StatsInternalErrors", dot5StatsInternalErrors)
                .add("dot5StatsLostFrameErrors", dot5StatsLostFrameErrors)
                .add("dot5StatsReceiveCongestions", dot5StatsReceiveCongestions)
                .add("dot5StatsFrameCopiedErrors", dot5StatsFrameCopiedErrors)
                .add("dot5StatsTokenErrors", dot5StatsTokenErrors)
                .add("dot5StatsSoftErrors", dot5StatsSoftErrors)
                .add("dot5StatsHardErrors", dot5StatsHardErrors)
                .add("dot5StatsSignalLoss", dot5StatsSignalLoss)
                .add("dot5StatsTransmitBeacons", dot5StatsTransmitBeacons)
                .add("dot5StatsRecoverys", dot5StatsRecoverys)
                .add("dot5StatsLobeWires", dot5StatsLobeWires)
                .add("dot5StatsRemoves", dot5StatsRemoves)
                .add("dot5StatsSingles", dot5StatsSingles)
                .add("dot5StatsFreqErrors", dot5StatsFreqErrors)
                .toString();
    }

    /**
     * Builder pattern to create an instance of InterfaceCounter.
     */
    private static class Builder {

        private InterfaceCounter generic;
        private int dot5StatsLineErrors;
        private int dot5StatsBurstErrors;
        private int dot5StatsAcErrors;
        private int dot5StatsAbortTransErrors;
        private int dot5StatsInternalErrors;
        private int dot5StatsLostFrameErrors;
        private int dot5StatsReceiveCongestions;
        private int dot5StatsFrameCopiedErrors;
        private int dot5StatsTokenErrors;
        private int dot5StatsSoftErrors;
        private int dot5StatsHardErrors;
        private int dot5StatsSignalLoss;
        private int dot5StatsTransmitBeacons;
        private int dot5StatsRecoverys;
        private int dot5StatsLobeWires;
        private int dot5StatsRemoves;
        private int dot5StatsSingles;
        private int dot5StatsFreqErrors;

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
         * Sets the count of line errors.
         *
         * @param dot5StatsLineErrors the count of line errors.
         * @return this builder instance.
         */
        public Builder dot5StatsLineErrors(int dot5StatsLineErrors) {
            this.dot5StatsLineErrors = dot5StatsLineErrors;
            return this;
        }

        /**
         * Sets the count of burst errors.
         *
         * @param dot5StatsBurstErrors the count of burst errors.
         * @return this builder instance.
         */
        public Builder dot5StatsBurstErrors(int dot5StatsBurstErrors) {
            this.dot5StatsBurstErrors = dot5StatsBurstErrors;
            return this;
        }

        /**
         * Sets the count of AC errors.
         *
         * @param dot5StatsAcErrors the count of AC errors.
         * @return this builder instance.
         */
        public Builder dot5StatsAcErrors(int dot5StatsAcErrors) {
            this.dot5StatsAcErrors = dot5StatsAcErrors;
            return this;
        }

        /**
         * Sets the count of abort transmit errors.
         *
         * @param dot5StatsAbortTransErrors the count of abort transmit errors.
         * @return this builder instance.
         */
        public Builder dot5StatsAbortTransErrors(int dot5StatsAbortTransErrors) {
            this.dot5StatsAbortTransErrors = dot5StatsAbortTransErrors;
            return this;
        }

        /**
         * Sets the count of internal errors.
         *
         * @param dot5StatsInternalErrors the count of internal errors.
         * @return this builder instance.
         */
        public Builder dot5StatsInternalErrors(int dot5StatsInternalErrors) {
            this.dot5StatsInternalErrors = dot5StatsInternalErrors;
            return this;
        }

        /**
         * Sets the count of lost frame errors.
         *
         * @param dot5StatsLostFrameErrors the count of lost frame errors.
         * @return this builder instance.
         */
        public Builder dot5StatsLostFrameErrors(int dot5StatsLostFrameErrors) {
            this.dot5StatsLostFrameErrors = dot5StatsLostFrameErrors;
            return this;
        }

        /**
         * Sets the count of receive congestion errors.
         *
         * @param dot5StatsReceiveCongestions the count of receive congestion errors.
         * @return this builder instance.
         */
        public Builder dot5StatsReceiveCongestions(int dot5StatsReceiveCongestions) {
            this.dot5StatsReceiveCongestions = dot5StatsReceiveCongestions;
            return this;
        }

        /**
         * Sets the count of frame copied errors.
         *
         * @param dot5StatsFrameCopiedErrors the count of frame copied errors.
         * @return this builder instance.
         */
        public Builder dot5StatsFrameCopiedErrors(int dot5StatsFrameCopiedErrors) {
            this.dot5StatsFrameCopiedErrors = dot5StatsFrameCopiedErrors;
            return this;
        }

        /**
         * Sets the count of token errors.
         *
         * @param dot5StatsTokenErrors the count of token errors.
         * @return this builder instance.
         */
        public Builder dot5StatsTokenErrors(int dot5StatsTokenErrors) {
            this.dot5StatsTokenErrors = dot5StatsTokenErrors;
            return this;
        }

        /**
         * Sets the count of soft errors.
         *
         * @param dot5StatsSoftErrors the count of soft errors.
         * @return this builder instance.
         */
        public Builder dot5StatsSoftErrors(int dot5StatsSoftErrors) {
            this.dot5StatsSoftErrors = dot5StatsSoftErrors;
            return this;
        }

        /**
         * Sets the count of hard errors.
         *
         * @param dot5StatsHardErrors the count of hard errors.
         * @return this builder instance.
         */
        public Builder dot5StatsHardErrors(int dot5StatsHardErrors) {
            this.dot5StatsHardErrors = dot5StatsHardErrors;
            return this;
        }

        /**
         * Sets the count of signal loss errors.
         *
         * @param dot5StatsSignalLoss the count of signal loss errors.
         * @return this builder instance.
         */
        public Builder dot5StatsSignalLoss(int dot5StatsSignalLoss) {
            this.dot5StatsSignalLoss = dot5StatsSignalLoss;
            return this;
        }

        /**
         * Sets the count of transmit beacons errors.
         *
         * @param dot5StatsTransmitBeacons the count of transmit beacons errors.
         * @return this builder instance.
         */
        public Builder dot5StatsTransmitBeacons(int dot5StatsTransmitBeacons) {
            this.dot5StatsTransmitBeacons = dot5StatsTransmitBeacons;
            return this;
        }

        /**
         * Sets the count of recovery.
         *
         * @param dot5StatsRecoverys the count of recovery.
         * @return this builder instance.
         */
        public Builder dot5StatsRecoverys(int dot5StatsRecoverys) {
            this.dot5StatsRecoverys = dot5StatsRecoverys;
            return this;
        }

        /**
         * Sets the count of lobe wires errors.
         *
         * @param dot5StatsLobeWires the count of lobe wires errors.
         * @return this builder instance.
         */
        public Builder dot5StatsLobeWires(int dot5StatsLobeWires) {
            this.dot5StatsLobeWires = dot5StatsLobeWires;
            return this;
        }

        /**
         * Sets the count of removes.
         *
         * @param dot5StatsRemoves the count of removes.
         * @return this builder instance.
         */
        public Builder dot5StatsRemoves(int dot5StatsRemoves) {
            this.dot5StatsRemoves = dot5StatsRemoves;
            return this;
        }

        /**
         * Sets the count of singles.
         *
         * @param dot5StatsSingles the count of singles.
         * @return this builder instance.
         */
        public Builder dot5StatsSingles(int dot5StatsSingles) {
            this.dot5StatsSingles = dot5StatsSingles;
            return this;
        }

        /**
         * Sets the count of frequency errors.
         *
         * @param dot5StatsFreqErrors the count of frequency errors.
         * @return this builder instance.
         */
        public Builder dot5StatsFreqErrors(int dot5StatsFreqErrors) {
            this.dot5StatsFreqErrors = dot5StatsFreqErrors;
            return this;
        }

        /**
         * Builds an instance of TokenRingCounter based on the configured parameters.
         *
         * @return an instance of TokenRingCounter.
         */
        public TokenRingCounter build() {
            return new TokenRingCounter(this);
        }
    }
}
