package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import core.SeizureMedication;

public class testSeizureMedication
{
    SeizureMedication test1;
    SeizureMedication test2;

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

        test1 = new SeizureMedication(medicationName1, dosage1, timesGiven1);
        test2 = new SeizureMedication(medicationName2, dosage2, timesGiven2);

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
    public void testGetSeizureMedicationName()
    {
        assertTrue(test1.getMedicationName().get().equals(medicationName1));
        assertTrue(test2.getMedicationName().get().equals(medicationName2));
    }

    /**
     * 
     * Purpose: checks that the dosages are the same
     */
    @Test
    public void testGetSeizureDosage()
    {
        assertTrue(test1.getDosage().get().equals(dosage1));
        assertTrue(test2.getDosage().get().equals(dosage2));
    }

    /**
     * 
     * Purpose: checks the times given are the same
     */
    @Test
    public void testGetSeizureTimesGiven()
    {
        assertTrue(test1.getTimesGiven().get().equals(timesGiven1));
        assertTrue(test2.getTimesGiven().get().equals(timesGiven2));
    }

}
