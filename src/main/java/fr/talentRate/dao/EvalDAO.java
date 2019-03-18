package fr.talentRate.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.talentRate.dto.EvalDTO;
import fr.talentRate.dto.FilterDTO;

/**
 * DAO for eval.
 * @author Aymeric
 *
 */
@Service
public class EvalDAO {

    /** logger.*/
    private static final Logger LOG = LogManager.getLogger();
    /**
     * first port number where elasticsearch client is connected.
     */
    private static final int PORT = 9200;
    /**
     * second port number where elasticsearch client is connected.
     */
    private static final int PORT2 = 9201;

    /** Default scroll page size. */
    private static final Integer DEFAULT_PAGE_SIZE = 10;

    /**
     * Rest High Level Client for elasticsearch.
     */
    private RestHighLevelClient client;

    /**
     * Jackson object mapper.
     */
    private ObjectMapper objectMapper = new ObjectMapper();

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

        String indexName = "eval2";

        IndexRequest request = new IndexRequest(indexName, "doc");

        //Map dataMap = objectMapper.convertValue(data, Map.class);

        String dataMap = null;
        try {
            dataMap = objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        request.source(dataMap, XContentType.JSON);

        IndexResponse response = null;
        try {
            response = client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            LOG.error("cannot create document");
        }

        if (null != response && response.getResult() == DocWriteResponse.Result.CREATED) {
            LOG.info("Document created with ID : " + response.getId());
        } else {
            LOG.warn("Document NOT created ( =>" + response.getResult() + " )");
        }

        return response;

    }

    /**
     * Retrieve all evals.
     * @return all evals as String.
     */
    public List<EvalDTO> retrieveEval() {
        return executeSearch(QueryBuilders.matchAllQuery());
    }

    /**
     * Retrieve evals with parameters.
     * @param filDTO reference to FilterDTO.
     * @return all evals matching with query
     */
    public List<EvalDTO> searchEval(final FilterDTO filDTO) {
        int dotPos = filDTO.getField().lastIndexOf('.');
        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery(filDTO.getField(), filDTO.getValue());

        QueryBuilder fullQuery = null;

        if (dotPos == -1) {
            fullQuery = matchQuery;
        } else {
            String prefix = filDTO.getField().substring(0, dotPos);
            //String field = filDTO.getField().substring(dotPos);
            NestedQueryBuilder netedBuilder = QueryBuilders.nestedQuery(prefix, matchQuery, ScoreMode.None);

            fullQuery = netedBuilder;
        }

        return executeSearch(fullQuery);
    }

    /**
     * Exectue a custopn SEARCH query and transform result to DTOs.
     * @param query query to execute
     * @return a List a EvalDTO matching the query
     */
    private List<EvalDTO> executeSearch(final QueryBuilder query) {
        SearchRequest searchRequest = new SearchRequest("eval2");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(query);
        searchSourceBuilder.size(DEFAULT_PAGE_SIZE);
        searchRequest.scroll(TimeValue.timeValueMinutes(1L));
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = null;
        List<EvalDTO> evalData = new ArrayList<>();
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            SearchHit[] data;
            do {
                data = searchResponse.getHits().getHits();
                transformAndAddToDtoList(data, evalData);

                searchResponse = scrollData(searchResponse.getScrollId());
            } while (searchResponse.getHits().getHits().length > 0);

        } catch (IOException e) {
            LOG.error("Error while matching ALL evals", e);
        }

        return evalData;
    }

    /**
     * Retieve srolled Data from a position.
     * @param scrollId poistion (extracted form previous scolled response)
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
     * @param existingData Lisit to add the new transformed Datas
     */
    private void transformAndAddToDtoList(final SearchHit[] data, final List<EvalDTO> existingData) {
        for (SearchHit evalHit : data) {
            String source = evalHit.getSourceAsString();

            try {
                EvalDTO transformedData = objectMapper.readValue(source, EvalDTO.class);
                existingData.add(transformedData);
            } catch (IOException e) {
                LOG.warn("Ignoring invalid Eval (source)", e);
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
