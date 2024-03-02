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
 * Represents VG counters for network interfaces.
 */
public final class VgCounter {

    private InterfaceCounter generic;
    private int dot12InHighPriorityFrames;
    private long dot12InHighPriorityOctets;
    private int dot12InNormPriorityFrames;
    private long dot12InNormPriorityOctets;
    private int dot12InIpmErrors;
    private int dot12InOversizeFrameErrors;
    private int dot12InDataErrors;
    private int dot12InNullAddressedFrames;
    private int dot12OutHighPriorityFrames;
    private long dot12OutHighPriorityOctets;
    private int dot12TransitionIntoTrainings;
    private long dot12HCInHighPriorityOctets;
    private long dot12HCInNormPriorityOctets;
    private long dot12HCOutHighPriorityOctets;

    private VgCounter(Builder builder) {
        this.generic = builder.generic;
        this.dot12InHighPriorityFrames = builder.dot12InHighPriorityFrames;
        this.dot12InHighPriorityOctets = builder.dot12InHighPriorityOctets;
        this.dot12InNormPriorityFrames = builder.dot12InNormPriorityFrames;
        this.dot12InNormPriorityOctets = builder.dot12InNormPriorityOctets;
        this.dot12InIpmErrors = builder.dot12InIpmErrors;
        this.dot12InOversizeFrameErrors = builder.dot12InOversizeFrameErrors;
        this.dot12InDataErrors = builder.dot12InDataErrors;
        this.dot12InNullAddressedFrames = builder.dot12InNullAddressedFrames;
        this.dot12OutHighPriorityFrames = builder.dot12OutHighPriorityFrames;
        this.dot12OutHighPriorityOctets = builder.dot12OutHighPriorityOctets;
        this.dot12TransitionIntoTrainings = builder.dot12TransitionIntoTrainings;
        this.dot12HCInHighPriorityOctets = builder.dot12HCInHighPriorityOctets;
        this.dot12HCInNormPriorityOctets = builder.dot12HCInNormPriorityOctets;
        this.dot12HCOutHighPriorityOctets = builder.dot12HCOutHighPriorityOctets;

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
     * Gets the count of high priority frames.
     *
     * @return count of high priority frames.
     */
    public int getDot12InHighPriorityFrames() {
        return dot12InHighPriorityFrames;
    }

    /**
     * Gets the count of high priority octets.
     *
     * @return high priority octets.
     */
    public long getDot12InHighPriorityOctets() {
        return dot12InHighPriorityOctets;
    }

    /**
     * Gets the count of normal priority frames.
     *
     * @return count of normal priority frames.
     */
    public int getDot12InNormPriorityFrames() {
        return dot12InNormPriorityFrames;
    }

    /**
     * Gets the count of normal priority octets.
     *
     * @return count of normal priority octets.
     */
    public long getDot12InNormPriorityOctets() {
        return dot12InNormPriorityOctets;
    }

    /**
     * Gets the count of ipm errors.
     *
     * @return count of ipm errors.
     */
    public int getDot12InIpmErrors() {
        return dot12InIpmErrors;
    }

    /**
     * Gets the count of over size frame errors.
     *
     * @return count of over size frame errors.
     */
    public int getDot12InOversizeFrameErrors() {
        return dot12InOversizeFrameErrors;
    }

    /**
     * Gets the count of in data errors.
     *
     * @return count of in data errors.
     */
    public int getDot12InDataErrors() {
        return dot12InDataErrors;
    }

    /**
     * Gets the count of in null addressed frames.
     *
     * @return count of in null addressed frames.
     */
    public int getDot12InNullAddressedFrames() {
        return dot12InNullAddressedFrames;
    }

    /**
     * Gets the count of out high priority frames.
     *
     * @return count of out high priority frames.
     */
    public int getDot12OutHighPriorityFrames() {
        return dot12OutHighPriorityFrames;
    }

    /**
     * Gets the count of out high priority octets.
     *
     * @return count of out high priority octets.
     */
    public long getDot12OutHighPriorityOctets() {
        return dot12OutHighPriorityOctets;
    }

    /**
     * Gets the count of transition.
     *
     * @return count of transition.
     */
    public int getDot12TransitionIntoTrainings() {
        return dot12TransitionIntoTrainings;
    }

    /**
     * Gets the count of high priority octets.
     *
     * @return count of high priority octets.
     */
    public long getDot12HCInHighPriorityOctets() {
        return dot12HCInHighPriorityOctets;
    }

    /**
     * Gets the count of in normal priority octets.
     *
     * @return count of in normal priority octets.
     */
    public long getDot12HCInNormPriorityOctets() {
        return dot12HCInNormPriorityOctets;
    }

    /**
     * Gets the count of out high priority octets.
     *
     * @return count of out high priority octets.
     */
    public long getDot12HCOutHighPriorityOctets() {
        return dot12HCOutHighPriorityOctets;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .add("generic", generic)
                .add("dot12InHighPriorityFrames", dot12InHighPriorityFrames)
                .add("dot12InHighPriorityOctets", dot12InHighPriorityOctets)
                .add("dot12InNormPriorityFrames", dot12InNormPriorityFrames)
                .add("dot12InNormPriorityOctets", dot12InNormPriorityOctets)
                .add("dot12InIPMErrors", dot12InIpmErrors)
                .add("dot12InOversizeFrameErrors", dot12InOversizeFrameErrors)
                .add("dot12InDataErrors", dot12InDataErrors)
                .add("dot12InNullAddressedFrames", dot12InNullAddressedFrames)
                .add("dot12OutHighPriorityFrames", dot12OutHighPriorityFrames)
                .add("dot12OutHighPriorityOctets", dot12OutHighPriorityOctets)
                .add("dot12TransitionIntoTrainings", dot12TransitionIntoTrainings)
                .add("dot12HCInHighPriorityOctets", dot12HCInHighPriorityOctets)
                .add("dot12HCInNormPriorityOctets", dot12HCInNormPriorityOctets)
                .add("dot12HCOutHighPriorityOctets", dot12HCOutHighPriorityOctets)
                .toString();
    }

    /**
     * Builder pattern to create an instance of InterfaceCounter.
     */
    private static class Builder {
        private InterfaceCounter generic;
        private int dot12InHighPriorityFrames;
        private long dot12InHighPriorityOctets;
        private int dot12InNormPriorityFrames;
        private long dot12InNormPriorityOctets;
        private int dot12InIpmErrors;
        private int dot12InOversizeFrameErrors;
        private int dot12InDataErrors;
        private int dot12InNullAddressedFrames;
        private int dot12OutHighPriorityFrames;
        private long dot12OutHighPriorityOctets;
        private int dot12TransitionIntoTrainings;
        private long dot12HCInHighPriorityOctets;
        private long dot12HCInNormPriorityOctets;
        private long dot12HCOutHighPriorityOctets;

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
         * Sets the count of high priority frames.
         *
         * @param dot12InHighPriorityFrames the count of high priority frames.
         * @return this builder instance.
         */
        public Builder dot12InHighPriorityFrames(int dot12InHighPriorityFrames) {
            this.dot12InHighPriorityFrames = dot12InHighPriorityFrames;
            return this;
        }

        /**
         * Sets the count of high priority octets.
         *
         * @param dot12InHighPriorityOctets the high priority octets.
         * @return this builder instance.
         */
        public Builder dot12InHighPriorityOctets(long dot12InHighPriorityOctets) {
            this.dot12InHighPriorityOctets = dot12InHighPriorityOctets;
            return this;
        }

        /**
         * Sets the count of normal priority frames.
         *
         * @param dot12InNormPriorityFrames the count of normal priority frames.
         * @return this builder instance.
         */
        public Builder dot12InNormPriorityFrames(int dot12InNormPriorityFrames) {
            this.dot12InNormPriorityFrames = dot12InNormPriorityFrames;
            return this;
        }

        /**
         * Sets the count of normal priority octets.
         *
         * @param dot12InNormPriorityOctets the count of normal priority octets.
         * @return this builder instance.
         */
        public Builder dot12InNormPriorityOctets(long dot12InNormPriorityOctets) {
            this.dot12InNormPriorityOctets = dot12InNormPriorityOctets;
            return this;
        }

        /**
         * Sets the count of ipm errors.
         *
         * @param dot12InIPMErrors the count of ipm errors.
         * @return this builder instance.
         */
        public Builder dot12InIpmErrors(int dot12InIpmErrors) {
            this.dot12InIpmErrors = dot12InIpmErrors;
            return this;
        }

        /**
         * Sets the count of over size frame errors.
         *
         * @param dot12InOversizeFrameErrors the count of over size frame errors.
         * @return this builder instance.
         */
        public Builder dot12InOversizeFrameErrors(int dot12InOversizeFrameErrors) {
            this.dot12InOversizeFrameErrors = dot12InOversizeFrameErrors;
            return this;
        }

        /**
         * Sets the count of in data errors.
         *
         * @param dot12InDataErrors the count of in data errors.
         * @return this builder instance.
         */
        public Builder dot12InDataErrors(int dot12InDataErrors) {
            this.dot12InDataErrors = dot12InDataErrors;
            return this;
        }

        /**
         * Sets the count of in null addressed frames.
         *
         * @param dot12InNullAddressedFrames the count of in null addressed frames.
         * @return this builder instance.
         */
        public Builder dot12InNullAddressedFrames(int dot12InNullAddressedFrames) {
            this.dot12InNullAddressedFrames = dot12InNullAddressedFrames;
            return this;
        }

        /**
         * Sets the count of out high priority frames.
         *
         * @param dot12OutHighPriorityFrames the count of out high priority frames.
         * @return this builder instance.
         */
        public Builder dot12OutHighPriorityFrames(int dot12OutHighPriorityFrames) {
            this.dot12OutHighPriorityFrames = dot12OutHighPriorityFrames;
            return this;
        }

        /**
         * Sets the count of out high priority octets.
         *
         * @param dot12OutHighPriorityOctets the count of out high priority octets.
         * @return this builder instance.
         */
        public Builder dot12OutHighPriorityOctets(long dot12OutHighPriorityOctets) {
            this.dot12OutHighPriorityOctets = dot12OutHighPriorityOctets;
            return this;
        }

        /**
         * Sets the count of transition.
         *
         * @param dot12TransitionIntoTrainings the count of transition.
         * @return this builder instance.
         */
        public Builder dot12TransitionIntoTrainings(int dot12TransitionIntoTrainings) {
            this.dot12TransitionIntoTrainings = dot12TransitionIntoTrainings;
            return this;
        }

        /**
         * Sets the count of high priority octets.
         *
         * @param dot12HCInHighPriorityOctets the count of high priority octets.
         * @return this builder instance.
         */
        public Builder dot12HCInHighPriorityOctets(long dot12HCInHighPriorityOctets) {
            this.dot12HCInHighPriorityOctets = dot12HCInHighPriorityOctets;
            return this;
        }

        /**
         * Sets the count of in normal priority octets.
         *
         * @param dot12HCInNormPriorityOctets the count of in normal priority octets.
         * @return this builder instance.
         */
        public Builder dot12HCInNormPriorityOctets(long dot12HCInNormPriorityOctets) {
            this.dot12HCInNormPriorityOctets = dot12HCInNormPriorityOctets;
            return this;
        }

        /**
         * Sets the count of out high priority octets.
         *
         * @param dot12HCOutHighPriorityOctets the count of out high priority octets.
         * @return this builder instance.
         */
        public Builder dot12HCOutHighPriorityOctets(long dot12HCOutHighPriorityOctets) {
            this.dot12HCOutHighPriorityOctets = dot12HCOutHighPriorityOctets;
            return this;
        }

        /**
         * Builds an instance of VgCounter based on the configured parameters.
         *
         * @return an instance of VgCounter.
         */
        public VgCounter build() {
            return new VgCounter(this);
        }

    }
}
