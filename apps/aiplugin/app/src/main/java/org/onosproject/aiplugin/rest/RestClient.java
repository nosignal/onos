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
 *
 */

package org.onosproject.aiplugin.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.IOException;

import static com.google.common.net.MediaType.JSON_UTF_8;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.slf4j.LoggerFactory.getLogger;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.onosproject.aiplugin.RestService;

/**
 * Manages REST Communication with AI-as-a-Service (AIaaS) APIs.
 * Interacts with datamonitor and analytics manager modules
 */
@Component(immediate = true, service = RestService.class)
public class RestClient implements RestService {
    private final Logger log = getLogger(getClass());
    private static final String UTF_8 = JSON_UTF_8.toString();
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final String url;

    @Activate
    protected void activate() {
        log.info("Started AI Adapter Service");
    }

    @Deactivate
    public void deactive() {
        log.info("Stopped AI Adapter Service");
    }
    /**
     * Constructor.
     *
     * @param aiServerIpAddress the IP address of the ai server
     * @param aiServerPort the port for the REST service on ai server
     */
    RestClient(String aiServerIpAddress, int aiServerPort) {
        this.url = "http://" + aiServerIpAddress + ":"
                + aiServerPort;
    }
    /**
     * Gets a client web resource builder.
     *
     * @param localUrl the URL to access AIaaS
     * @return web resource builder
     */
    private Invocation.Builder getClientBuilder(String localUrl) {
        log.info("URL: {}", localUrl);
        Client client = ClientBuilder.newClient();
        WebTarget wt = client.target(localUrl);
        return wt.request(UTF_8);
    }

    /**
     * Builds a REST client and fetches AI mapping data in JSON format.
     *
     * @return the JSON if REST GET succeeds, otherwise return null
     */
    private ObjectNode getRest(String endpoint) {
        Invocation.Builder builder = getClientBuilder(endpoint);
        Response response = builder.get();

        if (response.getStatus() != HTTP_OK) {
            log.info("REST GET request returned error code {}",
                    response.getStatus());
            return null;
        }

        String jsonString = builder.get(String.class);
        log.info("Fetched JSON string: {}", jsonString);

        JsonNode node;
        try {
            node = MAPPER.readTree(jsonString);
        } catch (IOException e) {
            log.error("Failed to read JSON string", e);
            return null;
        }

        return (ObjectNode) node;
    }

    /**
     * Builds a REST client and posts templates and prediction inputs in YAML format.
     * @param endpoint API endpoint in AIaaS
     * @param requestBody YAML templates OR JSON Prediction Inputs
     * @param requestBodyType Specifies the MediaType (Either YAML Or JSON)
     * @return Template Creation Status OR Predictions
     */
    private ObjectNode postRest(String endpoint, String requestBody, String requestBodyType) {
        Invocation.Builder builder = getClientBuilder(endpoint);
        Response response = builder.post(Entity.entity(requestBody, requestBodyType));

        if (response.getStatus() != HTTP_OK) {
            log.info("REST POST request returned error code {}",
                    response.getStatus());
            return null;
        }

        String jsonString = builder.get(String.class);
        log.info("Fetched JSON string: {}", jsonString);

        JsonNode node;
        try {
            node = MAPPER.readTree(jsonString);
        } catch (IOException e) {
            log.error("Failed to read JSON string", e);
            return null;
        }

        return (ObjectNode) node;
    }

    /**
     * Get list of template categories available in AIaaS.
     *
     * @return JSON list of template categories
     */
    @Override
    public ObjectNode getTemplateNames() {
        String endpoint = url + "/getTemplateCategories";
        return getRest(endpoint);
    }

    /**
     * Show the list of templates from the chosen category.
     *
     * @param categoryId Id of the template categories
     * @return List of templates available from the chosen category
     */
    @Override
    public ObjectNode showTemplates(Integer categoryId) {
        String endpoint = url + "/showTemplates/" + categoryId;
        return getRest(endpoint);
    }

    /**
     * Create a template out of any category.
     *
     * @param categoryId Id of the template categories
     * @param templateId Id of the template from the list in the chosen category
     * @param requestBody YAML template needs to be created
     * @return Template Creation Response
     */
    @Override
    public ObjectNode createTemplate(Integer categoryId, Integer templateId, String requestBody) {
        String endpoint = url + "/createTemplate/" + categoryId + templateId;
        return postRest(endpoint, requestBody, "application/yaml");
    }

    /**
     * @param templateId Id of the datasource template
     * @param requestBody JSON data to train and predict
     * @return Data Insertion Status
     */
    @Override
    public ObjectNode insertData(Integer templateId, String requestBody) {
        String endpoint = url + "/insertData/" + templateId;
        return postRest(endpoint, requestBody, "application/json");
    }

    /**
     * Get training status of the template.
     *
     * @param categoryId Id of the template categories
     * @param templateId Id of the template from the list in the chosen category
     * @return Training Status of the chosen template
     */
    @Override
    public ObjectNode getTrainingStatus(Integer categoryId, Integer templateId) {
        String endpoint = url + "/getTrainingStatus/" + categoryId + templateId;
        return getRest(endpoint);
    }

    /**
     * Get Predictions for the chosen template.
     *
     * @param categoryId Id of the template categories
     * @param templateId Id of the template from the list in the chosen category
     * @param requestBody Input values to the chosen template
     * @return JSON Predictions for the chosen prediction template
     */
    @Override
    public ObjectNode getPredictions(Integer categoryId, Integer templateId, String requestBody) {
        String endpoint = url + "/getPredictions/" + categoryId + templateId;
        return postRest(endpoint, requestBody, "application/json");
    }

}
