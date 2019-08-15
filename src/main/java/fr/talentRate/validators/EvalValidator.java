package fr.talentRate.validators;

import org.springframework.validation.Errors;

import fr.talentRate.dto.EvalDTO;

/*https://stackoverflow.com/questions/12146298/spring-mvc-how-to-perform-validation*/

/**
 * Allows to check if the received eval is valid.
 * Check if obtainable is superior or equal to score.
 * @author Aymeric
 *
 */

// TODO enregistrer ce validator auprès de spring.
public class EvalValidator {

    /**
     * Will return true of false if the Validator is able to validate this Class.
     * @param clazz class to validate
     * @return a boolean
     */
    public boolean supports(final Class<EvalDTO> clazz) {
        return EvalDTO.class.equals(clazz);
    }

    /**
     * Score validation process.
     * @param target the object to validate
     * @param errors java error object
     */
    public void validate(final Object target, final Errors errors) {
        EvalDTO eval = (EvalDTO) target;
        if (eval.getScore() > eval.getObtainable()) {
            errors.rejectValue("score", "INV_EVAL_SCORE", "Le Score ne peut être supérieur au Score Max");
        }
    }

}
