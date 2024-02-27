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

import java.util.List;
import org.onlab.packet.Deserializer;

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


    private List<Object> records;


    /**
     * Data deserializer function for flow interface counter.
     *
     * @return data deserializer function
     */
    public static Deserializer<CounterSample> deserializer() {
        return (data, offset, length) -> {
            return null;
        };
    }

}
