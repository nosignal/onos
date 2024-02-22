/*
 * Copyright 2018-present Open Networking Foundation
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

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class representing workflow program counter.
 */
public final class ProgramCounter {

    public static final ProgramCounter INIT_PC
            = ProgramCounter.valueOf(ListWorkflowSection.MAIN_SECTION, 0, Worklet.Common.INIT.name());

    public static final ProgramCounter TERMINATE_PC
            = ProgramCounter.valueOf(ListWorkflowSection.MAIN_SECTION, 0, Worklet.Common.COMPLETED.name());

    /**
     * Section name in workflow.
     */
    private final String sectionName;

    /**
     * index of the work-let in the sectionName.
     */
    private final int workletIndex;

    /**
     * Number of work-let processed.
     */
    private int indexCount;

    /**
     * Type of work-let.
     */
    private final String workletType;


    /**
     * Section name in workflow.
     * @return sectionName name
     */
    public String sectionName() {
        return this.sectionName;
    }

    /**
     * Index of work-let in the workflow section.
     * @return index of work-let
     */
    public int workletIndex() {
        return this.workletIndex;
    }

    /**
     * Number of work-let processed.
     * @return index count of work-let
     */
    public int indexCount() {
        return this.indexCount;
    }

    /**
     * Type of work-let.
     * @return type of work-let
     */
    public String workletType() {
        return this.workletType;
    }

    /**
     * Set total number of work-let processed.
     * @param indexCount total number of work-let processed
     */
    public void setIndexCount(int indexCount) {
        this.indexCount = indexCount;
    }

    /**
     * Constructor of workflow Program Counter.
     * @param workletType type of work-let
     * @param workletIndex index of work-let
     */
    private ProgramCounter(String sectionName, int workletIndex, int indexCount, String workletType) {
        this.workletType = workletType;
        this.sectionName = sectionName;
        this.workletIndex = workletIndex;
        this.indexCount = indexCount;
    }

    /**
     * Clones this workflow Program Counter.
     * @return clone of this workflow Program Counter
     */
    public ProgramCounter pcClone() {
        return ProgramCounter.valueOf(this.sectionName(), this.workletIndex(), this.workletType());
    }

    /**
     * Returns whether this program counter is INIT work-let program counter.
     * @return whether this program counter is INIT work-let program counter
     */
    public boolean isInit() {
        return Worklet.Common.INIT.tag().equals(this.workletType);
    }

    /**
     * Returns whether this program counter is COMPLETED work-let program counter.
     * @return whether this program counter is COMPLETED work-let program counter
     */
    public boolean isCompleted() {
        return Worklet.Common.COMPLETED.tag().equals(this.workletType);
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
        if (!(obj instanceof ProgramCounter)) {
            return false;
        }
        return Objects.equals(this.workletType(), ((ProgramCounter) obj).workletType())
                && Objects.equals(this.sectionName(), ((ProgramCounter) obj).sectionName())
                && Objects.equals(this.workletIndex(), ((ProgramCounter) obj).workletIndex())
         && Objects.equals(this.workletIndex(), ((ProgramCounter) obj).workletIndex());
    }

    @Override
    public String toString() {
        return String.format("(%s:%d:%d)%s", sectionName, workletIndex, indexCount, workletType);
    }

    /**
     * Builder of workflow Program Counter.
     * @param workletType type of work-let
     * @param sectionName name of section
     * @param workletIndex index of work-let
     * @return program counter
     */
    public static ProgramCounter valueOf(String sectionName, int workletIndex, String workletType) {
        return valueOf(sectionName, workletIndex, workletIndex, workletType);
    }

    public static ProgramCounter valueOf(String sectionName, int workletIndex, int moveCount, String workletType) {
        return new ProgramCounter(sectionName, workletIndex, moveCount, workletType);
    }

    /**
     * Builder of workflow Program Counter.
     * @param strProgramCounter string format for program counter '([sectionName]:[index])[class name]'
     * @return program counter
     */
    public static ProgramCounter valueOf(String strProgramCounter) {

        Matcher m = Pattern.compile("\\((.+)\\:(\\d+)\\:(\\d+)\\)(.+)").matcher(strProgramCounter);

        if (!m.matches()) {
            throw new IllegalArgumentException("Malformed program counter string");
        }

        return new ProgramCounter(m.group(1), Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)), m.group(4));
    }

}

