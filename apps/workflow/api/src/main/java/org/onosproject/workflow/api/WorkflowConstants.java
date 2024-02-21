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
package org.onosproject.workflow.api;

public final class WorkflowConstants {

    public static final String SLASH = "/";
    public static final String PAYLOAD = "payload";
    public static final String MSG = "msg";
    public static final String EXTRA = "extra";
    public static final String CHANGED = "changed";
    public static final String ARCHIVED = "archived";
    public static final String CANDIDATED = "candidated";
    public static final String WORKFLOW_PARAMS = "workflowParams";
    public static final long TIMEOUT_MS = 60000L;
    public static final long DEFAULT_ADDITIONAL_TIMEOUT = 500L;
    public static final String PARAMS = "params";
    public static final String WF_ID = "id";
    public static final String WORKPLACE_NAME = "workplaceName";
    public static final String OPERATION = "op";
    public static final String WORKFLOW_INVOKE = "workflow.invoke";
    public static final String WORFLOWS = "workflows";
    public static final String CONFIGURATION = "configuration";
    public static final String CONFIGURATION_PATH = SLASH + CONFIGURATION;
    public static final String VXLAN = "vxlan";
    public static final String HOSTNAME = "hostname";
    public static final String HOSTNAME_PATH = SLASH + HOSTNAME;
    public static final String MODEL = "model";
    public static final String MODEL_PATH = SLASH + MODEL;
    public static final String VENDOR = "vendor";
    public static final String JUNIPER_VENDOR = "JUNIPER";
    public static final String UBIQUOSS_VENDOR = "UBIQUOSS";
    public static final String VENDOR_PATH = SLASH + VENDOR;
    public static final String WORKSPACE = "workspace";
    public static final String WORKLETS = "worklets";
    public static final String NAME = "name";



    protected WorkflowConstants() {
    }

}

