package core;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Incident
{

    private SimpleStringProperty reportID;
    private SimpleStringProperty cosmoID;
    private SimpleStringProperty dateOfIncident;
    private SimpleStringProperty timeOfIncident;
    private SimpleStringProperty incidentDescription;
    
    public Incident( String reportID, String cosmoID, String dateOfIncident, 
            String timeOfIncident, String incidentDescription)
    {
        this.reportID = new SimpleStringProperty(reportID);
        this.cosmoID = new SimpleStringProperty(cosmoID);
        this.dateOfIncident = new SimpleStringProperty(dateOfIncident);
        this.timeOfIncident = new SimpleStringProperty(timeOfIncident);
        this.incidentDescription = new SimpleStringProperty(incidentDescription);
    }

    public String getIncidentID()
    {
        return reportID.get();
    }
    
    public StringProperty getReportID()
    {
        return reportID;
    }
    
    public StringProperty getCosmoID()
    {
        return cosmoID;
    }
    
    public StringProperty getDateOfIncident()
    {
        return dateOfIncident;
    }
    
    public StringProperty getTimeOfIncident()
    {
        return timeOfIncident;
    }
    
    public StringProperty getIncidentDescription()
    {
        return incidentDescription;
    }

    public StringProperty incidentIDProperty()
    {
        return reportID;
    }

    public StringProperty cosmoIDProperty()
    {
        return cosmoID;
    }

    public StringProperty dateOfIncidentIDProperty()
    {
        return dateOfIncident;
    }

    public StringProperty timeOfIncidentIDProperty()
    {
        return timeOfIncident;
    }

    public StringProperty descriptionProperty()
    {
        return incidentDescription;
    }
}
