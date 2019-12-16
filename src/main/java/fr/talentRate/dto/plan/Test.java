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

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
    private String name;

    /** Instructor who plan this Test.*/
    @ManyToOne
    private Instructor creator;

    /** all skill checkd by this Test.*/
    @ManyToOne
    private List<Control> controls;

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
    public List<Control> getControls() {
        return controls;
    }

    /**
     * @param newControls the controls to set
     */
    public void setControls(final List<Control> newControls) {
        this.controls = newControls;
    }

    /**
     * A Skill checked in this Test.
     * @param control the control point
     */
    public void addControl(final Control control) {
        this.controls.add(control);
        control.setTest(this);
    }

}
