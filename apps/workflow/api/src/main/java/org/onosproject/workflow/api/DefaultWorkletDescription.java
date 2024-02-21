/*
 * Copyright 2019-present Open Networking Foundation
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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.MoreObjects;
import org.slf4j.Logger;

import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Class for default work-let description.
 */
public final class DefaultWorkletDescription implements WorkletDescription {

    protected static final Logger log = getLogger(DefaultWorkletDescription.class);

    /**
     * work-let Name.
     */
    private String tag;

    /**
     * Label of work-let description(Optional).
     */
    private Optional<Label> optLabel;

    /**
     * work-let staticData model.
     */
    private JsonDataModelTree staticData;

    /**
     * Constructor of work-let description.
     *
     * @param builder work-let description builder
     */
    private DefaultWorkletDescription(DefaultWorkletDescription.Builder builder) {
        this.tag = builder.tag;
        this.optLabel = builder.optLabel;
        this.staticData = builder.staticData;
    }

    /**
     * Constructor of work-let description.
     *
     * @param tag work-let class name
     */
    public DefaultWorkletDescription(String tag) {
        this.tag = tag;
        this.optLabel = Optional.empty();
        this.staticData = new JsonDataModelTree();
    }

    @Override
    public String tag() {
        return this.tag;
    }

    @Override
    public Optional<Label> label() {
        return this.optLabel;
    }

    @Override
    public JsonDataModelTree data() {
        return this.staticData;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .add("tag", tag())
                .add("staticData", data())
                .add("optLabel", label())
                .toString();
    }

    /**
     * Gets builder instance.
     *
     * @return builder instance
     */
    public static DefaultWorkletDescription.Builder builder() {
        return new DefaultWorkletDescription.Builder();
    }

    /**
     * Builder for work-let description.
     */
    public static class Builder {

        /**
         * work-let name.
         */
        private String tag;

        /**
         * Label of work-let description(Optional).
         */
        private Optional<Label> optLabel = Optional.empty();

        /**
         * static staticData model tree.
         */
        JsonDataModelTree staticData = new JsonDataModelTree();

        /**
         * Sets optLabel of work-let description.
         *
         * @param label optLabel of work-let description
         * @return builder
         */
        public DefaultWorkletDescription.Builder label(Label label) {
            this.optLabel = Optional.of(label);
            return this;
        }

        /**
         * Sets work-let name.
         *
         * @param tag work-let name
         * @return builder
         */
        public DefaultWorkletDescription.Builder name(String tag) {
            this.tag = tag;
            return this;
        }

        /**
         * Sets static data model with path and string data type value.
         *
         * @param path static data model path
         * @param value string model value
         * @return builder
         * @throws WorkflowException workflow exception
         */
        public DefaultWorkletDescription.Builder staticDataModel(String path, String value) throws WorkflowException {

            staticData.setAt(path, value);

            return this;
        }

        /**
         * Sets static data model with path and integer data type value.
         *
         * @param path static data model path
         * @param value integer model value
         * @return builder
         * @throws WorkflowException workflow exception
         */
        public DefaultWorkletDescription.Builder staticDataModel(String path, Integer value) throws WorkflowException {

            staticData.setAt(path, value);

            return this;
        }

        /**
         * Sets static data model with path and boolean data type value.
         *
         * @param path static data model path
         * @param value boolean model value
         * @return builder
         * @throws WorkflowException workflow exception
         */
        public DefaultWorkletDescription.Builder staticDataModel(String path, Boolean value) throws WorkflowException {

            staticData.setAt(path, value);

            return this;
        }

        /**
         * Sets static data model with path and json data type value.
         *
         * @param path static data model path
         * @param value json model value
         * @return builder
         * @throws WorkflowException workflow exception
         */
        public DefaultWorkletDescription.Builder staticDataModel(String path, JsonNode value) throws WorkflowException {

            staticData.setAt(path, value);

            return this;
        }

        /**
         * Sets static data model with path and json array data type value.
         *
         * @param path static data model path
         * @param value json-array model value
         * @return builder
         * @throws WorkflowException workflow exception
         */
        public DefaultWorkletDescription.Builder staticDataModel(String path, ArrayNode value)
                throws WorkflowException {

            staticData.setAt(path, value);

            return this;
        }

        /**
         * Sets static data model with path and json-object data type value.
         *
         * @param path static data model path
         * @param value json-object model value
         * @return builder
         * @throws WorkflowException workflow exception
         */
        public DefaultWorkletDescription.Builder staticDataModel(String path, ObjectNode value)
                throws WorkflowException {

            staticData.setAt(path, value);

            return this;
        }

        /**
         * Builds work-let description from builder.
         *
         * @return instance of work-let description
         * @throws WorkflowException workflow exception
         */
        public DefaultWorkletDescription build() {
            return new DefaultWorkletDescription(this);
        }
    }
}

