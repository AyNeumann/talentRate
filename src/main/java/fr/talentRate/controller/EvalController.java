package fr.talentRate.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import fr.talentRate.dto.RetrieveEvalDTO;
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
     * @return id of the created document.
     */
    @PostMapping("/")
    public Map<String, String> createEval(@RequestBody @Valid final EvalDTO eval) {
        String response = evserv.create(eval);

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("ID:", response);
        return responseMap;
    }

    /**
     * Retrieve evals matching with parameters if there is no parameter retrieve all evals.
     * @param field name of the elasticsearch field.
     * @param data value of the field in elasticsearch.
     * @return evals matching with parameters as List of EvalDTO.
     */
    @GetMapping("/")
    public List<RetrieveEvalDTO> searchEval(@RequestParam(name = "field", required = false) final String field,
            @RequestParam(name = "data", required = false) final String data) {

        List<RetrieveEvalDTO> evals = null;
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
     * @return a RetrieveEvalDTO.
     */
    @GetMapping("/getbyid")
    public RetrieveEvalDTO retrieveById(@RequestParam(name = "id", required = false) final String id) {
        RetrieveEvalDTO eval = null;
        eval = evserv.retrieveEvalById(id);
        return eval;
    }

    /**
     * Update eval with matching id.
     * @param id id of the eval to update.
     * @param eval new eval data.
     * @return Updated status
     */
    @PostMapping("/{id}")
    public Map<String, Boolean> updateEval(@PathVariable("id") final String id,
            @RequestBody @Valid final EvalDTO eval) {
        Boolean isUpdated = evserv.updateEval(id, eval);
        Map<String, Boolean> responseMap = new HashMap<>();
        responseMap.put("sucess:", isUpdated);
        return responseMap;
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
