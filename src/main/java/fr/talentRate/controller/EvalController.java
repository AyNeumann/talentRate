package fr.talentRate.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.talentRate.dto.EvalDTO;
import fr.talentRate.service.EvalService;

/**
 * Manage request for the EvalService.
 * @author Aymeric
 *
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/eval")
public class EvalController {
    /** logger.*/
    private static final Logger LOG = LogManager.getLogger();

    /** Reference to EvalService. */
    @Autowired
    private EvalService evserv;

    /**
     * Put mapping to create an evals in the database.
     * @param eval evalDTO reference.
     * @return id of the created document.
     */
    @PostMapping("/")
    public Map<String, String> createEval(@RequestBody @Valid final EvalDTO eval) {
        Map<String, String> response = evserv.create(eval);
        return response;
    }

    /**
     * Retrieve all evals.
     * @return all evals
     */
    @GetMapping("/")
    public List<EvalDTO> retrieveEval() {
        List<EvalDTO> evals = evserv.retrieveEval();
        return evals;
    }

    /**
     * Retrieve evals matching with parameters.
     * @param field name of the elasticsearch field.
     * @param value value of the field in elasticsearch.
     * @return evals matching with parameters as List of EvalDTO.
     */
    @GetMapping("/retrieve")
    public List<EvalDTO> searchEval(@RequestParam("field") final String field,
            @RequestParam("value") final String value) {
        LOG.error("field = " + field);

        List<EvalDTO> evals = evserv.searchEval(field, value);
        return evals;
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
