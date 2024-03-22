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


import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.AdaptiveReceiveBufferSizePredictor;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.FixedReceiveBufferSizePredictorFactory;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.onosproject.bgpmonitoring.BmpController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

import static org.onlab.util.Tools.groupedThreads;

/**
 * The main controller class. Handles all setup and network listeners - Ownership of bmp message receiver
 */
public class Controller {

    private static final Logger log = LoggerFactory.getLogger(Controller.class);

    private ChannelGroup channelGroup;
    private Channel serverChannel;

    protected static final short BMP_PORT_NUM = 9000;
    private final int workerThreads = 16;

    private long systemStartTime;

    private NioServerSocketChannelFactory serverExecFactory;
    private BmpController bmpController;

    private static final int SEND_BUFFER_SIZE = 8 * 1024 * 1024;

    /**
     * Constructor to initialize the values.
     *
     * @param bmpController bmp controller instance
     */
    public Controller(BmpController bmpController) {
        this.bmpController = bmpController;
    }

    /**
     * To get system start time.
     *
     * @return system start time in milliseconds
     */
    public long getSystemStartTime() {
        return (this.systemStartTime);
    }

    public void init() {
        this.systemStartTime = System.currentTimeMillis();
    }

    /**
     * Gets run time memory.
     *
     * @return m run time memory
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
     * Tell controller it will accept bmp router connections.
     */
    public void run() {

        try {

            final ServerBootstrap bootstrap = createServerBootStrap();

            bootstrap.setOption("reuseAddress", true);
            bootstrap.setOption("tcpNoDelay", true);
            bootstrap.setOption("keepAlive", true);
            bootstrap.setOption("receiveBufferSize", Controller.SEND_BUFFER_SIZE);
            bootstrap.setOption("receiveBufferSizePredictorFactory",
                    new FixedReceiveBufferSizePredictorFactory(
                            Controller.SEND_BUFFER_SIZE));
            bootstrap.setOption("receiveBufferSizePredictor",
                    new AdaptiveReceiveBufferSizePredictor(64, 4096, 65536));
            bootstrap.setOption("child.keepAlive", true);
            bootstrap.setOption("child.tcpNoDelay", true);
            bootstrap.setOption("child.sendBufferSize", Controller.SEND_BUFFER_SIZE);
            bootstrap.setOption("child.receiveBufferSize", Controller.SEND_BUFFER_SIZE);
            bootstrap.setOption("child.receiveBufferSizePredictorFactory",
                    new FixedReceiveBufferSizePredictorFactory(
                            Controller.SEND_BUFFER_SIZE));
            bootstrap.setOption("child.reuseAddress", true);

            ChannelPipelineFactory pfact = null;

            bootstrap.setPipelineFactory(pfact);
            InetSocketAddress inetSocketAddress = new InetSocketAddress(BMP_PORT_NUM);
            channelGroup = new DefaultChannelGroup();
            serverChannel = bootstrap.bind(inetSocketAddress);
            channelGroup.add(serverChannel);
            log.info("Listening for bmp router connection on {}", inetSocketAddress);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Creates server boot strap.
     *
     * @return ServerBootStrap
     */
    private ServerBootstrap createServerBootStrap() {

        if (workerThreads == 0) {
            serverExecFactory = new NioServerSocketChannelFactory(
                    Executors.newCachedThreadPool(groupedThreads("onos/bmp", "boss-%d")),
                    Executors.newCachedThreadPool(groupedThreads("onos/bmp", "worker-%d")));
            return new ServerBootstrap(serverExecFactory);
        } else {
            serverExecFactory = new NioServerSocketChannelFactory(
                    Executors.newCachedThreadPool(groupedThreads("onos/bmp", "boss-%d")),
                    Executors.newCachedThreadPool(groupedThreads("onos/bmp", "worker-%d")),
                    workerThreads);
            return new ServerBootstrap(serverExecFactory);
        }
    }

    /**
     * Stops the BMP controller.
     */
    public void stop() {
        log.info("Stopped");
        serverExecFactory.shutdown();
        channelGroup.close();
    }

    /**
     * Starts the BMP controller.
     */
    public void start() {
        log.info("Started");
        this.run();
    }


}
