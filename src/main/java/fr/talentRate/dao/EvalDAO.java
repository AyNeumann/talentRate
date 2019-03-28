package fr.talentRate.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.talentRate.dto.EvalDTO;
import fr.talentRate.dto.FilterDTO;
import fr.talentRate.dto.RetrieveEvalDTO;

/**
 * DAO for eval.
 * @author Aymeric
 *
 */
@Service
public class EvalDAO {
    /** logger.*/
    private static final Logger LOG = LogManager.getLogger();
    /** First port number where elasticsearch client is connected. */
    private static final int PORT = 9200;
    /** Second port number where elasticsearch client is connected. */
    private static final int PORT2 = 9201;

    /** Default scroll page size. */
    private static final Integer DEFAULT_PAGE_SIZE = 10;

    /** Rest High Level Client for elasticsearch. */
    private RestHighLevelClient client;

    /** Jackson object mapper. */
    private ObjectMapper objectMapper = new ObjectMapper();

    /** Default index namefor evaluations. */
    public static final String INDEX_NAME = "eval2";
    /** Default data Type in elasticSearch. */
    private static final String DATA_TYPE = "doc";

    /**
     * creating client for elasticsearch.
     */
    public EvalDAO() {
        client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", PORT, "http"), new HttpHost("localhost", PORT2, "http")));
    }

    /**
     * Save an eval in the database.
     * @param data eval's data
     * @return boolean if eval is created
     */
    public IndexResponse saveEval(final EvalDTO data) {
        LOG.debug("Data to save : " + data);

        IndexRequest request = new IndexRequest(INDEX_NAME, DATA_TYPE);

        //Map dataMap = objectMapper.convertValue(data, Map.class);

        String dataMap = null;
        try {
            dataMap = objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException jpe) {
            LOG.error("Cannot save eval : error while parsing Input data : ", jpe);
        }

        request.source(dataMap, XContentType.JSON);

        IndexResponse response = null;
        try {
            response = client.index(request, RequestOptions.DEFAULT);
        } catch (IOException ioe) {
            LOG.error("cannot create document in ElasticSearch", ioe);
        }

        if (null != response && response.getResult() == DocWriteResponse.Result.CREATED) {
            LOG.info("Eval created with ID : " + response.getId());
        } else {
            LOG.warn("Eval NOT created ( =>" + response.getResult() + " )");
        }

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

        return executeSearch(fullQuery);
    }

    /**
     * Retrieve eval with matching id.
     * @param id id of the queried eval.
     * @return found eval.
     */
    public RetrieveEvalDTO retrieveEvalById(final String id) {
        GetRequest getRequest = new GetRequest(INDEX_NAME, DATA_TYPE, id);

        GetResponse getResponse = null;
        try {
            getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        } catch (IOException ioe) {
            LOG.error("Error while querying Eval with id : " + id, ioe);
        }

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
     * @return TRUE when save succefull.
     */
    public Boolean updateEval(final String id, final EvalDTO eval) {
        Boolean isUpdated = Boolean.FALSE;
        UpdateRequest updateRequest = new UpdateRequest(INDEX_NAME, DATA_TYPE, id);

        String updatedDoc = null;
        try {
            updatedDoc = objectMapper.writeValueAsString(eval);
        } catch (JsonProcessingException jpe) {
            LOG.error("response form ElasticSearch is invalid", jpe);
        }

        updateRequest.doc(updatedDoc, XContentType.JSON);
        try {
            client.update(updateRequest, RequestOptions.DEFAULT);
            isUpdated = Boolean.TRUE;
        } catch (IOException ioe) {
            LOG.error("Error while mapping response ", ioe);
        }

        return isUpdated;
    }

    /**
     * Execute a custom SEARCH query and transform result to DTOs.
     * @param query query to execute
     * @return a List a EvalDTO matching the query
     */
    private List<RetrieveEvalDTO> executeSearch(final QueryBuilder query) {
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(query);
        searchSourceBuilder.size(DEFAULT_PAGE_SIZE);
        searchRequest.scroll(TimeValue.timeValueMinutes(1L));
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = null;
        List<RetrieveEvalDTO> evalData = new ArrayList<>();
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            SearchHit[] data;
            do {
                data = searchResponse.getHits().getHits();
                transformAndAddToDtoList(data, evalData);

                searchResponse = scrollData(searchResponse.getScrollId());
            } while (searchResponse.getHits().getHits().length > 0);

        } catch (IOException e) {
            LOG.error("Error while matching evals with query " + query, e);
        }

        return evalData;
    }

    /**
     * Retrieve scrolled Data from a position.
     * @param scrollId position (extracted form previous scrolled response)
     * @return The data page
     * @throws IOException if IO errors
     */
    private SearchResponse scrollData(final String scrollId) throws IOException {
        SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
        scrollRequest.scroll(TimeValue.timeValueMinutes(1L));
        SearchResponse searchScrollResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);

        LOG.debug("Number of hits : " + searchScrollResponse.getHits().getTotalHits() + " with scroll ID : "
                + searchScrollResponse.getScrollId());

        return searchScrollResponse;

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
     * Close elasticsearch client.
     */
    public void closeClient() {
        try {
            client.close();
        } catch (IOException e) {
            LOG.error("Cannot close client", e);
        }
    }
}
