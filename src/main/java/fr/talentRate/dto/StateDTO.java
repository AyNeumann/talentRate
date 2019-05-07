package fr.talentRate.dto;

/**
 * Data Transfert Object for basic CRUD infos display.
 * Containing:
 * a boolean: if the action has been done with or without error.
 * an error message
 * @author Aymeric
 *
 */
public class StateDTO {

    /**Error boolean.*/
    private Boolean isDone;

    /** The Error message.*/
    private String message;

    /**
     * @return the asError
     */
    public Boolean getIsDone() {
        return isDone;
    }

    /**
     * @param newDone the asError to set
     */
    public void setIsDone(final Boolean newDone) {
        this.isDone = newDone;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param newMessage the message to set
     */
    public void setMessage(final String newMessage) {
        this.message = newMessage;
    }
}
