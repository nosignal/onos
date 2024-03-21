/*
 * Copyright 2021-present Open Networking Foundation
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
 * Enum to Provide the Different types of BMP peer down reasons.
 */
public enum PeerDownReason {

    LOCAL_SYSTEM_CLOSED_SESSION_WITH_NOTIFICATION(1),

    LOCAL_SYSTEM_CLOSED_SESSION_WITHOUT_NOTIFICATION(2),

    REMOTE_SYSTEM_CLOSED_SESSION_WITH_NOTIFICATION(3),

    REMOTE_SYSTEM_CLOSED_SESSION_WITHOUT_NOTIFICATION(4);


    private final int value;

    /**
     * Assign value with the value val as the types of BMP peer down reasons.
     *
     * @param val type of BMP peer down reasons
     */
    PeerDownReason(int val) {
        value = val;
    }

    /**
     * Returns value as type of BMP peer down reasons.
     *
     * @return value type of BMP peer down reasons
     */
    public int getReason() {
        return value;
    }


    private static Map<Integer, PeerDownReason> parser = new ConcurrentHashMap<>();

    static {
        Arrays.stream(PeerDownReason.values()).forEach(v -> parser.put(v.value, v));
    }

    public static PeerDownReason getType(int type) throws DeserializationException {
        if (type > 4) {
            throw new DeserializationException("Invalid bmp peer down reason type");
        }
        return Optional.of(type)
                .filter(id -> parser.containsKey(id))
                .map(id -> parser.get(id))
                .orElse(LOCAL_SYSTEM_CLOSED_SESSION_WITH_NOTIFICATION);
    }
}
