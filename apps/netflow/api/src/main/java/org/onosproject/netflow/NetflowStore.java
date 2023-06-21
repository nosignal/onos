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
package org.onosproject.netflow;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.Optional;

/**
 * Manages inventory of Netflow template and data flowset to distribute
 * information.
 */
public interface NetflowStore {

    /**
     * Get template flowset from store.
     * it will fetch current template which is matching to the template id from store.
     *
     * @param templateId template id.
     * @return optional of data template record, optional will be empty if template not found.
     */
    Optional<DataTemplateRecord> getTemplateFlowSet(TemplateId templateId);

    /**
     * Get set of template flowsets from store.
     * it will fetch all template flowsets from store.
     *
     * @return set of data template record, set will be empty if templates not found in the store.
     */
    Set<DataTemplateRecord> getTemplateFlowSet();

    /**
     * Get optional template flowset from store.
     * it will fetch current optional template which is matching to the template id from store.
     *
     * @param templateId template id.
     * @return optional of optional template flowset, optional will be empty if template not found.
     */
    Optional<OptionalTemplateFlowSet> getOptionalTemplateFlowSet(TemplateId templateId);

    /**
     * Get set of optional template flowsets from store.
     * it will fetch all optional template flowsets from store.
     *
     * @return set of optional template flowsets, set will be empty if templates not found in the store.
     */
    Set<OptionalTemplateFlowSet> getOptionalTemplateFlowSet();

    /**
     * Get data flowset from store.
     * it will fetch current data flowset which is matching to the template id from store.
     *
     * @param templateId template id.
     * @return list of data flow record, list will be empty if template id not matched.
     */
    List<DataFlowRecord> getDataFlowSet(TemplateId templateId);

    /**
     * Get data flowset from store.
     * it will fetch current data flowset which is matching to the template id from store.
     *
     * @return mapping from a template id to data flow record.
     */
    Map<TemplateId, List<DataFlowRecord>> getDataFlowSet();

    /**
     * Update template flowset to the store.
     * Add new template to store if it not exist,
     * otherwise it will replace the existing template.
     *
     * @param templateRecord template flowset record.
     */
    void updateTemplateFlowSet(DataTemplateRecord templateRecord);

    /**
     * Update optional template flowset to the store.
     * Add new optional template to store if it not exist,
     * otherwise it will replace the existing optional template.
     *
     * @param optionalTemplateFlowSet optional template flowset.
     */
    void updateOptionalTemplateFlowSet(OptionalTemplateFlowSet optionalTemplateFlowSet);

    /**
     * Add data flow record to the store.
     * Add new data flow record to store
     *
     * @param dataFlowRecord data flow record.
     */
    void addDataFlowSet(DataFlowRecord dataFlowRecord);

    /**
     * Remove template flowset from store.
     * it will remove template flowset which is matching to the given template id from store.
     *
     * @param templateId template id.
     */
    void clearTemplateFlowSet(TemplateId templateId);

    /**
     * Remove all template flowset from store.
     * it will remove all template flowsets from store.
     */
    void clearTemplateFlowSet();

    /**
     * Remove optional template flowset from store.
     * it will remove optional template which is matching to the given template id from store.
     *
     * @param templateId template id.
     */
    void clearOptionalTemplateFlowSet(TemplateId templateId);

    /**
     * Remove all optional template flowset from store.
     * it will remove all optional template flowsets from store.
     */
    void clearOptionalTemplateFlowSet();

    /**
     * Remove data flowset from store.
     * it will remove dataflowset which is matching to the given template id from store.
     *
     * @param templateId template id.
     */
    void clearDataFlowSet(TemplateId templateId);

    /**
     * Remove all data flowset from store.
     * it will remove all data flowsets from store.
     */
    void clearDataFlowSet();

    /**
     * Remove template, optional template and data flowsets from store.
     * it will remove all flowsets from store.
     */
    void clearAllFlowSet();

}