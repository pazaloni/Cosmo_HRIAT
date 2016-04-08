package helpers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import core.BodyArea;
/**
 *
 * Purpose: Helper for incident report form functionality.
 *
 * @author TEAM CIMP
 * @version 1.0
 */
public class IncidentReportFormHelper
{
    public static final String DATE_FORMAT = "dd-MMM-yyyy";

    private DatabaseHelper db;

    public IncidentReportFormHelper()
    {
        db = new DatabaseHelper();
    }

    public boolean saveIncidentInfo( String cosmoID, LocalDate dateOfIncident,
            String timeOfIncident, String locationOfIncident,
            String protectiveEquipment, String incidentDescription,
            String contributingFactors, String reportedTo,
            LocalDate dateReported, String timeReported,
            String verballyReported, LocalDate verbalReportDate,
            String verbalReportTime, LocalDate dateReportWritten,
            String timeReportWritten, String reportedWrittenBy,
            String[] injuredBodyAreas )
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
        if ( dateOfIncident != null )
        {
            dateOfIncidentString = formatDate(dateOfIncident);
        }

        updateValues[1][1] = dateOfIncidentString;
        updateValues[2][1] = timeOfIncident;
        updateValues[3][1] = locationOfIncident;
        updateValues[4][1] = protectiveEquipment;
        updateValues[5][1] = incidentDescription;
        updateValues[6][1] = contributingFactors;
        updateValues[7][1] = reportedTo;
        String dateReportedToString = "";

        if ( dateReported != null )
        {
            dateReportedToString = formatDate(dateReported);
        }

        updateValues[8][1] = dateReportedToString;
        updateValues[9][1] = timeReported;
        updateValues[10][1] = verballyReported;

        String verbalReportString = "";

        if ( verbalReportDate != null )
        {
            verbalReportString = formatDate(verbalReportDate);

        }
        updateValues[11][1] = verbalReportString;

        updateValues[12][1] = verbalReportTime;
        String dateReportWrittenString = "";

        if ( dateReportWritten != null )
        {
            dateReportWrittenString = formatDate(dateReportWritten);
        }
        updateValues[13][1] = dateReportWrittenString;
        updateValues[14][1] = timeReported;
        updateValues[15][1] = reportedWrittenBy;

        
        
        return false;
    }

    /**
     * 
     * Purpose: save the individual injured body areas in the database 
     * @param injuredAreas the areas that were injured   
     * @param incidentID the incident report for that has these injured body areas
     * @return true if successful, false otherwise
     */
    private boolean saveInjuredBodyAreas( BodyArea[] injuredAreas,
            int incidentID )
    {
        boolean result = false;
        
        DatabaseHelper db = new DatabaseHelper();
        db.connect();
        for ( int i = 0; i < injuredAreas.length; i++ )
        {
            String area[] = new String[2];

            area[0] = injuredAreas[i].getArea();
            area[1] = incidentID + "";

            result = db.insert(area, "InjuredBodyAreas");
        }
        db.disconnect();
        return result;
    }

    
    private boolean saveInjuryTypes()
    {
        boolean result = false;
        
        return result;
    }

    public String[] retrieveIncidentInfo( String incidentID )
    {

        return null;
    }

    /**
     * 
     * Purpose: Format date using the DATE_FORMAT (dd-MMM-yyyy) format provided
     * 
     * @param date the date to be formatted
     * @return the date in a String object
     */
    private String formatDate( LocalDate date )
    {
        return date.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }
}