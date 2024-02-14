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
import org.apache.karaf.shell.api.action.Completion;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.Collection;
import java.util.function.Predicate;

import org.onosproject.netflow.NetflowController;
import org.onosproject.netflow.TemplateId;
import org.onosproject.netflow.DataFlowRecord;
import org.onosproject.netflow.Flow;
import org.onosproject.netflow.FlowField;

/**
 * Lists all filtered data flowsets.
 */
@Service
@Command(scope = "onos", name = "netflow-traffic-filter",
        description = "Lists all filtered data flowsets received from netflow exporter.")
public class NetflowTrafficFilterCommand extends AbstractShellCommand {

    @Argument(index = 0, name = "field", description = "flow field",
            required = false, multiValued = false)
    @Completion(FlowFieldCompleter.class)
    protected String field = null;

    @Argument(index = 1, name = "value", description = "flow value",
            required = false, multiValued = false)
    protected String value = null;

    @Override
    protected void doExecute() {
        NetflowController controller = AbstractShellCommand.get(NetflowController.class);
        Map<TemplateId, List<DataFlowRecord>> dataFlowSets = controller.getDataFlowSet();
        if (dataFlowSets.isEmpty()) {
            print("Data not found");
            return;
        }
        dataFlowSets.values()
                .stream()
                .flatMap(Collection::stream)
                .filter(df -> {
                    Optional<Object> fieldValue = getFieldValue(df.getFlows(), field);
                    if (fieldValue.isPresent() && fieldValue.toString().equals(value)) {
                        return true;
                    }
                    return false;
                })
                .forEach(df -> prinDataflowSet(df));
    }

    /**
     * Get flow field value from collection of flows.
     * get flow field value which is matching to the given flow field.
     *
     * @param flows collection of flows
     * @param field flow field
     */
    private Optional<Object> getFieldValue(List<Flow> flows, String field) {
        FlowField flowField = FlowField.valueOf(field);
        return flows.stream()
                .filter(flow -> flow.getField() == flowField)
                .map(Flow::getValue)
                .findAny();
    }

    /**
     * Get flow field value from collection of flows.
     * get flow field value which is matching to the given flow field predicates.
     *
     * @param flows collection of flows
     * @param field flow field predicates
     */
    private Optional<Object> getFieldValue(List<Flow> flows, Predicate<FlowField> field) {
        return flows.stream()
                .filter(flow -> field.test(flow.getField()))
                .map(Flow::getValue)
                .findAny();
    }


    /**
     * Adds data flowset record details in specified row wise.
     *
     * @param dataFlowRecord data flowset record.
     */
    private void prinDataflowSet(DataFlowRecord dataFlowRecord) {
        print("Template ID : %d", dataFlowRecord.getTemplateId().getId());
        dataFlowRecord.getFlows().forEach(dataflow -> {
            print("Field : %s, Value : %s",
                    dataflow.getField().name(),
                    dataflow.getValue().toString());
        });
        print("\n");
    }
}
