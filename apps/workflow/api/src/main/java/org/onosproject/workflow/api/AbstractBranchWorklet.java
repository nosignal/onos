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

import static org.onosproject.workflow.api.CheckCondition.check;

/**
 * Abstract class representing branch work-let. Branch work-let is used for branching workflow execution.
 */
public abstract class AbstractBranchWorklet extends AbstractWorklet {

    public static final String BRANCH_LABEL_PATH = "/branch-target-label-data-path";

    @StaticDataModel(path = BRANCH_LABEL_PATH)
    String branchLabel;

    @Override
    public void process(WorkflowContext context) throws WorkflowException {
        throw new WorkflowException("This workletType.process should not be called");
    }

    /**
     * Checks the condition for branch.
     * @param context workflow context
     * @return true is branch, false is not-branch(passing branch)
     * @throws WorkflowException workflow exception
     */
    public abstract boolean isBranch(WorkflowContext context) throws WorkflowException;

    public Label getBranchLabel() throws WorkflowException {
        check(branchLabel != null, "Invalid branch label string(" + branchLabel + ")");
        return Label.as(branchLabel);
    }
}

