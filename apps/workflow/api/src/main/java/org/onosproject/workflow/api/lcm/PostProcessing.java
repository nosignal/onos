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
package org.onosproject.workflow.api.lcm;


import com.google.common.base.MoreObjects;
import org.onosproject.event.Event;
import org.onosproject.workflow.api.EventTimeoutTask;
import org.onosproject.workflow.api.TimeoutTask;
import org.onosproject.workflow.api.WorkExecutor;
import org.onosproject.workflow.api.Workflow;
import org.onosproject.workflow.api.WorkflowContext;
import org.onosproject.workflow.api.WorkflowException;
import org.onosproject.workflow.api.WorkflowExecutionService;
import org.onosproject.workflow.api.WorkflowState;
import org.slf4j.Logger;

import java.util.Set;

import static org.onosproject.workflow.api.CheckCondition.check;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Class for data for post-processing.
 */
public abstract class PostProcessing {

    /**
     * Processes post job of work-let execution.
     * @param context workflow context
     * @param workflow workflow
     * @throws WorkflowException workflow exception
     */
    public abstract void process(WorkflowContext context, Workflow workflow) throws WorkflowException;

    /**
     * Returns whether ths context LCM is PAUSE or not.
     * @param context workflow context
     * @return whether ths context LCM is PAUSE or not
     */
    protected boolean isPause(WorkflowContext context) {
        return context.workplaceStore().getLcm(context.name()) == WorkflowLcm.PAUSE;
    }

    /**
     * Returns whether ths context LCM is TERMINATE or not.
     * @param context workflow context
     * @return whether ths context LCM is TERMINATE or not
     */
    protected boolean isTerminate(WorkflowContext context) {
        return context.workplaceStore().getLcm(context.name()) == WorkflowLcm.TERMINATE;
    }

    /**
     * Class for data for wait completed post-processing.
     */
    public static final class WaitCompletion extends PostProcessing {

        private static final Logger log = getLogger(WaitCompletion.class);

        /**
         * Completion event type.
         */
        public transient Class<? extends Event> eventType;

        /**
         * Completion event hint Set.
         */
        public transient Set<String> eventHintSet;

        /**
         * Completion event generator method reference.
         */
        public transient WorkExecutor eventGenerator;

        /**
         * Completion event timeout milliseconds.
         */
        public transient long eventTimeoutMs;

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(getClass())
                    .add("eventType", eventType)
                    .add("eventHintSet", eventHintSet)
                    .add("eventGenerator", eventGenerator)
                    .add("eventTimeoutMs", eventTimeoutMs)
                    .toString();
        }

        @Override
        public void process(WorkflowContext context, Workflow workflow) throws WorkflowException {

            if (isPause(context) || isTerminate(context)) {
                // if LCM operation is pause, it does not execute event generator, and
                // does not schedule vent timeout task
                context.setTriggerNext(false);
                context.setState(WorkflowState.IDLE);
                return;
            }

            WorkflowExecutionService executionService = context.workflowService();

            executionService.registerEventMap(
                    eventType, eventHintSet, context.name(), context.current().toString());

            check(eventGenerator != null, "invalid event generator in " + context);

            eventGenerator.apply();

            if (eventTimeoutMs != 0L) {

                final EventTimeoutTask eventTimeoutTask = EventTimeoutTask.builder()
                        .context(context)
                        .programCounter(context.current())
                        .eventType(eventType.getName())
                        .eventHintSet(eventHintSet)
                        .build();

                executionService.scheduleHandlerTask(eventTimeoutMs, eventTimeoutTask);
            }
        }
    }

    /**
     * Class for data for wait for post-processing.
     */
    public static final class WaitFor extends PostProcessing {

        private static final Logger log = getLogger(WaitFor.class);

        /**
         * timeout milliseconds.
         */
        public transient long timeoutMs;

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(getClass())
                    .add("timeoutMs", timeoutMs)
                    .toString();
        }

        @Override
        public void process(WorkflowContext context, Workflow workflow) throws WorkflowException {

            if (isPause(context) || isTerminate(context)) {
                // if LCM operation is pause, it does not schedule vent timeout task
                context.setTriggerNext(false);
                context.setState(WorkflowState.IDLE);
                return;
            }

            final TimeoutTask timeoutTask = TimeoutTask.builder()
                    .context(context)
                    .programCounter(context.current())
                    .build();

            context.workflowService().scheduleHandlerTask(timeoutMs, timeoutTask);
        }
    }

    /**
     * Class for data for completed for post processing.
     */
    public static final class Completed extends PostProcessing {

        private static final Logger log = getLogger(Completed.class);

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(getClass())
                    .toString();
        }

        @Override
        public void process(WorkflowContext context, Workflow workflow) throws WorkflowException {

            context.workflowService().unregisterEventMap(context.name());
            context.setCurrent(workflow.increased(context.current()));

            if (isPause(context) || isTerminate(context)) {
                // if LCM operation is pause, it does not schedule vent timeout task
                context.setTriggerNext(false);
                context.setState(WorkflowState.IDLE);
                return;
            }

            context.setTriggerNext(true);
        }
    }
}

