package core;

/**
 * 
 * Purpose: represent the incident injury types in the database for one incident  
 * 
 * @author CIMP
 * @version 1.0
 */
public class IncidentInjuryType
{

    private int injuryId;
    private int incidnetID;

    public IncidentInjuryType(int injuryId, int incidentID)
    {
        this.setInjuryId(injuryId);
        this.incidnetID = incidentID;
    }

    public int getIncidnetID()
    {
        return incidnetID;
    }
    
    public void setIncidnetID( int incidnetID )
    {
        this.incidnetID = incidnetID;
    }

    public int getInjuryId()
    {
        return injuryId;
    }

    public void setInjuryId( int injuryId )
    {
        this.injuryId = injuryId;
    }
}
