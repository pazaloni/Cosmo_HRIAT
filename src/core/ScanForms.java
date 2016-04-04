package core;

import helpers.DatabaseHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ScanForms
{

    private StringProperty dateSaved;
    private StringProperty description;
    private StringProperty fileName;

    public ScanForms(LocalDate dateSaved, String description, String fileName)
    {
        this.dateSaved = new SimpleStringProperty(dateSaved.toString());
        this.description = new SimpleStringProperty(description);
        this.fileName = new SimpleStringProperty(fileName);

    }

    /**
     * 
     * Purpose: return the date form was saved
     * 
     * @return
     */
    public StringProperty getDateSaved()
    {       
        return dateSaved;
    }

    /**
     * 
     * Purpose: return the description
     * 
     * @return
     */
    public StringProperty getDescription()
    {
        return description;
    }

    /**
     * 
     * Purpose: return the fileName
     * 
     * @return
     */
    public StringProperty getFileName()
    {
        return fileName;
    }

    /**
     * Method for display properly formatted date value.
     * @return a properly formatted date string
     */
    public StringProperty displayDateSaved()
    {
        
        DateFormat dToSFormat = new SimpleDateFormat("dd-MMM-yyyy");
        DateFormat sToDFormat = new SimpleDateFormat("yyyy-MM-dd");
        String unformatedString = dateSaved.get();
        Date unformatedDate;
        String formattedString; 
        try
        {
            unformatedDate = sToDFormat.parse(unformatedString);
            formattedString = dToSFormat.format(unformatedDate);
        }
        catch ( ParseException e )
        {
            formattedString = dateSaved.toString();
        }
        
        StringProperty formattedDateTime = new SimpleStringProperty(formattedString);
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
        String dateTime = this.dateSaved.getValue();
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
     * Purpose: Add a scanned form for a participant in the database
     * 
     * @param form
     *            note to add
     * @param cosmoID
     *            the participant that will be receiving the form
     * @return The result of the insertion in the database
     */
    public static String createImage( ScanForms form, String cosmoID )
    {
        String result = "";

        if ( form.dateSaved.get().isEmpty() || form.description.get().isEmpty()
                || form.fileName.get().isEmpty() )
        {
            result = "You have missing required fields";
        }
        else
        {
            DatabaseHelper db = new DatabaseHelper();
            db.connect();

            boolean success = false;

            String scanFormValues[] = new String[4];

            scanFormValues[0] = form.getFileName().get();
            scanFormValues[1] = cosmoID;
            scanFormValues[2] = form.getDateSaved().get();            
            scanFormValues[3] = form.getDescription().get();
            

            success = db.insert(scanFormValues, "ScannedForms");

            if ( !success )
            {
                result = "The insertion was unsuccessful";
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
     * Purpose: Remove a scanned form for a participant from the database
     * 
     * @param note
     *            the note to remove
     * @param cosmoID
     *            the participant that will have the note removed
     * @return a string containing the status of the deletion
     */
    public static String deleteImage( ScanForms form, String cosmoID )
    {
        String result = "";
        DatabaseHelper db = new DatabaseHelper();

        db.connect();
        boolean success = false;
        success = db.delete("ScannedForms", "fileName='"
                + form.getFileName().get() + "'" + "AND cosmoID='" + cosmoID
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