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

import java.util.Objects;

/**
 * A label indicating the specific PC.
 */
public final class Label {

    /**
     * The name of label.
     */
    private String name;

    /**
     * The constructor of Label.
     * @param name The name of label
     */
    private Label(String name) {
        this.name = name;
    }

    /**
     * Returns the name of label.
     * @return name of label
     */
    public String name() {
        return this.name;
    }

    /**
     * Label builder.
     * @param name The name of label
     * @return A label
     */
    public static Label as(String name) {
        return new Label(name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        return Objects.equals(name, ((Label) obj).name);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .add("name", name)
                .toString();
    }
}

