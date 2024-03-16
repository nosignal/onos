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
import java.util.function.BiPredicate;
import java.nio.ByteBuffer;

import com.google.common.base.MoreObjects;

import static com.google.common.base.Preconditions.checkState;

/**
 * the sFlow Agent keep a list of counter sources being sampled.
 * When a flow sample is generated the
 * sFlow Agent examines the list and adds counters to the sample
 * datagram, least recently sampled first.  Counters are only added to
 * the datagram if the sources are within a short period,
 * of failing to meet the required sampling interval (see
 * sFlowCounterSamplingInterval in SFLOW MIB).  Whenever a counter
 * source's statistics are added to a sample datagram, the time the
 * counter source was last sampled is updated and the counter source is
 * placed at the end of the list.  Periodically, say every second, the
 * sFlow Agent examines the list of counter sources and sends any
 * counters that need to be sent to meet the sampling interval
 * requirement.
 * Ref: https://www.ietf.org/rfc/rfc3954.txt
 */
public final class CounterSample extends SflowSample {

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
   |              Total Number Of Counter Records                  |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                         Records                               |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    */

    public static final int COUNTER_HEADER_LENGTH = 20;
    public static final int RECORD_MIN_HEADER_LENGTH = 8;

    private List<CounterRecord> records;

    private CounterSample(Builder builder) {
        this.enterprise = builder.enterprise;
        this.type = builder.type;
        this.length = builder.length;
        this.sequenceNumber = builder.sequenceNumber;
        this.sourceId = builder.sourceId;
        this.sourceIndex = builder.sourceIndex;
        this.numberOfRecords = builder.numberOfRecords;
        this.records = builder.records;
    }

    /**
     * Get sFlow counter records.
     *
     * @return counter records.
     */
    public List<CounterRecord> getRecords() {
        return records;
    }

    /**
     * Data deserializer function for flow interface counter.
     *
     * @return data deserializer function
     */
    public static Deserializer<CounterSample> deserializer() {
        return (data, offset, length) -> {
            BiPredicate<ByteBuffer, Integer> isValidBuffer = (b, l)
                    -> b.hasRemaining() && b.remaining() >= l;

            ByteBuffer bb = ByteBuffer.wrap(data, offset, length);
            if (!isValidBuffer.test(bb, (COUNTER_HEADER_LENGTH + RECORD_MIN_HEADER_LENGTH))) {
                throw new IllegalStateException("Invalid interface sample counter buffer size.");
            }
            Builder builder = new Builder();
            builder.type(SflowSample.Type.getType(bb.getInt()))
                    .length(bb.getInt())
                    .sequenceNumber(bb.getInt())
                    .sourceIndex(bb.getInt())
                    .numberOfRecords(bb.getInt());

            while (bb.hasRemaining()) {
                int counterType = bb.getInt();
                int counterLength = bb.getInt();
                bb.position(bb.position() - RECORD_MIN_HEADER_LENGTH);
                int recordLenght = counterLength + RECORD_MIN_HEADER_LENGTH;
                if (bb.remaining() < recordLenght) {
                    break;
                }
                byte[] recordBytes = new byte[recordLenght];
                bb.get(recordBytes);
                builder.record(CounterRecord.deserializer()
                        .deserialize(recordBytes, 0, recordLenght));
            }
            return builder.build();

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
        final CounterSample other = (CounterSample) obj;
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
                .add("records", records)
                .toString();
    }

    /**
     * Builder for sFlow counter sample packet.
     */
    private static class Builder {

        private int enterprise;

        private SflowSample.Type type;

        private int length;

        private int sequenceNumber;

        private int sourceId;

        private int sourceIndex;

        private int numberOfRecords;

        private List<CounterRecord> records = new LinkedList<>();

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
         * Setter sFlow interface counter records.
         *
         * @param records interface counter records.
         * @return this class builder.
         */
        public Builder records(List<CounterRecord> records) {
            this.records = records;
            return this;
        }

        /**
         * Setter sFlow sample interface counter record.
         *
         * @param record sample interface counter record.
         * @return this class builder.
         */
        public Builder record(CounterRecord record) {
            this.records.add(record);
            return this;
        }

        /**
         * Checks arguments for sFlow sample interface counter.
         */
        private void checkArguments() {
            checkState(type != null, "Invalid sample type.");
            checkState(sourceId != 0, "Invalid source id.");
            checkState(sequenceNumber != 0, "Invalid sequence number.");
            checkState(numberOfRecords != 0, "Invalid number of records.");
            checkState(records.size() != 0, "Interface counter record is empty.");
        }

        /**
         * Builds sFlow interface counter sample.
         *
         * @return interface counter sample.
         */
        public CounterSample build() {
            checkArguments();
            return new CounterSample(this);
        }
    }
}