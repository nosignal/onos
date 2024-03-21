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
 * Enum to Provide the Different types of BMP termination message.
 */
public enum TerminationType {

    UTF8_STRING(0),

    REASON(1);

    private final int value;

    /**
     * Assign value with the value val as the types of BMP termination message.
     *
     * @param val type of BMP termination message
     */
    TerminationType(int val) {
        value = val;
    }

    /**
     * Returns value as type of BMP termination message.
     *
     * @return value type of BMP termination message
     */
    public int getType() {
        return value;
    }


    private static Map<Integer, TerminationType> parser = new ConcurrentHashMap<>();

    static {
        Arrays.stream(TerminationType.values()).forEach(v -> parser.put(v.value, v));
    }

    public static TerminationType getType(int type) throws DeserializationException {
        if (type > 2) {
            throw new DeserializationException("Invalid bmp termination message type");
        }
        return Optional.of(type)
                .filter(id -> parser.containsKey(id))
                .map(id -> parser.get(id))
                .orElse(REASON);
    }

}
