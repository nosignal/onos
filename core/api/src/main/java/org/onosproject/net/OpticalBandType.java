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
 *
 * This Work is contributed by CNR within the B5G-OPEN project.
 */

package org.onosproject.net;


/**
 * Represents type of optical bands.
 *
 */
public enum OpticalBandType {
    L_BAND, // Starts at 184500 GHz ends at 191500 GHz
    C_BAND, // Starts at 191500 GHz ends at 195500 GHz
    S_BAND; // Starts at 195500 GHz ends at 205300 GHz
}

