package fr.talentRate.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Eval Data Transfert Object with an ID.
 * @author Aymeric
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RetrieveEvalDTO extends EvalDTO {
    /** Size of Retrieve Specific Eval char size. */
    private static final int DEFAUT_RETRIEVE_CHAR_SIZE = 64;

    /**Eval id.*/
    @NotNull
    private String evalId;

    /** Default RetrieveEvalDTO constructor. */
    public RetrieveEvalDTO() {
        super();
    }

    /**
     * Initialize a new retrieve Eval DTO from a standard Eval DTO.
     * @param sourceEvalDTO the Eval data without ID
     */
    public RetrieveEvalDTO(final EvalDTO sourceEvalDTO) {
        this.setSchool(sourceEvalDTO.getSchool());
        this.setModule(sourceEvalDTO.getModule());
        this.setPromotion(sourceEvalDTO.getPromotion());
        this.setCategory(sourceEvalDTO.getCategory());
        this.setSkill(sourceEvalDTO.getSkill());
        this.setHomework(sourceEvalDTO.getHomework());
        this.setStudent(sourceEvalDTO.getStudent());
        this.setScore(sourceEvalDTO.getScore());
        this.setObtainable(sourceEvalDTO.getObtainable());
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        String evalString = super.toString();
        @SuppressWarnings("PMD.InsufficientStringBufferDeclaration")
        StringBuilder builder = new StringBuilder(DEFAUT_RETRIEVE_CHAR_SIZE + evalString.length());
        builder.append("RetrieveEvalDTO [evalId=").append(evalId).append(", parentEvalData=").append(evalString)
                .append(']');
        return builder.toString();
    }

    /**
     * @return the id
     */
    public String getEvalId() {
        return evalId;
    }

    /**
     * @param newId the id to set
     */
    public void setId(final String newId) {
        this.evalId = newId;
    }
}
