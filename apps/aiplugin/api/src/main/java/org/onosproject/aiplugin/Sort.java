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

package aiplugin.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;

/**
 * Immutable class representing a sorting criteria with fields and an order.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = Sort.Builder.class)
public final class Sort {
    private List<String> fields;
    private String order;

    /**
     * Private constructor for the Sort class.
     *
     * @param builder The Builder object providing field values.
     */
    private Sort(Builder builder) {
        this.fields = builder.fields;
        this.order = (builder.order != null) ? builder.order : "asc";
    }

    /**
     * Returns the fields used for sorting.
     *
     * @return A list of field names.
     */
    public List<String> getFields() {
        return fields;
    }

    /**
     * Returns the order of sorting (asc or desc).
     *
     * @return The sorting order as a string.
     */
    public String getOrder() {
        return order;
    }

    /**
     * Static method to create a new Builder instance.
     *
     * @return A new Builder for creating a Sort instance.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for Sort.
     */
    @JsonPOJOBuilder(withPrefix = "", buildMethodName = "build")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Builder {
        private List<String> fields = new ArrayList<>();
        private String order;

        /**
         * Adds or appends a list of fields to the Sort criteria.
         * If fields already exist, this method appends the new list to them.
         *
         * @param fields A list of field names.
         * @return The Builder instance for chaining.
         */
        public Builder fields(List<String> fields) {
            this.fields.addAll(fields);
            return this;
        }

        /**
         * Adds a single field to the Sort criteria.
         * This method allows adding fields one by one.
         *
         * @param field A single field name.
         * @return The Builder instance.
         */
        public Builder fields(String field) {
            this.fields.add(field);
            return this;
        }

        /**
         * Sets the sorting order for the Sort criteria.
         *
         * @param order The sorting order ("asc" for ascending or "desc" for descending).
         * @return The Builder instance.
         */
        public Builder order(String order) {
            this.order = order;
            return this;
        }

        /**
         * Builds the Sort instance.
         * Before creating the Sort, it checks that fields and order are not null.
         *
         * @return A new Sort instance.
         */
        public Sort build() {
            Preconditions.checkNotNull(fields, "Fields cannot be null");
            return new Sort(this);
        }
    }

    /**
     * Generates a string representation of the Sort criteria.
     *
     * @return A string representation of the Sort criteria.
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("fields", fields)
                .add("order", order)
                .toString();
    }
}
