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

import org.onlab.packet.BasePacket;

/**
 * Abstraction of an entity providing BMP Packet.
 */
public abstract class BmpPacket extends BasePacket {

    @Override
    public byte[] serialize() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Returns BMP packet length.
     *
     * @return BMP packet length
     */
    public abstract int getLength();

    /**
     * Returns BMP packet version.
     *
     * @return BMP packet version
     */
    public abstract BmpVersion getVersion();

    /**
     * Returns BMP message type.
     *
     * @return BMP message type
     */
    public abstract String getType();

    /**
     * Returns BMP message.
     *
     * @return BMP message
     */
    public abstract BmpMessage getMessage();

}
