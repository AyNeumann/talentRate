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
package fr.talentRate.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Aymeric
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class StudentDTO {

    /** Student Full name. */
    private String name;

    /**
     * Initialize student's values.
     * @param jsonData plain Data
     */
    public void fromJson(final String jsonData) {
        //TODO to refactore wehn Student becomem ore complexe.
        this.name = jsonData;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     * ?????
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("StudentDTO [name=").append(name).append(']');
        return builder.toString();
    }

    /**
     * Populate String for student name.
     * @return name of the student.
     */
    public String tocCustomString() {
        return "{" + name + "}";
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param newName the name to set
     */
    public void setName(final String newName) {
        this.name = newName;
    }

}
