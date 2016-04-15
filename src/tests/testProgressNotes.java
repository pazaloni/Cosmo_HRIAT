package tests;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import core.ProgressNotes;
import core.SeizureMedication;

/**
 * Test for the progressNotes class
 * 
 * @author CIMP
 *
 */
public class testProgressNotes
{
    ProgressNotes test1;
    ProgressNotes test2;
    ProgressNotes test3;
    ProgressNotes test4;
    ProgressNotes test5;
    ProgressNotes test6;
    ProgressNotes test7;

    String participantName1;
    LocalDate dateTime;
    String num;

    String numEmpty;
    String participantNameEmpty;

    String participantName2;
    String numSame;
    String numDifferent;

    String stringTime;

    @Before
    public void setUp() throws Exception
    {
        participantName1 = "Jeff";
        participantName2 = "Goerge";

        dateTime = dateTime.now();
        numEmpty = "";
        participantNameEmpty = "";

        stringTime = dateTime.toString();
        DateFormat dToSFormat = new SimpleDateFormat("dd-MMM-yyyy");
        DateFormat sToDFormat = new SimpleDateFormat("yyyy-MM-dd");
        String unformatedString = stringTime;
        Date unformatedDate;
        try
        {
            unformatedDate = sToDFormat.parse(unformatedString);
            stringTime = dToSFormat.format(unformatedDate);
        }
        catch ( ParseException e )
        {

        }

        num = "666";
        numSame = "666";
        numDifferent = "777";

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
        test1 = new ProgressNotes(dateTime, participantName1, num);
        test2 = new ProgressNotes(dateTime, participantName2, numSame);

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
        test1 = new ProgressNotes(dateTime, participantName1, num);
        test2 = new ProgressNotes(dateTime, participantName2, numSame);

        assertTrue(test1.getDateTime().get().equals(dateTime.toString()));
        assertTrue(test2.getDateTime().get().equals(dateTime.toString()));
    }

    /**
     * 
     * Purpose: checks the nums given are the same
     */
    @Test
    public void testGetNum()
    {
        test1 = new ProgressNotes(dateTime, participantName1, num);
        test2 = new ProgressNotes(dateTime, participantName2, numSame);

        assertTrue(test1.getNum().get().equals(num));
        assertTrue(test2.getNum().get().equals(numSame));
    }

    /**
     * 
     * Purpose: checks that the times are properly formatted
     */
    @Test
    public void testDisplayDateTime()
    {
        test1 = new ProgressNotes(dateTime, participantName1, num);
        test2 = new ProgressNotes(dateTime, participantName2, numSame);

        assertTrue(test1.displayDateTime().get().equals(stringTime));
        assertTrue(test2.displayDateTime().get().equals(stringTime));
    }

    /**
     * 
     * Purpose: checks that progress notes can be properly created
     */
    @Test
    public void testCreateProgressNote()
    {
        test1 = new ProgressNotes(dateTime, participantName1, num);
        test2 = new ProgressNotes(dateTime, participantName2, numSame);
        test3 = new ProgressNotes(dateTime, participantNameEmpty, numEmpty);
        test4 = new ProgressNotes(dateTime, participantName2, participantName2);
        test5 = new ProgressNotes(dateTime, participantName2, numEmpty);
        test6 = new ProgressNotes(dateTime, participantNameEmpty, numDifferent);
        assertTrue(ProgressNotes.createProgressNote(test1, "123").equals(""));
        assertTrue(ProgressNotes.createProgressNote(test2, "123").equals(
                "That No. is already taken"));
        assertTrue(ProgressNotes.createProgressNote(test3, "123").equals(
                "You have missing required fields"));
        assertTrue(ProgressNotes.createProgressNote(test4, "123").equals(
                "No. needs to be an integer"));
        assertTrue(ProgressNotes.createProgressNote(test5, "123").equals(
                "You have missing required fields"));
        assertTrue(ProgressNotes.createProgressNote(test6, "123").equals(
                "You have missing required fields"));
        ProgressNotes.deleteProgressNote(test1, "123");
    }

    /**
     * 
     * Purpose: checks that progress notes can be properly created
     */
    @Test
    public void testUpdateProgressNote()
    {
        test1 = new ProgressNotes(dateTime, participantName1, num);
        test2 = new ProgressNotes(dateTime, participantName2, numSame);
        test3 = new ProgressNotes(dateTime, participantNameEmpty, numEmpty);
        test4 = new ProgressNotes(dateTime, participantName2, participantName2);
        test5 = new ProgressNotes(dateTime, participantName2, numEmpty);
        test6 = new ProgressNotes(dateTime, participantNameEmpty, numDifferent);
        test7 = new ProgressNotes(dateTime, participantName2, numDifferent);
        ProgressNotes.createProgressNote(test1, "123").equals("");
        ProgressNotes.createProgressNote(test7, "123").equals("");
        assertTrue(ProgressNotes.updateProgressNote(test2, test1, "123")
                .equals(""));
        assertTrue(ProgressNotes.updateProgressNote(test3, test1, "123")
                .equals("You have missing required fields"));
        assertTrue(ProgressNotes.updateProgressNote(test4, test1, "123")
                .equals("No. needs to be an integer"));
        assertTrue(ProgressNotes.updateProgressNote(test5, test1, "123")
                .equals("You have missing required fields"));
        assertTrue(ProgressNotes.updateProgressNote(test6, test1, "123")
                .equals("You have missing required fields"));
        assertTrue(ProgressNotes.updateProgressNote(test7, test1, "123")
                .equals("That No. is already taken"));
        ProgressNotes.deleteProgressNote(test1, "123");
        ProgressNotes.deleteProgressNote(test7, "123");
    }

    /**
     * 
     * Purpose: checks that progress notes can be properly deleted
     */
    @Test
    public void testDeleteProgressNote()
    {
        test1 = new ProgressNotes(dateTime, participantName1, num);
        ProgressNotes.createProgressNote(test1, "123").equals("");

        assertTrue(ProgressNotes.deleteProgressNote(test1, "123").equals(
                "Deleted successfully"));

    }
}
