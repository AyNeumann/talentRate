package fr.talentRate.dto;

import java.util.Map;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Eval Data Transfert Object.
 * @author Aymeric
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EvalDTO {

    /**Name of the school where the eval is done.*/
    @NotNull
    private String school;

    /**Name of the module which is evaluated.*/
    @NotNull
    private String module;

    /**Name of the promotion of the evaluated student.*/
    @NotNull
    private String promotion;

    /**Category of evaluated skill.*/
    @NotNull
    private String category;

    /**Skill name which is evaluated.*/
    @NotNull
    private String skill;

    /**Name of the homework.*/
    @NotNull
    private String homework;

    /** All student infos.*/
    @NotNull
    private StudentDTO student;

    /** Number of point obtained by the student.*/
    @NotNull
    private String score;

    /** Maximum number of obtainable points.*/
    @NotNull
    private String obtainable;

    /**
     * Init an Eval's values.
     * @param datas Plain Data.
     */
    public void fromArray(final Map<String, String> datas) {
        this.school = datas.get("school");
        this.module = datas.get("module");
        this.score = datas.get("score");
        //TODO to be finished

        this.student = new StudentDTO();
        this.student.fromJson(datas.get("student"));
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("EvalDTO [school=").append(school).append(", module=").append(module).append(", promotion=")
                .append(promotion).append(", category=").append(category).append(", skill=").append(skill)
                .append(", homework=").append(homework).append(", student=").append(student).append(", score=")
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
    public String getScore() {
        return score;
    }

    /**
     * @param newScore the score to set
     */
    public void setScore(final String newScore) {
        this.score = newScore;
    }

    /**
     * @return the obtainable
     */
    public String getObtainable() {
        return obtainable;
    }

    /**
     * @param newObtainable the obtainable to set
     */
    public void setObtainable(final String newObtainable) {
        this.obtainable = newObtainable;
    }

}
