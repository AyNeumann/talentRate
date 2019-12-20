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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * Activity are part of day. They are realized as part of a course.
 * @author djer13
 */
@Entity
public class Activity {

    /** Activity ID.*/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /** Activity description. */
    private String description;

    /** Activity start Date and Time.*/
    @NotNull
    private Date startDate;

    /** Activity end Date and Time.*/
    @NotNull
    private Date endDate;

    /** Activity belong to this course.*/
    @ManyToOne
    private Course course;

    /** Kind of activity. */
    @ManyToOne
    private ActivityKind kind;

    /**
     * @return The id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param newId The id to set
     */
    public void setId(final Long newId) {
        this.id = newId;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param newDescription The description to set
     */
    public void setDescription(final String newDescription) {
        this.description = newDescription;
    }

    /**
     * @return The startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param newStartDate The startDate to set
     */
    public void setStartDate(final Date newStartDate) {
        this.startDate = newStartDate;
    }

    /**
     * @return The endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param newEndDate The endDate to set
     */
    public void setEndDate(final Date newEndDate) {
        this.endDate = newEndDate;
    }

    /**
     * @return The course
     */
    public Course getCourse() {
        return course;
    }

    /**
     * @param newCourse The course to set
     */
    public void setCourse(final Course newCourse) {
        this.course = newCourse;
    }

    /**
     * @return The kind
     */
    public ActivityKind getKind() {
        return kind;
    }

    /**
     * @param newKind The kind to set
     */
    public void setKind(final ActivityKind newKind) {
        this.kind = newKind;
    }

}
