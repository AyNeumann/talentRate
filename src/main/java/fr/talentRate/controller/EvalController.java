package fr.talentRate.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.delete.DeleteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.talentRate.dto.EvalDTO;
import fr.talentRate.dto.MultiStackedDataDTO;
import fr.talentRate.exception.TalentRateInvalidParameterException;
import fr.talentRate.service.EvalService;
import fr.talentRate.validators.EvalValidator;

/**
 * Manage request for the EvalService.
 * @author Aymeric
 *
 */
@RestController
@RequestMapping("/eval")
public class EvalController {

    /** logger.*/
    private static final Logger LOG = LogManager.getLogger();

    /** Reference to EvalService. */
    @Autowired
    private EvalService evserv;

    /**
     * Exception handler.
     * @param ex type of the handled exception.
     * @return the IllegalArgumentException message.
     */
    @ExceptionHandler({ IllegalArgumentException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleException(final Exception ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Post mapping to create an evals in the database.
     * @param eval evalDTO reference.
     * @param result ????
     * @return a StateDTO containing status of the createEval and the id of the eval as message.
     */
    @PostMapping("/")
    public EvalDTO createEval(@RequestBody @Valid final EvalDTO eval, final BindingResult result) {
        LOG.debug("creatingeEval: " + eval);
        EvalDTO createdEval;

        if (result.hasErrors()) {
            String message = "Attempt to create an Eval with at least one required filed empty";
            LOG.info(message);
            throw new TalentRateInvalidParameterException("Au moins un champs requis est vide", result);
        }

        EvalValidator evalValidator = new EvalValidator();
        evalValidator.validate(eval, result);
        if (result.hasErrors()) {
            LOG.info("Attempt to create an Eval with a score superior to obtainable");
            throw new TalentRateInvalidParameterException(result);
        } else {
            createdEval = evserv.create(eval);
        }

        return createdEval;
    }

    /**
     * Retrieve evals matching with parameters if there is no parameter retrieve all evals.
     * @param field name of the elasticsearch field.
     * @param data value of the field in elasticsearch.
     * @return evals matching with parameters as List of EvalDTO.
     */
    @GetMapping("/")
    public List<EvalDTO> searchEval(@RequestParam(name = "field", required = false) final String field,
            @RequestParam(name = "data", required = false) final String data) {

        List<EvalDTO> evals = null;
        if (field == null && data == null) {
            evals = evserv.retrieveEval();
        } else if (field == null || data == null) {
            throw new IllegalArgumentException("Field and Data are compulsory if at least one them is declared.");
        } else {
            evals = evserv.searchEval(field, data);
        }

        return evals;
    }

    /**
     * Retrieve eval by id.
     * @param id id id of the queried eval.
     * @return a EvalDTO.
     */
    @GetMapping("/getbyid")
    public EvalDTO retrieveById(@RequestParam(name = "id", required = false) final String id) {
        EvalDTO eval = null;
        eval = evserv.retrieveEvalById(id);
        LOG.info("Eval retrivied by id: " + eval);
        return eval;
    }

    /**
     * Retrieve datas for graphs.
     * @param field name of the elasticsearch field to retrieve.
     * @param data value of the field in elasticsearch.
     * @param graphType type of required graph will define datas to return.
     * @return nb of eval
     */
    @GetMapping("/getgraphdata")
    public List<MultiStackedDataDTO> retrieveGraphData(
            @RequestParam(name = "field", required = false) final String field,
            @RequestParam(name = "data", required = false) final String data,
            @RequestParam(name = "graphType", required = true) final String graphType) {
        List<MultiStackedDataDTO> result = new ArrayList<>();
        //List<MultiStackedDataDTO> listResult = new ArrayList<>();
        if (field != null && data != null) {
            LOG.debug("retrieveGraphData called, field and data are NOT null");
            result = evserv.retrieveGraphData(field, data, graphType);
        } else if (field == null && data == null) {
            result = evserv.retrieveGraphData(graphType);
            LOG.debug("retrieveGraphData called, field and data are null. Graph Type: " + graphType);
        }

        //listResult.add(result);
        return result;
    }

    /**
     * Update eval with matching id.
     * @param eval new eval data.
     * @param result ???
     * @return Updated status
     */
    @PostMapping("/{id}")
    public EvalDTO updateEval(@RequestBody @Valid final EvalDTO eval, final BindingResult result) {
        EvalDTO updatedEval = new EvalDTO();
        if (result.hasErrors()) {
            LOG.info("Attempt to modify an Eval with at least one required filed empty");
            throw new TalentRateInvalidParameterException(result);
        }
        EvalValidator evalValidator = new EvalValidator();
        evalValidator.validate(eval, result);
        if (result.hasErrors()) {
            updatedEval.setIsDone(false);
            updatedEval.setMessage("Le Score ne peut être supérieur au Score Max");
            LOG.info("Attempt to update an Eval with a score superior to obtainable");
        } else {
            updatedEval = evserv.updateEval(eval);
        }

        return updatedEval;
    }

    /**
     * Delete eval with matching id.
     * @param id id id of the queried eval.
     * @return deleted eval.
     */
    @DeleteMapping("/{id}")
    public DeleteResponse deleteEval(@PathVariable final String id) {
        DeleteResponse response = evserv.deleteEval(id);
        return response;
    }

    /**
     * @return the EvalService
     */
    public EvalService getEvserv() {
        return evserv;
    }

    /**
     * @param newEvserv the EvalService to set.
     */
    public void setEvserv(final EvalService newEvserv) {
        this.evserv = newEvserv;
    }
}
