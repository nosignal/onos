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
 * Enum to provide BMP Message Version.
 */
public enum BmpVersion {

    BMP_3(3);

    public final int packetVersion;

    /**
     * Assign BMP PacketVersion with specified packetVersion.
     *
     * @param packetVersion version of BMP
     */
    BmpVersion(final int packetVersion) {
        this.packetVersion = packetVersion;
    }

    /**
     * Returns Packet version of BMP Message.
     *
     * @return packetVersion
     */
    public int getPacketVersion() {
        return packetVersion;
    }

    private static Map<Integer, BmpVersion> parser = new ConcurrentHashMap<>();

    static {
        Arrays.stream(BmpVersion.values()).forEach(v -> parser.put(v.packetVersion, v));
    }

    public static BmpVersion getVersion(int version) throws DeserializationException {
        if (version != 3) {
            throw new DeserializationException("Invalid bmp version");
        }
        return Optional.of(version)
                .filter(id -> parser.containsKey(id))
                .map(id -> parser.get(id))
                .orElse(BMP_3);
    }
}