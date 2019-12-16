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
import javax.persistence.Id;
import javax.persistence.IdClass;

/**
 * A Skill is assign to an instructor for à particular Skill with available Point to give to each Student.
 * @author djer13
 */
@Entity
@IdClass(AssignId.class)
public class Assign {

    /** The LearningPath. */
    @Id
    private LearningPath learningPath;

    /** The Skill. */
    @Id
    private Skill skill;

    /** The instructor. */
    @Id
    private Instructor instructor;

    /** Point given to instructor to evaluate each student for this a skill.*/
    private Integer distributablePoints;

    /**
     * @return the learningPath
     */
    public LearningPath getLearningPath() {
        return learningPath;
    }

    /**
     * @param newLearningPath the learningPath to set
     */
    public void setLearningPath(final LearningPath newLearningPath) {
        this.learningPath = newLearningPath;
    }

    /**
     * @return the skill
     */
    public Skill getSkill() {
        return skill;
    }

    /**
     * @param newSkill the skill to set
     */
    public void setSkill(final Skill newSkill) {
        this.skill = newSkill;
    }

    /**
     * @return the instructor
     */
    public Instructor getInstructor() {
        return instructor;
    }

    /**
     * @param newInstructor the instructor to set
     */
    public void setInstructor(final Instructor newInstructor) {
        this.instructor = newInstructor;
    }

    /**
     * @return the distributablePoints
     */
    public Integer getDistributablePoints() {
        return distributablePoints;
    }

    /**
     * @param newDistributablePoints the distributablePoints to set
     */
    public void setDistributablePoints(final Integer newDistributablePoints) {
        this.distributablePoints = newDistributablePoints;
    }

}
