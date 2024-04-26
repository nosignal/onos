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
 * Class representing a prediction template.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = PredictionTemplate.Builder.class)
public final class PredictionTemplate {
    private String templateVersion;
    private String type;
    private String id;
    private String datasource;
    private Selector selector;
    private Group group;
    private Sort sort;
    private Condition condition;

    /**
     * Private constructor for the PredictionTemplate class.
     *
     * @param builder The Builder object providing field values.
     */
    private PredictionTemplate(Builder builder) {
        this.templateVersion = builder.templateVersion;
        this.type = builder.type;
        this.id = builder.id;
        this.datasource = builder.datasource;
        this.selector = builder.selector;
        this.group = builder.group;
        this.sort = builder.sort;
        this.condition = builder.condition;
    }

    /**
     * Returns the template version.
     *
     * @return The template version as a string.
     */
    public String getTemplateVersion() {
        return templateVersion;
    }

    /**
     * Returns the type of template.
     *
     * @return The template type as a string.
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the ID of the template.
     *
     * @return The template ID as a string.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the datasource for the template.
     *
     * @return The datasource as a string.
     */
    public String getDatasource() {
        return datasource;
    }

    /**
     * Returns the selector for the template.
     *
     * @return The selector object.
     */
    public Selector getSelector() {
        return selector;
    }

    /**
     * Returns the group for the template.
     *
     * @return The group object.
     */
    public Group getGroup() {
        return group;
    }

    /**
     * Returns the sort for the template.
     *
     * @return The sort object.
     */
    public Sort getSort() {
        return sort;
    }

    /**
     * Returns the condition for the template.
     *
     * @return The condition object.
     */
    public Condition getCondition() {
        return condition;
    }

    /**
     * Compares this prediction template to the specified object. The result is true if and only if
     * the argument is not null and is a PredictionTemplate object that represents the same template
     * as this object.
     *
     * @param obj The object to compare this prediction template against.
     * @return True if the given object represents a PredictionTemplate equivalent to this template.
     * False otherwise.
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
        PredictionTemplate other = (PredictionTemplate) obj;
        return Objects.equals(templateVersion, other.templateVersion) &&
                Objects.equals(id, other.id) &&
                Objects.equals(type, other.type) &&
                Objects.equals(datasource, other.datasource) &&
                Objects.equals(selector, other.selector) &&
                Objects.equals(group, other.group) &&
                Objects.equals(sort, other.sort) &&
                Objects.equals(condition, other.condition);
    }

    /**
     * Returns a hash code value for the prediction template.
     *
     * @return A hash code value for this prediction template.
     */
    @Override
    public int hashCode() {
        int result = 59;
        result = result * 59 + (templateVersion != null ? templateVersion.hashCode() : 0);
        result = result * 59 + (id != null ? id.hashCode() : 0);
        result = result * 59 + (type != null ? type.hashCode() : 0);
        result = result * 59 + (datasource != null ? datasource.hashCode() : 0);
        result = result * 59 + (selector != null ? selector.hashCode() : 0);
        result = result * 59 + (group != null ? group.hashCode() : 0);
        result = result * 59 + (sort != null ? sort.hashCode() : 0);
        result = result * 59 + (condition != null ? condition.hashCode() : 0);
        return result;
    }

    /**
     * Static method to create a new Builder instance.
     *
     * @return A new Builder for creating a PredictionTemplate instance.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for PredictionTemplate.
     */
    @JsonPOJOBuilder(withPrefix = "", buildMethodName = "build")
    public static class Builder {
        private String templateVersion;
        private String type;
        private String id;
        private String datasource;
        private Selector selector;
        private Group group;
        private Sort sort;
        private Condition condition;

        /**
         * Sets the template version.
         *
         * @param templateVersion The template version as a string.
         * @return The Builder instance.
         */
        public Builder templateVersion(String templateVersion) {
            this.templateVersion = templateVersion;
            return this;
        }

        /**
         * Sets the template type.
         *
         * @param type The template type as a string.
         * @return The Builder instance.
         */
        public Builder type(String type) {
            this.type = type;
            return this;
        }

        /**
         * Sets the template ID.
         *
         * @param id The template ID as a string.
         * @return The Builder instance.
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the datasource for the template.
         *
         * @param datasource The datasource as a string.
         * @return The Builder instance.
         */
        public Builder datasource(String datasource) {
            this.datasource = datasource;
            return this;
        }

        /**
         * Sets the selector for the template.
         *
         * @param selector The selector object.
         * @return The Builder instance.
         */
        public Builder selector(Selector selector) {
            this.selector = selector;
            return this;
        }

        /**
         * Sets the group for the template.
         *
         * @param group The group object.
         * @return The Builder instance.
         */
        public Builder group(Group group) {
            this.group = group;
            return this;
        }

        /**
         * Sets the sort for the template.
         *
         * @param sort The sort object.
         * @return The Builder instance.
         */
        public Builder sort(Sort sort) {
            this.sort = sort;
            return this;
        }

        /**
         * Sets the condition for the template.
         *
         * @param condition The condition object.
         * @return The Builder instance.
         */
        public Builder condition(Condition condition) {
            this.condition = condition;
            return this;
        }

        /**
         * Builds the PredictionTemplate instance.
         * Before creating the PredictionTemplate, it checks that all required fields are not null.
         *
         * @return A new PredictionTemplate instance.
         * @throws NullPointerException if any required field is null.
         */
        public PredictionTemplate build() {
            Preconditions.checkNotNull(templateVersion, "Template version cannot be null");
            Preconditions.checkNotNull(type, "Type cannot be null");
            Preconditions.checkNotNull(datasource, "Datasource cannot be null");
            Preconditions.checkNotNull(id, "Template ID cannot be null");
            Preconditions.checkNotNull(selector, "Selector cannot be null");
            return new PredictionTemplate(this);
        }

        /**
         * Generates a string representation of the PredictionTemplate.
         *
         * @return A string representation of the PredictionTemplate.
         */
        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("templateVersion", templateVersion)
                    .add("type", type)
                    .add("id", id)
                    .add("datasource", datasource)
                    .add("selector", selector)
                    .add("group", group)
                    .add("sort", sort)
                    .add("condition", condition)
                    .toString();
        }
    }
}
