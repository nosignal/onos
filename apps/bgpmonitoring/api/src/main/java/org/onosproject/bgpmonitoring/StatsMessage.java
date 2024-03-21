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

import java.util.List;

/**
 * Abstraction of an BMP statistics report message.
 */
public abstract class StatsMessage extends BmpMessage {

    public static final int STATS_REPORT_HEADER_MIN_LENGTH = 4;

    /**
     * Returns BMP Peer Header of BMP Message.
     *
     * @return BMP Peer Header of BMP Message
     */
    public abstract PerPeer getPerPeer();

    /**
     * Returns number of BMP statistics records.
     *
     * @return number of BMP statistics records
     */
    public abstract int getStatsCount();

    /**
     * Returns BGP statistics.
     *
     * @return BGP statistics
     */
    public abstract List<BmpStats> getStats();

}