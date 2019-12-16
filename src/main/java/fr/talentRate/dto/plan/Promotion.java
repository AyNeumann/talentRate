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
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Allow to create group of students over time.
 * @author djer13
 */
@Entity
public class Promotion {

    /** Class Identifier.*/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    /** Class Name. Ex : "House of Code 2019".*/
    private String name;

    /** Students of this class.*/
    @ManyToMany(mappedBy = "promotions")
    private Set<Student> students;

    /** The Learning Path of this promotion.*/
    @ManyToOne
    private LearningPath enrolled;

    /** Courses available for this promotion.*/
    @OneToMany
    private List<Course> courses;

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
     * @return the students
     */
    public Set<Student> getStudents() {
        return students;
    }

    /**
     * @param newStudents the students to set
     */
    public void setStudents(final Set<Student> newStudents) {
        this.students = newStudents;
    }

    /**
     * Add a Student to this class.
     * @param newStudent The student to add
     */
    public void addStudent(final Student newStudent) {
        this.students.add(newStudent);
        newStudent.addPromotion(this);
    }

    /**
     * @return the learning path
     */
    public LearningPath getEnrolled() {
        return enrolled;
    }

    /**
     * @param newLearningPath the new Learning Path to set
     */
    public void setEnrolled(final LearningPath newLearningPath) {
        this.enrolled = newLearningPath;
    }

    /**
     * @return the courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * @param newCourses the courses to set
     */
    public void setCourses(final List<Course> newCourses) {
        this.courses = newCourses;
    }

}
