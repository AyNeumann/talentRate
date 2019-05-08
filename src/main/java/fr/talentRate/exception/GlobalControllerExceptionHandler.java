package fr.talentRate.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.talentRate.dto.EvalDTO;

/**
 * Handle all custom Exceptions.
 * @author Aymeric
 *
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    /** logger.*/
    private static final Logger LOG = LogManager.getLogger();

    /**
     * Allows to handle TalentRateInvalidParameterException.
     * @param invparam exception to handle.
     */
    //    @ExceptionHandler(TalentRateInvalidParameterException.class)
    //    public void handleInvalidPaeameter(final TalentRateInvalidParameterException invparam) {
    //          // Nothing to do
    //    }

    /**
     * Allows to handle TalentRateInvalidParameterException.
     * @param invparam exception to handle.
     * @return a really relevant error message.
     */
    //    @ExceptionHandler(TalentRateInvalidParameterException.class)
    //    @ResponseBody
    //    public String handleInvalidPaeameter(final TalentRateInvalidParameterException invparam) {
    //        LOG.debug("getResult(): " + invparam.getResult());
    //        return "A TOUT CASSE";
    //    }

    /**
     * Allows to handle TalentRateInvalidParameterException.
     * @param invparam exception to handle.
     * @return an error message wrapped in an EvalDTO.
     */
    @ExceptionHandler(TalentRateInvalidParameterException.class)
    @ResponseBody
    public EvalDTO handleInvalidPaeameter(final TalentRateInvalidParameterException invparam) {
        LOG.debug(invparam.getResult());
        BindingResult springErrorMessage = invparam.getResult();
        EvalDTO errorEval = new EvalDTO();
        errorEval.setIsDone(false);
        errorEval.setMessage(springErrorMessage.getErrorCount() + " champs requis vide, le 1er est: {"
                + springErrorMessage.getFieldError().getField() + "}");

        return errorEval;
    }

}
