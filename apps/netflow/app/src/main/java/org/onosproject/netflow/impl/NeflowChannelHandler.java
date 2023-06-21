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
package org.onosproject.netflow.impl;

import java.util.Optional;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.SimpleChannelHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.onosproject.netflow.DataTemplateRecord;
import org.onosproject.netflow.FlowSet;
import org.onosproject.netflow.TemplateFlowSet;
import org.onosproject.netflow.DataFlowSet;
import org.onosproject.netflow.TemplateId;
import org.onosproject.netflow.NetflowController;

/**
 * Channel handler deals with the netfow exporter connection and dispatches messages
 * from netfow exporter to the appropriate locations.
 */
public class NeflowChannelHandler extends SimpleChannelHandler {

    private static final Logger log = LoggerFactory.getLogger(NeflowChannelHandler.class);

    private Channel channel;

    private NetflowController controller;

    /**
     * Create a new netflow channelHandler instance.
     *
     * @param controller netflow controller.
     */
    NeflowChannelHandler(NetflowController controller) {
        this.controller = controller;
    }

    /**
     * Netflow channel connect to netflow exporter.
     *
     * @param ctx   channel handler context
     * @param event channel state event
     * @throws Exception on error while connecting channel
     */
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent event) throws Exception {
        channel = event.getChannel();
    }

    /**
     * Netflow channel disconnect to netflow exporter.
     *
     * @param ctx   channel handler context
     * @param event channel state event
     * @throws Exception on error while disconnecting channel
     */
    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent event) throws Exception {
        channel = event.getChannel();
    }

    /**
     * Netflow channel exception to netflow exporter.
     *
     * @param ctx   channel handler context
     * @param event channel exception event
     * @throws Exception on error while parsing exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent event) throws Exception {
        //TODO exception handler
    }

    /**
     * Netflow message receive from netflow exporter.
     *
     * @param ctx   channel handler context
     * @param event channel message event
     * @throws Exception on error while parsing exception
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent event) throws Exception {
        try {
            NetFlowPacket netFlowPacket = (NetFlowPacket) event.getMessage();
            netFlowPacket.getFlowSets()
                    .stream()
                    .filter(n -> n.getType() == FlowSet.Type.TEMPLATE_FLOWSET)
                    .map(t -> (TemplateFlowSet) t)
                    .flatMap(t -> t.getRecords().stream())
                    .forEach(t -> controller.addTemplateFlowSet(t));

            netFlowPacket.getFlowSets()
                    .stream()
                    .filter(n -> n.getType() == FlowSet.Type.DATA_FLOWSET)
                    .map(t -> (DataFlowSet) t)
                    .forEach(data -> {
                        Optional<DataTemplateRecord> template = controller
                                .getTemplateFlowSet(TemplateId.valueOf(data.getFlowSetId()));
                        if (!template.isPresent()) {
                            return;
                        }
                        try {
                            data.dataDeserializer(template.get());
                            data.getDataFlow()
                                    .stream()
                                    .forEach(dataflow -> controller.updateDataFlowSet(dataflow));
                        } catch (Exception ex) {
                            log.error("Netflow dataflow deserializer exception ", ex);
                        }
                    });
            log.info("Netflow message received {}", netFlowPacket);
        } catch (Exception er) {
            log.error("Netflow message deserializer exception ", er);
        }

    }

}
