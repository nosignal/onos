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

package org.onosproject.bgpmonitoring;


/**
 * Custom Exception for BMP IO.
 */
public class BmpParseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor to create a new exception.
     */
    public BmpParseException() {
        super();
    }

    /**
     * Constructor to create exception from message and cause.
     *
     * @param message  the detail of exception in string
     * @param cause underlying cause of the error
     */
    public BmpParseException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor to create exception from message.
     *
     * @param message the detail of exception in string
     */
    public BmpParseException(final String message) {
        super(message);
    }

    /**
     * Constructor to create exception from cause.
     *
     * @param cause underlying cause of the error
     */
    public BmpParseException(final Throwable cause) {
        super(cause);
    }

}
