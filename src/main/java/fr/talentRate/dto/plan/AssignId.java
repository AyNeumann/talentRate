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

import javax.persistence.ManyToOne;

/**
 * Assign ID.
 * @author djer13
 */
public class AssignId {

    /** The LearningPath. */
    @ManyToOne
    private LearningPath learningPath;

    /** The Skill. */
    @ManyToOne
    private Skill skill;

    /** The instructor. */
    @ManyToOne
    private Instructor instructor;

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

}
