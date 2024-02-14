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
import java.util.Optional;
import java.util.Map;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.function.Predicate;

import org.onosproject.netflow.NetflowController;
import org.onosproject.netflow.TemplateId;
import org.onosproject.netflow.DataFlowRecord;
import org.onosproject.netflow.Flow;
import org.onosproject.netflow.FlowField;

/**
 * Flow traffic summary report.
 */
@Service
@Command(scope = "onos", name = "netflow-traffic-summary",
        description = "Summary of data flowset records based on in/out bytes/packets, protocols or applications.")
public class NetflowTrafficSummaryCommand extends AbstractShellCommand {

    @Override
    protected void doExecute() {
        NetflowController controller = AbstractShellCommand.get(NetflowController.class);
        Map<TemplateId, List<DataFlowRecord>> dataFlowSets = controller.getDataFlowSet();
        if (dataFlowSets.isEmpty()) {
            print("Data not found");
            return;
        }

        Map<String, List<DataFlowRecord>> ds = dataFlowSets.values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(d -> hostWiseFlow(d.getFlows())));
        printHostTraffic(ds);

        Map<String, Map<String, List<DataFlowRecord>>> protocol = new HashMap<>();
        ds.entrySet().forEach(entry -> {
            protocol.put(entry.getKey(), protocolWiseFlow(entry.getValue()));
        });
        printPrtocolTraffic(protocol);

        Map<String, Map<String, List<DataFlowRecord>>> application = new HashMap<>();
        ds.entrySet().forEach(entry -> {
            application.put(entry.getKey(), applicationWiseFlow(entry.getValue()));
        });
        printApplicationTraffic(application);
    }

    /**
     * Host wise flow filter.
     * ipv4 and ipv6 host flow fields will be filtered and flow direction
     * added from src ip to dst ip
     *
     * @param flows collection of flows
     */
    private String hostWiseFlow(List<Flow> flows) {
        Predicate<FlowField> srcIpParser = f -> (f.equals(FlowField.IPV4_SRC_ADDR) ||
                f.equals(FlowField.IPV6_SRC_ADDR));
        Predicate<FlowField> dstIpParser = f -> (f.equals(FlowField.IPV4_DST_ADDR) ||
                f.equals(FlowField.IPV6_DST_ADDR));
        String srcIp = getFieldValue(flows, srcIpParser).get().toString();
        String dstIp = getFieldValue(flows, dstIpParser).get().toString();
        return srcIp + "  =>  " + dstIp;
    }

    /**
     * Summary of all ingress bytes.
     *
     * @param dataFlowRecords collection of data flowset recored.
     */
    private int totalInBytes(List<DataFlowRecord> dataFlowRecords) {
        return dataFlowRecords.stream()
                .map(data -> inBytes(data.getFlows()))
                .collect(Collectors.summingInt(Integer::intValue));
    }

    /**
     * Summary of all ingress packets.
     *
     * @param dataFlowRecords collection of data flowset recored.
     */
    private int totalInPackets(List<DataFlowRecord> dataFlowRecords) {
        return dataFlowRecords.stream()
                .map(data -> inPackets(data.getFlows()))
                .collect(Collectors.summingInt(Integer::intValue));
    }

    /**
     * Application protocol wise flow filter.
     *
     * @param dataFlowRecords collection of data flowset recored.
     */
    private Map<String, List<DataFlowRecord>> applicationWiseFlow(List<DataFlowRecord> dataFlowRecords) {
        return dataFlowRecords.stream()
                .collect(Collectors.groupingBy(d -> getFieldValue(d.getFlows(),
                        FlowField.L4_DST_PORT).get().toString()));

    }

    /**
     * Transport protocol wise flow filter.
     *
     * @param dataFlowRecords collection of data flowset recored.
     */
    private Map<String, List<DataFlowRecord>> protocolWiseFlow(List<DataFlowRecord> dataFlowRecords) {
        return dataFlowRecords.stream()
                .collect(Collectors.groupingBy(d -> getFieldValue(d.getFlows(), FlowField.PROTOCOL).get().toString()));

    }

    /**
     * Filter ingress bytes from flows and type cast to integer.
     *
     * @param flows collection of flows.
     */
    private int inBytes(List<Flow> flows) {
        return Integer.parseInt(getFieldValue(flows, FlowField.IN_BYTES).get().toString());
    }

    /**
     * Filter ingress packets from flows and type cast to integer.
     *
     * @param flows collection of flows.
     */
    private int inPackets(List<Flow> flows) {
        return Integer.parseInt(getFieldValue(flows, FlowField.IN_PKTS).get().toString());
    }

    /**
     * Get flow field value from collection of flows.
     * get flow field value which is matching to the given flow field.
     *
     * @param flows collection of flows
     * @param field flow field
     */
    private Optional<Object> getFieldValue(List<Flow> flows, FlowField field) {
        return flows.stream()
                .filter(flow -> flow.getField() == field)
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
     * Adds host wise traffic summary in specified row wise.
     *
     * @param hosts mapping of host and traffic details.
     */
    private void printHostTraffic(Map<String, List<DataFlowRecord>> hosts) {
        print("\nHost wise traffic flow");
        hosts.entrySet().forEach(entry -> {
            print("Traffic flow : %s, Total bytes : %d, Total packets : %d",
                    entry.getKey(),
                    totalInBytes(entry.getValue()),
                    totalInPackets(entry.getValue()));
        });
        print("\n");
    }

    /**
     * Adds protocol wise traffic summary in specified row wise.
     *
     * @param protocol mapping of portocol and traffic details.
     */
    private void printPrtocolTraffic(Map<String, Map<String, List<DataFlowRecord>>> protocol) {
        print("Protocol wise traffic flow");
        protocol.entrySet().forEach(entry -> {
            entry.getValue().entrySet().forEach(port -> {
                print("Traffic flow : %s, Protocol : %s, Total bytes : %d, Total packets : %d",
                        entry.getKey(),
                        port.getKey(),
                        totalInBytes(port.getValue()),
                        totalInPackets(port.getValue()));
            });
        });
        print("\n");
    }

    /**
     * Adds application wise traffic summary in specified row wise.
     *
     * @param applications mapping of application and traffic details.
     */
    private void printApplicationTraffic(Map<String, Map<String, List<DataFlowRecord>>> applications) {
        print("Application wise traffic flow");
        applications.entrySet().forEach(entry -> {
            entry.getValue().entrySet().forEach(port -> {
                print("Traffic flow : %s, Application : %s, Total bytes : %d, Total packets : %d",
                        entry.getKey(),
                        port.getKey(),
                        totalInBytes(port.getValue()),
                        totalInPackets(port.getValue()));
            });
        });
        print("\n");
    }
}
