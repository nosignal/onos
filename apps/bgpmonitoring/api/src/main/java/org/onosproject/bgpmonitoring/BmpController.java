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

/**
 * Service for interacting with the BMP controller.
 */
public interface BmpController {

    /**
     * Start listening bmp messages from bmp router.
     */
    void startListener();

    /**
     * Stop listening bmp messages from bmp router.
     */
    void closeListener();

    /**
     * Process bmp message and notify the appropriate listeners.
     *
     * @param packet   the BMP packet to process.
     * @throws BmpParseException on data processing error
     */
    void processBmpPacket(BmpPacket packet) throws BmpParseException;

}
