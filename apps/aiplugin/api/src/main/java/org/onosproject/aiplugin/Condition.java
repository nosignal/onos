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
import java.util.Objects;

/**
 * Immutable class representing a condition with name, field, match, value, action, and aggregation.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = Condition.Builder.class)
public final class Condition {
    private String name;
    private String field;
    private String match;
    private String value;
    private String action;
    private String aggregation;

    /**
     * Private constructor for the Condition class.
     *
     * @param builder The Builder object providing field values.
     */
    private Condition(Builder builder) {
        this.name = builder.name;
        this.field = builder.field;
        this.match = builder.match;
        this.value = builder.value;
        this.action = builder.action;
        this.aggregation = builder.aggregation;
    }

    /**
     * Returns the name of the condition.
     *
     * @return The name as a string.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the field used in the condition.
     *
     * @return The field as a string.
     */
    public String getField() {
        return field;
    }

    /**
     * Returns the match operator used in the condition.
     *
     * @return The match operator as a string.
     */
    public String getMatch() {
        return match;
    }

    /**
     * Returns the value used in the condition.
     *
     * @return The value as a string.
     */
    public String getValue() {
        return value;
    }

    /**
     * Returns the action performed by the condition.
     *
     * @return The action as a string.
     */
    public String getAction() {
        return action;
    }

    /**
     * Returns the aggregation performed by the condition.
     *
     * @return The aggregation as a string.
     */
    public String getAggregation() {
        return aggregation;
    }

    /**
     * Checks if this Condition is equal to another object.
     * The result is true if and only if the argument is not null and is a Condition object that
     * has the same values for all fields.
     *
     * @param obj the object to compare this Condition against
     * @return true if the given object represents a Condition equivalent to this one, false otherwise
     */
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
        Condition other = (Condition) obj;
        return Objects.equals(name, other.name)
                && Objects.equals(field, other.field)
                && Objects.equals(match, other.match)
                && Objects.equals(value, other.value)
                && Objects.equals(action, other.action)
                && Objects.equals(aggregation, other.aggregation);
    }

    /**
     * Returns a hash code value for the Condition.
     * This method is supported for the benefit of hash tables such as those provided by HashMap.
     *
     * @return a hash code value for this Condition
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 59 * result + (name != null ? name.hashCode() : 0);
        result = 59 * result + (field != null ? field.hashCode() : 0);
        result = 59 * result + (match != null ? match.hashCode() : 0);
        result = 59 * result + (value != null ? value.hashCode() : 0);
        result = 59 * result + (action != null ? action.hashCode() : 0);
        result = 59 * result + (aggregation != null ? aggregation.hashCode() : 0);
        return result;
    }

    /**
     * Static method to create a new Builder instance.
     *
     * @return A new Builder for creating a Condition instance.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for Condition.
     */
    @JsonPOJOBuilder(withPrefix = "", buildMethodName = "build")
    public static class Builder {
        private String name;
        private String field;
        private String match;
        private String value;
        private String action;
        private String aggregation;

        /**
         * Sets the name of the condition.
         *
         * @param name The name as a string.
         * @return The Builder instance.
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the field used in the condition.
         *
         * @param field The field as a string.
         * @return The Builder instance.
         */
        public Builder field(String field) {
            this.field = field;
            return this;
        }

        /**
         * Sets the match operator used in the condition.
         *
         * @param match The match operator as a string.
         * @return The Builder instance.
         */
        public Builder match(String match) {
            this.match = match;
            return this;
        }

        /**
         * Sets the value used in the condition.
         *
         * @param value The value as a string.
         * @return The Builder instance.
         */
        public Builder value(String value) {
            this.value = value;
            return this;
        }

        /**
         * Sets the action performed by the condition.
         *
         * @param action The action as a string.
         * @return The Builder instance.
         */
        public Builder action(String action) {
            this.action = action;
            return this;
        }

        /**
         * Sets the aggregation performed by the condition.
         *
         * @param aggregation The aggregation as a string.
         * @return The Builder instance.
         */
        public Builder aggregation(String aggregation) {
            this.aggregation = aggregation;
            return this;
        }

        /**
         * Builds the Condition instance.
         * Before creating the Condition, it checks that name, field, match, and value are not null.
         *
         * @return A new Condition instance.
         */
        public Condition build() {
            Preconditions.checkNotNull(name, "Name cannot be null");
            Preconditions.checkNotNull(field, "Field cannot be null");
            Preconditions.checkNotNull(match, "Match cannot be null");
            Preconditions.checkNotNull(value, "Value cannot be null");
            return new Condition(this);
        }
    }

    /**
     * Generates a string representation of the Condition.
     *
     * @return A string representation of the Condition.
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("field", field)
                .add("match", match)
                .add("value", value)
                .add("action", action)
                .add("aggregation", aggregation)
                .toString();
    }
}
