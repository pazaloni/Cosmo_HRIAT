package core;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Purpose: Represents a incident report
 * 
 * @author Steven Palchinski
 * @version 1.0
 *
 */
public class Incident
{

    // Simple strings to hold column info
    private SimpleStringProperty reportID;
    private SimpleStringProperty cosmoID;
    private SimpleStringProperty dateOfIncident;
    private SimpleStringProperty timeOfIncident;
    private SimpleStringProperty incidentDescription;

    /**
     * Purpose: Constructor for the incident object
     * 
     * @param reportID The id for the incident
     * @param cosmoID The cosmo ID of the participant attached to the incident
     * @param dateOfIncident date of the incident
     * @param timeOfIncident time of the incident
     * @param incidentDescription Description of hte incident
     */
    public Incident(String reportID, String cosmoID, String dateOfIncident,
            String timeOfIncident, String incidentDescription)
    {
        this.reportID = new SimpleStringProperty(reportID);
        this.cosmoID = new SimpleStringProperty(cosmoID);
        this.dateOfIncident = new SimpleStringProperty(dateOfIncident);
        this.timeOfIncident = new SimpleStringProperty(timeOfIncident);
        this.incidentDescription = new SimpleStringProperty(incidentDescription);
    }

    /**
     * Purpose: get the incident ID
     * 
     * @return the reportID
     */
    public String getIncidentID()
    {
        return reportID.get();
    }

    /**
     * Purpose: get the report ID string property
     * 
     * @return report ID String property
     */
    public StringProperty getReportID()
    {
        return reportID;
    }

    /**
     * Purpose: get the cosmoID string property
     * 
     * @return cosmoID string property
     */
    public StringProperty getCosmoID()
    {
        return cosmoID;
    }

    /**
     * Purpose: Get the date of Incident String property
     * 
     * @return The dateOfIncident string property
     */
    public StringProperty getDateOfIncident()
    {
        return dateOfIncident;
    }

    /**
     * Purpose: Get the time of incident string property
     * 
     * @return the timeOfIncident string porperty
     */
    public StringProperty getTimeOfIncident()
    {
        return timeOfIncident;
    }

    /**
     * Purpose: Get the incident description string property
     * 
     * @return the incident description string property
     */
    public StringProperty getIncidentDescription()
    {
        return incidentDescription;
    }

}
