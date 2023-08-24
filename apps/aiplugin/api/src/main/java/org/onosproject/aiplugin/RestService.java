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

package org.onosproject.aiplugin;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Manages REST Communication with AI-as-a-Service (AIaaS) APIs.
 * Interacts with datamonitor and analytics manager modules
 */
public interface RestService {
    /**
     * Get list of template categories available in AIaaS.
     *
     * @return JSON list of template categories
     */
    ObjectNode getTemplateNames();

    /**
     * Show the list of templates from the chosen category.
     *
     * @param categoryId Id of the template categories
     * @return List of templates available from the chosen category
     */
    ObjectNode showTemplates(Integer categoryId);

    /**
     * Create a template out of any category.
     *
     * @param categoryId  Id of the template categories
     * @param templateId  Id of the template from the list in the chosen category
     * @param requestBody YAML template needs to be created
     * @return Template Creation Response
     */
    ObjectNode createTemplate(Integer categoryId, Integer templateId, String requestBody);

    /**
     * @param templateId  Id of the datasource template
     * @param requestBody JSON data to train and predict
     * @return Data Insertion Status
     */
    ObjectNode insertData(Integer templateId, String requestBody);

    /**
     * Get training status of the template.
     *
     * @param categoryId Id of the template categories
     * @param templateId Id of the template from the list in the chosen category
     * @return Training Status of the chosen template
     */
    ObjectNode getTrainingStatus(Integer categoryId, Integer templateId);

    /**
     * Get Predictions for the chosen template.
     *
     * @param categoryId  Id of the template categories
     * @param templateId  Id of the template from the list in the chosen category
     * @param requestBody Input values to the chosen template
     * @return JSON Predictions for the chosen prediction template
     */
    ObjectNode getPredictions(Integer categoryId, Integer templateId, String requestBody);
}

