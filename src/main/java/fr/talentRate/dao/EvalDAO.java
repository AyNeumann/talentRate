package fr.talentRate.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.HasAggregations;
import org.elasticsearch.search.aggregations.bucket.filter.Filter;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.sum.ParsedSum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.talentRate.dto.EvalDTO;
import fr.talentRate.dto.FilterDTO;
import fr.talentRate.dto.MultiStackedDataDTO;
import fr.talentRate.dto.RetrieveEvalDTO;

/**
 * DAO for eval.
 * @author Aymeric
 *
 */
@Service
public class EvalDAO extends ElasticDAO {
    /** logger.*/
    private static final Logger LOG = LogManager.getLogger();

    /** Jackson object mapper. */
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Save an eval in the database.
     * @param data eval's data
     * @return boolean if eval is created
     */
    public IndexResponse saveEval(final EvalDTO data) {
        LOG.debug("Data to save : " + data);

        String dataString = null;
        try {
            dataString = objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException jpe) {
            LOG.error("Cannot save eval : error while parsing Input data : ", jpe);
        }

        IndexResponse response = saveData(dataString);

        return response;
    }

    /**
     * Retrieve all evals.
     * @return all evals as String.
     */
    public List<RetrieveEvalDTO> retrieveEval() {
        return executeSearch(QueryBuilders.matchAllQuery());
    }

    /**
     * Retrieve evals with parameters.
     * @param filterDTO reference to FilterDTO.
     * @return all evals matching with query
     */
    public List<RetrieveEvalDTO> searchEval(final FilterDTO filterDTO) {
        QueryBuilder fullQuery = builtQuery(filterDTO);
        return executeSearch(fullQuery);
    }

    /**
     * Transform a custom DTO to a "Elastic" Query.
     * @param filterDTO filters params
     * @return Valid query to filter datasa
     */
    private QueryBuilder builtQuery(final FilterDTO filterDTO) {
        TermQueryBuilder termQuery = QueryBuilders.termQuery(filterDTO.getField() + ".keyword", filterDTO.getValue());
        QueryBuilder fullQuery = null;

        int dotPos = filterDTO.getField().lastIndexOf('.');
        if (dotPos == -1) {
            fullQuery = termQuery;
        } else {
            String prefix = filterDTO.getField().substring(0, dotPos);
            NestedQueryBuilder nestedBuilder = QueryBuilders.nestedQuery(prefix, termQuery, ScoreMode.None);

            fullQuery = nestedBuilder;
        }

        return fullQuery;
    }

    /**
     * Transform a custom DTO to a "Elastic" Query.
     * @param filterDTO filters params
     * @return Valid query to filter datasa
     */
    private QueryBuilder builFiltertQuery(final FilterDTO filterDTO) {
        BoolQueryBuilder termQuery = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery(filterDTO.getField() + ".keyword", filterDTO.getValue()));

        QueryBuilder fullQuery = null;

        int dotPos = filterDTO.getField().lastIndexOf('.');
        if (dotPos == -1) {
            fullQuery = termQuery;
        } else {
            String prefix = filterDTO.getField().substring(0, dotPos);
            NestedQueryBuilder nestedBuilder = QueryBuilders.nestedQuery(prefix, termQuery, ScoreMode.None);

            fullQuery = nestedBuilder;
        }

