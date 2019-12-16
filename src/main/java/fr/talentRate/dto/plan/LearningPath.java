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
import javax.persistence.OneToMany;

/**
 * Regroup informations about à learning Path.
 * @author djer13
 */
@Entity
public class LearningPath {

    /** learning path ID.*/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    /**Learning path name. Ex "House of Code".*/
    private String name;

    /** Training of this LearningPath.*/
    @OneToMany(mappedBy = "learningPath")
    private Set<Train> trained;

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
     * @return the trained
     */
    public Set<Train> getTrained() {
        return trained;
    }

    /**
     * @param newTrained the trained to set
     */
    public void setTrained(final Set<Train> newTrained) {
        this.trained = newTrained;
    }

    /**
     * Add a trainable Skill (with an achievable threshold). You should use addSkill instead of this low level method.
     * @param train a trainable Skill;
     */
    public void addTrained(final Train train) {
        if (null == this.trained) {
            this.trained = new HashSet<>();
        }
        this.trained.add(train);
        train.setLearningPath(this);
    }

    /**
     * Add a skill to this LearningPath with an achievable threshold.
     * @param skill The skill to be learned
     * @param achievableThreshold the maximum level attainable
     */
    public void addSkill(final Skill skill, final Integer achievableThreshold) {
        Train train = new Train();
        train.setSkill(skill);
        train.setAchievableThreshold(achievableThreshold);

        addTrained(train);
    }

}
