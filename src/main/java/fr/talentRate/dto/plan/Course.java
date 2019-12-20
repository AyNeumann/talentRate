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
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * @author djer1
 *
 */
@Entity
public class Course {

    /** Course ID.*/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /** Course description. */
    @NotNull
    private String description;

    /** Course start Date and Time.*/
    @NotNull
    private Date startDate;

    /** Course end Date and Time.*/
    @NotNull
    private Date endDate;

    /** Instructor for this course.*/
    @ManyToOne
    @NotNull
    private Instructor instructor;
    /** Promotion following this course. */

    @ManyToOne
    @NotNull
    private Promotion promotion;

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(description, endDate, id, instructor, promotion, startDate);
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
        Course other = (Course) obj;
        return Objects.equals(description, other.description) && Objects.equals(endDate, other.endDate)
                && Objects.equals(id, other.id) && Objects.equals(instructor, other.instructor)
                && Objects.equals(promotion, other.promotion) && Objects.equals(startDate, other.startDate);
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
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param newDescription the description to set
     */
    public void setDescription(final String newDescription) {
        this.description = newDescription;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param newStartDate the startDate to set
     */
    public void setStartDate(final Date newStartDate) {
        this.startDate = newStartDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param newEndDate the endDate to set
     */
    public void setEndDate(final Date newEndDate) {
        this.endDate = newEndDate;
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
     * @return the promotion
     */
    public Promotion getPromotion() {
        return promotion;
    }

    /**
     * @param newPromotion the promotion to set
     */
    public void setPromotion(final Promotion newPromotion) {
        this.promotion = newPromotion;
    }

}
