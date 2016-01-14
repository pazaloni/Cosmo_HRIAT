import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * Purpose: Query the database with a given participant ID and
 * 
 * 
 * @author TEAM CIMP
 * @version 1.0
 */
public class PreviewPaneHelper
{
    public static final int PARTICIPANT_COLUMN_SIZE = 6;

    // Preview pane information
    private String participantId;
    private String firstName;
    private String lastName;
    private String physician;
    private String seizures;
    private String allergies;

    private DatabaseHelper db;

    public PreviewPaneHelper()
    {
        db = new DatabaseHelper();
    }

    /**
     * 
     * Purpose: Query the database for a participant with their allergies,
     * seizures, firstName, lastName, and physician information
     * 
     * @param participantID the participant to query
     * @return A string array representation of the prevew pane information.
     */
    public String[] queryParticipant( String participantID )
    {
        this.participantId = participantID;

        ResultSet participantInfo, physicianInfo;

        // Query for the participant information
        participantInfo = db.select("firstName, lastName, physicianID",
                "Participant", "cosmoID=" + participantID, "");
        String physicianID = "";

        // Get the seizure info for the participant
        retreiveSeizureInfo(participantID);
        
        //Get the allergies info for the participant 
        retreiveAllergieInfo(participantID);
        try
        {
            participantInfo.next();
            // retreive the first and last name
            firstName = participantInfo.getString(1);
            lastName = participantInfo.getString(2);

            // get the phyisican id for the particiapnt
            physicianID = participantInfo.getString(3);

            // close the result set, free up resources.
            participantInfo.close();

            // Result set used to get the physician first and last name
            physicianInfo = db.select("firstName, lastName", "Physician",
                    "physicianID=" + physicianID, "");

            physicianInfo.next();

            // Append the first and last names of the physician
            physician = physicianInfo.getString(1) + " "
                    + physicianInfo.getString(2);

            // close the result set, free up resources.
            physicianInfo.close();

        }
        catch ( SQLException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String[] participantToReturn = assembleParticipantInfo();

        return participantToReturn;
    }

    /**
     * 
     * Purpose: Assemble all the information for the participant once every
     * piece has been retreived from the database
     * 
     * @return A string array representation of the prevew pane information.
     */
    private String[] assembleParticipantInfo()
    {
        String[] participantToReturn = new String[PARTICIPANT_COLUMN_SIZE];

        participantToReturn[0] = participantId;
        participantToReturn[1] = firstName;
        participantToReturn[2] = lastName;
        participantToReturn[3] = physician;
        participantToReturn[4] = seizures;
        participantToReturn[5] = allergies;

        return participantToReturn;
    }

    /**
     * 
     * Purpose: retreive the allergie information about the participant
     * 
     * @param participantID the participant's id
     */
    private void retreiveAllergieInfo( String participantID )
    {
        // Query for the allergies for the participant
        ResultSet allergieInfo = db.select("allergicTo", "Allergies",
                "cosmoID=" + participantID, "");

        try
        {
            allergies = "";
            while ( allergieInfo.next() )
            {
                // If the result is the last one don't append the comma at the
                // end
                if ( allergieInfo.isLast() )
                {
                    allergies += allergieInfo.getString(1);
                }
                else
                {
                    allergies += allergieInfo.getString(1) + ", ";
                }
            }
            // close the result set, free up resources.
            allergieInfo.close();
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

    }

    /**
     * 
     * Purpose: retreive the sezure information about the participant
     * 
     * @param participantID: the participant's id
     */
    private void retreiveSeizureInfo( String participantID )
    {
        // Query for the seizure for the participant
        ResultSet seizureInfo = db.select("seizureType", "Seizures", "cosmoID="
                + participantID, "");
        try
        {
            seizures = "";
            while ( seizureInfo.next() )
            {

                // If the result is the last one don't append the comma at the
                // end
                if ( seizureInfo.isLast() )
                {
                    seizures += seizureInfo.getString(1);
                }
                else
                {
                    seizures += seizureInfo.getString(1) + ", ";
                }
            }
            // close the result set, free up resources.
            seizureInfo.close();
        }
        catch ( SQLException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
