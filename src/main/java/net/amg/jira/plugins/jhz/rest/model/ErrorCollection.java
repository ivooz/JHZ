/*
 * Copyright 2015 AMG.net - Politechnika Łódzka
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

package net.amg.jira.plugins.jhz.rest.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a collection of errors found in field configuration of the gadget.
 */
@XmlRootElement
public class ErrorCollection {

    /**
     * Generic error messages
     */
    @XmlElement
    private Collection<String> errorMessages = new ArrayList<String>();

    /**
     * Errors specific to a certain field.
     */
    @XmlElement
    private Collection<ValidationError> errors = new ArrayList<ValidationError>();

    public void addErrorMessage(String message) {
        errorMessages.add(message);
    }

    public void addValidationError(ValidationError validationError) {
        errors.add(validationError);
    }

    public boolean isEmpty() {
        return errorMessages.isEmpty() && errors.isEmpty();
    }
}

