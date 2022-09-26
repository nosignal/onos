/*
 * Copyright 2022-present Open Networking Foundation
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
package org.onosproject.kubevirtnetworking.api;

import org.onosproject.event.AbstractEvent;

/**
 * Manages inventory of kubernetes external load balancer states; not intended for direct use.
 */
public class KubernetesExternalLbEvent
    extends AbstractEvent<KubernetesExternalLbEvent.Type, KubernetesExternalLb> {

    public KubernetesExternalLbEvent(Type type, KubernetesExternalLb subject) {
        super(type, subject);
    }

    /**
     * Kubernetes external lb events.
     */
    public enum Type {
        /**
         * Signifies that a new kubevirt load balancer is created.
         */
        KUBERNETES_EXTERNAL_LOAD_BALANCER_CREATED,

        /**
         * Signifies that a kubevirt load balancer is removed.
         */
        KUBERNETES_EXTERNAL_LOAD_BALANCER_REMOVED,

        /**
         * Signifies that a kubevirt load balancer is updated.
         */
        KUBERNETES_EXTERNAL_LOAD_BALANCER_UPDATED,
    }
}
