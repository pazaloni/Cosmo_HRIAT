package core;
import helpers.DatabaseHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 
 * Purpose: Represent a progress note in the database, also have the ability
 * to add, update, delete progress notes
 * 
 * 
 * @author CIMP
 * @version 1.0
 */
public class ProgressNotes
{

    private StringProperty dateTime;
    private StringProperty name;
    private StringProperty num;

    public ProgressNotes(String dateTime, String name, String num)
    {
        this.dateTime = new SimpleStringProperty(dateTime);
        this.name = new SimpleStringProperty(name);
        this.num = new SimpleStringProperty(num);

    }

    /**
     * 
     * Purpose: return the date time
     * 
     * @return
     */
    public StringProperty getDateTime()
    {
        return dateTime;
    }

    /**
     * 
     * Purpose: return the name
     * 
     * @return
     */
    public StringProperty getName()
    {
        return name;
    }

    /**
     * 
     * Purpose: return the num
     * 
     * @return
     */
    public StringProperty getNum()
    {
        return name;
    }
    
    /**
     * 
     * Purpose: Add a progress note for a participant in the database
     * 
     * @param note note to add
     * @param cosmoID the participant that will be receiving the note
     * @return The result of the insertion in the database
     */
    public static String createProgressNote( ProgressNotes note,
            String cosmoID )
    {
        String result = "";

        if ( note.dateTime.get().isEmpty()
                || note.name.get().isEmpty() 
                || note.num.get().isEmpty() )
        {
            result = "You have missing required fields";
        }
        else
        {
            DatabaseHelper db = new DatabaseHelper();
            db.connect();

            boolean success = false;

            String progressNotesValues[] = new String[4];

            progressNotesValues[0] = note.getDateTime().get();
            progressNotesValues[1] = cosmoID;
            progressNotesValues[2] = note.getName().get();
            progressNotesValues[3] = note.getNum().get();

            success = db.insert(progressNotesValues, "ProgressNotes");

            if ( !success )
            {
                result = "The insertion was not successful";
            }
            else
            {
                result = "";
            }

            db.disconnect();
        }
        return result;
    }

    /**
     * 
     * Purpose: Update a progress note for a participant
     * 
     * @param newNote the note that will be new in the database
     * @param oldNote the note that will be changed
     * @param cosmoID the participant that will have the note changed
     * @return a string containing the status of the update
     */
    public static String updateProgressNote( ProgressNotes newNote,
            ProgressNotes oldNote, String cosmoID )
    {
        String result = "";

        if ( newNote.dateTime.get().isEmpty()
                || newNote.name.get().isEmpty() )
        {
            result = "You have missing required fields";
        }
        else
        {
            DatabaseHelper db = new DatabaseHelper();
            db.connect();

            boolean success = false;
            String updateValues[][] = new String[3][2];
            updateValues[0][0] = "dateTime";
            updateValues[1][0] = "participantName";
            updateValues[2][0] = "num";

            updateValues[0][1] = newNote.getDateTime().get();
            updateValues[1][1] = newNote.getName().get();
            updateValues[1][1] = newNote.getNum().get();

            success = db.update(updateValues, "ProgressNotes", oldNote
                    .getDateTime().get() + "'AND cosmoID=" + "'" + cosmoID);

            if ( !success )
            {
                result = "Update not successful";
            }
            else
            {
                result = "";
            }
            db.disconnect();
        }

        return result;
    }

    /**
     * 
     * Purpose: Remove a progress note for a participant from the database
     * 
     * @param note the note to remove
     * @param cosmoID the participant that will have the note removed
     * @return a string containing the status of the deletion
     */
    public static String deleteProgressNote( ProgressNotes note,
            String cosmoID )
    {
        String result = "";
        DatabaseHelper db = new DatabaseHelper();

        db.connect();
        boolean success = false;
        success = db.delete("ProgressNotes", "dateTime='"
                + note.getDateTime().get() + "'" + "AND cosmoID='"
                + cosmoID + "'");
        if ( success )
        {
            result = "Deleted successfully";
        }
        else
        {
            result = "Deletion did not occur";
        }
        db.disconnect();
        return result;
    }

}