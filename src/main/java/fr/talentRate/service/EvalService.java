package fr.talentRate.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.talentRate.Configuration;
import fr.talentRate.dao.EvalDAO;
import fr.talentRate.dao.IndexDAO;
import fr.talentRate.dto.EvalDTO;
import fr.talentRate.dto.FilterDTO;
import fr.talentRate.dto.MultiStackedDataDTO;

/**
 * Eval service.
 * @author Aymeric
 *
 */
@Service
public class EvalService {
    /** logger.*/
    private static final Logger LOG = LogManager.getLogger();

    /** Reference to configuration. */
    @Autowired
    private Configuration config;

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
    public EvalDTO create(final EvalDTO data) {
        LOG.info("Creating Eval : " + data);

        IndexResponse response = null;
        try {
            indexDao.createIfNotExists(config.getIndex());
            response = evalDao.saveEval(data);
        } catch (RuntimeException e) {
            LOG.error("Error while saving document", e);
        }

        EvalDTO result = new EvalDTO();
        if (response.status().name().equals("CREATED")) {
            result = data;
            result.setEvalId(response.getId());
            result.setMessage("Données sauvegardées!");
            result.setIsDone(true);
        }
        // TODO gérer erreur si BDD HS
        return result;
    }

    /**
     * Retrieve all evals.
     * @return all evals as List of EvalDTO.
     */
    public List<EvalDTO> retrieveEval() {
        List<EvalDTO> response = evalDao.retrieveEval();
        return response;
    }

    /**
     * Retrieve evals matching with parameters.
     * @param field name of the elasticsearch field.
     * @param data value of the field in elasticsearch.
     * @return evals matching with parameters as List of EvalDTO.
     */
    public List<EvalDTO> searchEval(final String field, final String data) {
        LOG.info("Searching evals: " + field + "data: " + data);
        FilterDTO filterDto = new FilterDTO();

        filterDto.setField(field);
        filterDto.setValue(data);
        List<EvalDTO> response = evalDao.searchEval(filterDto);
        return response;
    }

    /**
     * Retrieve eval with matching id.
     * @param id id of the queried eval.
     * @return found eval.
     */
    public EvalDTO retrieveEvalById(final String id) {
        EvalDTO response = evalDao.retrieveEvalById(id);

        return response;
    }

    /**
     * Retrieve data for required graph, WITH a filter value.
     * @param field name of the elasticsearch field to retrieve.
     * @param data value of the field in elasticsearch.
     * @param graphType type of required graph will define datas to return.
     * @return the datas
     */
    public List<MultiStackedDataDTO> retrieveGraphData(final String field, final String data, final String graphType) {
        LOG.info("Building graph with datas field: " + field + "data: " + data + "graphType: " + graphType);
        FilterDTO filterDto = new FilterDTO();

        filterDto.setField(field);
        filterDto.setValue(data);
        filterDto.setGraphType(graphType);

        //TODO Pourquoi passer "grahType" en paramètre alors qu'il est DANS le DTO
        return evalDao.retrieveGraphData(graphType, filterDto);
    }

    /**
     * Retrieve data for required graph, WITHOUT a filter value.
     * @param graphType type of required graph will define datas to return.
     * @return nb of eval.
     */
    public List<MultiStackedDataDTO> retrieveGraphData(final String graphType) {
        LOG.info("Building graph without datas. graphType: " + graphType);
        List<MultiStackedDataDTO> result = evalDao.retrieveGraphData(graphType);
        return result;
    }

    /**
     * Update eval with matching id.
     * @param eval new eval data.
     * @return TRUE when eval is succefully saved
     */
    public EvalDTO updateEval(final EvalDTO eval) {
        Boolean isUpdated = false;
        Boolean isErrorSet = false;
        EvalDTO result = new EvalDTO();

        if (eval.getEvalId() == null || eval.getEvalId().contentEquals("")) {
            isErrorSet = true;
            result.setIsDone(false);
            result.setMessage("Tentative de mise à jour d'une évaluation ne contenant pas ou ayany un ID inexistant.");
            LOG.warn("Attempt to update an eval without id: " + eval.toString());
        } else {
            isUpdated = evalDao.updateEval(eval);
        }

        if (isUpdated) {
            result = eval;
            result.setIsDone(true);
            result.setMessage("Données sauvegardées!");
            LOG.info("Eval updated: " + result.getEvalId());
        } else {
            if (!isErrorSet) {
                result.setIsDone(false);
                result.setMessage("Une erreur s\' est produite lors de l\' envoie des données.");
                LOG.warn("Eval NOT updated: " + result.getEvalId());
            }
        }
        return result;
    }

    /**
     * Delete eval with matching id.
     * @param id id id of the queried eval.
     * @return deleted eval.
     */
    public DeleteResponse deleteEval(final String id) {
        DeleteResponse response = evalDao.deleteEvalById(id);
        return response;
    }
}
