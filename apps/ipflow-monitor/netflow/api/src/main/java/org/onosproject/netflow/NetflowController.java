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

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Map;

/**
 * It control and manage the netflow traffic.
 * it is Collecting, Storing and analyzing NetFlow data
 * it can help to understand which applications, and protocols
 * may be consuming the most network bandwidth by tracking processes,
 * protocols, times of day, and traffic routing.
 * Ref: https://www.ietf.org/rfc/rfc3954.txt
 */
public interface NetflowController {

    /**
     * Add template flowset to controller.
     * Add new template to controller if it not exist,
     * otherwise it will replace the existing template.
     *
     * @param templateRecord template flowset record.
     */
    void addTemplateFlowSet(DataTemplateRecord templateRecord);

    /**
     * Update data flowset to controller.
     * it will update new data flowset to store.
     *
     * @param dataFlowRecord data flowset record.
     */
    void updateDataFlowSet(DataFlowRecord dataFlowRecord);

    /**
     * Get template flowset from controller.
     * it will fetch current template which is matching to the template id from store.
     *
     * @param templateId template id.
     * @return optional of data template record, optional will be empty if template not found.
     */
    Optional<DataTemplateRecord> getTemplateFlowSet(TemplateId templateId);

    /**
     * Get data flowset from controller.
     * it will fetch current data flowset which is matching to the template id from store.
     *
     * @param templateId template id.
     * @return list of data flow record, list will be empty if template id not matched.
     */
    List<DataFlowRecord> getDataFlowSet(TemplateId templateId);

    /**
     * Get all template flowsets from controller.
     * it will fetch all templates from store.
     *
     * @return set of data template record, set will be empty if templates not found in the store.
     */
    Set<DataTemplateRecord> getTemplateFlowSet();

    /**
     * Get data flowset from controller.
     * it will fetch current data flowset which is matching to the template id from store.
     *
     * @return mapping from a template id to data flow record.
     */
    Map<TemplateId, List<DataFlowRecord>> getDataFlowSet();
}
