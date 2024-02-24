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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.onosproject.core.ApplicationId;
import org.onosproject.core.CoreService;
import org.onosproject.store.service.StorageService;
import org.onosproject.workflow.api.Worklet;
import org.onosproject.workflow.api.WorkletObjectStore;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

@Component(immediate = true, service = WorkletObjectStore.class)
public class InMemoryWorkletObjectStore implements WorkletObjectStore {

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected CoreService coreService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected StorageService storageService;

    private final Logger log = getLogger(getClass());

    private Map<String, Worklet> localWorkletMap = Maps.newConcurrentMap();

    private ApplicationId appId;

    @Activate
    public void activate() {
        appId = coreService.registerApplication("org.onosproject.workletobjectstore");
        log.info("appId=" + appId);

        localWorkletMap.clear();

        log.info("Started");
    }

    @Deactivate
    public void deactivate() {
        localWorkletMap.clear();
        log.info("Stopped");
    }

    /**
     * Registers worklet object.
     *
     * @param worklet registering worklet
     */
    @Override
    public void register(Worklet worklet) {
        String name = worklet.getClass().getName().split("\\$")[1];
        localWorkletMap.put(name, worklet);
    }

    /**
     * Unregisters worklet object.
     *
     * @param name name of worklet
     */
    @Override
    public void unregister(String name) {
        localWorkletMap.remove(name);
    }

    /**
     * Gets worklet objects.
     *
     * @param name id of worklet
     * @return worklet
     */
    @Override
    public Optional<Worklet> get(String name) {
        return Optional.ofNullable(localWorkletMap.get(name));
    }

    /**
     * Gets all worklet objects.
     *
     * @return collection of worklet
     */
    @Override
    public Collection<Worklet> getAll() {
        return localWorkletMap.values();
    }

    /**
     * Gets worklet objects keys.
     *
     * @return List of worklet keys
     */
    @Override
    public List<String> getKeys() {
        return Lists.newArrayList(localWorkletMap.keySet());
    }

    /**
     * Gets class from registered worklet object.
     *
     * @param name name of object
     * @return Optional<class> Optional
     * @throws ClassNotFoundException class not found exception
     */
    @Override
    public Optional<Class> getWorkletClass(String name) throws ClassNotFoundException {
        Worklet targetWorklet = localWorkletMap.get(name);
        if (targetWorklet == null) {
            return Optional.empty();
        }
        return Optional.of(targetWorklet.getClass());
    }
}

