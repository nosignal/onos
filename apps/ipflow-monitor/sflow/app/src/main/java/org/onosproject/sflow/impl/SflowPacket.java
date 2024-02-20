/*
 * Copyright 2023-present Open Networking Foundation
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

package org.onosproject.sflow.impl;

import java.util.LinkedList;
import java.util.List;
import com.google.common.base.MoreObjects;
import java.util.Objects;

import org.onosproject.sflow.SflowSample;
import org.onlab.packet.Deserializer;
import org.onlab.packet.BasePacket;

import static com.google.common.base.Preconditions.checkState;

/**
 * The sFlow Datagram structure permits multiple samples to be included in each
 * datagram, the sFlow Agent must not wait for a buffer to fill with samples
 * before sending the sFlow Datagram. sFlow is intended to provide timely
 * information on traffic
 * Ref: (A) https://www.ietf.org/rfc/rfc3176.txt
 * (B) https://sflow.org/sflow_version_5.txt
 * The Packet Header format is specified as:
 * <p>
 * 0                   1                   2                   3
 * 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * |                    sFlow Version Number                       |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * |                     Agent IP Version                          |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * |                     Agent IP Address                          |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * |                      SubAgent ID                              |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * |                     Sequence Number                           |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * |                     System Up Time                            |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * |                     Number of Samples                         |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * |                     Sample Data Headers                       |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 */

public final class SflowPacket extends BasePacket {

    private int version; //current supported version is 5
    private int agentIpVersion; // current supported version 1=v4, 2=v6
    private String agentAddress; // sflow agent IP address
    private int subAgentID; //sflow implemented on distributed device
    private int seqNumber; // To overcome spoofed attacks of spoofed sflow dgrams
    private int sysUptime; // Milliseconds since device last booted
    private int numberOfSamples; // number of samplings
    private List<SflowSample> sFlowsample; // List of sampling data headers

    private SflowPacket(Builder builder) {
        this.version = builder.version;
        this.agentIpVersion = builder.agentIpVersion;
        this.agentAddress = builder.agentAddress;
        this.subAgentID = builder.subAgentID;
        this.seqNumber = builder.seqNumber;
        this.sysUptime = builder.sysUptime;
        this.numberOfSamples = builder.numberOfSamples;
        this.sFlowsample = builder.sflowSample;
    }


    /**
     * Returns Version of Flow entry ; Supported Version 2,4,5.
     *
     * @return version number
     */
    public int getVersion() {
        return version;
    }

    /**
     * Returns The address type of the address associated with this agent.
     * Supported Version IPv4
     *
     * @return version number
     */
    public int getAgentIpVersion() {
        return agentIpVersion;
    }

    /**
     * Returns The IP address associated with this agent ; Supported Version IPv4.
     * The IP address associated with this agent. In the case of a multi-homed
     * agent, this should be the loopback address of the agent. The sFlowAgent
     * address must provide SNMP connectivity to the agent. The address should be an
     * invariant that does not change as interfaces are reconfigured, enabled,
     * disabled, added or removed. A manager should be able to use the
     * sFlowAgentAddress as a unique key that will identify this agent over extended
     * periods of time so that a history can be maintained.
     *
     * @return IP Address
     */
    public String getAgentAddress() {
        return agentAddress;
    }

    /**
     * Returns : The sub-agent field is used when an sFlow agent is implemented on a.
     * distributed architecture and where it is impractical to bring the samples to
     * a single point for transmission.
     *
     * @return IP Address of subAgent
     */
    public int getSubAgentID() {
        return subAgentID;
    }

    /**
     * Returns Sequence number of flow entry . Incremented with each flow sample.
     * generated by this source_id
     *
     * @return sequence number
     */
    public int getSeqNumber() {
        return seqNumber;
    }

    /**
     * Returns time in milliseconds since this device was first booted.
     *
     * @return system up time
     **/
    public int getSysUptime() {
        return sysUptime;
    }

    /**
     * Returns number of samplings generated in sFlow datagram.
     *
     * @return Number of samplings
     **/

    public int getNumberSample() {
        return numberOfSamples;
    }

    /**
     * Returns number of samplings generated in sFlow datagram. An sFlow Datagram.
     * contains lists of Packet Flow Records and counter records. The format of each
     * Packet Flow Record is identified by a data_format value. The data_format name
     * space is extensible, allowing for the addition of standard record types as
     * well as vendor specific extensions.
     *
     * @return List of Sample data Headers
     **/
    public List<SflowSample> getSampleDataHeaders() {
        return sFlowsample;
    }

