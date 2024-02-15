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
package org.onosproject.sflow.impl;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.onosproject.sflow.SflowController;

/**
 * Creates a ChannelPipeline for a server-side sFlow message channel.
 */
public class SflowPipelineFactory implements ChannelPipelineFactory {

    private SflowController controller;

    /**
     * Constructor to initialize the values.
     *
     * @param controller sFlow controller.
     */
    public SflowPipelineFactory(SflowController controller) {
        super();
        this.controller = controller;
    }

    /**
     * Get server-side pipe line channel.
     *
     * @return ChannelPipeline server-side pipe line channel
     * @throws Exception on while getting pipe line
     */
    @Override
    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipeline = Channels.pipeline();
        return pipeline;
    }

}

