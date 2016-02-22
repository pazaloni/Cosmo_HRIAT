import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * 
 * Purpose: generate a list of all the relevant seizure information that will be
 * needed in the seizure form excluding the table data
 *
 * @author cst205 cst207
 * @version 1.0
 */
public class SeizureDescriptionFormHelper
{
    private DatabaseHelper db;

    /**
     * 
     * Constructor for the SeizureDescriptionFormHelper class.
     */
    public SeizureDescriptionFormHelper()
    {
        db = new DatabaseHelper();
    }

    /**
     * 
     * Purpose: query the data base for a specific participant and return the an
     * array with the relevant information or empty if the participant does not
     * have any seizures
     * 
     * @param cosmoId
     *            of the participant being passes in
     * @return
     */
    public String[] retieveSeizureInformation( String cosmoId )
    {
        String[] seizureStatusInfo = new String[8];

        String seizureType = null;
        String description = null;
        String frequency = null;
        String duration = null;
        String aftermath = null;
        String aftermathAssistance = null;
        String emergencyTreatment = null;
        Date lastUpdated = new Date();
        lastUpdated = null;
        String medicationName = null;
        String dosage = null;
        Date timesGiven = new Date();

        // query for seizure informtion
        ResultSet participantQuery = db
                .select("seizureType, description, frequency, duration, "
                        + "aftermath, aftermathAssistance, emergencyTreatment, "
                        + "seizuresLastUpdated", "Seizures", "cosmoID="
                        + cosmoId, "");

        try
        {
            while ( participantQuery.next() )
            {
                seizureType = participantQuery.getString(1);
                description = participantQuery.getString(2);
                frequency = participantQuery.getString(3);
                duration = participantQuery.getString(4);
                aftermath = participantQuery.getString(5);
                aftermathAssistance = participantQuery.getString(6);
                emergencyTreatment = participantQuery.getString(7);
                lastUpdated = participantQuery.getDate(8);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();

            System.out.println("Failed to query paritipant's seizure info");
        }
        // fill the string array with seizure information
        seizureStatusInfo[0] = seizureType;
        seizureStatusInfo[1] = description;
        seizureStatusInfo[2] = frequency;
        seizureStatusInfo[3] = duration;
        seizureStatusInfo[4] = aftermath;
        seizureStatusInfo[5] = aftermathAssistance;
        seizureStatusInfo[6] = emergencyTreatment;

        // making sure participant does not have an date if no seizure
        try
        {
            seizureStatusInfo[7] = lastUpdated.toString();
        }
        catch ( NullPointerException e )
        {
            seizureStatusInfo[7] = " ";
        }
        for ( int i = 0; i < seizureStatusInfo.length; i++ )
        {
            // any null fields filled in with empty strings
            if ( seizureStatusInfo[i] == null )
            {
                seizureStatusInfo[i] = " ";
            }
        }

        return seizureStatusInfo;
    }

    /**
     * Method for saving seizure information
     * @param seizureInformation: The values to be saved
     * @param cosmoID: The person you are saving this for.
     * @return Whether or not the insertion was successful
     */
    public String saveSeizureInformation( String[] seizureInformation,
            String cosmoID )
    {
        String result = "";
        DatabaseHelper db = new DatabaseHelper();
        db.connect();
        //Get the seizureID for the current participant
        ResultSet idResult = db.select("seizureID", "Seizures", "cosmoId = '"
                + cosmoID + "'", "");
        String seizureID = "";
        boolean successful = true;
        try
        {
            idResult.next();
            seizureID = idResult.getString(1);
        }
        catch ( SQLException e )
        {
            //If the participant does not have a seizureID, make one. 
            String[][] initializerValues = new String[1][2];
            initializerValues[0][0] = "cosmoID";
            initializerValues[0][1] = cosmoID;
            successful = db.insert(initializerValues, "Seizures");
            if ( !successful )
            {
                result = "Could not insert a seizure entry for this individual";
            }
            else
            {
                idResult = db.select("seizureID", "Seizures", "cosmoId = '"
                        + cosmoID + "'", "");
                seizureID = "";

                try
                {
                    idResult.next();
                    seizureID = idResult.getString(1);
                }
                catch ( SQLException r )
                {
                    e.printStackTrace();
                }
            }
        }
        if ( successful )
        {
            //Prepare to add all the information for the seizure
            String[][] values = new String[9][2];
            values[0][0] = "seizureID";
            values[1][0] = "seizureType";
            values[2][0] = "description";
            values[3][0] = "frequency";
            values[4][0] = "duration";
            values[5][0] = "aftermath";
            values[6][0] = "aftermathAssistance";
            values[7][0] = "emergencyTreatment";
            values[8][0] = "seizuresLastUpdated";
            values[0][1] = seizureID;
            for ( int i = 1; i < values.length; i++ )
            {
                values[i][1] = seizureInformation[i-1];
            }
            successful = db.update(values, "Seizures", seizureID);

            // if failed on update of the seizure
            if ( !successful )
            {
                result = "The insertion was not successful";
            }
        }
        return result;
    }
}
