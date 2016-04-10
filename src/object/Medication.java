package object;
import helpers.DatabaseHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * Purpose: represents the medication that one participant can be getting
 * 
 *
 * @author CIMP
 * @version 1.0
 */
public class Medication
{
    private StringProperty medicationName;
    private StringProperty dosage;
    private StringProperty timesGiven;
    private StringProperty reason;

    public Medication(String medicationName, String dosage, String timesGiven,
            String reason)
    {

        this.medicationName = new SimpleStringProperty(medicationName);
        this.dosage = new SimpleStringProperty(dosage);
        this.timesGiven = new SimpleStringProperty(timesGiven);
        this.reason = new SimpleStringProperty(reason);
    }

    /**
     * 
     * Purpose: return the name of the medication that the person is taking
     * 
     * @return
     */
    public StringProperty getMedicationName()
    {
        return medicationName;
    }

    /**
     * 
     * Purpose: return the dosage of the medication that the person is taking
     * 
     * @return
     */
    public StringProperty getDosage()
    {
        return dosage;
    }

    /**
     * 
     * Purpose: return the times that the person is to receive the medication
     * 
     * @return
     */
    public StringProperty getTimesGiven()
    {
        return timesGiven;
    }

    /**
     * 
     * Purpose: return why the person is receiving medication
     * 
     * @return
     */
    public StringProperty getReason()
    {
        return reason;
    }

