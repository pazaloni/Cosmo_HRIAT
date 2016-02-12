import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NotePaneHelper
{
    private DatabaseHelper db;
    private Note note;

    public NotePaneHelper()
    {
        db = new DatabaseHelper();

    }

    public Note queryNote( String selectedPK )
    {
        // ResultSet that will get back all of the notes that are not resolved
        ResultSet rs = db.select("*", "Notes", "noteID=" + selectedPK, "");

        // Integer that will store the note id
        int noteID;
        // Integer that will store the participant ID/CosmoID
        int participant;
        // Sting that will store the creators username
        String createdBy;
        // date that will store the timestamp of the notes creation
        Date submitted;
        // String that will hold the text of the note
        String description;
        // Boolean that will hold the viewed status
        Boolean viewed;
        // Boolean that will hold the resolved status
        Boolean resolved;

        try
        {
            // while there is still a result
            while ( rs.next() )
            {
                // set the note id from the results
                noteID = rs.getInt(1);
                // set the participant from the results
                participant = rs.getInt(2);
                // set the username from the results
                createdBy = rs.getString(3);
                // set the timestamp from the results
                submitted = rs.getDate(4);
                // set the note text from the results
                description = rs.getString(5);
                // set the viewed status from the results
                viewed = rs.getBoolean(6);
                // set the resolved status from the results
                resolved = rs.getBoolean(7);

                // create a new note using the previously gathered information
                note = new Note(noteID, participant, description, createdBy,
                        submitted, viewed, resolved);
            }
        }
        // if this fail, print the stack trace
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        

        return note;
    }

    private String getParticipantName(String cosmoID)
    {
        ResultSet rs = db.select("firstName, lastName", "Participant", "cosmoID=" + cosmoID, "");
        
        String participantName = null;
        try
        {
            participantName = rs.getString(1) + " " + rs.getString(2);
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }
        
        return participantName;
    }
}
