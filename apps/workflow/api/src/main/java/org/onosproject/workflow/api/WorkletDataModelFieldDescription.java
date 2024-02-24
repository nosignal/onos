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
package org.onosproject.workflow.api;

import com.google.common.base.MoreObjects;

import java.util.Objects;

/**
 * Description of work-let data model field.
 */
public class WorkletDataModelFieldDescription {

    /**
     * Type name of work-let.
     */
    private final String workletType;

    /**
     * Data model path.
     */
    private final String path;

    /**
     * Class of work-let field.
     */
    private final Class fieldType;

    /**
     * Option for whether this field is mandatory or optional.
     */
    private final boolean optional;

    /**
     * Gets path attribute of this data model.
     * @return path attribute of this data model
     */
    public String path() {
        return this.path;
    }

    /**
     * Constructor of worklet data model field description.
     *
     * @param workletType worklet type
     * @param path        path of data model
     * @param fieldType        type of data model
     * @param optional    optional
     */
    public WorkletDataModelFieldDescription(String workletType, String path, Class fieldType, boolean optional) {
        this.workletType = workletType;
        this.path = path;
        this.fieldType = fieldType;
        this.optional = optional;
    }

    /**
     * Checks the attributes of worklet data model field.
     *
     * @param desc worklet data model description
     * @return true means that this worklet data model field description has same attributes with desc
     */
    public boolean hasSameAttributes(WorkletDataModelFieldDescription desc) {

        if (!Objects.equals(fieldType, desc.fieldType)) {
            return false;
        }
        return Objects.equals(optional, desc.optional);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.workletType);
        hash = 59 * hash + Objects.hashCode(this.path);
        hash = 59 * hash + Objects.hashCode(this.fieldType);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WorkletDataModelFieldDescription other = (WorkletDataModelFieldDescription) obj;

        if (!Objects.equals(this.workletType, other.workletType)) {
            return false;
        }
        if (!Objects.equals(this.path, other.path)) {
            return false;
        }
        return Objects.equals(this.fieldType, other.fieldType);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .add("worklet", workletType)
                .add("path", path)
                .add("fieldType", fieldType)
                .add("optional", optional)
                .toString();
    }
}

