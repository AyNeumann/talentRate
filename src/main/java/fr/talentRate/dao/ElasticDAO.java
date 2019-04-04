package fr.talentRate.dao;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.http.HttpHost;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
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
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import fr.talentRate.Configuration;

/**
 * Elastic Data Access Object mother class.
 * @author Aymeric
 *
 */
public class ElasticDAO {
    /** logger.*/
    private static final Logger LOG = LogManager.getLogger();
    /**Reference to Configuration.*/
    @Autowired
    private Configuration config;
    /** Rest High Level Client for elasticsearch. */
    private static RestHighLevelClient client;
    /** Name of the elasticSearch index. */
    private String elasticIndex;
    /** Default data Type in elasticSearch. */
    private static final String DATA_TYPE = "_doc";

    /** Default scroll page size. */
    private static final Integer DEFAULT_PAGE_SIZE = 10;

    /**
     * Initialize elsaticsearch client.
     */
    @PostConstruct
    public void init() {
        client = new RestHighLevelClient(RestClient
                .builder(new HttpHost(config.getDataBaseUrl(), config.getDataBasePort(), config.getProtocol())));
        elasticIndex = config.getIndex();
    }

    /**
     * Retrieve DATA form an Idenx with an Id.
     * @param id id of document to retrieve
     * @return the Response or NULL if error occurs
     */
    protected GetResponse getData(final String id) {
        GetRequest request = new GetRequest(elasticIndex, DATA_TYPE, id);
        GetResponse getResponse = null;
        try {
            getResponse = client.get(request, RequestOptions.DEFAULT);
        } catch (IOException ioe) {
            LOG.error("Error while querying Eval with id : " + id, ioe);
        }

        return getResponse;
    }

    /**
     * Search for documents using aggregations and filters.
     * @param agg The aggregation
     * @param filterQuery The filter
     * @return The response or NULL if errors occurs
     */
    protected SearchResponse searchData(final QueryBuilder filterQuery, final AggregationBuilder agg) {
        return searchDatInternal(filterQuery, agg);
    }

    /**
     * Search for documents using aggregations and filters.
     * @param filterQuery The filter
     * @return The response or NULL if errors occurs
     */
    protected SearchResponse searchData(final QueryBuilder filterQuery) {
        return searchDatInternal(filterQuery, null);
    }

    /**
     * Search Data with a (filter)query and an OPTIONNAL aggregation.
     * @param filterQuery the query to filter document
     * @param agg the OPTIONNAL aggregation
     * @return The Search Response or NULL if errors occurs
     */
    private SearchResponse searchDatInternal(final QueryBuilder filterQuery, final AggregationBuilder agg) {
        SearchResponse sr = null;

        SearchRequest searchRequest;

        if (null == agg) {
            searchRequest = createRequest(filterQuery);
        } else {
            searchRequest = createRequest(filterQuery, agg);
        }

        try {
            sr = client.search(searchRequest, RequestOptions.DEFAULT);
            LOG.debug("Elastic response : " + sr);
        } catch (IOException ioe) {
            LOG.error("Error while retrieving graph data", ioe);
        }

        return sr;
    }

    /**
     * Save a document.
     * @param data document's data
     * @return the Elastic Index response or NULL if errors occurs
     */
    protected IndexResponse saveData(final String data) {
        IndexResponse response = null;

        IndexRequest request = new IndexRequest(elasticIndex, DATA_TYPE);
        request.source(data, XContentType.JSON);

        try {
            response = client.index(request, RequestOptions.DEFAULT);
        } catch (IOException ioe) {
            LOG.error("cannot create document in ElasticSearch", ioe);
        }

        if (null != response && response.getResult() == DocWriteResponse.Result.CREATED) {
            LOG.info("Document created with ID : " + response.getId());
        } else {
            LOG.warn("Document NOT created ( =>" + response.getResult() + " )");
        }

        return response;
    }

