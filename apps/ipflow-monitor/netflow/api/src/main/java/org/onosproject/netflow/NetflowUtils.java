/*
 * Copyright 2023-present Open Networking Foundation
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
package org.onosproject.netflow;

import java.nio.ByteBuffer;
import java.util.function.BiFunction;
import org.onlab.packet.IpAddress;


/**
 * Netflow utility.
 */
public final class NetflowUtils {
    private NetflowUtils() {
    }

    public static final BiFunction<ByteBuffer, Integer, Object> VAR_BYTE = (bb, len) -> {
        return bb.get();
    };

    public static final BiFunction<ByteBuffer, Integer, Object> VAR_UNSIGNED_BYTE = (bb, len) -> {
        return getUnsignedByte(bb);
    };

    public static final BiFunction<ByteBuffer, Integer, Object> VAR_SHORT = (bb, len) -> {
        return bb.getShort();
    };

    public static final BiFunction<ByteBuffer, Integer, Object> VAR_UNSIGNED_SHORT = (bb, len) -> {
        return asUnsignedShort(bb);
    };

    public static short getUnsignedByte(ByteBuffer bb) {
        return ((short) (bb.get() & 0xFF));
    }

    public static int asUnsignedShort(ByteBuffer bb) {
        return bb.getShort() & 0xFFFF;
    }

    public static long getUnsignedInt(ByteBuffer bb) {
        return ((long) bb.getInt() & 0xFFFFFFFFL);
    }

    public static final BiFunction<ByteBuffer, Integer, Object> VAR_INT = (bb, len) -> {
        return bb.getInt();
    };

    public static final BiFunction<ByteBuffer, Integer, Object> VAR_UNSIGNED_INT = (bb, len) -> {
        return getUnsignedInt(bb);
    };

    public static final BiFunction<ByteBuffer, Integer, Object> VAR_FLOAT = (bb, len) -> {
        return bb.getFloat();
    };
    public static final BiFunction<ByteBuffer, Integer, Object> VAR_LONG = (bb, len) -> {
        return bb.getLong();
    };

    public static final BiFunction<ByteBuffer, Integer, Object> NULL = (bb, len) -> {
        byte[] address = new byte[len];
        bb.get(address);
        return null;
    };

    public static final BiFunction<ByteBuffer, Integer, byte[]> BYTE_ARRAY = (bb, len) -> {
        byte[] bytes = new byte[len];
        bb.get(bytes);
        return bytes;
    };

    public static final BiFunction<ByteBuffer, Integer, Object> VAR_INT_LONG = (bb, len) -> {
        if (len == 4) {
            return bb.getInt();
        }
        return bb.getLong();
    };

    public static final BiFunction<ByteBuffer, Integer, Object> VAR_SHORT_INT = (bb, len) -> {
        if (len == 2) {
            return bb.getShort();
        }
        return bb.getInt();
    };

    public static final BiFunction<ByteBuffer, Integer, Object> VAR_IPV4_ADDRESS = (bb, len) -> {
        byte[] value = getByteArray(bb, len);
        return IpAddress.valueOf(IpAddress.Version.INET, value).toInetAddress().getHostAddress();
    };

    public static final BiFunction<ByteBuffer, Integer, Object> VAR_IPV6_ADDRESS = (bb, len) -> {
        byte[] value = getByteArray(bb, len);
        return IpAddress.valueOf(IpAddress.Version.INET6, value).toInetAddress().getHostAddress();
    };

    public static final BiFunction<ByteBuffer, Integer, Object> VAR_MAC = (bb, len) -> {
        byte[] mac = new byte[len];
        bb.get(mac);
        //return MacAddress.valueOf(mac);;
        return null;
    };

    private static byte[] getByteArray(ByteBuffer bb, int length) {
        byte[] bytes = new byte[length];
        bb.get(bytes);
        return bytes;
    }

}
