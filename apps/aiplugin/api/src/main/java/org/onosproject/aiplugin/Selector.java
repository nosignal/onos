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
import java.util.Objects;

/**
 * Immutable class representing a Selector with fields and an action.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = Selector.Builder.class)
public final class Selector {
    private List<String> fields;
    private String action;

    /**
     * Private constructor for the Selector class.
     *
     * @param builder The Builder object providing field values.
     */
    private Selector(Builder builder) {
        this.fields = builder.fields;
        this.action = builder.action;
    }

    /**
     * Returns the fields of the selector.
     *
     * @return A list of fields.
     */
    public List<String> getFields() {
        return fields;
    }

    /**
     * Returns the action associated with the selector.
     *
     * @return The action as a string.
     */
    public String getAction() {
        return action;
    }

    /**
     * Checks if this Selector is equal to another object.
     * The result is true if and only if the argument is not null and is a Selector object with same fields and action.
     *
     * @param obj the object to compare this Selector against
     * @return true if the given object represents a Selector equivalent to this Selector, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
                return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
                return false;
        }
        Selector selector = (Selector) obj;
        return Objects.equals(fields, selector.fields) &&
               Objects.equals(action, selector.action);
    }

    /**
     * Returns a hash code value for the Selector.
     * This method is supported for the benefit of hash tables such as those provided by HashMap.
     *
     * @return a hash code value for this Selector
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 59 * result + (fields != null ? fields.hashCode() : 0);
        result = 59 * result + (action != null ? action.hashCode() : 0);
        return result;
    }

    /**
     * Static method to create a new Builder instance.
     *
     * @return A new Builder for creating a Selector instance.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for Selector.
     */
    @JsonPOJOBuilder(withPrefix = "", buildMethodName = "build")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Builder {
        private List<String> fields = new ArrayList<>();
        private String action;

        /**
         * Adds or appends a list of fields to the Selector.
         * If fields already exist, this method appends the new list to them.
         *
         * @param fields A list of field names.
         * @return The Builder instance.
         */
        public Builder fields(List<String> fields) {
            this.fields.addAll(fields);
            return this;
        }

        /**
         * Adds a single field to the Selector.
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
         * Sets the action of the Selector.
         *
         * @param action The action as a string.
         * @return The Builder instance.
         */
        public Builder action(String action) {
            this.action = action;
            return this;
        }

        /**
         * Builds the Selector instance.
         * Before creating the Selector, it checks that fields and action are not null.
         *
         * @return A new Selector instance.
         */
        public Selector build() {
            Preconditions.checkNotNull(action, "Action cannot be null");
            Preconditions.checkArgument(!fields.isEmpty(), "Fields cannot be empty");
            return new Selector(this);
        }
    }

    /**
     * Generates a string representation of the Selector.
     *
     * @return A string representation of the Selector.
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("fields", fields)
                .add("action", action)
                .toString();
    }
}
