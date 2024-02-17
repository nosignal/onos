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

public class SleepWorklet extends AbstractWorklet {

    // Path for duration of sleep in msec.
    public static final String SLEEP_MSEC_PATH = "/sleep-msec-data-path";

    @StaticDataModel(path = SLEEP_MSEC_PATH)
    Integer time;

    protected static final Logger log = getLogger(SleepWorklet.class);

    @Override
    public boolean needsProcess(WorkflowContext context) throws WorkflowException {
        return true;
    }

    @Override
    public void process(WorkflowContext context) throws WorkflowException {
        log.info("Workflow-process: {}@{} ",
                this.getClass().getSimpleName(), context.workplaceName());
        context.waitFor(time);
    }

    @Override
    public void timeout(WorkflowContext context) throws WorkflowException {
        log.info("sleep worklet timeout happened");
        context.completed(); //Complete the job of worklet by timeout
    }
}