    /**
     * create a new medication and add it to database
     * 
     * @param medicationName
     *            : The name of the medication to be added
     * @param dosage
     *            : How much of the medication needs to be taken
     * @param timesGiven
     *            : What times the medication is taken
     * @param reason
     *            : Why the medication is taken.
     * @param cosmoID
     *            : Who the medication is being taken by.
     * @return: The result of the insertion to the database.
     */
    public static String createMedication( String medicationName,
            String dosage, String timesGiven, String reason, String cosmoID )
    {
        String result = "";
        // Check if any of the fields are empty
        if ( medicationName.isEmpty() )
        {
            result = "You must have a medication name";
        }
        else
        {
            DatabaseHelper db = new DatabaseHelper();
            db.connect();
            boolean successful = false;
            if ( !dosage.matches("\\d+") && dosage.length() != 0 )
            {
                result = "Dosage needs to be a number";
            }
            else
            {

                ResultSet medicationExists = db.select("count(*)",
                        "medication", "medicationName = '" + medicationName
                                + "' AND cosmoID = '" + cosmoID + "'", "");

                int recordExists = 0;

                // check if there is already a medication with that name for
                // that user
                try
                {
                    medicationExists.next();
                    recordExists = medicationExists.getInt(1);
                }
                catch ( SQLException e )
                {
                    e.printStackTrace();
                }
                // If no medication with that name exists, then insert the
                // medication
                if ( recordExists == 0 )
                {
                    String medicationValues[][] = new String[5][2];

                    medicationValues[0][0] = "medicationName";
                    medicationValues[1][0] = "cosmoID";
                    medicationValues[2][0] = "dosage";
                    medicationValues[3][0] = "timesGiven";
                    medicationValues[4][0] = "reason";

                    medicationValues[0][1] = medicationName;
                    medicationValues[1][1] = cosmoID;
                    medicationValues[2][1] = dosage;
                    medicationValues[3][1] = timesGiven;
                    medicationValues[4][1] = reason;

                    successful = db.insert(medicationValues, "medication");
                    if ( !successful )
                    {
                        result = "The insertion was not successful";
                    }
                    // If the insertion was successful, check if the medication
                    // has an associated seizure
                    else if ( reason.contains("Seizure") )
                    {
                        // Get the medicationID of the new medication
                        ResultSet medicationIDSet = db.select("medicationID",
                                "medication", "medicationName = '"
                                        + medicationName + "' AND cosmoID = '"
                                        + cosmoID + "'", "");

                        String medicationID = "";

                        try
                        {
                            medicationIDSet.next();
                            medicationID = medicationIDSet.getString(1);
                        }
                        catch ( SQLException e )
                        {
                            e.printStackTrace();
                        }

                        ResultSet seizureIDSet = db.select("seizureID",
                                "seizures", "cosmoID = '" + cosmoID + "'", "");

                        String seizureID = "";

                        // check if there is already a seizure for that
                        // participant
                        try
                        {
                            seizureIDSet.next();
                            seizureID = seizureIDSet.getString(1);
                        }
                        catch ( SQLException e )
                        {
                            // If the participant does not have a seizureID,
                            // make one.
                            String[][] initializerValues = new String[1][2];
                            initializerValues[0][0] = "cosmoID";
                            initializerValues[0][1] = cosmoID;
                            successful = db.insert(initializerValues,
                                    "Seizures");
                            if ( !successful )
                            {
                                result = "Could not insert a seizure entry for this individual";
                            }
                            else
                            { // Get the new seizureID for the participant
                                ResultSet idResult = db.select("seizureID",
                                        "Seizures", "cosmoId = '" + cosmoID
                                                + "'", "");
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
                        // Prepare to insert the join between seizure and
                        // medication
                        String[][] seizureMedicationValues = new String[2][2];

                        seizureMedicationValues[0][0] = "seizureID";
                        seizureMedicationValues[1][0] = "medicationID";
                        seizureMedicationValues[0][1] = seizureID;
                        seizureMedicationValues[1][1] = medicationID;

                        successful = db.insert(seizureMedicationValues,
                                "seizureMedication");
                        // If the seizure was not added successfully, remove the
                        // medication
                        if ( !successful )
                        {
                            result = "The insertion was not successful";
                            removeMedication(medicationName, cosmoID);
                        }
                    }
                }
                else
                {
                    result = "A medication with that name already exists";
                }

            }
            db.disconnect();
        }
        return result;
    }

    /**
     * Method for removing medication from the database
     * 
     * @param medicationName
     *            : The name of the medication to be removed.
     * @param cosmoID
     *            : The person the medication is being removed for.
     * @return: Whether or not the removal was successful
     */
    public static boolean removeMedication( String medicationName,
            String cosmoID )
    {
        boolean result = false;
        DatabaseHelper db = new DatabaseHelper();
        db.connect();

        // Get the ID for the medication
        ResultSet medicationIDSet = db.select("medicationID", "medication",
                "medicationName = '" + medicationName + "' AND cosmoID = '"
                        + cosmoID + "'", "");

        String medicationID = "";
        try
        {
            medicationIDSet.next();
            medicationID = medicationIDSet.getString(1);
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }
        // Check if the medication is attached to a seizure
        ResultSet seizureIDSet = db.select("seizureID", "seizures",
                "cosmoID = '" + cosmoID + "'", "");

        String seizureID = "";

        try
        {
            seizureIDSet.next();
            seizureID = seizureIDSet.getString(1);
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        // If the medication is attached to a seizure, remove that attachment
        if ( !seizureID.equals("") )
        {
            ResultSet seizureMedicationExists = db.select("count(*)",
                    "seizureMedication", "medicationID = '" + medicationID
                            + "' AND seizureID = '" + seizureID + "'", "");

            int recordExists = 0;
            try
            {
                seizureMedicationExists.next();
                recordExists = seizureMedicationExists.getInt(1);
            }
            catch ( SQLException e )
            {
                e.printStackTrace();
            }

            if ( recordExists != 0 )
            {
                db.delete("seizureMedication", "medicationID = '"
                        + medicationID + "' AND seizureID = '" + seizureID
                        + "'");
            }
        }
        // Remove the medication form the database.
        result = db.delete("medication", "medicationID = '" + medicationID
                + "'");
        return result;
    }

    /**
     * Method for modifying an already existing medication.
     * 
     * @param medicationName
     *            : The new medication name
     * @param dosage
     *            : The new dosage
     * @param timesGiven
     *            : The new time of day that the medication is to administrated
     * @param reason
     *            : The new reason for taking the medication
     * @param cosmoID
     *            : The individual whose medication is being updated.
     * @param oldMedicationName
     *            : The old name of the medication that is being updated.
     * @return
     */
    public static String updateMedication( String medicationName,
            String dosage, String timesGiven, String reason, String cosmoID,
            String oldMedicationName )
    {
        String result = "";
        if ( medicationName.isEmpty() )
        {
            result = "You must have a medication name";
        }
        else
        {
            DatabaseHelper db = new DatabaseHelper();
            db.connect();
            boolean successful = false;
            if ( !dosage.matches("\\d+") && dosage.length() != 0 )
            {
                result = "Dosage needs to be a number";
            }
            else
            {

                ResultSet medicationIDSet = db.select("medicationID",
                        "medication", "medicationName = '" + oldMedicationName
                                + "' AND cosmoID = '" + cosmoID + "'", "");

                String medicationID = "";

                try
                {
                    medicationIDSet.next();
                    medicationID = medicationIDSet.getString(1);
                }
                catch ( SQLException e )
                {
                    e.printStackTrace();
                }

                String medicationValues[][] = new String[6][2];

                medicationValues[0][0] = "medicationID";
                medicationValues[1][0] = "medicationName";
                medicationValues[2][0] = "cosmoID";
                medicationValues[3][0] = "dosage";
                medicationValues[4][0] = "timesGiven";
                medicationValues[5][0] = "reason";

                medicationValues[0][1] = medicationID;
                medicationValues[1][1] = medicationName;
                medicationValues[2][1] = cosmoID;
                medicationValues[3][1] = dosage;
                medicationValues[4][1] = timesGiven;
                medicationValues[5][1] = reason;

                successful = db.update(medicationValues, "medication",
                        medicationID);
                if ( !successful )
                {
                    result = "The update was not completed";
                }
            }
        }
        return result;
    }

}
