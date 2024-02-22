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

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;

/**
 * Class for list-workflow section. Section is the part of list workflow.
 * This is the logical segment of list workflow program.
 * main-section is the main workflow sequence implementing normal workflow behavior.
 */
public final class ListWorkflowSection {

    /**
     * Main list workflow section name.
     */
    public static final String MAIN_SECTION = "main-section";

    /**
     * Main exception handler list workflow section name.
     */
    public static final String MAIN_EXCEPTION_HANDLER_SECTION = "main-exception-handler-section";

    /**
     * Name of list-workflow section.
     */
    private String name;

    /**
     * Sequential program(list of work-let desc) of list-Workflow section to be executed.
     */
    private List<WorkletDescription> program;

    /**
     * Default constructor of ListWorkflowSection.
     * @param builder builder of ListWorkflowSection
     */
    private ListWorkflowSection(Builder builder) {
        this.name = builder.name;
        this.program = ImmutableList.copyOf(builder.program);
    }

    /**
     * Gets name of list-workflow section.
     * @return name of list-workflow section.
     */
    public String name() {
        return this.name;
    }

    /**
     * Gets references of program(List of work-let desc) composing this list-workflow section.
     * @return references of program
     */
    public List<WorkletDescription> refProgram() {
        return program;
    }

    /**
     * Gets program counter of the entry point of this workflow section.
     * @return program counter of the entry point of this workflow section.
     */
    public ProgramCounter getEntryPoint() {
        //  Builder guarantees the section has at least 1 work-let descriptor(for INIT)
        return ProgramCounter.valueOf(name(), 0, refProgram().get(0).tag());
    }

    /**
     * Returns whether the pc is the pc of exception handler section.
     * @param pc program counter
     * @return whether the pc is the pc of exception handler section
     */
    public static boolean isExceptionHandlerSection(ProgramCounter pc) {
        return MAIN_EXCEPTION_HANDLER_SECTION.equals(pc.sectionName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ListWorkflowSection)) {
            return false;
        }
        return  Objects.equals(this.name, ((ListWorkflowSection) obj).name)
                && Objects.equals(this.program, ((ListWorkflowSection) obj).program);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .add("name", name)
                .add("work-list", program)
                .toString();
    }

    /**
     * Gets a instance of builder.
     *
     * @return instance of builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder of ListWorkflowSection.
     */
    public static final class Builder {

        /**
         * Name of list-workflow section.
         */
        private String name;

        /**
         * Sequential program(List of work-let desc) of list-Workflow Section to be executed.
         */
        private final List<WorkletDescription> program = Lists.newArrayList();

        private Builder() {
            program.add(new DefaultWorkletDescription(Worklet.Common.INIT.tag()));
        }

        /**
         * Sets name of list-workflow section.
         * This method is only called by only the package internal.
         *
         * @param name name of list-workflow section
         * @return builder
         */
        Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Chains work-let class name of list-workflow section.
         *
         * @param workletClassName class name of work-let
         * @return builder
         */
        public Builder chain(String workletClassName) {
            program.add(DefaultWorkletDescription.builder()
                    .name(workletClassName)
                    .build());
            return this;
        }

        /**
         * Chains work-let class name of list-workflow section with label.
         *
         * @param label my label of this work-let(for supporting branching)
         * @param workletClassName class name of work-let
         * @return builder
         */
        public Builder chain(Label label, String workletClassName) {
            program.add(DefaultWorkletDescription.builder()
                    .name(workletClassName)
                    .label(label)
                    .build());
            return this;
        }

        /**
         * Chains worklet class name of list-workflow section.
         *
         * @param workletDesc work-let description
         * @return builder
         */
        public Builder chain(DefaultWorkletDescription workletDesc) {
            program.add(workletDesc);
            return this;
        }

        /**
         * Chains branch work-let on the list-workflow section.
         *
         * @param targetLabel target label to jump
         * @return builder
         * @throws WorkflowException workflow exception
         */
        public Builder jump(String targetLabel) throws WorkflowException {
            program.add(DefaultWorkletDescription.builder()
                    .name(JumpWorklet.class.getName())
                    .staticDataModel(AbstractBranchWorklet.BRANCH_LABEL_PATH, targetLabel)
                    .build());
            return this;
        }

        /**
         * Makes the work-let sleep for the given time.
         * @param time duration of sleep
         * @return builder
         * @throws WorkflowException workflow exception
         */
        public Builder sleep(int time) throws WorkflowException {
            program.add(DefaultWorkletDescription.builder()
                    .name(SleepWorklet.class.getName())
                    .staticDataModel(SleepWorklet.SLEEP_MSEC_PATH, time)
                    .build());
            return this;
        }

        /**
         * Set custom lcm state to the workflow.
         *
         * @param state state of the lcm
         * @return builder
         * @throws WorkflowException workflow exception
         */
        public Builder state(CustomLcmState state) throws WorkflowException {
            program.add(DefaultWorkletDescription.builder()
                    .name(StateWorklet.class.getName())
                    .staticDataModel(StateWorklet.STATE_PATH, state.name())
                    .build());
            return this;
        }

        /**
         * Pass work-let class name of list-workflow section with label.
         *
         * @param label my label of this work-let(for supporting branching)
         * @return builder
         */
        public Builder pass(Label label) {
            program.add(DefaultWorkletDescription.builder()
                    .name(PassWorklet.class.getName())
                    .label(label)
                    .build());
            return this;
        }

        /**
         * Chains branch work-let on the list-workflow section with label.
         *
         * @param label my label of this work-let(for supporting branching)
         * @param targetLabel target label to jump
         * @return builder
         * @throws WorkflowException workflow exception
         */
        public Builder jump(Label label, String targetLabel) throws WorkflowException {
            program.add(DefaultWorkletDescription.builder()
                    .name(JumpWorklet.class.getName())
                    .label(label)
                    .staticDataModel(AbstractBranchWorklet.BRANCH_LABEL_PATH, targetLabel)
                    .build());
            return this;
        }

        /**
         * Chains conditional branch work-let on the list-workflow section.
         *
         * @param branchWorkletClassName branch worklet class name
         * @param targetLabel target label to jump
         * @return builder
         * @throws WorkflowException workflow exception
         */
        public Builder ifJump(String branchWorkletClassName, String targetLabel) throws WorkflowException {
            program.add(DefaultWorkletDescription.builder()
                    .name(branchWorkletClassName)
                    .staticDataModel(AbstractBranchWorklet.BRANCH_LABEL_PATH, targetLabel)
                    .build());
            return this;
        }

        /**
         * Chains conditional branch work-let on the list-workflow section with label.
         *
         * @param label my label of this work-let(for supporting branching)
         * @param branchWorkletClassName branch worklet class name
         * @param targetLabel target label to jump
         * @return builder
         * @throws WorkflowException workflow exception
         */
        public Builder ifJump(Label label, String branchWorkletClassName, String targetLabel) throws WorkflowException {
            program.add(DefaultWorkletDescription.builder()
                    .name(branchWorkletClassName)
                    .label(label)
                    .staticDataModel(AbstractBranchWorklet.BRANCH_LABEL_PATH, targetLabel)
                    .build());
            return this;
        }

        /**
         * Builds ListWorkflowSection.
         *
         * @return instance of ListWorkflowSection
         */
        public ListWorkflowSection build() {
            program.add(new DefaultWorkletDescription(Worklet.Common.COMPLETED.tag()));
            return new ListWorkflowSection(this);
        }

    }
}

