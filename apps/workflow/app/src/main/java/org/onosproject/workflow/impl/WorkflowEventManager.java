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

import org.osgi.service.component.annotations.Component;
import org.onosproject.event.ListenerRegistry;
import org.onosproject.workflow.api.WorkflowListener;
import org.onosproject.workflow.api.WorkflowEvent;
import org.onosproject.workflow.api.WorkflowEventService;

@Component(immediate = true, service = WorkflowEventService.class)
public class WorkflowEventManager extends ListenerRegistry<WorkflowEvent, WorkflowListener>
        implements WorkflowEventService {

    @Override
    public void processWorkflowEvent(WorkflowEvent event) {
        super.process(event);
    }
}

