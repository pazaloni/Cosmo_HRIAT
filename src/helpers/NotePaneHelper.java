package helpers;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import core.Note;

/**
 * Purpose: Provide the functionality when the user has to deal with notes and
 * when they set the notes as "resolved"
 * 
 *
 * @author CIMP
 * @version 1.0
 */
public class NotePaneHelper
{
    private DatabaseHelper db;
    private Note note;

    public NotePaneHelper()
    {
        db = new DatabaseHelper();

    }

    /**
     * 
     * Purpose: Query the database for the note based on the primary key
     * 
     * @param selectedPK the pimary key of the note
     * @return the Note queried from the database
     */
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

    /**
     * 
     * Purpose: Get the participant first and last name
     * 
     * @param cosmoID the participants comsoid
     * @return string containing the participants first and last name
     */
    public String getParticipantName( String cosmoID )
    {
        ResultSet rs = db.select("firstName, lastName", "Participant",
                "cosmoID=" + cosmoID, "");

        String participantName = null;
        try
        {
            while ( rs.next() )
            {
                participantName = rs.getString(1) + " " + rs.getString(2);
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        return participantName;
    }

    /**
     * 
     * Purpose: Get the first and last name of the staff
     * 
     * @param username: the username of the staff
     * @return String containing the staff first and last name
     */
    public String getStaffName( String username )
    {

        ResultSet rs = db.select("firstName, lastName", "Staff", "UserName="
                + "'" + username + "'", "");

        String staffName = null;
        try
        {
            while ( rs.next() )
            {
                staffName = rs.getString(1) + " " + rs.getString(2);
            }
        }
        catch ( SQLException e )
        {
            // Auto-generated catch block
            e.printStackTrace();
        }
        return staffName;
    }

    /**
     * 
     * Purpose: Get the id of the last note in the database
     * 
     * @return an int representing the last in the database
     */
    public int getLastNote()
    {
        int noteID = -1;
        ResultSet rs = db.select("Max(noteID)", "Notes", "", "");
        try
        {
            while ( rs.next() )
            {
                noteID = rs.getInt(1);
            }
        }
        catch ( SQLException e )
        {
            // Auto-generated catch block
            e.printStackTrace();
        }
        return noteID;
    }

    /**
     * 
     * Purpose: set a note as "resolved" in the database when they click the
     * checkbox that says resolved
     * 
     * @param noteID the note that will be resolved
     */
    public void setNoteAsResolved( Note note )
    {
        // creates a string array that will hold the values that will be used to
        // update the note
        String vals[][] = new String[2][2];
        vals[0][0] = "noteID";
        vals[0][1] = note.getNoteID();
        vals[1][0] = "resolved";
        vals[1][1] = "true";
        // updates the note in the database, using the array of values
        db.update(vals, "Notes", note.getNoteID());

    }
}
