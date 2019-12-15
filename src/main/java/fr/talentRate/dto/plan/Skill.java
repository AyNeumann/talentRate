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
package fr.talentRate.dto.plan;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author djer1
 *
 */
@Entity
public class Skill {

    /** the Skill Id.*/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    /** The Skill name.*/
    private String name;
    /** The Skill description.*/
    private String description;
    /** The skill **global** minimum threshold.*/
    private Integer minimumThreshold;
    /** The skill **global** intermediate threshold.*/
    private Integer intermediaryThreshold;
    /** The skill **global** maximum threshold.*/
    private Integer maximumThreshold;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param newId the id to set
     */
    public void setId(final Long newId) {
        this.id = newId;
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

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param newDescription the description to set
     */
    public void setDescription(final String newDescription) {
        this.description = newDescription;
    }

    /**
     * @return the minimumThreshold
     */
    public Integer getMinimumThreshold() {
        return minimumThreshold;
    }

    /**
     * @param newMinimumThreshold the minimumThreshold to set
     */
    public void setMinimumThreshold(final Integer newMinimumThreshold) {
        this.minimumThreshold = newMinimumThreshold;
    }

    /**
     * @return the intermediaryThreshold
     */
    public Integer getIntermediaryThreshold() {
        return intermediaryThreshold;
    }

    /**
     * @param newIntermediaryThreshold the intermediaryThreshold to set
     */
    public void setIntermediaryThreshold(final Integer newIntermediaryThreshold) {
        this.intermediaryThreshold = newIntermediaryThreshold;
    }

    /**
     * @return the maximumThreshold
     */
    public Integer getMaximumThreshold() {
        return maximumThreshold;
    }

    /**
     * @param newMaximumThreshold the maximumThreshold to set
     */
    public void setMaximumThreshold(final Integer newMaximumThreshold) {
        this.maximumThreshold = newMaximumThreshold;
    }
}
