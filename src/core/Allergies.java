package core;

import helpers.DatabaseHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 
 * Purpose: represent an allergy object and also have helper methods to create,
 * delete, and update allergies
 * 
 * @author Team CIMP
 * @version 1.0
 *
 */
public class Allergies
{
    private StringProperty allergicTo;
    private StringProperty allergyType;
    private StringProperty description;

    public Allergies(String allergicTo, String allergyType, String description)
    {
        this.allergicTo = new SimpleStringProperty(allergicTo);
        this.allergyType = new SimpleStringProperty(allergyType);
        this.description = new SimpleStringProperty(description);
    }

    /**
     * 
     * Purpose: return the name of the persons allergies
     * 
     * @return
     */
    public StringProperty getAllergicTo()
    {
        return allergicTo;
    }

    /**
     * 
     * Purpose: return the dosage of the medication that the person is taking
     * 
     * @return
     */
    public StringProperty getAllergyType()
    {
        return allergyType;
    }

    /**
     * 
     * Purpose: return the times that the person is to receive the medication
     * 
     * @return
     */
    public StringProperty getDescription()
    {
        return description;
    }

    /**
     * 
     * Purpose: Add an allergy for the specified participant.
     * 
     * @param allergicTo: the thing the participant is allergic to
     * @param allergyType The allergy type
     * @param description The description of the allergy
     * @param cosmoID The participant that has this allergy
     * @return The result of the insertion in the database.
     */
    public static String createAllergy( String allergicTo, String allergyType,
            String description, String cosmoID )
    {
        String result = "";

        if ( allergicTo.isEmpty() || allergyType.isEmpty()
                || description.isEmpty() )
        {
            result = "You have missing required fields";
        }
        else
        {
            DatabaseHelper db = new DatabaseHelper();
            db.connect();
            ResultSet rs = db.select("count(*)", "Allergies", "cosmoID = "
                    + cosmoID + " AND allergicTo = '" + allergicTo + "'", "");

            int recordExists = 0;

            // check if there is already a physician with that name
            try
            {
                rs.next();
                recordExists = rs.getInt(1);
            }
            catch ( SQLException e )
            {
                e.printStackTrace();
            }

            if ( recordExists == 0 )
            {
                boolean success = false;

                String allergyValues[] = new String[4];

                allergyValues[0] = allergicTo;
                allergyValues[1] = cosmoID;
                allergyValues[2] = allergyType;
                allergyValues[3] = description;

                success = db.insert(allergyValues, "Allergies");

                if ( !success )
                {
                    result = "The insertion was not successful";
                }
                else
                {
                    result = "Success";
                }
            }
            else
            {
                result = "That person already has that allergy.";
            }
            db.disconnect();
        }
        return result;
    }

    /**
     * 
     * Purpose: Delete an allergy from the database
     * 
     * @param allergicTo the thing the participant is allergy to
     * @param allergyType the allergy type
     * @param description the description of the allergy
     * @param cosmoID the comsmoID of the participant that will have the allergy
     *            deleted
     * @return a string containing the status of the deletion
     */
    public static String deleteAllergy( Allergies allergy, String cosmoID )
    {
        String result = "";

        DatabaseHelper db = new DatabaseHelper();
        db.connect();
        boolean success = false;

        success = db.delete("Allergies",
                "allergicTo='" + allergy.allergicTo.get() + "'"
                        + "AND cosmoID='" + cosmoID + "'");

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

    /**
     * 
     * Purpose: update an allergy for a participant
     * 
     * @param newAllergy the changed allergy
     * @param oldAllergy the allergy that will be changed
     * @param cosmoID the comsmoID of the participant that will have the allergy
     *            updated
     * @return a string containing the status of the update
     */
    public static String updateAllergy( Allergies newAllergy,
            Allergies oldAllergy, String cosmoID )

    {
        String result = "";
        if ( newAllergy.allergicTo.get().isEmpty() )
        {

            result = "You have missing required fields";
        }
        else
        {
            DatabaseHelper db = new DatabaseHelper();
            db.connect();
            boolean success = false;
            String updateValues[][] = new String[3][2];
            updateValues[0][0] = "allergicTo";
            updateValues[1][0] = "allergyType";
            updateValues[2][0] = "description";

            updateValues[0][1] = newAllergy.getAllergicTo().get();
            updateValues[1][1] = newAllergy.getAllergyType().get();
            updateValues[2][1] = newAllergy.getDescription().get();

            success = db.update(updateValues, "Allergies", oldAllergy
                    .getAllergicTo().get() + "'AND cosmoID=" + "'" + cosmoID);
            if ( success )
            {
                result = "Update successfull";
            }
            else
            {
                result = "Update failed";
            }
            db.disconnect();
        }
        return result;
    }

}