    /**
     * Deserializer function for sFlow packets.
     *
     * @return deserializer function
     */
    public static Deserializer<SflowPacket> deserializer() {
        return (data, offset, length) -> null;
    }

    @Override
    public byte[] serialize() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + this.version;
        hash = 59 * hash + this.agentIpVersion;
        hash = 59 * hash + this.seqNumber;
        hash = 59 * hash + this.subAgentID;
        hash = 59 * hash + this.sysUptime;
        hash = 59 * hash + this.numberOfSamples;
        hash = 59 * hash + Objects.hashCode(this.agentAddress);
        hash = 59 * hash + Objects.hashCode(this.sFlowsample);
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
        final SflowPacket other = (SflowPacket) obj;
        if (this.version != other.version) {
            return false;
        }
        if (this.agentIpVersion != other.agentIpVersion) {
            return false;
        }
        if (this.seqNumber != other.seqNumber) {
            return false;
        }
        if (this.subAgentID != other.subAgentID) {
            return false;
        }
        if (this.sysUptime != other.sysUptime) {
            return false;
        }
        if (this.numberOfSamples != other.numberOfSamples) {
            return false;
        }
        return Objects.equals(this.agentAddress, other.agentAddress);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .add("version", version)
                .add("agentIpVersion", agentIpVersion)
                .add("seqNumber", seqNumber)
                .add("subAgentID", subAgentID)
                .add("sysUptime", sysUptime)
                .add("numberOfSamples", numberOfSamples)
                .add("agentAddress", agentAddress)
                .toString();
    }

    /**
     * Builder for sFlow packet.
     */
    private static class Builder {
        private int version;
        private int agentIpVersion;
        private String agentAddress;
        private int subAgentID;
        private int seqNumber;
        private int sysUptime;
        private int numberOfSamples;
        private List<SflowSample> sflowSample = new LinkedList<>();

        /**
         * Setter sFlow Version of Flow entry ; Supported Version 2,4,5.
         *
         * @param version number.
         * @return this class builder.
         */
        public Builder version(int version) {
            this.version = version;
            return this;
        }

        /**
         * Setter for The address type of the address associated with this agent.
         *
         * @param agent ip version.
         * @return this class builder.
         */
        public Builder agentIpVersion(int agentIpVersion) {
            this.agentIpVersion = agentIpVersion;
            return this;
        }

        /**
         * Setter The IP address associated with this agent.
         *
         * @param IP Address.
         * @return this class builder.
         */
        public Builder agentAddress(String agentAddress) {
            this.agentAddress = agentAddress;
            return this;
        }

        /**
         * Setter for time in milliseconds since this device was first booted.
         *
         * @param agent id.
         * @return this class builder.
         */
        public Builder subAgentID(int subAgentID) {
            this.subAgentID = subAgentID;
            return this;
        }

        /**
         * Setter for number of samplings generated in sFlow datagram.
         *
         * @param Number of samplings.
         * @return this class builder.
         */
        public Builder numberOfSamples(int numberOfSamples) {
            this.numberOfSamples = numberOfSamples;
            return this;
        }

        /**
         * Setter for time in milliseconds since this device was first booted.
         *
         * @param sysUptime system up time.
         * @return this class builder.
         */
        public Builder sysUptime(int sysUptime) {
            this.sysUptime = sysUptime;
            return this;
        }

        /**
         * Returns Sequence number of flow entry . Incremented with each flow sampling.
         * generated by this source_id
         *
         * @param sequence number.
         * @return this class builder.
         */
        public Builder seqNumber(int seqNumber) {
            this.seqNumber = seqNumber;
            return this;
        }

        /**
         * Setter for list of samplings.
         *
         * @param sflowSample list of samplings.
         * @return this class builder.
         */
        public Builder sflowSample(SflowSample sflowSample) {
            this.sflowSample.add(sflowSample);
            return this;
        }


        /**
         * Checks arguments for sFlow packet.
         */
        private void checkArguments() {
            checkState(version != 0, "Invalid Version.");
            checkState(agentIpVersion != 0, "Invalid ipVersionAgent.");
            checkState(subAgentID != 0, "Invalid subAgentID.");
            checkState(seqNumber != 0, "Invalid SeqNumber.");
            checkState(sysUptime != 0, "Invalid sysUptime.");
        }

        /**
         * Builds sFlow packet.
         *
         * @return sflowpacket.
         */
        public SflowPacket build() {
            checkArguments();
            return new SflowPacket(this);
        }

    }
}
