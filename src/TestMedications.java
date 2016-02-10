import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
/**
 * 
 *  Purpose: Test the medication class 
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

    @Before
    public void setUp() throws Exception
    {
        medicationName1 = "Advil";
        medicationName2 = "Tylenol";

        dosage1 = "1";
        dosage2 = "2";

        timesGiven1 = "2016-02-08 05:12:18";
        timesGiven2 = "2016-04-15 05:12:18";

        reason1 = "they had a headache";
        reason2 = "they had a cold";

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
}
