package helpers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import core.Witness;

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
            String[] injuredBodyAreas, String[] typesOfInjuries,
            Witness[] witnesses )
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

        // Insert data into Incident table
        int incidentId = getLastIncidentId();
        saveInjuredBodyAreas(typesOfInjuries, incidentId);
        saveInjuryTypes(typesOfInjuries, incidentId);
        saveIncidentWitnesses(witnesses, incidentId);

        return false;
    }

    /**
     * 
     * Purpose: like the name says, gets the last incident id
     * 
     * @return an integer representing the last integer ID
     */
    public int getLastIncidentId()
    {
        int result = -1;

        DatabaseHelper db = new DatabaseHelper();
        db.connect();
        ResultSet rs = db.select("MAX(incidentID)", "Incidents", "", "");
        try
        {
            rs.next();
            result = rs.getInt(1);
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        db.disconnect();

        return result;
    }

    /**
     * 
     * Purpose: save the individual injured body areas in the database
     * 
     * @param injuredAreas the areas that were injured
     * @param incidentID the incident report for that has these injured body
     *            areas
     * @return true if successful, false otherwise
     */
    private boolean saveInjuredBodyAreas( String[] injuredAreas, int incidentID )
    {
        boolean result = false;

        DatabaseHelper db = new DatabaseHelper();
        db.connect();
        for ( int i = 0; i < injuredAreas.length; i++ )
        {
            String area[] = new String[2];

            area[0] = injuredAreas[i];
            area[1] = incidentID + "";

            result = db.insert(area, "InjuredBodyAreas");
        }
        db.disconnect();
        return result;
    }

    /**
     * 
     * Purpose: Save the injury types for the current incident
     * 
     * @param injuries array of injures to save
     * @param incidentId the incident that we will save this information to
     * @return true if the injures were saved successfully, false otherwise
     */
    private boolean saveInjuryTypes( String[] injuries, int incidentId )
    {
        boolean result = false;

        boolean newInjuryResult = false;
        String newInjuryType = injuries[injuries.length - 1];
        if ( !injuryTypeExists(newInjuryType) )
        {

            newInjuryResult = saveNewInjuryType(newInjuryType);
        }
        else
        {
            // At this point, we didn't have to add a new injury
            newInjuryResult = true;

            boolean currentResult = true;

            // Adding all the injury types
            for ( int i = 0; i < injuries.length && currentResult; i++ )
            {
                int currentInjuryId = getInjuryId(injuries[i]);
                currentResult = addInjuryToIncident(incidentId, currentInjuryId);
            }
        }

        return result;
    }

    /**
     * 
     * Purpose: Save the incident witnesses in the database for a incident
     * 
     * @param witnesses the witnesses of the incident
     * @param incidentId the incident that we will save the witnesses for
     * @return true if the witnesses were saved successfully, false otherwise
     */
    private boolean saveIncidentWitnesses( Witness[] witnesses, int incidentId )
    {
        boolean result = false;

        for ( int i = 0; i < witnesses.length; i++ )
        {
            // the witness already exists in the database already, so we'll just
            // connect their id and the incidnet's id
            if ( witnessExists(witnesses[i]) )
            {
                int currentWittnessId = getWitnessId(witnesses[i]);
                addWittnessToIncident(incidentId, currentWittnessId);
            }
            else
            // The witness is new and we need to add them to the database first
            {
                if ( saveNewWitness(witnesses[i]) )
                {
                    int currentWittnessId = getWitnessId(witnesses[i]);
                    addWittnessToIncident(incidentId, currentWittnessId);
                }
            }
        }

        return result;
    }

    /**
     * 
     * Purpose: Add an injury to the incident
     * 
     * @param incidentId the incident id to add
     * @param injuryId the injury id to add
     * @return true if the injury was added, false otherwise
     */
    private boolean addInjuryToIncident( int incidentId, int injuryId )
    {
        boolean result = false;

        DatabaseHelper db = new DatabaseHelper();
        db.connect();

        String incidentInjuryValues[] = new String[2];
        incidentInjuryValues[0] = injuryId + "";
        incidentInjuryValues[1] = incidentId + "";

        result = db.insert(incidentInjuryValues, "IncidentInjuryTypes");

        db.disconnect();
        return result;
    }

    /**
     * 
     * Purpose: get the injury id form the database
     * 
     * @param injuryName the injury to search for
     * @return the injury id
     */
    private int getInjuryId( String injuryName )
    {
        int id = 0;

        DatabaseHelper db = new DatabaseHelper();
        db.connect();

        ResultSet rs = db.select("injuryID", "InjuryType", "injuryName = '"
                + injuryName + "'", "");
        try
        {
            rs.next();
            id = rs.getInt(1);
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        db.disconnect();

        return id;
    }

    /**
     * 
     * Purpose: Save the new injury type when the checkbox for the other injury
     * type is selected
     * 
     * @param injuryType the new injury
     * @return true if the injury was saved or not
     */
    private boolean saveNewInjuryType( String injuryType )
    {
        boolean result = false;

        DatabaseHelper db = new DatabaseHelper();
        db.connect();
        String[][] newRecord = new String[1][1];
        // new injury record
        newRecord[0][0] = "injuryName";
        newRecord[0][1] = injuryType;

        result = db.insert(newRecord, "InjuryType");

        db.disconnect();

        return result;
    }

    /**
     * 
     * Purpose: Check the database to see if the selected injury type exits
     * 
     * @param injuryType the injury type we're checking
     * @return true if it exists, false otherwise
     */
    private boolean injuryTypeExists( String injuryType )
    {
        DatabaseHelper db = new DatabaseHelper();
        db.connect();

        ResultSet rs = db.select("count(*)", "InjuryType", "injuryName = '"
                + injuryType + "'", "");

        int recordExits = 0;

        // check if the injury type exists.
        try
        {
            rs.next();
            recordExits = rs.getInt(1);
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        db.disconnect();
        return recordExits != 0;
    }

    /**
     * 
     * Purpose: Add a new witness to the database
     * 
     * @param witness the witness to add
     * @return true if new addition was successful, false otherwise
     */
    private boolean saveNewWitness( Witness witness )
    {
        boolean result = false;
        DatabaseHelper db = new DatabaseHelper();

        db.connect();
        String[][] newRecord = new String[1][1];

        // new witness record
        newRecord[0][0] = "witnessNames";
        newRecord[1][0] = "type";

        newRecord[0][1] = witness.getWitnessName();
        newRecord[1][1] = witness.getWitnessType();

        result = db.insert(newRecord, "Witness");

        db.disconnect();

        return result;
    }

    /**
     * 
     * Purpose: check the database to see if the witness exists
     * 
     * @param witness the witness to check
     * @return true if the witness exits, false otherwise
     */
    private boolean witnessExists( Witness witness )
    {
        DatabaseHelper db = new DatabaseHelper();
        db.connect();

        ResultSet rs = db.select("count(*)", "Witness", "witnessNames = '"
                + witness.getWitnessName() + "'", "");

        int recordExits = 0;

        // check if the witness already exists in the database
        try
        {
            rs.next();
            recordExits = rs.getInt(1);
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        db.disconnect();
        return recordExits != 0;
    }

    /**
     * 
     * Purpose: add a witness to an incident
     * 
     * @param incidentId the incident to add the witness to
     * @param witnessId the witness id in the database
     * @return true if addition was successful, false otherwise
     */
    private boolean addWittnessToIncident( int incidentId, int witnessId )
    {
        boolean result = false;
        DatabaseHelper db = new DatabaseHelper();
        db.connect();

        String[] injuryWitnessvalues = new String[2];

        injuryWitnessvalues[0] = incidentId + "";
        injuryWitnessvalues[1] = witnessId + "";

        result = db.insert(injuryWitnessvalues, "IncidentWitness");

        db.disconnect();
        return result;
    }

    /**
     * 
     * Purpose: get the witness id from the database
     * 
     * @param witness the witness to look for
     * @return and integer representing that witness' id
     */
    private int getWitnessId( Witness witness )
    {
        int witnessId = 0;

        DatabaseHelper db = new DatabaseHelper();
        db.connect();

        ResultSet rs = db.select("witnessID", "Witness", "witnessNames = '"
                + witness.getWitnessName() + "'", "");

        try
        {
            rs.next();
            witnessId = rs.getInt(1);
        }
        catch ( SQLException e )
        {

            e.printStackTrace();
        }
        return witnessId;

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