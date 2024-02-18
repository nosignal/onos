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

import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class StateWorklet extends AbstractWorklet {

    // Path for lcm state.
    public static final String STATE_PATH = "/custom-state";

    @StaticDataModel(path = STATE_PATH)
    String state;

    protected static final Logger log = getLogger(StateWorklet.class);

    @Override
    public boolean needsProcess(WorkflowContext context) throws WorkflowException {
        return true;
    }

    @Override
    public void process(WorkflowContext context) throws WorkflowException {
        log.info("Workflow-process: {}@{} ",
                this.getClass().getSimpleName(), context.workplaceName());
        context.setLcmState(CustomLcmState.valueOf(state));
        context.completed();
    }
}


