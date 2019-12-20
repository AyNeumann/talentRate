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
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 * A test to check Student progression.
 * @author djer13
 */
@Entity
public class Test {

    /** Test Id.*/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /** test Name. */
    @NotNull
    private String name;

    /** Instructor who plan this Test.*/
    @ManyToOne
    @NotNull
    private Instructor creator;

    /** All skill checked by this test.*/
    @OneToMany
    private Set<Control> controls;

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(controls, creator, id, name);
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
        Test other = (Test) obj;
        return Objects.equals(controls, other.controls) && Objects.equals(creator, other.creator)
                && Objects.equals(id, other.id) && Objects.equals(name, other.name);
    }

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
     * @return the creator
     */
    public Instructor getCreator() {
        return creator;
    }

    /**
     * @param newCreator the creator to set
     */
    public void setCreator(final Instructor newCreator) {
        this.creator = newCreator;
    }

    /**
     * @return the controls
     */
    public Set<Control> getControls() {
        return controls;
    }

    /**
     * @param newControls the controls to set
     */
    public void setControls(final Set<Control> newControls) {
        this.controls = newControls;
    }

    /**
     * A Skill checked in this Test.
     * @param control the control point
     */
    public void addControl(final Control control) {
        if (null == this.controls) {
            this.controls = new HashSet<>();
        }
        this.controls.add(control);
        control.setTest(this);
    }

}
