package fr.talentRate.dto.plan;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

/**
 * Student data representation.
 * @author djer13
 */
@Entity
public class Student {

    /** Student ID.*/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**Student Name.*/
    @NotNull
    private String name;

    /** Student first name.*/
    @NotNull
    private String firstName;

    /** Student birth date.*/
    @NotNull
    private Date birthdate;

    /** Class this student Belong to.*/
    @ManyToMany
    private Set<Promotion> promotions;

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(birthdate, firstName, id, name, promotions);
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
        Student other = (Student) obj;
        return Objects.equals(birthdate, other.birthdate) && Objects.equals(firstName, other.firstName)
                && Objects.equals(id, other.id) && Objects.equals(name, other.name)
                && Objects.equals(promotions, other.promotions);
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
     * @return the birthdate
     */
    public Date getBirthdate() {
        return birthdate;
    }

    /**
     * @param newBirthdate the birthdate to set
     */
    public void setBirthdate(final Date newBirthdate) {
        this.birthdate = newBirthdate;
    }

    /**
     * @return the classes
     */
    public Set<Promotion> getPromotions() {
        return promotions;
    }

    /**
     * @param newPromotions the classes to set
     */
    public void setPromotions(final Set<Promotion> newPromotions) {
        this.promotions = newPromotions;
    }

    /**
     * Add a promotion to the Student.
     * @param newPromotion the new Class
     */
    public void addPromotion(final Promotion newPromotion) {
        if (null == this.promotions) {
            this.promotions = new HashSet<>();
        }
        this.promotions.add(newPromotion);
        newPromotion.addStudent(this);
    }

}
