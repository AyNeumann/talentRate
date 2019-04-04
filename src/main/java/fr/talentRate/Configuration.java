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
    /** ElasticSearch user name. */
    @Value("${db-info.user}")
    private String user;
    /** Elastic search user password. */
    @Value("${db-info.password}")
    private String password;

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

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param theUser the user to set
     */
    public void setUser(final String theUser) {
        this.user = theUser;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param thePassword the password to set
     */
    public void setPassword(final String thePassword) {
        this.password = thePassword;
    }

}