        return fullQuery;
    }

    /**
     * Retrieve eval with matching id.
     * @param id id of the queried eval.
     * @return found eval.
     */
    public RetrieveEvalDTO retrieveEvalById(final String id) {
        GetResponse getResponse = getData(id);

        String getResponseString = getResponse.getSourceAsString();

        RetrieveEvalDTO transformedData = null;
        try {
            transformedData = objectMapper.readValue(getResponseString, RetrieveEvalDTO.class);
        } catch (JsonParseException | JsonMappingException e) {
            LOG.error("response form ElasticSearch is invalid", e);
        } catch (IOException ioe) {
            LOG.error("Error while mapping response ", ioe);
        }

        return transformedData;
    }

    /**
     * Update eval with matching id.
     * @param id id of the eval to update.
     * @param eval new eval data.
     * @return TRUE when save successful.
     */
    public Boolean updateEval(final String id, final EvalDTO eval) {
        String docToUpdate = null;

        try {
            docToUpdate = objectMapper.writeValueAsString(eval);
        } catch (JsonProcessingException jpe) {
            LOG.error("response form ElasticSearch is invalid", jpe);
        }

        Boolean isUpdated = updateData(docToUpdate, id);

        return isUpdated;
    }

    /**
     * Retrieve queried Graph data WITHOUT filter options.
     * @param graphType type of required graph will define datas to return.
     * @return strucured datas
     */
    public List<MultiStackedDataDTO> retrieveGraphData(final String graphType) {
        return retrieveGraphData(graphType, QueryBuilders.matchAllQuery());
    }

    /**
     * Retrieve queried Graph data WITH filter options.
     * @param graphType Type of graph
     * @param filterDTO reference to filterDTO
     * @return structured datas
     */
    public List<MultiStackedDataDTO> retrieveGraphData(final String graphType, final FilterDTO filterDTO) {
        QueryBuilder query = builFiltertQuery(filterDTO);
        return retrieveGraphData(graphType, query);
    }

    /**
     * Retrieve queried Graph data WITH filter options.
     * @param graphType Type od graph
     * @param filterQuery Querry to filter Data
     * @return structured Datas
     */
    private List<MultiStackedDataDTO> retrieveGraphData(final String graphType, final QueryBuilder filterQuery) {
        List<MultiStackedDataDTO> graphResult = new ArrayList<MultiStackedDataDTO>();

        SumAggregationBuilder sumScore = AggregationBuilders.sum("total_score").field("score");
        SumAggregationBuilder sumObtainable = AggregationBuilders.sum("total_obtenable").field("obtainable");

        // SI "filterDTO" ajouter "filter"
        AggregationBuilder all;
        // Sinon créer un "vide"

        if ("progress_stacked_by_skill".equals(graphType)) {
            //AJOUTER le regroupement par TERM à l'agregation existantes
            all = AggregationBuilders.terms("byTerm").field("skill.keyword");
        } else {
            //THis FILTER is a BOX !!
            all = AggregationBuilders.filter("globs", QueryBuilders.matchAllQuery());
        }

        all.subAggregation(sumScore).subAggregation(sumObtainable);

        SearchResponse sr = searchData(filterQuery, all);

        Filter globalData = sr.getAggregations().get("globs");
        Terms byTermData = sr.getAggregations().get("byTerm");
        if (null != globalData) {
            //Global globRes = data.getAggregations().get("byTerm");
            transformAndAddToList(globalData, graphResult);
        } else if (null != byTermData) {
            for (Terms.Bucket bucket : byTermData.getBuckets()) {
                transformAndAddToList(bucket.getKeyAsString(), bucket, graphResult);
            }
        } else {
            LOG.error("Canno't extract data from unknown bucket type ('glob' and 'byTerm' suported)");
        }

        return graphResult;
    }

    /**
     * Execute a custom SEARCH query and transform result to DTOs.
     * @param query query to execute
     * @return a List a EvalDTO matching the query
     */
    private List<RetrieveEvalDTO> executeSearch(final QueryBuilder query) {
        SearchResponse searchResponse = searchData(query);

        List<RetrieveEvalDTO> evalData = new ArrayList<>();
        SearchHit[] data;
        do {
            data = searchResponse.getHits().getHits();
            transformAndAddToDtoList(data, evalData);

            searchResponse = scrollData(searchResponse.getScrollId());
        } while (searchResponse.getHits().getHits().length > 0);

        LOG.debug(evalData);
        return evalData;
    }

    /**
     * Transform each searchResult (of Eval) and add then to an existing list.
     * @param data the Bulk Data from ElasticSearch
     * @param existingData List to add the new transformed Datas
     */
    private void transformAndAddToDtoList(final SearchHit[] data, final List<RetrieveEvalDTO> existingData) {
        for (SearchHit evalHit : data) {
            String source = evalHit.getSourceAsString();
            try {
                EvalDTO transformedData = objectMapper.readValue(source, EvalDTO.class);

                RetrieveEvalDTO fullData = new RetrieveEvalDTO(transformedData);
                fullData.setId(evalHit.getId());
                fullData.setSchool(transformedData.getSchool());

                existingData.add(fullData);
            } catch (IOException ioe) {
                LOG.warn("Ignoring invalid Eval (source) with ID : " + evalHit.getId(), ioe);
            }
        }
    }

    /**
     * Extract Score Versus Obtainable form a SINGLE bucket.
     * @param agregsData the Bucket
     * @param graphResult existing list to add the "transformed" data
     */
    private void transformAndAddToList(final HasAggregations agregsData, final List<MultiStackedDataDTO> graphResult) {
        transformAndAddToList("Total", agregsData, graphResult);
    }

    /**
     * Extract Score Versus Obtainable form a SINGLE bucket.
     * @param bucketName the nam of the "block"
     * @param agregsData the Bucket
     * @param graphResult existing list to add the "transformed" data
     */
    private void transformAndAddToList(final String bucketName, final HasAggregations agregsData,
            final List<MultiStackedDataDTO> graphResult) {
        if (null != agregsData) {
            MultiStackedDataDTO graphData = new MultiStackedDataDTO();
            graphData.setName(bucketName);
            Double totalScore = 0d;

            ParsedSum totScore = agregsData.getAggregations().get("total_score");
            if (null != totScore) {
                totalScore = totScore.getValue();
                graphData.addPoint("total_score", totalScore);
            }

            ParsedSum totObtainable = agregsData.getAggregations().get("total_obtenable");
            if (null != totScore) {
                Double totalObtainable = totObtainable.getValue();
                graphData.addPoint("total_missed", totalObtainable - totalScore);
            }

            graphResult.add(graphData);
        }
    }

}
