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

package org.onosproject.bgpmonitoring;

import java.net.InetAddress;

/**
 * Abstraction of an BMP Per Peer header.
 */
public interface PerPeer {

    /**
     * Returns BMP per peer header of BMP message.
     *
     * @return BMP per peer header of BMP message
     */
    PeerType getType();

    /**
     * Returns BMP per peer flag.
     *
     * @return BMP BMP per peer flag
     */
    byte getFlag();

    /**
     * Returns BMP per peer distinguisher.
     *
     * @return BMP per peer distinguisher
     */
    byte[] getPeerDistinguisher();

    /**
     * Returns BMP per peer inet address.
     *
     * @return BMP per peer inet address
     */
    InetAddress getIntAddress();

    /**
     * Returns BMP per peer bgp AS.
     *
     * @return BMP per peer bgp AS
     */
    int getPeerAs();

    /**
     * Returns BMP per peer bgp ID.
     *
     * @return BMP per peer bgp ID
     */
    String getPeerBgpId();

    /**
     * Returns BMP per peer message sent time in seconds.
     *
     * @return BMP per peer message sent time in seconds
     */
    int getSeconds();

    /**
     * Returns BMP per peer message sent time in micro seconds.
     *
     * @return BMP per peer message sent time in micro seconds
     */
    int getMicroseconds();

    /**
     * Returns BMP per peer header has ipv6 address or not.
     *
     * @return BMP per peer header has ipv6 address or not
     */
    boolean isIpv6();
}