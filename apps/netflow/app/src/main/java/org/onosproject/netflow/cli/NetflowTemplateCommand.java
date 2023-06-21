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
import java.util.Set;
import java.util.Optional;

import org.onosproject.netflow.NetflowController;
import org.onosproject.netflow.TemplateId;
import org.onosproject.netflow.DataTemplateRecord;

/**
 * Lists all netflow template flowsets.
 */
@Service
@Command(scope = "onos", name = "netflow-template",
        description = "Lists all template flowsets received from netflow exporter.")
public class NetflowTemplateCommand extends AbstractShellCommand {

    @Argument(index = 0, name = "templateID",
            description = "Netflow template ID",
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
            Optional<DataTemplateRecord> template = controller.getTemplateFlowSet(TemplateId.valueOf(templateID));
            if (template.isPresent()) {
                printTemplate(template.get());
            } else {
                print("Default template not found");
            }
        } else {
            Set<DataTemplateRecord> templates = controller.getTemplateFlowSet();
            if (templates.isEmpty()) {
                print("Default template not found");
            } else {
                templates.stream().forEach(template -> printTemplate(template));
            }
        }
    }

    /**
     * Adds template flowset details in specified row wise.
     *
     * @param template template flow set.
     */
    private void printTemplate(DataTemplateRecord template) {
        print("Template ID : %d, Field Count : %d",
                template.getTemplateId().getId(),
                template.getFiledCount());
        template.getFields().forEach(flowTemplateField -> {
            print("Field : %s, Length : %d",
                    flowTemplateField.getFlowField().name(),
                    flowTemplateField.getLength());
        });
        print("\n");
    }
}
