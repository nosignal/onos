/*
 * Copyright 2023-present Open Networking Foundation
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
package org.onosproject.netflow.impl;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.onlab.util.KryoNamespace;
import org.onosproject.store.serializers.KryoNamespaces;
import org.onosproject.store.service.EventuallyConsistentMap;
import org.onosproject.store.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.onosproject.netflow.NetflowStore;
import org.onosproject.netflow.Flow;
import org.onosproject.netflow.FlowField;
import org.onosproject.netflow.FlowTemplateField;
import org.onosproject.netflow.SourceId;
import org.onosproject.netflow.TemplateId;
import org.onosproject.netflow.DataRecord;
import org.onosproject.netflow.DataFlowRecord;
import org.onosproject.netflow.FlowSet;
import org.onosproject.netflow.DataFlowSet;
import org.onosproject.netflow.TemplateRecord;
import org.onosproject.netflow.DataTemplateRecord;
import org.onosproject.netflow.OptionalTemplateFlowSet;
import org.onosproject.netflow.TemplateFlowSet;
import org.onlab.packet.BasePacket;

/**
 * Manages inventory of Netflow template and data flowset to distribute
 * information.
 */
@Component(immediate = true, service = NetflowStore.class)
public class DistributedNetflowStore implements NetflowStore {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private EventuallyConsistentMap<TemplateId, DataTemplateRecord> templateFlowSet;
    private EventuallyConsistentMap<TemplateId, OptionalTemplateFlowSet> optionalTemplateFlowSet;
    private EventuallyConsistentMap<TemplateId, List<DataFlowRecord>> dataFlowSet;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected StorageService storageService;

    @Activate
    protected void activate() {
        KryoNamespace.Builder serializer = KryoNamespace.newBuilder()
                .register(KryoNamespaces.MISC)
                .register(List.class)
                .register(LinkedList.class)
                .register(ArrayList.class)
                .register(HashSet.class)
                .register(BasePacket.class)
                .register(Flow.class)
                .register(FlowField.class)
                .register(FlowTemplateField.class)
                .register(SourceId.class)
                .register(TemplateId.class)
                .register(DataRecord.class)
                .register(DataFlowRecord.class)
                .register(FlowSet.class)
                .register(DataFlowSet.class)
                .register(TemplateRecord.class)
                .register(DataTemplateRecord.class)
                .register(NetFlowPacket.class)
                .register(OptionalTemplateFlowSet.class)
                .register(TemplateFlowSet.class)
                .register(TemplateRecord.class);

        templateFlowSet = storageService.<TemplateId, DataTemplateRecord>eventuallyConsistentMapBuilder()
                .withSerializer(serializer)
                .withName("netflow-templateflowset")
                .withAntiEntropyPeriod(10, TimeUnit.SECONDS)
                .withTimestampProvider((k, v) -> new org.onosproject.store.service.WallClockTimestamp())
                .withTombstonesDisabled()
                .build();

        optionalTemplateFlowSet = storageService.<TemplateId, OptionalTemplateFlowSet>eventuallyConsistentMapBuilder()
                .withSerializer(serializer)
                .withName("netflow-optionaltemplateflowset")
                .withAntiEntropyPeriod(10, TimeUnit.SECONDS)
                .withTimestampProvider((k, v) -> new org.onosproject.store.service.WallClockTimestamp())
                .withTombstonesDisabled()
                .build();

        dataFlowSet = storageService.<TemplateId, List<DataFlowRecord>>eventuallyConsistentMapBuilder()
                .withSerializer(serializer)
                .withName("netflow-dataflowset")
                .withAntiEntropyPeriod(10, TimeUnit.SECONDS)
                .withTimestampProvider((k, v) -> new org.onosproject.store.service.WallClockTimestamp())
                .withTombstonesDisabled()
                .build();

        log.info("Started");
    }

    @Deactivate
    public void deactive() {
        log.info("Stopped");
    }

    /**
     * Get template flowset from store.
     * it will fetch current template which is matching to the template id from store.
     *
     * @param templateId template id.
     * @return optional of data template record, optional will be empty if template not found.
     */
    @Override
    public Optional<DataTemplateRecord> getTemplateFlowSet(TemplateId templateId) {
        return Optional.ofNullable(templateFlowSet.get(templateId));
    }

    /**
     * Get set of template flowsets from store.
     * it will fetch all template flowsets from store.
     *
     * @return set of data template record, set will be empty if templates not found in the store.
     */
    @Override
    public Set<DataTemplateRecord> getTemplateFlowSet() {
        return templateFlowSet.values().stream()
                .collect(Collectors.toSet());
    }

