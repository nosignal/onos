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

import org.onlab.util.Identifier;

/**
 * Template Records is given a unique Template ID.
 * This uniqueness is local to the Observation
 * Domain that generated the Template ID.  Template IDs 0-255 are
 * reserved for Template FlowSets, Options FlowSets, and other
 * reserved FlowSets yet to be created.  Template IDs of Data
 * FlowSets are numbered from 256 to 65535.
 * Ref: https://www.ietf.org/rfc/rfc3954.txt
 */
public class TemplateId extends Identifier<Integer> {

    /**
     * Default constructor.
     *
     * @param id template id.
     */
    public TemplateId(int id) {
        super(id);
    }

    /**
     * Get the value of the template id.
     *
     * @return the value of the template id.
     */
    public int getId() {
        return identifier;
    }

    /**
     * Get the value of the templateid object.
     *
     * @param id template id.
     * @return the value of the templateid object.
     */
    public static TemplateId valueOf(int id) {
        return new TemplateId(id);
    }

}
