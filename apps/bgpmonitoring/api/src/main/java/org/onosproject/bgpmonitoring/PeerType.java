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

import org.onlab.packet.DeserializationException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Enum to Provide the Different types of BMP peer type.
 *
 * Peer Type = 0: Global Instance Peer
 * Peer Type = 1: RD Instance Peer
 * Peer Type = 2: Local Instance Peer
 */
public enum PeerType {

    GLOBAL_INSTANCE_PEER(0),

    RD_INSTANCE_PEER(1),

    LOCAL_INSTANCE_PEER(2);

    private final int value;

    /**
     * Assign value with the value val as the types of BMP per peer type.
     *
     * @param val type of BMP per peer type
     */
    PeerType(int val) {
        value = val;
    }

    /**
     * Returns value as type of BMP per peer type.
     *
     * @return value type of BMP per peer type
     */
    public int getType() {
        return value;
    }


    private static Map<Integer, PeerType> parser = new ConcurrentHashMap<>();

    static {
        Arrays.stream(PeerType.values()).forEach(v -> parser.put(v.value, v));
    }

    public static PeerType getType(int type) throws DeserializationException {
        if (type > 2) {
            throw new DeserializationException("Invalid bmp per peer type");
        }
        return Optional.of(type)
                .filter(id -> parser.containsKey(id))
                .map(id -> parser.get(id))
                .orElse(GLOBAL_INSTANCE_PEER);
    }
}