    /**
     * Update a document.
     * @param docToUpdate document as string to update.
     * @param id of the document to update.
     * @return An elastic UpdateRequest or NULL if errors occurs.
     */
    protected Boolean updateData(final String docToUpdate, final String id) {
        Boolean isUpdated = Boolean.FALSE;
        UpdateRequest updateRequest = new UpdateRequest(elasticIndex, DATA_TYPE, id);

        updateRequest.doc(docToUpdate, XContentType.JSON);
        try {
            client.update(updateRequest, RequestOptions.DEFAULT);
            isUpdated = Boolean.TRUE;
        } catch (IOException ioe) {
            LOG.error("Error while mapping response ", ioe);
        }

        return isUpdated;
    }

    /**
     * check if index already exist in database.
     * @param indexName name of the index to create.
     * @return return if Index already exist. Values: null, true, false
     */
    public Boolean checkExists(final String indexName) {
        GetIndexRequest requestCheckExist = new GetIndexRequest();

        Boolean exists = null;
        try {
            exists = client.indices().exists(requestCheckExist, RequestOptions.DEFAULT);
        } catch (IOException e) {
            LOG.error("Cannot check if index " + indexName + " exists");
        }

        requestCheckExist.indices(indexName);

        return exists;

    }

    /**
     * create the index in the elasticsearch Database.
     * @param indexName name of the index to create.
     */
    public void createIndex(final String indexName) {

        CreateIndexRequest requestCreateIndex = new CreateIndexRequest(indexName);
        CreateIndexResponse createIndexResponse = null;
        try {
            createIndexResponse = client.indices().create(requestCreateIndex, RequestOptions.DEFAULT);
        } catch (IOException e1) {
            LOG.error("No index response, index has not been created", e1);
        }

        if (null != createIndexResponse && createIndexResponse.isAcknowledged()) {
            LOG.info("Index " + indexName + " created");
        } else {
            LOG.error("Index " + indexName + " CANNOT be created");
        }

    }

    /**
     * Build a aggregations Query for evals.
     * @param builder aggregations parameters.
     * @return a ready to send to aggregation request
     */
    private SearchRequest createRequest(final AggregationBuilder builder) {
        SearchRequest searchRequest = new SearchRequest("eval3");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.aggregation(builder);
        searchRequest.source(searchSourceBuilder);

        LOG.debug("(Agregation) Request built : " + searchSourceBuilder);

        return searchRequest;
    }

    /**
     * Build a aggregations Query for evals.
     * @param builder aggregations parameters.
     * @param query Query to Filter documents BEFORE agregations
     * @return a ready to send to aggregation request
     */
    private SearchRequest createRequest(final QueryBuilder query, final AggregationBuilder builder) {
        SearchRequest searchRequest = createRequest(builder);
        searchRequest.source().query(query);

        LOG.debug("(Aggregation + query) Request completed with query : " + searchRequest);

        return searchRequest;
    }

    /**
     * Build a search Query for evals.
     * @param query the query to retrieve data.
     * @return a ready to send to search request
     */
    private SearchRequest createRequest(final QueryBuilder query) {
        SearchRequest searchRequest = new SearchRequest("eval3");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(query);
        searchSourceBuilder.size(DEFAULT_PAGE_SIZE);
        searchRequest.scroll(TimeValue.timeValueMinutes(1L));
        searchRequest.source(searchSourceBuilder);

        LOG.debug("(Query) Request built : " + searchSourceBuilder);

        return searchRequest;
    }

    /**
     * Retrieve scrolled Data from a position.
     * @param scrollId position (extracted form previous scrolled response)
     * @return The data page or NULL if error occurs
     */
    protected SearchResponse scrollData(final String scrollId) {
        SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
        scrollRequest.scroll(TimeValue.timeValueMinutes(1L));
        SearchResponse searchScrollResponse = null;
        try {
            searchScrollResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
            LOG.debug("Number of hits : " + searchScrollResponse.getHits().getTotalHits() + " with scroll ID : "
                    + searchScrollResponse.getScrollId());
        } catch (IOException ioe) {
            LOG.error("Error while mapping response ", ioe);
        }

        return searchScrollResponse;
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

    /**
     * @return the client
     */
    protected static RestHighLevelClient getClient() {
        return client;
    }

    /**
     * @return the elasticIndex
     */
    protected String getElasticIndex() {
        return elasticIndex;
    }

}
