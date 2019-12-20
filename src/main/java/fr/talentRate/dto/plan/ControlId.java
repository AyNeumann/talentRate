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

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.ManyToOne;

/**
 * Control Id.
 * @author djer13
 */
public class ControlId implements Serializable {

    /**serialVersionUID. */
    private static final long serialVersionUID = 6953141533093695795L;

    /** A Skill. */
    @ManyToOne
    private Skill skill;

    /** A Test. */
    @ManyToOne
    private Test test;

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(skill, test);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ControlId other = (ControlId) obj;
        return Objects.equals(skill, other.skill) && Objects.equals(test, other.test);
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
     * @return the test
     */
    public Test getTest() {
        return test;
    }

    /**
     * @param newTest the test to set
     */
    public void setTest(final Test newTest) {
        this.test = newTest;
    }

}
