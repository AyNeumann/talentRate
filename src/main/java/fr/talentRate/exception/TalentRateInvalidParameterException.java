/*
Copyright [2019] [Aymeric NEUMANN]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package fr.talentRate.exception;

import org.springframework.validation.BindingResult;

/**
 * Custom Exception when some client provided parameters are invalids.
 * @author Aymeric
 */
public class TalentRateInvalidParameterException extends RuntimeException {

    /** serialVersionUID. */
    private static final long serialVersionUID = 6482157982120865970L;

    /** Optional : the Spring Binding result when available.*/
    private BindingResult result;

    /**
     * Create exception from a Spring BindingResult.
     * @param bindingResult The spring Binding result
     */
    public TalentRateInvalidParameterException(final BindingResult bindingResult) {
        super();
        this.result = bindingResult;
    }

    /**
     * @return the result
     */
    public BindingResult getResult() {
        return result;
    }
}
