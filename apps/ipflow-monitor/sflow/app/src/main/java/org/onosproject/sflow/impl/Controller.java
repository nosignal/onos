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

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ConnectionlessBootstrap;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.FixedReceiveBufferSizePredictorFactory;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioDatagramChannelFactory;
import org.jboss.netty.channel.FixedReceiveBufferSizePredictor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.onosproject.sflow.SflowController;

/**
 * The main controller class. Handles all setup and network listeners -
 * Ownership of sflow message receiver.
 */
public class Controller {

    private static final Logger log = LoggerFactory.getLogger(Controller.class);

    private ChannelGroup channelGroup;
    private Channel serverChannel;

    // Configuration options
    protected static final short SFLOW_PORT_NUM = 6343;
    private final int workerThreads = 16;

    // Start time of the controller
    private long systemStartTime;

    private ChannelFactory serverExecFactory;

    private static final int BUFFER_SIZE = 5 * 1024;

    private SflowController controller;

    /**
     * Constructor to initialize the values.
     *
     * @param controller sFlow controller instance
     */
    public Controller(SflowController controller) {
        this.controller = controller;
    }

    /**
     * To get system start time.
     *
     * @return system start time in milliseconds
     */
    public long getSystemStartTime() {
        return (this.systemStartTime);
    }

    /**
     * Initialize timer.
     */
    public void init() {
        this.systemStartTime = System.currentTimeMillis();
    }

    /**
     * Gets run time memory.
     *
     * @return run time memory
     */
    public Map<String, Long> getMemory() {
        Map<String, Long> m = new HashMap<>();
        Runtime runtime = Runtime.getRuntime();
        m.put("total", runtime.totalMemory());
        m.put("free", runtime.freeMemory());
        return m;
    }

    /**
     * Gets UP time.
     *
     * @return UP time
     */
    public Long getUptime() {
        RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
        return rb.getUptime();
    }

    /**
     * sFlow collector it will receive message from sFlow exporter.
     */
    public void run() {

        try {

            final ConnectionlessBootstrap bootstrap = createServerBootStrap();

            bootstrap.setOption("reuseAddress", false);
            bootstrap.setOption("child.reuseAddress", false);
            bootstrap.setOption("readBufferSize", BUFFER_SIZE);
            bootstrap.setOption("receiveBufferSizePredictor",
                    new FixedReceiveBufferSizePredictor(BUFFER_SIZE));
            bootstrap.setOption("receiveBufferSizePredictorFactory",
                    new FixedReceiveBufferSizePredictorFactory(BUFFER_SIZE));
            ChannelPipelineFactory pfact = new SflowPipelineFactory(this.controller);

            bootstrap.setPipelineFactory(pfact);
            InetSocketAddress inetSocketAddress = new InetSocketAddress(SFLOW_PORT_NUM);
            channelGroup = new DefaultChannelGroup();
            serverChannel = bootstrap.bind(inetSocketAddress);
            channelGroup.add(serverChannel);
            log.info("Listening for sFlow exporter connection on {}", inetSocketAddress);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Creates server boot strap.
     *
     * @return ServerBootStrap
     */
    private ConnectionlessBootstrap createServerBootStrap() {

        if (workerThreads == 0) {
            serverExecFactory = new NioDatagramChannelFactory(
                    Executors.newFixedThreadPool(2));
            return new ConnectionlessBootstrap(serverExecFactory);
        } else {
            serverExecFactory = new NioDatagramChannelFactory(
                    Executors.newFixedThreadPool(2),
                    workerThreads);
            return new ConnectionlessBootstrap(serverExecFactory);
        }
    }

    /**
     * Stops the sFlow collector.
     */
    public void stop() {
        log.info("Stopped");
        channelGroup.close();
    }

    /**
     * Starts the sFlow collector.
     */
    public void start() {
        log.info("Started");
        this.run();
    }
}
