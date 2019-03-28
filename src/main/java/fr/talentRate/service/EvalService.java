package fr.talentRate.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.index.IndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.talentRate.dao.EvalDAO;
import fr.talentRate.dao.IndexDAO;
import fr.talentRate.dto.EvalDTO;
import fr.talentRate.dto.FilterDTO;
import fr.talentRate.dto.RetrieveEvalDTO;

/**
 * Eval service.
 * @author Aymeric
 *
 */
@Service
public class EvalService {
    /** logger.*/
    private static final Logger LOG = LogManager.getLogger();

    /**Reference to indexDAO.*/
    @Autowired
    private IndexDAO indexDao;

    /** Reference to evalDAO. */
    @Autowired
    private EvalDAO evalDao;

    /**
     * Create a new Eval.
     * @param data ref to EvalDTO.
     * @return The id of created document
     */
    public String create(final EvalDTO data) {
        LOG.info("Creating Eval : " + data);

        IndexResponse response = null;
        try {
            indexDao.createIfNotExists(EvalDAO.INDEX_NAME);
            response = evalDao.saveEval(data);
        } catch (RuntimeException e) {
            LOG.error("Error while saving document", e);
        }

        return response.getId();
    }

    /**
     * Retrieve all evals.
     * @return all evals as List of EvalDTO.
     */
    public List<RetrieveEvalDTO> retrieveEval() {
        List<RetrieveEvalDTO> response = evalDao.retrieveEval();
        return response;
    }

    /**
     * Retrieve evals matching with parameters.
     * @param field name of the elasticsearch field.
     * @param data value of the field in elasticsearch.
     * @return evals matching with parameters as List of EvalDTO.
     */
    public List<RetrieveEvalDTO> searchEval(final String field, final String data) {
        FilterDTO filterDto = new FilterDTO();

        filterDto.setField(field);
        filterDto.setValue(data);
        List<RetrieveEvalDTO> response = evalDao.searchEval(filterDto);
        return response;
    }

    /**
     * Retrieve eval with matching id.
     * @param id id of the queried eval.
     * @return found eval.
     */
    public RetrieveEvalDTO retrieveEvalById(final String id) {
        RetrieveEvalDTO response = evalDao.retrieveEvalById(id);

        return response;
    }

    /**
     * Update eval with matching id.
     * @param id id of the eval to update.
     * @param eval new eval data.
     * @return TRUE when eval is succefully saved
     */
    public Boolean updateEval(final String id, final EvalDTO eval) {
        return evalDao.updateEval(id, eval);
    }
}
