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

import org.onlab.packet.Deserializer;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import com.google.common.base.MoreObjects;

import static com.google.common.base.Preconditions.checkState;

/**
 * A sample involves either copying the packet's header, or
 * extracting features from the packet (see sFlow Datagram Format for a
 * description of the different forms of sample).  Every time a sample
 * is taken, the counter Total_Samples, is incremented.  Total_Samples
 * is a count of the number of samples generated.  Samples are sent by
 * the sampling entity to the sFlow Agent for processing.  The sample
 * includes the packet information, and the values of the Total_Packets
 * and Total_Samples counters.
 * Ref: https://www.ietf.org/rfc/rfc3954.txt
 */
public final class FlowSample extends SflowSample {

    /*
    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                     Sequence Number                           |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                         Source Id                             |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                       Source Index                            |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                        Sampling Rate                          |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                          Sample Pool                          |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                       Packet drop counter                     |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                        Input Interface Id                     |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                 Input Interface reason (Optional)             |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                      Output Interface Id                      |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                Output Interface reason (Optional)             |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                 Total Number Of Flow Records                  |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                         Records                               |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    */

    private int samplingRate;

    private int samplePool;

    private int drops;

    private int inputInterfaceId;

    private int inputInterfaceValue;

    private int outputInterfaceId;

    private int outputInterfaceValue;

    private List<Object> records;

    private FlowSample(Builder builder) {
        this.enterprise = builder.enterprise;
        this.type = builder.type;
        this.length = builder.length;
        this.sequenceNumber = builder.sequenceNumber;
        this.sourceId = builder.sourceId;
        this.sourceIndex = builder.sourceIndex;
        this.numberOfRecords = builder.numberOfRecords;
        this.samplingRate = builder.samplingRate;
        this.samplePool = builder.samplePool;
        this.drops = builder.drops;
        this.inputInterfaceId = builder.inputInterfaceId;
        this.inputInterfaceValue = builder.inputInterfaceValue;
        this.outputInterfaceId = builder.outputInterfaceId;
        this.outputInterfaceValue = builder.outputInterfaceValue;
        this.records = builder.records;
    }

    /**
     * Get sFlow flow sampling rate.
     *
     * @return flow sampling rate.
     */
    public int getSamplingRate() {
        return samplingRate;
    }

    /**
     * Get sFlow sample pool.
     *
     * @return flow sample pool.
     */
    public int getSamplePool() {
        return samplePool;
    }

    /**
     * Get sFlow packet drop.
     *
     * @return packet drop.
     */
    public int getDrops() {
        return drops;
    }

    /**
     * Get sFlow packet ingress interface id.
     *
     * @return packet ingress interface id.
     */
    public int getInputInterfaceId() {
        return inputInterfaceId;
    }

    /**
     * Get sFlow packet ingress interface value.
     *
     * @return packet ingress interface value.
     */
    public int getInputInterfaceValue() {
        return inputInterfaceValue;
    }

    /**
     * Get sFlow packet egress interface id.
     *
     * @return packet egress interface id.
     */
    public int getOutputInterfaceId() {
        return outputInterfaceId;
    }

    /**
     * Get sFlow packet egress interface value.
     *
     * @return packet egress interface value.
     */
    public int getOutputInterfaceValue() {
        return outputInterfaceValue;
    }

    /**
     * Get sFlow flow records.
     *
     * @return flow records.
     */
    public List<Object> getRecords() {
        return records;
    }

