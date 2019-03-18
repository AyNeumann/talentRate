package fr.talentRate.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.index.IndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.talentRate.dao.EvalDAO;
import fr.talentRate.dao.IndexDAO;
import fr.talentRate.dto.EvalDTO;
import fr.talentRate.dto.FilterDTO;

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
    private IndexDAO indao;

    /** Reference to evalDAO. */
    @Autowired
    private EvalDAO evdao;

    /**
     * Create a new document.
     * @param data ref to EvalDTO.
     * @return the created JSON
     */
    public Map<String, String> create(final EvalDTO data) {
        LOG.info("Creation du document XXXXX");

        IndexResponse response = null;
        try {
            indao.createIfNotExists("eval2");
            response = evdao.saveEval(data);

        } catch (Exception e) {
            LOG.error("Error while saving document", e);
        }
        //        } finally {
        //            evdao.closeClient();
        //            indao.closeClient();
        //        }

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("ID:", response.getId());
        return responseMap;
    }

    /**
     * Retrieve all evals.
     * @return all evals as List of EvalDTO.
     */
    public List<EvalDTO> retrieveEval() {
        List<EvalDTO> response = evdao.retrieveEval();
        return response;
    }

    /**
     * Retrieve evals matching with parameters.
     * @param field name of the elasticsearch field.
     * @param value value of the field in elasticsearch.
     * @return evals matching with parameters as List of EvalDTO.
     */
    public List<EvalDTO> searchEval(final String field, final String value) {
        FilterDTO fildto = new FilterDTO();

        fildto.setField(field);
        fildto.setValue(value);
        List<EvalDTO> response = evdao.searchEval(fildto);
        return response;
    }
}
