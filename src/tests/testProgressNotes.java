package tests;
import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import core.ProgressNotes;
import core.SeizureMedication;

/**
 * Test for the progressNotes class
 * @author cst207
 *
 */
public class testProgressNotes
{
    ProgressNotes test1;
    ProgressNotes test2;

    String participantName1;
    LocalDate dateTime;
    String num1;

    String participantName2;
    String num2;

    @Before
    public void setUp() throws Exception
    {
        participantName1 = "Jeff";
        participantName2 = "Goerge";

        dateTime = dateTime.now();

        num1 = "2";
        num2 = "5";



        test1 = new ProgressNotes(dateTime, participantName1, num1);
        test2 = new ProgressNotes(dateTime, participantName2, num2);

    }

    @After
    public void tearDown() throws Exception
    {
    }

    /**
     * 
     * Purpose: checks that the names are the same
     */
    @Test
    public void testGetName()
    {
        assertTrue(test1.getName().get().equals(participantName1));
        assertTrue(test2.getName().get().equals(participantName2));
    }

    /**
     * 
     * Purpose: checks that the times are the same
     */
    @Test
    public void testGetDateTime()
    {
        assertTrue(test1.getDateTime().get().equals(dateTime));
        assertTrue(test2.getDateTime().get().equals(dateTime));
    }

    /**
     * 
     * Purpose: checks the nums given are the same
     */
    @Test
    public void testGetNum()
    {
        assertTrue(test1.getNum().get().equals(num1));
        assertTrue(test2.getNum().get().equals(num2));
    }


}



