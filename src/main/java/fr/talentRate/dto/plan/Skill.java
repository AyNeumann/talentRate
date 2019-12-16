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

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

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

    /** All instructor who can train student for this Skill.*/
    @ManyToMany
    private Set<Instructor> instructors;

    /** Possible training (link to the LearningPath, with an achievable threshold).*/
    @OneToMany(mappedBy = "skill")
    private Set<Train> teachedIn;

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

    /**
     * @return the instructors
     */
    public Set<Instructor> getInstructors() {
        return instructors;
    }

    /**
     * @param newInstructors the instructors to set
     */
    public void setInstructors(final Set<Instructor> newInstructors) {
        this.instructors = newInstructors;
    }

    /**
     * Add an instructor for this Skill.
     * @param instructor the instructor who an teach this skill
     */
    public void addInstructor(final Instructor instructor) {
        if (null == this.instructors) {
            this.instructors = new HashSet<>();
        }
        this.instructors.add(instructor);
        instructor.addInstructed(this);
    }

    /**
     * @return the teachedIn
     */
    public Set<Train> getTeachedIn() {
        return teachedIn;
    }

    /**
     * @param newTeachedIn the teachedIn to set
     */
    public void setTeachedIn(final Set<Train> newTeachedIn) {
        this.teachedIn = newTeachedIn;
    }

}
