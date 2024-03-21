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

import org.onosproject.bgpio.protocol.BgpMessage;
import java.net.InetAddress;

/**
 * Abstraction of an BMP peer up notification message.
 */
public abstract class PeerUpNotificationMessage extends BmpMessage {

    public static final int PEERUP_NOTIFICATION_HEADER_MIN_LENGTH = 40;

    public static final int PADDING_BYTES = 16;

    public static final int IPV4_ADDRS = 4;

    public static final int IPV6_ADDRS = 16;

    public static final int BGP_LENGTH_FIELD = 16;

    /**
     * Returns BMP per peer header of BMP message.
     *
     * @return BMP per peer header of BMP message
     */
    public abstract PerPeer getPerPeer();

    /**
     * Returns local ip address.
     *
     * @return local ip address
     */
    public abstract InetAddress getLocalAddress();

    /**
     * Returns local port number.
     *
     * @return local port number
     */
    public abstract int getLocalPort();

    /**
     * Returns remote port number.
     *
     * @return remote port number
     */
    public abstract int getRemotePort();

    /**
     * Returns Bgp sent open message.
     *
     * @return Bgp sent open message
     */
    public abstract BgpMessage getSentOpenMsg();

    /**
     * Returns Bgp received open message.
     *
     * @return Bgp received open message
     */
    public abstract BgpMessage getReceivedOpenMsg();

    /**
     * Returns BMP peer information.
     *
     * @return BMP peer information
     */
    public abstract byte[] getInformation();

}