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

/**
 * Enum to Provide the interface of BMP initiation message.
 */
public interface BmpMsg {

    /**
     * Returns BMP initiation message type.
     *
     * @return BMP initiation message type
     */
    public abstract short getType();

    /**
     * Returns BMP initiation message length.
     *
     * @return BMP initiation message length
     */
    public abstract short getLength();

    /**
     * Returns BMP initiation message.
     *
     * @return BMP initiation message
     */
    public abstract String getData();

}