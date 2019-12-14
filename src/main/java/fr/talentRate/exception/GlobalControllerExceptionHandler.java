package fr.talentRate.exception;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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

    /** Minimal size of to display errors messages. */
    private static final int MINIMAL_ERRORS_MESSAGE_SIZE = 128;

    /**
     * Allows to handle TalentRateInvalidParameterException.
     * @param trInvParamException exception handled.
     * @return an error message wrapped in an EvalDTO.
     */
    @ExceptionHandler(TalentRateInvalidParameterException.class)
    @ResponseBody
    public EvalDTO handleInvalidPaeameter(final TalentRateInvalidParameterException trInvParamException) {
        LOG.debug("Invalid parameter(s) detected with error : " + trInvParamException.getResult());
        BindingResult bindigResult = trInvParamException.getResult();
        EvalDTO errorEval = new EvalDTO();
        errorEval.setIsDone(false);

        errorEval.setMessage(trInvParamException.getMessage());

        StringBuilder sb = new StringBuilder(MINIMAL_ERRORS_MESSAGE_SIZE);

        sb.append(errorEval.getMessage()).append(' ').append(bindigResult.getErrorCount()).append(" erreur(s) : ");
        LOG.debug("BidningResult value : " + bindigResult);

        List<ObjectError> eroors = bindigResult.getAllErrors();

        for (ObjectError error : eroors) {
            sb.append('[').append(error.getCode()).append("] : ").append(error.getObjectName()).append(" => ")
                    .append(error.getDefaultMessage());
        }

        errorEval.setMessage(sb.toString());

        return errorEval;
    }

}
