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
package org.onosproject.netflow.cli;

import org.apache.karaf.shell.api.action.Command;
import org.onosproject.cli.AbstractShellCommand;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.stream.Collectors;

import org.onosproject.netflow.NetflowController;
import org.onosproject.netflow.TemplateId;
import org.onosproject.netflow.DataTemplateRecord;
import org.onosproject.netflow.DataFlowRecord;

/**
 * Netflow overall summary report.
 */
@Service
@Command(scope = "onos", name = "netflow-summary",
        description = "Summary of template flowsets and data flowsets.")
public class NetflowSummaryCommand extends AbstractShellCommand {

    @Override
    protected void doExecute() {
        NetflowController controller = AbstractShellCommand.get(NetflowController.class);
        Set<DataTemplateRecord> templates = controller.getTemplateFlowSet();
        if (templates.isEmpty()) {
            print("Template not found");
        } else {
            Set<Integer> templateIds = templates.stream()
                    .map(DataTemplateRecord::getTemplateId)
                    .map(TemplateId::getId)
                    .collect(Collectors.toSet());
            printTemplateflowSet(templateIds);

        }
        Map<TemplateId, List<DataFlowRecord>> dataFlowSets = controller.getDataFlowSet();
        if (dataFlowSets.isEmpty()) {
            print("Data not found");
        } else {
            printDataflowSet(dataFlowSets);
        }
    }

    /**
     * Adds template flowset summary in specified row wise.
     *
     * @param templateIds template ids
     */
    private void printTemplateflowSet(Set<Integer> templateIds) {
        print("Number of templates : %d, Template IDs : %s",
                templateIds.size(),
                templateIds.toString());
    }

    /**
     * Adds data flowset summary in specified row wise.
     *
     * @param dataflowMap data flow map
     */
    private void printDataflowSet(Map<TemplateId, List<DataFlowRecord>> dataflowMap) {
        dataflowMap.entrySet().forEach(entry -> {
            print("Template ID : %d, Data flow count : %d",
                    entry.getKey().getId(),
                    entry.getValue().size());
        });
        print("\n");
    }
}
