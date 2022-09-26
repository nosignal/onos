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

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

import java.util.Objects;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Default implementation of kubernetes external load balancer.
 */
public final class DefaultKubernetesExternalLb implements KubernetesExternalLb {

    private static final String NOT_NULL_MSG = "Loadbalancer % cannot be null";

    private final String serviceName;
    private final String loadbalancerIp;
    private final Set<Integer> nodePortSet;
    private final Set<Integer> portSet;
    private final Set<String> endpointSet;
    private final String electedGateway;

    public DefaultKubernetesExternalLb(String serviceName, String loadbalancerIp,
                                       Set<Integer> nodePortSet, Set<Integer> portSet,
                                       Set<String> endpointSet, String electedGateway) {
        this.serviceName = serviceName;
        this.loadbalancerIp = loadbalancerIp;
        this.nodePortSet = nodePortSet;
        this.portSet = portSet;
        this.endpointSet = endpointSet;
        this.electedGateway = electedGateway;
    }

    @Override
    public String serviceName() {
        return serviceName;
    }

    @Override
    public String loadBalancerIp() {
        return loadbalancerIp;
    }

    @Override
    public Set<Integer> nodePortSet() {
        return ImmutableSet.copyOf(nodePortSet);
    }

    @Override
    public Set<Integer> portSet() {
        return ImmutableSet.copyOf(portSet);
    }

    @Override
    public Set<String> endpointSet() {
        return ImmutableSet.copyOf(endpointSet);
    }

    @Override
    public String electedGateway() {
        return electedGateway;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DefaultKubernetesExternalLb that = (DefaultKubernetesExternalLb) o;
        return serviceName.equals(that.serviceName) && loadbalancerIp.equals(that.loadbalancerIp) &&
                Objects.equals(nodePortSet, that.nodePortSet) &&
                Objects.equals(portSet, that.portSet) &&
                Objects.equals(endpointSet, that.endpointSet) &&
                Objects.equals(electedGateway, that.electedGateway);
    }

    @Override
    public KubernetesExternalLb updateElectedGateway(String electedGateway) {
        return DefaultKubernetesExternalLb.builder()
                .serviceName(serviceName)
                .loadBalancerIp(loadbalancerIp)
                .nodePortSet(nodePortSet)
                .portSet(portSet)
                .endpointSet(endpointSet)
                .electedGateway(electedGateway)
                .build();
    }
    @Override
    public int hashCode() {
        return Objects.hash(serviceName, loadbalancerIp);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("serviceName", serviceName)
                .add("loadbalancerIp", loadbalancerIp)
                .add("nodePort", nodePortSet)
                .add("port", portSet)
                .add("endpointSet", endpointSet)
                .add("electedGateway", electedGateway)
                .toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder implements KubernetesExternalLb.Builder {
        private String serviceName;
        private String loadbalancerIp;
        private Set<Integer> nodePortSet;
        private Set<Integer> portSet;
        private Set<String> endpointSet;
        private String electedGateway;

        private Builder() {
        }

        @Override
        public KubernetesExternalLb build() {
            checkArgument(serviceName != null, NOT_NULL_MSG, "serviceName");
            checkArgument(loadbalancerIp != null, NOT_NULL_MSG, "loadbalancerIp");
            checkArgument(!nodePortSet.isEmpty(), NOT_NULL_MSG, "nodePortSet");
            checkArgument(!portSet.isEmpty(), NOT_NULL_MSG, "portSet");

            return new DefaultKubernetesExternalLb(serviceName, loadbalancerIp,
                    nodePortSet, portSet, endpointSet, electedGateway);
        }

        @Override
        public Builder serviceName(String serviceName) {
            this.serviceName = serviceName;
            return this;
        }

        @Override
        public Builder loadBalancerIp(String loadBalancerIp) {
            this.loadbalancerIp = loadBalancerIp;
            return this;
        }

        @Override
        public Builder nodePortSet(Set<Integer> nodePortSet) {
            this.nodePortSet = nodePortSet;
            return this;
        }

        @Override
        public Builder portSet(Set<Integer> portSet) {
            this.portSet = portSet;
            return this;
        }

        @Override
        public Builder endpointSet(Set<String> endpointSet) {
            this.endpointSet = endpointSet;
            return this;
        }

        public Builder electedGateway(String electedGateway) {
            this.electedGateway = electedGateway;
            return this;
        }
    }
}
