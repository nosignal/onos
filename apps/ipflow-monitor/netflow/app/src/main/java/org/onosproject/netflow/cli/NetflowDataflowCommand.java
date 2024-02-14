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


import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.onosproject.cli.AbstractShellCommand;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import java.util.List;
import java.util.Map;
import java.util.Collection;

import org.onosproject.netflow.NetflowController;
import org.onosproject.netflow.TemplateId;
import org.onosproject.netflow.DataFlowRecord;

/**
 * Lists all netflow data flowsets.
 */
@Service
@Command(scope = "onos", name = "netflow-dataflow",
        description = "Lists all data flowsets received from netflow exporter.")
public class NetflowDataflowCommand extends AbstractShellCommand {

    @Argument(index = 0, name = "templateid",
            description = "Data flowset template id",
            required = false, multiValued = false)
    int templateID = 0;

    @Override
    protected void doExecute() {
        NetflowController controller = AbstractShellCommand.get(NetflowController.class);

        if (templateID < 0) {
            print("Invalid template ID");
            return;
        }
        if (templateID > 0) {
            List<DataFlowRecord> dataFlowSet = controller.getDataFlowSet(TemplateId.valueOf(templateID));
            if (!dataFlowSet.isEmpty()) {
                dataFlowSet.stream().forEach(data -> printDataflowSet(data));
            } else {
                print("Default template not found");
            }
        } else {
            Map<TemplateId, List<DataFlowRecord>> dataFlowSets = controller.getDataFlowSet();
            if (dataFlowSets.isEmpty()) {
                print("Default template not found");
            } else {
                dataFlowSets.values().stream().flatMap(Collection::stream).forEach(data -> printDataflowSet(data));
            }
        }
    }

    /**
     * Adds data flowset details in specified row wise.
     *
     * @param dataFlowRecord data flowset record.
     */
    private void printDataflowSet(DataFlowRecord dataFlowRecord) {
        print("Template ID : %d", dataFlowRecord.getTemplateId().getId());
        dataFlowRecord.getFlows().forEach(dataflow -> {
            print("Field : %s,  Value : %s", dataflow.getField().name(),
                    dataflow.getValue().toString());
        });
        print("\n");
    }
}
