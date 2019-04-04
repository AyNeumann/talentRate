package fr.talentRate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Cionfiguration class for elacticsearch client.
 * @author Aymeric
 *
 */
@Component
public class Configuration {
    /** Database url.*/
    @Value("${db-info.url}")
    private String dataBaseUrl;
    /** First port number where elasticsearch client is connected. */
    @Value("${db-info.port}")
    private int dataBasePort;
    /** First port number where elasticsearch client is connected. */
    @Value("${db-info.protocol}")
    private String protocol;
    /** Name of the elasticSearch index. */
    @Value("${db-info.index}")
    private String index;

    /**
     * @return the dataBaseUrl
     */
    public String getDataBaseUrl() {
        return dataBaseUrl;
    }

    /**
     * @param theDataBaseUrl the dataBaseUrl to set
     */
    public void setDataBaseUrl(final String theDataBaseUrl) {
        this.dataBaseUrl = theDataBaseUrl;
    }

    /**
     * @return the dataBasePort
     */
    public int getDataBasePort() {
        return dataBasePort;
    }

    /**
     * @param theDataBasePort the dataBasePort to set
     */
    public void setDataBasePort(final int theDataBasePort) {
        this.dataBasePort = theDataBasePort;
    }

    /**
     * @return the protocol
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * @param theProtocol the protocol to set
     */
    public void setProtocol(final String theProtocol) {
        this.protocol = theProtocol;
    }

    /**
     * @return the index
     */
    public String getIndex() {
        return index;
    }

    /**
     * @param theIndex the index to set
     */
    public void setIndex(final String theIndex) {
        this.index = theIndex;
    }

}
