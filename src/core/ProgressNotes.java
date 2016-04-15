package core;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import helpers.DatabaseHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 
 * Purpose: Represent a progress note in the database, also have the ability to
 * add, update, delete progress notes
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

    public ProgressNotes(LocalDate dateTime, String name, String num)
    {
        this.dateTime = new SimpleStringProperty(dateTime.toString());
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
        return num;
    }

    /**
     * Method for display properly formatted date value.
     * 
     * @return a properly formatted date string
     */
    public StringProperty displayDateTime()
    {

        DateFormat dToSFormat = new SimpleDateFormat("dd-MMM-yyyy");
        DateFormat sToDFormat = new SimpleDateFormat("yyyy-MM-dd");
        String unformatedString = dateTime.get();
        Date unformatedDate;
        String formattedString;
        try
        {
            unformatedDate = sToDFormat.parse(unformatedString);
            formattedString = dToSFormat.format(unformatedDate);
        }
        catch ( ParseException e )
        {
            formattedString = dateTime.toString();
        }

        StringProperty formattedDateTime = new SimpleStringProperty(
                formattedString);
        return formattedDateTime;
    }

    /**
     * Purpose to return the date as a localDate
     * 
     * @return the date converted to a localdate
     */
    public LocalDate getLocalDateTime()
    {
        LocalDate localDateTime = null;
        String dateTime = this.dateTime.getValue();
        int yearEnd = dateTime.indexOf('-');
        int year = Integer.parseInt(dateTime.substring(0, yearEnd));
        dateTime = dateTime.substring(yearEnd + 1);
        int monthEnd = dateTime.indexOf('-');
        int month = Integer.parseInt(dateTime.substring(0, monthEnd));
        dateTime = dateTime.substring(monthEnd + 1);
        int day = Integer.parseInt(dateTime.substring(0, 2));
        localDateTime = LocalDate.of(year, month, day);
        return localDateTime;
    }

    /**
     * 
     * Purpose: Add a progress note for a participant in the database
     * 
     * @param note note to add
     * @param cosmoID the participant that will be receiving the note
     * @return The result of the insertion in the database
     */
    public static String createProgressNote( ProgressNotes note, String cosmoID )
    {
        String result = "";

        if ( note.dateTime.get().isEmpty() || note.name.get().isEmpty()
                || note.num.get().isEmpty() )
        {
            result = "You have missing required fields";
        }
        else if ( !note.num.get().matches("^\\d+$") )
        {
            result = "No. needs to be an integer";
        }
        else
        {
            DatabaseHelper db = new DatabaseHelper();
            db.connect();

            boolean success = false;

            String progressNotesValues[] = new String[4];

            progressNotesValues[0] = note.getNum().get();
            progressNotesValues[1] = cosmoID;
            progressNotesValues[2] = note.getDateTime().get();
            progressNotesValues[3] = note.getName().get();

            success = db.insert(progressNotesValues, "ProgressNotes");

            if ( !success )
            {
                result = "That No. is already taken";
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

        if ( newNote.dateTime.get().isEmpty() || newNote.name.get().isEmpty()
                || newNote.num.get().isEmpty() )
        {
            result = "You have missing required fields";
        }
        else if ( !newNote.num.get().matches("^\\d+$") )
        {
            result = "No. needs to be an integer";
        }
        else
        {
            DatabaseHelper db = new DatabaseHelper();
            db.connect();

            boolean success = false;
            String updateValues[][] = new String[3][2];
            updateValues[0][0] = "num";
            updateValues[1][0] = "participantName";
            updateValues[2][0] = "dateTime";

            updateValues[0][1] = newNote.getNum().get();
            updateValues[1][1] = newNote.getName().get();
            updateValues[2][1] = newNote.getDateTime().get();

            success = db.update(updateValues, "ProgressNotes", oldNote.getNum()
                    .get() + "'AND cosmoID=" + "'" + cosmoID);

            if ( !success )
            {
                result = "That No. is already taken";
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
    public static String deleteProgressNote( ProgressNotes note, String cosmoID )
    {
        String result = "";
        DatabaseHelper db = new DatabaseHelper();

        db.connect();
        boolean success = false;
        success = db.delete("ProgressNotes", "dateTime='"
                + note.getDateTime().get() + "'" + "AND cosmoID='" + cosmoID
                + "'");
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