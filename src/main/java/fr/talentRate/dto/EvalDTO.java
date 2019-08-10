package fr.talentRate.dto;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Eval Data Transfert Object.
 * @author Aymeric
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Validated
public class EvalDTO {
    /** Default Evaluation size WITHOUT student size.*/
    protected static final int DEFAULT_EVAL_CHAR_SIZE = 256;

    /** State DTO composition.*/
    private StateDTO state;

    /**Eval id.*/
    private String evalId;

    /**Name of the school where the eval is done.*/
    @NotNull
    @NotBlank
    private String school;

    /**Name of the module which is evaluated.*/
    @NotNull
    @NotBlank
    private String module;

    /**Name of the promotion of the evaluated student.*/
    @NotNull
    @NotBlank
    private String promotion;

    /**Category of evaluated skill.*/
    @NotNull
    @NotBlank
    private String category;

    /**Skill name which is evaluated.*/
    @NotNull
    @NotBlank
    private String skill;

    /**Name of the homework.*/
    @NotNull
    @NotBlank
    private String homework;

    /** All student infos.*/
    @NotNull
    @Valid
    private StudentDTO student;

    /** Number of point obtained by the student.*/
    @NotNull
    private Integer score;

    /** Maximum number of obtainable points.*/
    @NotNull
    private Integer obtainable;

    /** Eval date.*/
    @NotNull
    private Date given;

    /** Default EvalDTO constructor. */
    public EvalDTO() {
        this.state = new StateDTO();
    }

    /**
     * Initialize a new retrieve Eval DTO from a standard Eval DTO.
     * @param sourceEvalDTO the Eval data without ID
     */
    public EvalDTO(final EvalDTO sourceEvalDTO) {
        this.setSchool(sourceEvalDTO.getSchool());
        this.setModule(sourceEvalDTO.getModule());
        this.setPromotion(sourceEvalDTO.getPromotion());
        this.setCategory(sourceEvalDTO.getCategory());
        this.setSkill(sourceEvalDTO.getSkill());
        this.setHomework(sourceEvalDTO.getHomework());
        this.setStudent(sourceEvalDTO.getStudent());
        this.setScore(sourceEvalDTO.getScore());
        this.setObtainable(sourceEvalDTO.getObtainable());
        this.setGiven(sourceEvalDTO.getGiven());
        this.state = new StateDTO();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        String studentStr = student.toString();
        @SuppressWarnings("PMD.InsufficientStringBufferDeclaration")
        StringBuilder builder = new StringBuilder(DEFAULT_EVAL_CHAR_SIZE + studentStr.length());
        builder.append("EvalDTO [school=").append(school).append(", module=").append(module).append(", promotion=")
                .append(promotion).append(", category=").append(category).append(", skill=").append(skill)
                .append(", homework=").append(homework).append(", student=").append(studentStr).append(", score=")
                .append(score).append(", obtainable=").append(obtainable).append(']');
        return builder.toString();
    }

    /**
     * @return the school
     */
    public String getSchool() {
        return school;
    }

    /**
     * @param newSchool the school to set
     */
    public void setSchool(final String newSchool) {
        this.school = newSchool;
    }

    /**
     * @return the module
     */
    public String getModule() {
        return module;
    }

    /**
     * @param newModule the module to set
     */
    public void setModule(final String newModule) {
        this.module = newModule;
    }

    /**
     * @return the promotion
     */
    public String getPromotion() {
        return promotion;
    }

    /**
     * @param newPromotion the promotion to set
     */
    public void setPromotion(final String newPromotion) {
        this.promotion = newPromotion;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param newCategory the category to set
     */
    public void setCategory(final String newCategory) {
        this.category = newCategory;
    }

    /**
     * @return the skill
     */
    public String getSkill() {
        return skill;
    }

    /**
     * @param newSkill the skill to set
     */
    public void setSkill(final String newSkill) {
        this.skill = newSkill;
    }

    /**
     * @return the homework
     */
    public String getHomework() {
        return homework;
    }

    /**
     * @param newHomework the homework to set
     */
    public void setHomework(final String newHomework) {
        this.homework = newHomework;
    }

    /**
     * @return the student
     */
    public StudentDTO getStudent() {
        return student;
    }

    /**
     * @param newStudent the student to set
     */
    public void setStudent(final StudentDTO newStudent) {
        this.student = newStudent;
    }

    /**
     * @return the score
     */
    public Integer getScore() {
        return score;
    }

    /**
     * @param newScore the score to set
     */
    public void setScore(final Integer newScore) {
        this.score = newScore;
    }

    /**
     * @return the obtainable
     */
    public Integer getObtainable() {
        return obtainable;
    }

    /**
     * @param newObtainable the obtainable to set
     */
    public void setObtainable(final Integer newObtainable) {
        this.obtainable = newObtainable;
    }

    /**
     * @return the evalId
     */
    public String getEvalId() {
        return evalId;
    }

    /**
     * @param newEvalId the obtainable to set
     */
    public void setEvalId(final String newEvalId) {
        this.evalId = newEvalId;
    }

    /**
     * This parameter is an OUTPUT parameter.
     * @return False if error occurs error (@see getMessage for more details)
     */
    public Boolean getIsDone() {
        return state.getIsDone();
    }

    /**
     * This parameter is an OUTPUT parameter, should be NULL when send to server.
     * @param newDone the asError to set
     */
    public void setIsDone(final Boolean newDone) {
        state.setIsDone(newDone);
    }

    /**
     * Contains details informations about the Eval operations.
     * @return the message
     */
    public String getMessage() {
        return state.getMessage();
    }

    /**
     * This parameter is an OUTPUT parameter, should be NULL when send to server.
     * @param newMessage the message to set
     */
    public void setMessage(final String newMessage) {
        state.setMessage(newMessage);
    }

    /**
     * @return the given
     */
    public Date getGiven() {
        return given;
    }

    /**
     * @param newGiven the given to set
     */
    public void setGiven(final Date newGiven) {
        this.given = newGiven;
    }

}
