package helpers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import core.Participant;
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

    public IncidentReportFormHelper()
    {

    }

    public static ObservableList<Participant> getParticipants()
    {
        ObservableList<Participant> participants = FXCollections
                .observableArrayList();
        DatabaseHelper db = new DatabaseHelper();
        db.connect();

        ResultSet rs = db.select("cosmoID, firstName, lastName", "Participant",
                "participantStatus='Active'", "firstName");
        try
        {
            while ( rs.next() )
            {
                String cosmoId = rs.getString(1);
                String name = rs.getString(2) + " " + rs.getString(3);
                participants.add(new Participant(cosmoId, name));
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        db.disconnect();

        return participants;

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
            ArrayList<Witness> witnesses )
    {
        boolean result = false;

        DatabaseHelper db = new DatabaseHelper();

        db.connect();

        String newRecord[][] = new String[16][2];
        newRecord[0][0] = "cosmoID";
        newRecord[1][0] = "dateOfIncident";
        newRecord[2][0] = "timeOfIncident";
        newRecord[3][0] = "locationOfIncident";
        newRecord[4][0] = "protectiveEquipment";
        newRecord[5][0] = "incidentDescription";
        newRecord[6][0] = "contributingFactors";
        newRecord[7][0] = "reportedTo";
        newRecord[8][0] = "dateReported";
        newRecord[9][0] = "timeReported";
        newRecord[10][0] = "verballyReportedTo";
        newRecord[11][0] = "verbalReportDate";
        newRecord[12][0] = "verbalReportTime";
        newRecord[13][0] = "dateReportWritten";
        newRecord[14][0] = "timeReportWritten";
        newRecord[15][0] = "reportWrittenBy";

        newRecord[0][1] = cosmoID;

        String dateOfIncidentString = "";
        if ( dateOfIncident != null )
        {
            dateOfIncidentString = formatDate(dateOfIncident);
        }

        newRecord[1][1] = dateOfIncidentString;
        newRecord[2][1] = timeOfIncident;
        newRecord[3][1] = locationOfIncident;
        newRecord[4][1] = protectiveEquipment;
        newRecord[5][1] = incidentDescription;
        newRecord[6][1] = contributingFactors;
        newRecord[7][1] = reportedTo;
        String dateReportedToString = "";

        if ( dateReported != null )
        {
            dateReportedToString = formatDate(dateReported);
        }

        newRecord[8][1] = dateReportedToString;
        newRecord[9][1] = timeReported;
        newRecord[10][1] = verballyReported;

        String verbalReportString = "";

        if ( verbalReportDate != null )
        {
            verbalReportString = formatDate(verbalReportDate);

        }
        newRecord[11][1] = verbalReportString;

        newRecord[12][1] = verbalReportTime;
        String dateReportWrittenString = "";

        if ( dateReportWritten != null )
        {
            dateReportWrittenString = formatDate(dateReportWritten);
        }
        newRecord[13][1] = dateReportWrittenString;
        newRecord[14][1] = timeReported;
        newRecord[15][1] = reportedWrittenBy;

        boolean incidnetResult = db.insert(newRecord, "Incidents");

        db.disconnect();
        if ( incidnetResult )
        {
            // Insert data into Incident table
            int incidentId = getLastIncidentId();
            boolean injuredAreas = saveInjuredBodyAreas(injuredBodyAreas,
                    incidentId);
            boolean injuryTypes = saveInjuryTypes(typesOfInjuries, incidentId);
            boolean incidentWitness = saveIncidentWitnesses(witnesses,
                    incidentId);
            if ( injuredAreas && injuryTypes && incidentWitness )
            {
                result = true;
            }
        }

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
        boolean result = true;

        // Get the last injury and check if its a new one.
        String newInjuryName = injuries[injuries.length - 1];
        // Check if that last one exists in the database.
        boolean needNewInjury = injuryTypeExists(newInjuryName);

        // if false, we need a new injury to be added in the database
        if ( needNewInjury == false )
        {
            needNewInjury = saveNewInjuryType(newInjuryName);
            // if we were not able to add the new injury
            if ( !needNewInjury )
            {
                result = false;
            }
        }
        else if ( result == true )
        {
            boolean currentResult = true;

            // Adding all the injury types
            for ( int i = 0; i < injuries.length && currentResult; i++ )
            {
                int currentInjuryId = getInjuryId(injuries[i]);
                currentResult = addInjuryToIncident(incidentId, currentInjuryId);
                if ( currentResult == false )
                {
                    result = false;
                }
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
    private boolean saveIncidentWitnesses( ArrayList<Witness> witnesses,
            int incidentId )
    {
        boolean result = false;

        for ( Witness wit : witnesses )
        {
            // the witness already exists in the database already, so we'll just
            // connect their id and the incidnet's id
            if ( witnessAlreadyExists(wit) )
            {
                int currentWittnessId = getWitnessId(wit);
                addWitnessToIncident(incidentId, currentWittnessId);
            }
            else
            // The witness is new and we need to add them to the database first
            {
                if ( saveNewWitness(wit) )
                {
                    int currentWittnessId = getWitnessId(wit);
                    addWitnessToIncident(incidentId, currentWittnessId);
                }
            }
        }

        return result;
    }

    /**
     * 
     * Purpose: Save the new injury type when the checkbox for the other injury
     * type is selected
     * 
     * @param injuryType the new injury
     * @return true if the injury was saved false otherwise
     */
    private boolean saveNewInjuryType( String injuryType )
    {
        boolean result = false;

        DatabaseHelper db = new DatabaseHelper();
        db.connect();
        String[][] newRecord = new String[1][2];
        // new injury record
        newRecord[0][0] = "injuryName";
        newRecord[0][1] = injuryType;

        result = db.insert(newRecord, "InjuryType");

        db.disconnect();

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
     * Purpose: Check the database to see if the selected injury type exits
     * 
     * @param injuryName the injury type we're checking
     * @return true if it exists, false otherwise
     */
    private boolean injuryTypeExists( String injuryName )
    {
        DatabaseHelper db = new DatabaseHelper();
        db.connect();

        ResultSet rs = db.select("count(*)", "InjuryType", "injuryName = '"
                + injuryName + "'", "");

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
        String[][] newRecord = new String[2][2];

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
    private boolean witnessAlreadyExists( Witness witness )
    {
        DatabaseHelper db = new DatabaseHelper();
        db.connect();

        System.out.println(witness.getWitnessName());
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
    private boolean addWitnessToIncident( int incidentId, int witnessId )
    {
        boolean result = false;
        DatabaseHelper db = new DatabaseHelper();
        db.connect();

        String[] injuryWitnessvalues = new String[2];

        injuryWitnessvalues[0] = witnessId + "";
        injuryWitnessvalues[1] = incidentId + "";

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
     * Purpose: like the name says, gets the last incident id
     * 
     * @return an integer representing the last integer ID
     */
    private int getLastIncidentId()
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