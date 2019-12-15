package fr.talentRate.dto.plan;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

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
    private String name;
    /** Student first name.*/
    private String firstName;
    /** Student birth date.*/
    private Date birthdate;

    /** Class this student Belong to.*/
    @ManyToMany
    private Set<Promotion> promotions;

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
        this.promotions.add(newPromotion);
        newPromotion.addStudent(this);
    }

}