    /**
     * Data deserializer function for flow sample data.
     *
     * @return data deserializer function
     */
    public static Deserializer<FlowSample> deserializer() {
        return (data, offset, length) -> {
            return null;
        };
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.type);
        hash = 59 * hash + this.length;
        hash = 59 * hash + this.sequenceNumber;
        hash = 59 * hash + this.sourceId;
        hash = 59 * hash + this.numberOfRecords;
        hash = 59 * hash + this.samplingRate;
        hash = 59 * hash + this.samplePool;
        hash = 59 * hash + this.inputInterfaceId;
        hash = 59 * hash + this.outputInterfaceId;
        hash = 59 * hash + Objects.hashCode(this.records);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FlowSample other = (FlowSample) obj;
        if (this.type != other.type) {
            return false;
        }
        if (this.length != other.length) {
            return false;
        }
        if (this.sequenceNumber != other.sequenceNumber) {
            return false;
        }
        if (this.sourceId != other.sourceId) {
            return false;
        }
        if (this.sourceIndex != other.sourceIndex) {
            return false;
        }
        if (this.numberOfRecords != other.numberOfRecords) {
            return false;
        }
        if (this.samplingRate != other.samplingRate) {
            return false;
        }
        if (this.samplePool != other.samplePool) {
            return false;
        }
        if (this.drops != other.drops) {
            return false;
        }
        if (this.inputInterfaceId != other.inputInterfaceId) {
            return false;
        }
        if (this.outputInterfaceId != other.outputInterfaceId) {
            return false;
        }
        return Objects.equals(this.records, other.records);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .add("enterprise", enterprise)
                .add("type", type)
                .add("length", length)
                .add("sequenceNumber", sequenceNumber)
                .add("sourceId", sourceId)
                .add("sourceIndex", sourceIndex)
                .add("numberOfRecords", numberOfRecords)
                .add("samplingRate", samplingRate)
                .add("samplePool", samplePool)
                .add("drops", drops)
                .add("inputInterfaceId", inputInterfaceId)
                .add("inputInterfaceValue", inputInterfaceValue)
                .add("outputInterfaceId", outputInterfaceId)
                .add("outputInterfaceValue", outputInterfaceValue)
                .add("records", records)
                .toString();
    }

    /**
     * Builder for sFlow flow sample.
     */
    private static class Builder {

        private int enterprise;

        private SflowSample.Type type;

        private int length;

        private int sequenceNumber;

        private int sourceId;

        private int sourceIndex;

        private int numberOfRecords;

        private int samplingRate;

        private int samplePool;

        private int drops;

        private int inputInterfaceId;

        private int inputInterfaceValue;

        private int outputInterfaceId;

        private int outputInterfaceValue;

        private List<Object> records = new LinkedList<>();

        /**
         * Setter sFlow packet sampling rate.
         *
         * @param samplingRate sampling rate.
         * @return this class builder.
         */
        public Builder samplingRate(int samplingRate) {
            this.samplingRate = samplingRate;
            return this;
        }

        /**
         * Setter sFlow packet sampling pool.
         *
         * @param samplePool sampling pool.
         * @return this class builder.
         */
        public Builder samplePool(int samplePool) {
            this.samplePool = samplePool;
            return this;
        }

        /**
         * Setter sFlow packet drops.
         *
         * @param drops packet drops.
         * @return this class builder.
         */
        public Builder drops(int drops) {
            this.drops = drops;
            return this;
        }

        /**
         * Setter sFlow packet ingress interface.
         *
         * @param inputInterfaceId ingress interface id.
         * @return this class builder.
         */
        public Builder inputInterfaceId(int inputInterfaceId) {
            this.inputInterfaceId = inputInterfaceId;
            return this;
        }

        /**
         * Setter sFlow packet ingress interface value.
         *
         * @param inputInterfaceValue ingress interface value.
         * @return this class builder.
         */
        public Builder inputInterfaceValue(int inputInterfaceValue) {
            this.inputInterfaceValue = inputInterfaceValue;
            return this;
        }

        /**
         * Setter sFlow packet egress interface.
         *
         * @param outputInterfaceId egress interface id.
         * @return this class builder.
         */
        public Builder outputInterfaceId(int outputInterfaceId) {
            this.outputInterfaceId = outputInterfaceId;
            return this;
        }

        /**
         * Setter sFlow packet egress interface value.
         *
         * @param outputInterfaceValue egress interface value.
         * @return this class builder.
         */
        public Builder outputInterfaceValue(int outputInterfaceValue) {
            this.outputInterfaceValue = outputInterfaceValue;
            return this;
        }

        /**
         * Setter sFlow flow packet record.
         *
         * @param records flow packet records.
         * @return this class builder.
         */
        public Builder records(List<Object> records) {
            this.records = records;
            return this;
        }

        /**
         * Setter sFlow flow packet record.
         *
         * @param record flow packet record.
         * @return this class builder.
         */
        public Builder record(Object record) {
            this.records.add(record);
            return this;
        }

        /**
         * Setter sFlow flow packet record count.
         *
         * @param numberOfRecords flow packet record count.
         * @return this class builder.
         */
        public Builder numberOfRecords(int numberOfRecords) {
            this.numberOfRecords = numberOfRecords;
            return this;
        }

        /**
         * Setter sFlow agent source index.
         *
         * @param sourceIndex agent source index.
         * @return this class builder.
         */
        public Builder sourceIndex(int sourceIndex) {
            this.sourceIndex = sourceIndex;
            return this;
        }

        /**
         * Setter sFlow agent source id.
         *
         * @param sourceId agent source id.
         * @return this class builder.
         */
        public Builder sourceId(int sourceId) {
            this.sourceId = sourceId;
            return this;
        }

        /**
         * Setter sFlow flow packet sequence number.
         *
         * @param sequenceNumber flow packet sequence number.
         * @return this class builder.
         */
        public Builder sequenceNumber(int sequenceNumber) {
            this.sequenceNumber = sequenceNumber;
            return this;
        }

        /**
         * Setter sFlow sample length.
         *
         * @param length sample length.
         * @return this class builder.
         */
        public Builder length(int length) {
            this.length = length;
            return this;
        }

        /**
         * Setter sFlow sample type.
         *
         * @param type sFlow sample type.
         * @return this class builder.
         */
        public Builder type(SflowSample.Type type) {
            this.type = type;
            return this;
        }

        /**
         * Setter sFlow enterprise id.
         *
         * @param enterprise sFlow enterprise id.
         * @return this class builder.
         */
        public Builder enterprise(int enterprise) {
            this.enterprise = enterprise;
            return this;
        }

        /**
         * Checks arguments for sFlow sample flow.
         */
        private void checkArguments() {
            checkState(type != null, "Invalid sample type.");
            checkState(sourceId != 0, "Invalid source id.");
            checkState(sequenceNumber != 0, "Invalid sequence number.");
            checkState(numberOfRecords != 0, "Invalid number of records.");
            checkState(samplingRate != 0, "Invalid sample rate.");
            checkState(inputInterfaceId != 0, "Invalid ingress interface id.");
            checkState(outputInterfaceId != 0, "Invalid egress interface id.");
            checkState(records.size() != 0, "Sample record is empty.");
        }

        /**
         * Builds sFlow sample flow.
         *
         * @return flowsample.
         */
        public FlowSample build() {
            checkArguments();
            return new FlowSample(this);
        }
    }
}