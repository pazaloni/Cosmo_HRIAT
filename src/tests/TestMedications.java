package tests;
import static org.junit.Assert.*;
import helpers.DatabaseHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import core.Medication;

/**
 * 
 * Purpose: Test the medication class
 *
 * @author CIMP
 * @version 1.0
 */

public class TestMedications
{

    Medication test1;
    Medication test2;

    String medicationName1;
    String dosage1;
    String timesGiven1;
    String reason1;

    String medicationName2;
    String dosage2;
    String timesGiven2;
    String reason2;

    String cosmoID1;
    DatabaseHelper db;

    @Before
    public void setUp() throws Exception
    {

        db = new DatabaseHelper();

        medicationName1 = "Advil";
        medicationName2 = "Tylenol";

        dosage1 = "1";
        dosage2 = "2";

        timesGiven1 = "2016-02-08 05:12:18";
        timesGiven2 = "2016-04-15 05:12:18";

        reason1 = "they had a headache";
        reason2 = "they had a cold";

        cosmoID1 = "123";

        test1 = new Medication(medicationName1, dosage1, timesGiven1, reason1);
        test2 = new Medication(medicationName2, dosage2, timesGiven2, reason2);

    }

    @After
    public void tearDown() throws Exception
    {

    }

    /**
     * 
     * Purpose: checks that the medication names are the same
     */
    @Test
    public void testGetMedicationName()
    {
        assertTrue(test1.getMedicationName().get().equals(medicationName1));
        assertTrue(test2.getMedicationName().get().equals(medicationName2));
    }

    /**
     * 
     * Purpose: checks that the dosages are the same
     */
    @Test
    public void testGetDosage()
    {
        assertTrue(test1.getDosage().get().equals(dosage1));
        assertTrue(test2.getDosage().get().equals(dosage2));
    }

    /**
     * 
     * Purpose: checks the times given are the same
     */
    @Test
    public void testGetTimesGiven()
    {
        assertTrue(test1.getTimesGiven().get().equals(timesGiven1));
        assertTrue(test2.getTimesGiven().get().equals(timesGiven2));
    }

    /**
     * 
     * Purpose: checks that the reasons are the same
     */
    @Test
    public void testGetReason()
    {
        assertTrue(test1.getReason().get().equals(reason1));
        assertTrue(test2.getReason().get().equals(reason2));
    }

    /**
     * 
     * Purpose: checks that the reasons are the same
     * 
     * @throws SQLException
     */
    @Test
    public void testCreateMedication() throws SQLException
    {
        String result = Medication.createMedication(medicationName1, dosage1,
                timesGiven1, reason1, cosmoID1);
        ResultSet results = db.select("reason", "Medication",
                "medicationName = '" + medicationName1 + "' AND cosmoID = '"
                        + cosmoID1 + "'", "");
        String reason = "";
        while ( results.next() )
        {
            reason = results.getString(1);
        }
        assertTrue(reason.equals(reason1));

        Medication.removeMedication(medicationName1, cosmoID1);
    }

    /**
     * 
     * Purpose: checks that a medication is not made if there is no name
     * 
     * @throws SQLException
     */
    @Test
    public void testCreateMedicationFailName() throws SQLException
    {
        String result = Medication.createMedication("", dosage1, timesGiven1,
                reason1, cosmoID1);

        assertFalse(result.length() == 0);

    }

    /**
     * 
     * Purpose: checks that a medication is not made if the dosage is not a
     * number
     * 
     * @throws SQLException
     */
    @Test
    public void testCreateMedicationFailDosage() throws SQLException
    {
        String result = Medication.createMedication(medicationName1, "gsdgsdf",
                timesGiven1, reason1, cosmoID1);

        assertFalse(result.length() == 0);

    }

