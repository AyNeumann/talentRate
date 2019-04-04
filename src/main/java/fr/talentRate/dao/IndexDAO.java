package fr.talentRate.dao;

import org.springframework.stereotype.Service;

/**
 * Elasticsearch index DAO.
 * @author Aymeric
 *
 */
@Service
public class IndexDAO extends ElasticDAO {

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

}
