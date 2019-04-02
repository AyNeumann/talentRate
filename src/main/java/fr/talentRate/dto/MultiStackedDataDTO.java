package fr.talentRate.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * Data Transfert object for graphics data.
 * @author Aymeric
 *
 */
public class MultiStackedDataDTO {
    /** Graph X axis item name.*/
    @NotNull
    private String name;

    /** */
    @NotNull
    private List<DataDTO> series = new ArrayList<DataDTO>();

    /**
     * add a Point.
     * @param theName name
     * @param theValue value
     */
    public void addPoint(final String theName, final Double theValue) {
        series.add(new DataDTO(theName, theValue));
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param newName the name to set.
     */
    public void setName(final String newName) {
        this.name = newName;
    }

    /**
     * @return the datas
     */
    public List<DataDTO> getSeries() {
        return series;
    }

    /**
     * @param newDatas the datas to set
     */
    public void setSeries(final List<DataDTO> newDatas) {
        this.series = newDatas;
    }

}
