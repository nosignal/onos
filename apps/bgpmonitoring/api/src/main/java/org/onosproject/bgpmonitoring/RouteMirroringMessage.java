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
 * Abstraction of an BMP route mirroring message.
 */
public abstract class RouteMirroringMessage extends BmpMessage {

    public static final int ROUTE_MIRRORING_HEADER_MIN_LENGTH = 4;

    /**
     * Returns Bgp route mirroring type.
     *
     * @return Bgp route mirroring type
     */
    public abstract MirroringType getMirroringType();

    /**
     * Returns Bgp route mirroring length.
     *
     * @return Bgp route mirroring length
     */
    public abstract int getMirroringLength();

    /**
     * Returns Bgp message.
     *
     * @return Bgp message
     */
    public abstract BgpMessage getBgpMessage();

    /**
     * Returns BMP per peer header.
     *
     * @return BMP per peer header
     */
    public abstract PerPeer getPerPeer();

}