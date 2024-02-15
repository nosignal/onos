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

/**
 * sFlow is a technology for monitoring traffic in data networks containing switches and routers.
 * In particular, it defines the traffic sampling mechanisms implemented in
 * sFlow Agents, the sFlow MIB for configuring sFlow Agents, and the format
 * of the sFlow Datagram that carries traffic measurement data from sFlow
 * Agents to an sFlow Collector.
 * Ref : https://datatracker.ietf.org/doc/html/rfc3176
 */
public interface SflowController {

    /**
     * Get total sFlow sample packets counter.
     * Total packets is a count of all the packets that could
     * have been sampled.
     *
     * @return total number sFlow sample packet counter.
     */
    int getSampleCount();

}
