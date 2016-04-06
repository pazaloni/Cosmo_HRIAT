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
import core.ScanForms;
import core.SeizureMedication;

/**
 * Test for the ScanForms class
 * 
 * @author CIMP
 *
 */
public class testScanForms
{
    ScanForms test1;
    ScanForms test2;
    ScanForms test3;
    ScanForms test4;
    ScanForms test5;
    ScanForms test6;
    ScanForms test7;

    String name1;
    String name1DB;
    String name2;
    String name2DB;
    String nameSame;
    String nameEmpty;
    String description1;    
    String description2;
    String descriptionEmpty;    
    LocalDate dateTime;                
    String stringTime;
    
    @Before
    public void setUp() throws Exception
    {
        name1 = "C:\\Users\\Public\\Pictures\\Sample Pictures\\Koala.jpg";
        name1DB = "/images/scannedImages/123Koala.jpg";
        name2 = "C:\\Users\\Public\\Pictures\\Sample Pictures\\Desert.jpg";;
        name2DB = "/images/scannedImages/123Desert.jpg";
        nameSame = "C:\\Users\\Public\\Pictures\\Sample Pictures\\Koala.jpg";;
        nameEmpty = "";
        descriptionEmpty= "";
        
        dateTime = dateTime.now();        
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

        description1 = "Koala";
        description2 = "Desert";
        

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
    public void testGetFileName()
    {
        test1 = new ScanForms(dateTime, description1, name1);
        test2 = new ScanForms(dateTime, description2, name2);

        assertTrue(test1.getFileName().get().equals(name1));
        assertTrue(test2.getFileName().get().equals(name2));
    }

    /**
     * 
     * Purpose: checks that the dates saved are the same
     */
    @Test
    public void testGetDateSaved()
    {
        test1 = new ScanForms(dateTime, description1, name1);
        test2 = new ScanForms(dateTime, description2, name2);

        assertTrue(test1.getDateSaved().get().equals(dateTime.toString()));
        assertTrue(test2.getDateSaved().get().equals(dateTime.toString()));
    }

    /**
     * 
     * Purpose: checks the descriptions given are the same
     */
    @Test
    public void testGetDescription()
    {
        test1 = new ScanForms(dateTime, description1, name1);
        test2 = new ScanForms(dateTime, description2, name2 );

        assertTrue(test1.getDescription().get().equals(description1));
        assertTrue(test2.getDescription().get().equals(description2));
    }

    /**
     * 
     * Purpose: checks that the times are properly formatted
     */
    @Test
    public void testDisplayDateTime()
    {
        test1 = new ScanForms(dateTime, description1, name1);
        test2 = new ScanForms(dateTime, description2, name2);

        assertTrue(test1.displayDateSaved().get().equals(stringTime));
        assertTrue(test2.displayDateSaved().get().equals(stringTime));
    }

    /**
     * 
     * Purpose: checks that image can be properly created
     */
    @Test
    public void testCreateImage()
    {
        //Test success
        test1 = new ScanForms(dateTime, description1, name1 );
        //test description empty
        test2 = new ScanForms(dateTime, descriptionEmpty, name2);
        //test name empty
        test3 = new ScanForms(dateTime, description1,  nameEmpty);
        //test both empty
        test4 = new ScanForms(dateTime, descriptionEmpty, nameEmpty);
        //test name exists
        test5 = new ScanForms(dateTime, description1, nameSame);
        assertTrue(ScanForms.createImage(test1, "123").equals(""));
        assertTrue(ScanForms.createImage(test2, "123").equals(
                "You have missing required fields"));
        assertTrue(ScanForms.createImage(test3, "123").equals(
                "You have missing required fields"));
        assertTrue(ScanForms.createImage(test4, "123").equals(
                "You have missing required fields"));
        assertTrue(ScanForms.createImage(test5, "123").equals(
                "An image with that name already exists."));
        ScanForms.deleteImage(test1, "123");
    }
    
    /**
     * 
     * Purpose: checks that image can be properly deleted
     */
    @Test
    public void testDeleteImage()
    {
        test1 = new ScanForms(dateTime, description1, name1);
        ScanForms.createImage(test1, "123").equals("");
        
        test1 = null;
        test1 = new ScanForms(dateTime, description1, name1DB);
        assertTrue(ScanForms.deleteImage(test1, "123").equals( "Deleted successfully"));
        
    }
}
