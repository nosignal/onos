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

/**
 * Abstraction of an BMP peer down notification message.
 */
public abstract class PeerDownNotificationMessage extends BmpMessage {

    public static final int PEERDOWN_NOTIFICATION_HEADER_MIN_LENGTH = 4;

    /**
     * Returns BMP peer down reason.
     *
     * @return BMP peer down reason
     */
    public abstract PeerDownReason getReason();

    /**
     * Returns Bgp message.
     *
     * @return Bgp message
     */
    public abstract BgpMessage getBgpMessage();

    /**
     * Returns BMP Peer Header of BMP Message.
     *
     * @return BMP Peer Header of BMP Message
     */
    public abstract PerPeer getPerPeer();

}