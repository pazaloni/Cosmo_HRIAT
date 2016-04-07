package helpers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * Purpose: Helper for incident report form functionality.
 *
 * @author TEAM CIMP
 * @version 1.0
 */
public class IncidentReportFormHelper
{
    private DatabaseHelper db;
    
    public IncidentReportFormHelper()
    {
        db = new DatabaseHelper();
    }
    
    public boolean saveIncidentInfo(String cosmoID, LocalDate dateOfIncident, 
            String timeOfIncident, String LocationOfIncident, String protectiveEquipment,
            String contributingFactors, String reportedTo, LocalDate dateReported,
            String timeReported, String verballyReported, LocalDate verbalReportDate,
            String verbalReportTime, LocalDate dateReportWritten, String timeReportWritten,
            String reportedWrittenBy, )
    {
        db.connect();
        
        String updateValues[][] = new String[16][2];
        updateValues[0][0] = "participantID";
        updateValues[1][0] = "dateOfIncident";
        updateValues[2][0] = "timeOfIncident";
        updateValues[3][0] = "locationOfIncident";
        updateValues[4][0] = "protectiveEquipment";
        updateValues[5][0] = "incidentDescription";
        updateValues[6][0] = "contributingFactors";
        updateValues[7][0] = "reportedTo";
        updateValues[8][0] = "dateReported";
        updateValues[9][0] = "timeReported";
        updateValues[10][0] = "verballyReportedTo";
        updateValues[11][0] = "verbalReportDate";
        updateValues[12][0] = "verbalReportTime";
        updateValues[13][0] = "dateReportWritten";
        updateValues[14][0] = "timeReportWritten";
        updateValues[15][0] = "reportedWrittenBy";
        
        updateValues[0][1] = cosmoID;
        String dateOfIncidentString = "";
        Date 
        if( info[1] != null )
        {
            dateOfIncidentString = info[1].format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
        }
        
        updateValues[1][1] = info[1];
        updateValues[2][1] = timeOfIncident;
        updateValues[3][1] = LocationOfIncident;
        updateValues[4][1] = info[4];
        updateValues[5][1] = info[5];
        updateValues[6][1] = info[6];
        updateValues[7][1] = info[7];
        
        updateValues[8][1] = info[8];
        updateValues[9][1] = info[9];
        updateValues[10][1] =info[10];
        updateValues[11][1] =info[11];
        updateValues[12][1] =info[12];
        
        updateValues[13][1] =info[13];
        updateValues[14][1] =info[14];
        updateValues[15][1] =info[15];
        
        
        return false;
    }  
    
    public String[] retrieveIncidentInfo(String incidentID)
    {
        
        return null;
    }
}