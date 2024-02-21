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
package org.onosproject.workflow.impl;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang.StringUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.onosproject.workflow.api.ImmutableListWorkflow;
import org.onosproject.workflow.api.WorkflowAttribute;
import org.onosproject.workflow.api.WorkflowConstants;
import org.onosproject.workflow.api.WorkflowStore;
import org.onosproject.workflow.api.WorkflowRegistrar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.onosproject.workflow.api.WorkflowConstants.WORFLOWS;
import static org.onosproject.workflow.api.WorkflowConstants.WORKLETS;
import static org.onosproject.workflow.api.WorkflowConstants.NAME;


@Component(immediate = true, service = WorkflowRegistrar.class)
public class WorkflowRegistrarImpl implements WorkflowRegistrar {

    private static final Logger log = LoggerFactory.getLogger(WorkflowRegistrarImpl.class);

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    private WorkflowStore workflowStore;

    /**
     * Register workflow using json.
     *
     * @param jsonNode json node
     */
    @Override
    public void registerWorkflowUsingJson(JsonNode jsonNode) {
        StreamSupport.stream(Optional.ofNullable(jsonNode.get(WORFLOWS))
                .filter(JsonNode::isArray)
                .orElseThrow(() ->
                    new IllegalArgumentException("there is no " + WORFLOWS + " node")).spliterator(), false)
            .forEach(workflow -> {
                String id = Optional.ofNullable(workflow.get(WorkflowConstants.WF_ID))
                        .orElseThrow(() ->
                                new IllegalArgumentException("there is no " + WorkflowConstants.WF_ID))
                        .asText();
                ImmutableListWorkflow.Builder builder = new ImmutableListWorkflow.Builder()
                        .id(URI.create(id))
                        .attribute(WorkflowAttribute.REMOVE_AFTER_COMPLETE);

                Optional.ofNullable(workflow.path(WORKLETS))
                        .filter(JsonNode::isArray)
                        .ifPresent(worklets -> StreamSupport.stream(worklets.spliterator(), false)
                                .map(worklet -> worklet.path(NAME).asText())
                                .filter(StringUtils::isNotEmpty)
                                .forEach(builder::chain)
                        );
                try {
                    workflowStore.register(builder.build());
                } catch (Exception e) {
                    log.error("registering invalid workflow. ", e);
                }
            }
        );
    }
}

