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

import org.onlab.packet.BasePacket;

/**
 * A Template Record defines the structure and interpretation of fields.
 * in a Flow Data Record.
 */
public abstract class TemplateRecord extends BasePacket implements FlowRecord {

    @Override
    public byte[] serialize() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
