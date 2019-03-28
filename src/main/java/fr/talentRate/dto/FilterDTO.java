package fr.talentRate.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Request filter Data Transfert Object.
 * @author Aymeric
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FilterDTO {

    /** Determinate which key of the elasticsearch index is targeted.*/
    @NotNull
    private String field;

    /** Determinate the value of the targeted index.*/
    @NotNull
    private String data;

    /**
     * @return the field
     */
    public String getField() {
        return field;
    }

    /**
     * @param newField the field to set
     */
    public void setField(final String newField) {
        this.field = newField;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return data;
    }

    /**
     * @param newValue the value to set
     */
    public void setValue(final String newValue) {
        this.data = newValue;
    }

}
