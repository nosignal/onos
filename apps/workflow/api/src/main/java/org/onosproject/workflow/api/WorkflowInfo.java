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

import com.fasterxml.jackson.annotation.JsonAutoDetect;


/**
 * Workflow Information Class for other applications.
 * it has name, type and step.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public final class WorkflowInfo {
    private String name;
    private String type;
    private int step;

    /**
     * Constructor of parameters.
     * @param name Workflow's name
     * @param type Workflow's type
     * @param step the number of workflow's worklet
     */
    private WorkflowInfo(String name, String type, int step) {
        this.name = name;
        this.type = type;
        this.step = step;
    }

    /**
     * Construct method for 'workflow'.
     * @param workflow workflow to be converted to WorkflowInfo
     * @return WorkflowInfo object.
     */
    public static WorkflowInfo of(Workflow workflow) {
        return new WorkflowInfo(workflow.id().toString(),
                                createTypeFromWorkletType(workflow.getProgram().get(1).workletType()),
                                workflow.getProgram().size());
    }


    /**
     * Parsing workflow type using workletType in workflow's worklet descriptions.
     * @param workletType first workletType in workflow.
     * @return parsed value.
     */
    private static String createTypeFromWorkletType(String workletType) {
        String[] candidate = workletType.split("\\$")[0].split("\\.");
        return candidate[candidate.length - 1];
    }
}

