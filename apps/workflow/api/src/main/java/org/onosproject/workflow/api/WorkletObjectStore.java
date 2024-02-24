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

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Store for managing worklet.
 */
public interface WorkletObjectStore {
    /**
     * Registers worklet object.
     *
     * @param worklet registering worklet
     */
    void register(Worklet worklet);

    /**
     * Unregisters worklet object.
     *
     * @param name name of worklet
     */
    void unregister(String name);

    /**
     * Gets worklet objects.
     *
     * @param name id of worklet
     * @return worklet
     */
    Optional<Worklet> get(String name);

    /**
     * Gets all worklet objects.
     *
     * @return collection of worklet
     */
    Collection<Worklet> getAll();

    /**
     * Gets worklet objects keys.
     *
     * @return List of worklet keys
     */
    List<String> getKeys();


    /**
     * Gets class from registered worklet object.
     *
     * @param name name of object
     * @return class
     * @throws ClassNotFoundException class not found exception
     */
    Optional<Class> getWorkletClass(String name) throws ClassNotFoundException;
}
