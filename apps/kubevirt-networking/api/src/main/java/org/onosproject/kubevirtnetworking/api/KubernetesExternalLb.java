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

import java.util.Set;

/**
 * Representation of kubernetes external load balancer.
 */
public interface KubernetesExternalLb {
    /**
     * Returns the service name.
     *
     * @return service name
     */
    String serviceName();

    /**
     * Returns the load balancer IP.
     *
     * @return load balancer IP
     */
    String loadBalancerIp();

    /**
     * Returns the set of node port.
     *
     * @return node port
     */
    Set<Integer> nodePortSet();

    /**
     * Returns the set of port.
     *
     * @return port number
     */
    Set<Integer> portSet();

    /**
     * Returns the set of endpoint.
     *
     * @return endpoint set
     */
    Set<String> endpointSet();

    /**
     * Returns the elected gateway node for this service.
     *
     * @return gateway node hostname
     */
    String electedGateway();

    /**
     * Updates the elected gateway node host name.
     *
     * @param electedGateway updated elected gateway node hostname
     * @return kubernetes external lb with the updated gateway node hostname
     */
    KubernetesExternalLb updateElectedGateway(String electedGateway);

    interface Builder {
        /**
         * Builds an immutable kubernetes external load balancer instance.
         *
         * @return kubernetes external load balancer
         */
        KubernetesExternalLb build();

        /**
         * Returns kubernetes external load balancer builder with supplied service name.
         *
         * @param serviceName external load balancer service name
         * @return external load balancer builder
         */
        Builder serviceName(String serviceName);

        /**
         * Returns kubernetes external load balancer builder with supplied load balancer Ip.
         *
         * @param loadBalancerIp external load balancer Ip
         * @return external load balancer builder
         */
        Builder loadBalancerIp(String loadBalancerIp);

        /**
         * Returns kubernetes external load balancer builder with supplied node port set.
         *
         * @param nodePortSet node port set
         * @return external load balancer builder
         */
        Builder nodePortSet(Set<Integer> nodePortSet);

        /**
         * Returns kubernetes external load balancer builder with supplied port set.
         *
         * @param portSet port set
         * @return external load balancer builder
         */
        Builder portSet(Set<Integer> portSet);

        /**
         * Returns kubernetes external load balancer builder with supplied endpoint set.
         *
         * @param endpointSet endpoint set
         * @return external load balancer builder
         */
        Builder endpointSet(Set<String> endpointSet);

        /**
         * Returns kubernetes external load balancer builder with supplied elected gateway.
         *
         * @param gateway gateway node hostname
         * @return gateway node hostname
         */
        Builder electedGateway(String gateway);
    }
}
