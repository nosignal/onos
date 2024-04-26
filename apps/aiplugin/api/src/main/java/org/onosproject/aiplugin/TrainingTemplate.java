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
import java.util.UUID;

/**
 * Represents a training template with all necessary configurations for model training.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = TrainingTemplate.Builder.class)
public final class TrainingTemplate {

    private final String templateVersion;
    private final String type;
    private final UUID id;
    private final String datasource;
    private final Selector selector;
    private final Group group;
    private final Sort sort;
    private final Condition condition;
    private final String window;
    private final String horizon;
    private final String target;

    private TrainingTemplate(Builder builder) {
        this.templateVersion = builder.templateVersion;
        this.type = builder.type;
        this.id = builder.id == null ? UUID.randomUUID() : builder.id;
        this.datasource = builder.datasource;
        this.selector = builder.selector;
        this.group = builder.group;
        this.sort = builder.sort;
        this.condition = builder.condition;
        this.window = builder.window;
        this.horizon = builder.horizon;
        this.target = builder.target;
    }

    /**
     * Static method to create a new Builder instance.
     *
     * @return A new Builder for creating a TrainingTemplate instance.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for Training Template.
     */
    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
        private String templateVersion;
        private String type;
        private UUID id;
        private String datasource;
        private Selector selector;
        private Group group;
        private Sort sort;
        private Condition condition;
        private String window;
        private String horizon;
        private String target;

        public Builder() {
        }

        /**
         * Builds and returns the Training Template instance.
         * This method ensures that all necessary fields are set and validates the template's state.
         *
         * @return the constructed TrainingTemplate instance.
         * @throws NullPointerException if any required field is not set or invalid.
         */
        public TrainingTemplate build() {
            Preconditions.checkNotNull(templateVersion, "Template version cannot be null");
            Preconditions.checkNotNull(type, "Type cannot be null");
            Preconditions.checkNotNull(target, "Target cannot be null");
            Preconditions.checkNotNull(datasource, "Datasource cannot be null");
            Preconditions.checkNotNull(selector, "Selector cannot be null");

            return new TrainingTemplate(this);
        }
    }

    /**
     * Returns a string representation of the Training Template object. This includes all relevant fields
     * of the template, providing a comprehensive overview of its state.
     *
     * @return A string representation of the Training Template object, including its fields and their values.
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
                .add("window", window)
                .add("horizon", horizon)
                .add("target", target)
                .toString();
    }
}
