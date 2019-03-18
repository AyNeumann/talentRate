package fr.talentRate.dao;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Service;

/**
 * Elasticsearch index DAO.
 * @author Aymeric
 *
 */
@Service
public class IndexDAO {

    /** logger.*/
    private static final Logger LOG = LogManager.getLogger();

    /** first port number where elasticsearch client is connected. */
    private static final int PORT = 9200;
    /**second port number where elasticsearch client is connected. */
    private static final int PORT2 = 9201;

    /** Rest High Level Client for elasticsearch. */
    private RestHighLevelClient client;

    /**
     * creating client for elasticsearch.
     */
    public IndexDAO() {
        client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", PORT, "http"), new HttpHost("localhost", PORT2, "http")));
    }

    /**
     * create the index if it's not already exist in the elasticsearch Database.
     * @param indexName name of the index to create.
     */
    public void createIfNotExists(final String indexName) {
        Boolean idxExists = checkExists(indexName);

        if (null != idxExists && !idxExists) {
            createIndex(indexName);
        }
    }

    /**
     * check if index already exist in database.
     * @param indexName name of the index to create.
     * @return return if Index already exist. Values: null, true, false
     */
    public Boolean checkExists(final String indexName) {
        GetIndexRequest requestCheckExist = new GetIndexRequest();

        requestCheckExist.indices(indexName);

        Boolean exists = null;
        try {
            exists = client.indices().exists(requestCheckExist, RequestOptions.DEFAULT);
        } catch (IOException e) {
            LOG.error("Cannot check if index " + indexName + " exists");
        }

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
            LOG.error("No index response, index has'nt been created", e1);
        }

        if (null != createIndexResponse && createIndexResponse.isAcknowledged()) {
            LOG.info("Index " + indexName + " created");
        } else {
            LOG.error("Index " + indexName + " CANNOT be created");
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
