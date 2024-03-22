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

import org.onosproject.bgpmonitoring.BmpController;
import org.onosproject.bgpmonitoring.BmpPacket;
import org.onosproject.bgpmonitoring.BmpParseException;
import org.onosproject.core.ApplicationId;
import org.onosproject.core.CoreService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Deactivate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate = true)
public class BmpControllerImpl implements BmpController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private Controller controller;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected CoreService coreService;

    private ApplicationId appId;

    @Activate
    protected void activate() {

        appId = coreService.registerApplication("org.onosproject.bgpmonitoring");
        controller = new Controller(this);
        controller.start();
        log.info("BMP activated");
    }

    @Deactivate
    protected void deactivate() {
        log.info("BMP stopped");
    }

    /**
     * Start listening bmp messages from bmp router.
     */
    @Override
    public void startListener() {
        log.info("BMP station start listening...");
        controller.start();
    }

    /**
     * Stop listening bmp messages from bmp router.
     */
    @Override
    public void closeListener() {
        log.info("BMP station stop listening...");
        controller.stop();
    }

    /**
     * Process bmp message and notify the appropriate listeners.
     *
     * @param packet the BMP packet to process.
     * @throws BmpParseException on data processing error
     */
    @Override
    public void processBmpPacket(BmpPacket packet) throws BmpParseException {
        //TODO
        log.info("BMP message received {}", packet);
    }
}
