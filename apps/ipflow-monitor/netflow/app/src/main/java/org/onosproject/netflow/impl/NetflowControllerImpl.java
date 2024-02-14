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

import org.onosproject.netflow.NetflowController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

import org.onosproject.netflow.NetflowStore;
import org.onosproject.netflow.TemplateId;
import org.onosproject.netflow.DataFlowRecord;
import org.onosproject.netflow.DataTemplateRecord;

import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.Optional;

/**
 * It control and manage the netflow traffic.
 * it is Collecting, Storing and analyzing NetFlow data
 * it can help to understand which applications, and protocols
 * may be consuming the most network bandwidth by tracking processes,
 * protocols, times of day, and traffic routing.
 * Ref: https://www.ietf.org/rfc/rfc3954.txt
 */
@Component(immediate = true, service = NetflowController.class)
public class NetflowControllerImpl implements NetflowController {

    private static final Logger log = LoggerFactory.getLogger(NetflowControllerImpl.class);

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected NetflowStore store;

    @Activate
    public void activate() {
        Controller ctrl = new Controller(this);
        ctrl.start();
        log.info("Started");
    }

    @Deactivate
    public void deactivate() {
        log.info("Stopped");
    }

    /**
     * Add template flowset to controller.
     * Add new template to controller if it not exist,
     * otherwise it will replace the existing template.
     *
     * @param templateRecord template flowset record.
     */
    @Override
    public void addTemplateFlowSet(DataTemplateRecord templateRecord) {
        store.updateTemplateFlowSet(templateRecord);
    }

    /**
     * Update data flowset to controller.
     * it will update new data flowset to store.
     *
     * @param dataFlowRecord data flowset record.
     */
    @Override
    public void updateDataFlowSet(DataFlowRecord dataFlowRecord) {
        store.addDataFlowSet(dataFlowRecord);
    }

    /**
     * Get template flowset from controller.
     * it will fetch current template which is matching to the template id from store.
     *
     * @param templateId template id.
     * @return optional of data template record, optional will be empty if template not found.
     */
    @Override
    public Optional<DataTemplateRecord> getTemplateFlowSet(TemplateId templateId) {
        return store.getTemplateFlowSet(templateId);
    }

    /**
     * Get data flowset from controller.
     * it will fetch current data flowset which is matching to the template id from store.
     *
     * @param templateId template id.
     * @return list of data flow record, list will be empty if template id not matched.
     */
    @Override
    public List<DataFlowRecord> getDataFlowSet(TemplateId templateId) {
        return store.getDataFlowSet(templateId);
    }

    /**
     * Get all template flowsets from controller.
     * it will fetch all templates from store.
     *
     * @return set of data template record, set will be empty if templates not found in the store.
     */
    @Override
    public Set<DataTemplateRecord> getTemplateFlowSet() {
        return store.getTemplateFlowSet();
    }

    /**
     * Get data flowset from controller.
     * it will fetch current data flowset which is matching to the template id from store.
     *
     * @return mapping from a template id to data flow record.
     */
    @Override
    public Map<TemplateId, List<DataFlowRecord>> getDataFlowSet() {
        return store.getDataFlowSet();
    }

}