    /**
     * 
     * Purpose: checks that a join with the seizure table is made.
     * 
     * @throws SQLException
     */
    @Test
    public void testCreateSeizureMedication() throws SQLException
    {
        String result = Medication.createMedication(medicationName1, dosage1,
                timesGiven1, "Seizure Medication", cosmoID1);

        ResultSet medicationIDSet = db.select("medicationID", "medication",
                "medicationName = '" + medicationName1 + "' AND cosmoID = '"
                        + cosmoID1 + "'", "");

        String medicationID = "";

        medicationIDSet.next();
        medicationID = medicationIDSet.getString(1);

        ResultSet seizureIDSet = db.select("seizureID", "seizures",
                "cosmoID = '" + cosmoID1 + "'", "");

        String seizureID = "";

        seizureIDSet.next();
        seizureID = seizureIDSet.getString(1);

        ResultSet medicationSeizureSet = db.select("count(*)",
                "seizureMedication", "medicationID = " + medicationID
                        + " AND seizureID = " + seizureID, "");
        medicationSeizureSet.next();
        int medicationSeizureCount = medicationSeizureSet.getInt(1);

        assertTrue(medicationSeizureCount == 1);

        Medication.removeMedication(medicationName1, cosmoID1);
    }

    /**
     * 
     * Purpose: checks that the medication was removed
     * 
     * @throws SQLException
     */
    @Test
    public void testRemoveMedication() throws SQLException
    {
        String result = Medication.createMedication(medicationName1, dosage1,
                timesGiven1, reason1, cosmoID1);

        Medication.removeMedication(medicationName1, cosmoID1);

        ResultSet results = db.select("count(*)", "Medication",
                "medicationName = '" + medicationName1 + "' AND cosmoID = '"
                        + cosmoID1 + "'", "");
        int count = -1;
        results.next();
        count = results.getInt(1);

        assertTrue(count == 0);

    }

    /**
     * 
     * Purpose: checks that a join with the seizure table is removed.
     * 
     * @throws SQLException
     */
    @Test
    public void testRemoveSeizureMedication() throws SQLException
    {
        String result = Medication.createMedication(medicationName1, dosage1,
                timesGiven1, "Seizure Medication", cosmoID1);

        ResultSet medicationIDSet = db.select("medicationID", "medication",
                "medicationName = '" + medicationName1 + "' AND cosmoID = '"
                        + cosmoID1 + "'", "");

        String medicationID = "";

        medicationIDSet.next();
        medicationID = medicationIDSet.getString(1);

        ResultSet seizureIDSet = db.select("seizureID", "seizures",
                "cosmoID = '" + cosmoID1 + "'", "");

        String seizureID = "";

        seizureIDSet.next();
        seizureID = seizureIDSet.getString(1);

        Medication.removeMedication(medicationName1, cosmoID1);

        ResultSet medicationSeizureSet = db.select("count(*)",
                "seizureMedication", "medicationID = " + medicationID
                        + " AND seizureID = " + seizureID, "");
        medicationSeizureSet.next();
        int medicationSeizureCount = medicationSeizureSet.getInt(1);

        assertTrue(medicationSeizureCount == 0);
    }

    /**
     * 
     * Purpose: checks that the medicationName was changed
     * 
     * @throws SQLException
     */
    @Test
    public void testUpdateMedication() throws SQLException
    {
        String result = Medication.createMedication(medicationName1, dosage1,
                timesGiven1, reason1, cosmoID1);

        result = Medication.updateMedication(medicationName2, dosage1,
                timesGiven1, reason1, cosmoID1, medicationName1);
        ResultSet results = db.select("medicationName", "Medication",
                "medicationName = '" + medicationName2 + "' AND cosmoID = '"
                        + cosmoID1 + "'", "");
        String medicationName = "";
        while ( results.next() )
        {
            medicationName = results.getString(1);
        }
        assertTrue(medicationName.equals(medicationName2));

        Medication.removeMedication(medicationName2, cosmoID1);
    }

    /**
     * 
     * Purpose: checks that a medication is not updated if there is no name
     * 
     * @throws SQLException
     */
    @Test
    public void testUpdateMedicationFailName() throws SQLException
    {
        String result = Medication.createMedication(medicationName1, dosage1,
                timesGiven1, reason1, cosmoID1);

        result = Medication.updateMedication("", dosage1, timesGiven1, reason1,
                cosmoID1, medicationName1);

        assertFalse(result.length() == 0);

    }

    /**
     * 
     * Purpose: checks that a medication is not updated if the dosage is not a
     * number
     * 
     * @throws SQLException
     */
    @Test
    public void testUpdateMedicationFailDosage() throws SQLException
    {
        String result = Medication.createMedication(medicationName1, dosage1,
                timesGiven1, reason1, cosmoID1);

        result = Medication.updateMedication(medicationName1, "32fszsd",
                timesGiven1, reason1, cosmoID1, medicationName1);

        assertFalse(result.length() == 0);

    }

}
