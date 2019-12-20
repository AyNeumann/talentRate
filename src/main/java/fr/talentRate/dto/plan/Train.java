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
import javax.validation.constraints.NotNull;

/**
 * Represent an available training.
 * @author djer13
 */
@Entity
@IdClass(TrainId.class)
public class Train {

    /** The Learning Path. */
    @Id
    private LearningPath learningPath;

    /** The Skill. */
    @Id
    private Skill skill;

    /** max point obtainable for the Skill in this LearningPath. */
    @NotNull
    private Integer achievableThreshold;

    /**
     * @return The learningPath
     */
    public LearningPath getLearningPath() {
        return learningPath;
    }

    /**
     * @param newLearningPath The learningPath to set
     */
    public void setLearningPath(final LearningPath newLearningPath) {
        this.learningPath = newLearningPath;
    }

    /**
     * @return The skill
     */
    public Skill getSkill() {
        return skill;
    }

    /**
     * @param newSkill The skill to set
     */
    public void setSkill(final Skill newSkill) {
        this.skill = newSkill;
    }

    /**
     * @return The achievableThreshold
     */
    public Integer getAchievableThreshold() {
        return achievableThreshold;
    }

    /**
     * @param newAchievableThreshold The achievableThreshold to set
     */
    public void setAchievableThreshold(final Integer newAchievableThreshold) {
        this.achievableThreshold = newAchievableThreshold;
    }

}