    /**
     * Get optional template flowset from store.
     * it will fetch current optional template which is matching to the template id from store.
     *
     * @param templateId template id.
     * @return optional of optional template flowset, optional will be empty if template not found.
     */
    @Override
    public Optional<OptionalTemplateFlowSet> getOptionalTemplateFlowSet(TemplateId templateId) {
        return Optional.ofNullable(optionalTemplateFlowSet.get(templateId));
    }

    /**
     * Get set of optional template flowsets from store.
     * it will fetch all optional template flowsets from store.
     *
     * @return set of optional template flowsets, set will be empty if templates not found in the store.
     */
    @Override
    public Set<OptionalTemplateFlowSet> getOptionalTemplateFlowSet() {
        return ImmutableSet.copyOf(optionalTemplateFlowSet.values());
    }

    /**
     * Get data flowset from store.
     * it will fetch current data flowset which is matching to the template id from store.
     *
     * @param templateId template id.
     * @return list of data flow record, list will be empty if template id not matched.
     */
    @Override
    public List<DataFlowRecord> getDataFlowSet(TemplateId templateId) {
        List<DataFlowRecord> dataRecord = dataFlowSet.get(templateId);
        if (!Objects.isNull(dataRecord)) {
            return ImmutableList.copyOf(dataRecord);
        }
        return Lists.newArrayList();
    }

    /**
     * Get data flowset from store.
     * it will fetch current data flowset which is matching to the template id from store.
     *
     * @return mapping from a template id to data flow record.
     */
    @Override
    public Map<TemplateId, List<DataFlowRecord>> getDataFlowSet() {
        return dataFlowSet.entrySet().stream().collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));
    }

    /**
     * Update template flowset to the store.
     * Add new template to store if it not exist,
     * otherwise it will replace the existing template.
     *
     * @param templateRecord template flowset record.
     */
    @Override
    public void updateTemplateFlowSet(DataTemplateRecord templateRecord) {
        templateFlowSet.put(templateRecord.getTemplateId(), templateRecord);
    }

    /**
     * Update optional template flowset to the store.
     * Add new optional template to store if it not exist,
     * otherwise it will replace the existing optional template.
     *
     * @param templateFlowSet optional template flowset.
     */
    @Override
    public void updateOptionalTemplateFlowSet(OptionalTemplateFlowSet templateFlowSet) {
        optionalTemplateFlowSet.put(templateFlowSet.getTemplateId(), templateFlowSet);
    }

    /**
     * Add data flow record to the store.
     * Add new data flow record to store
     *
     * @param dataFlowRecord data flow record.
     */
    @Override
    public void addDataFlowSet(DataFlowRecord dataFlowRecord) {
        dataFlowSet.compute(dataFlowRecord.getTemplateId(),
                (id, flowSet) -> {
                    List<DataFlowRecord> newSet = new ArrayList<>();
                    if (flowSet != null) {
                        newSet.addAll(flowSet);
                    }
                    newSet.add(dataFlowRecord);
                    return newSet;
                });
    }

    /**
     * Remove template flowset from store.
     * it will remove template flowset which is matching to the given template id from store.
     *
     * @param templateId template id.
     */
    @Override
    public void clearTemplateFlowSet(TemplateId templateId) {
        if (templateFlowSet.containsKey(templateId)) {
            templateFlowSet.remove(templateId);
        }
    }

    /**
     * Remove all template flowset from store.
     * it will remove all template flowsets from store.
     */
    @Override
    public void clearTemplateFlowSet() {
        templateFlowSet.clear();
    }

    /**
     * Remove optional template flowset from store.
     * it will remove optional template which is matching to the given template id from store.
     *
     * @param templateId template id.
     */
    @Override
    public void clearOptionalTemplateFlowSet(TemplateId templateId) {
        if (optionalTemplateFlowSet.containsKey(templateId)) {
            optionalTemplateFlowSet.remove(templateId);
        }
    }

    /**
     * Remove all optional template flowset from store.
     * it will remove all optional template flowsets from store.
     */
    @Override
    public void clearOptionalTemplateFlowSet() {
        optionalTemplateFlowSet.clear();
    }

    /**
     * Remove data flowset from store.
     * it will remove dataflowset which is matching to the given template id from store.
     *
     * @param templateId template id.
     */
    @Override
    public void clearDataFlowSet(TemplateId templateId) {
        if (dataFlowSet.containsKey(templateId)) {
            dataFlowSet.remove(templateId);
        }
    }

    /**
     * Remove all data flowset from store.
     * it will remove all data flowsets from store.
     */
    @Override
    public void clearDataFlowSet() {
        dataFlowSet.clear();
    }

    /**
     * Remove template, optional template and data flowsets from store.
     * it will remove all flowsets from store.
     */
    @Override
    public void clearAllFlowSet() {
        templateFlowSet.clear();
        optionalTemplateFlowSet.clear();
        dataFlowSet.clear();
    }

}
