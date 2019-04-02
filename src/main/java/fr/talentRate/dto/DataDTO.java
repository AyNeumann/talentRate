package fr.talentRate.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Data Transfert object for a basic single graphic data.
 * @author Aymeric
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DataDTO {
    /** Size of a single data.*/
    protected static final int DEFAULT_DATA_DTO_SIZE = 128;

    /** Graph X axis item name.*/
    @NotNull
    private String name;

    /** Graph value of the item.*/
    @NotNull
    private Float value;

    /**
     * A Point of series.
     * @param theName legend name
     * @param theValue value for bucket
     */
    public DataDTO(final String theName, final Double theValue) {
        this.name = theName;
        this.value = new Float(theValue);
    }

    /**
     * String builder for DataDTO.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(DEFAULT_DATA_DTO_SIZE);
        builder.append("{'name':'").append(name).append(",'value':").append(value).append("},");
        return builder.toString();
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
     * @return the value
     */
    public Float getValue() {
        return value;
    }

    /**
     * @param newValue the value to set
     */
    public void setValue(final Float newValue) {
        this.value = newValue;
    }

}
