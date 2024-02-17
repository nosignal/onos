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

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.SimpleChannelHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.onosproject.sflow.SflowController;

/**
 * Channel handler deals with the netfow exporter connection and dispatches messages
 * from netfow exporter to the appropriate locations.
 */
public class SflowChannelHandler extends SimpleChannelHandler {

    private static final Logger log = LoggerFactory.getLogger(SflowChannelHandler.class);

    private Channel channel;

    private SflowController controller;

    /**
     * Create a new sFlow channelHandler instance.
     *
     * @param controller sFlow controller.
     */
    SflowChannelHandler(SflowController controller) {
        this.controller = controller;
    }

    /**
     * sFlow channel connect to sFlow exporter.
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
     * sFlow channel disconnect to sFlow exporter.
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
     * sFlow channel exception to sFlow exporter.
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
     * sFlow message receive from sFlow exporter.
     *
     * @param ctx   channel handler context
     * @param event channel message event
     * @throws Exception on error while parsing exception
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent event) throws Exception {
        channel = event.getChannel();
    }

}

