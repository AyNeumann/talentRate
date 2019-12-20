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

import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 * @author djer1
 *
 */
@Entity
public class ActivityKind {

    /** Activity Id.*/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /** Activity Name. */
    @NotNull
    private String name;

    /** All course of this Kind.*/
    @OneToMany(mappedBy = "kind")
    private Set<Activity> activities;

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(activities, id, name);
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
        ActivityKind other = (ActivityKind) obj;
        return Objects.equals(activities, other.activities) && Objects.equals(id, other.id)
                && Objects.equals(name, other.name);
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
     * @return the courses
     */
    public Set<Activity> getActivities() {
        return activities;
    }

    /**
     * @param newActivity the activities to set
     */
    public void setActivities(final Set<Activity> newActivity) {
        this.activities = newActivity;
    }

}
