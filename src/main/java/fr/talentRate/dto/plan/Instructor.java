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

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * @author djer1
 *
 */
@Entity
public class Instructor {

    /** Instructor ID.*/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /** Instructor Name.*/
    @NotNull
    private String name;

    /** Instructor first name.*/
    @NotNull
    private String firstName;

    /** Instructor birth date.*/
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date birthdate;

    /** Skill this instructor can teach.*/
    @ManyToMany(mappedBy = "instructors")
    private Set<Skill> instructed;

    /** Test created by this instructor. */
    @OneToMany(mappedBy = "creator")
    private Set<Test> tests;

    /** Courses animated by this instructor.*/
    @OneToMany
    private Set<Course> courses;

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
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param newFirstName the firstName to set
     */
    public void setFirstName(final String newFirstName) {
        this.firstName = newFirstName;
    }

    /**
     * @return the birth date
     */
    public Date getBirthdate() {
        return birthdate;
    }

    /**
     * @param newBirthdate the birth date to set
     */
    public void setBirthdate(final Date newBirthdate) {
        this.birthdate = newBirthdate;
    }

    /**
     * @return the instructed
     */
    public Set<Skill> getInstructed() {
        return instructed;
    }

    /**
     * @param newInstructed the instructed to set
     */
    public void setInstructed(final Set<Skill> newInstructed) {
        this.instructed = newInstructed;
    }

    /**
     * Add a skill this instructor can teach.
     * @param skill the Skill
     */
    public void addInstructed(final Skill skill) {
        if (null == this.instructed) {
            this.instructed = new HashSet<>();
        }
        this.instructed.add(skill);
        skill.addInstructor(this);
    }

    /**
     * @return the tests
     */
    public Set<Test> getTests() {
        return tests;
    }

    /**
     * @param newTests the tests to set
     */
    public void setTests(final Set<Test> newTests) {
        this.tests = newTests;
    }

    /**
     * Add a Test created by this instructor.
     * @param test a New Test
     */
    public void addTest(final Test test) {
        if (null == this.tests) {
            this.tests = new HashSet<>();
        }
        this.tests.add(test);
        test.setCreator(this);
    }

    /**
     * @return the courses
     */
    public Set<Course> getCourses() {
        return courses;
    }

    /**
     * @param newCourses the courses to set
     */
    public void setCourses(final Set<Course> newCourses) {
        this.courses = newCourses;
    }

}
