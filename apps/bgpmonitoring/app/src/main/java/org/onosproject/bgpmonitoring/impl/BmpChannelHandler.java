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

package org.onosproject.bgpmonitoring.impl;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;
import org.jboss.netty.channel.Channel;

import org.onosproject.bgpmonitoring.BmpController;
import org.onosproject.bgpmonitoring.BmpPacket;
import org.onosproject.bgpmonitoring.BmpParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Channel handler deals with the bmp router connection and
 * dispatches messages from bmp router to the appropriate locations.
 */
public class BmpChannelHandler extends IdleStateAwareChannelHandler {

    private static final Logger log = LoggerFactory.getLogger(BmpChannelHandler.class);

    private Channel channel;
    private BmpController controller;


    /**
     * Create a new BmpChannelHandler instance.
     *
     * @param controller bmp controller
     */
    BmpChannelHandler(BmpController controller) {
        this.controller = controller;
    }

    /**
     * BMP channel connect to BMP router.
     *
     * @param ctx   channel handler context
     * @param event channel state event
     * @throws Exception on error while connecting channel
     */
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent event) throws Exception {
        channel = event.getChannel();
        log.info("Bmp connected from {}", channel.getRemoteAddress());
    }

    /**
     * BMP channel disconnect from BMP router.
     *
     * @param ctx   channel handler context
     * @param event channel state event
     * @throws Exception on error while disconnecting channel
     */
    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent event) throws Exception {
        channel = event.getChannel();
        log.info("Bmp disconnected from {}", channel.getRemoteAddress());
    }


    /**
     * BMP channel caught exception message from BMP router.
     *
     * @param ctx   channel handler context
     * @param event channel exception event
     * @throws Exception on error while parsing exception
     */

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent event) throws Exception {
        log.error("Bmp channel exception caught ", event.getCause());
    }

    /**
     * BMP channel receives BMP message from BMP router.
     *
     * @param ctx   channel handler context
     * @param event channel message event
     * @throws Exception on error while parsing exception
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent event) throws Exception {
        log.info("Bmp message received {}", event.getMessage());
        if (event.getMessage() instanceof List) {
            ((List<BmpPacket>) event.getMessage()).stream()
                    .forEach(bmp -> processBmpPacket(bmp));
        } else {
            processBmpPacket((BmpPacket) event.getMessage());
        }
    }

    private void processBmpPacket(BmpPacket packet) {
        try {
            controller.processBmpPacket(packet);
        } catch (BmpParseException ex) {
            log.error("Bmp packet parser exception ", ex);
        }

    }
}
