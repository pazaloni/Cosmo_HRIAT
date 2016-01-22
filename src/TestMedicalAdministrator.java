import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * Purpose: Class to test the functionality of the medical administrator.
 * 
 * @author TEAM CIMP
 * @version 1.0
 */
public class TestMedicalAdministrator
{
    MedicalAdministrator testHelper;

    //Used to test the filepath for the participant image
    PreviewPaneHelper paneHelper; 
    
    private String cosmoIDOne;
    private String participantFirstNameOne;
    private String participantLastNameOne;
    private LocalDate participantBirthdateOne;
    private String physicianFNameOne;
    private String physicianLNameOne;
    private String healthNumberOne;
    private String phoneOne;
    private String addressOne;

    private String cosmoIDTwo;
    private String participantFirstNameTwo;
    private String participantLastNameTwo;
    private LocalDate participantBirthdateTwo;
    private String physicianFNameTwo;
    private String physicianLNameTwo;
    private String healthNumberTwo;
    private String phoneTwo;
    private String addressTwo;

    private String cosmoIDThree;
    private String participantFirstNameThree;
    private String participantLastNameThree;
    private LocalDate participantBirthdateThree;
    private String physicianFNameThree;
    private String physicianLNameThree;
    private String healthNumberThree;
    private String phoneThree;
    private String addressThree;
    private String imageThree;

    private String failEmailWithoutBoth;
    private String failEmailWithoutAt;
    private String failEmailWithoutPeriod;
    private String failNumeric;
    private String failEmptyField;
    private LocalDate failDate;

    /**
     * 
     * Purpose: Set up the data for the participants to be added
     * 
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception
    {
        String date1 = "07-07-2007";
        String date2 = "20-12-2012";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        cosmoIDOne = "777";
        participantFirstNameOne = "Jeff";
        participantLastNameOne = "Jones";
        participantBirthdateOne = LocalDate.parse(date1, formatter);
        physicianFNameOne = "Phil";
        physicianLNameOne = "Donihue";
        healthNumberOne = "111111111";
        phoneOne = "1111111111";
        addressOne = "123 Fake Street";

        cosmoIDTwo = "666";
        participantFirstNameTwo = "Chad";
        participantLastNameTwo = "Stones";
        participantBirthdateTwo = LocalDate.parse(date2, formatter);
        physicianFNameTwo = "Louis ";
        physicianLNameTwo = "Cipher";
        healthNumberTwo = "999999999";
        phoneTwo = "6669116666";
        addressTwo = "123 Cookie Blvd";

        cosmoIDThree = "999";
        participantFirstNameThree = "vim";
        participantLastNameThree = "snickles";
        participantBirthdateThree = LocalDate.parse(date2, formatter);
        imageThree = "images/"+cosmoIDThree + ".jpg";
        
        healthNumberThree = "999999999";
        phoneThree = "6669116666";
        addressThree = "123 Yummy yum";

        failEmailWithoutBoth = "1234";
        failEmailWithoutAt = "hello.hello";
        failEmailWithoutPeriod = "1234@1234";
        failNumeric = "random";

        paneHelper = new PreviewPaneHelper();
        
        failEmptyField = "";
        failDate = null;

    }

    /**
     * 
     * Purpose: Tests every field to make sure that when one of them is empty an
     * error is shown
     */
    @Test
    public void testFieldIsEmpty()
    {
        String result = MedicalAdministrator.createParticipant(cosmoIDOne,
                failEmptyField, participantLastNameOne,
                participantBirthdateOne, physicianFNameOne, physicianLNameOne,
                healthNumberOne, phoneOne, addressOne);
        assertTrue(result.equals("One of your fields is empty"));

        result = MedicalAdministrator.createParticipant(failEmptyField,
                participantFirstNameOne, participantLastNameOne,
                participantBirthdateOne, physicianFNameOne, physicianLNameOne,
                healthNumberOne, phoneOne, addressOne);
        assertTrue(result.equals("One of your fields is empty"));

        result = MedicalAdministrator.createParticipant(cosmoIDOne,
                participantFirstNameOne, failEmptyField,
                participantBirthdateOne, physicianFNameOne, physicianLNameOne,
                healthNumberOne, phoneOne, addressOne);
        assertTrue(result.equals("One of your fields is empty"));

        result = MedicalAdministrator.createParticipant(cosmoIDOne,
                participantFirstNameOne, participantLastNameOne, failDate,
                physicianFNameOne, physicianLNameOne, healthNumberOne,
                phoneOne, addressOne);
        assertTrue(result.equals("One of your fields is empty"));

        result = MedicalAdministrator.createParticipant(cosmoIDOne,
                participantFirstNameOne, participantLastNameOne,
                participantBirthdateOne, failEmptyField, physicianLNameOne,
                healthNumberOne, phoneOne, addressOne);
        assertTrue(result.equals("One of your fields is empty"));

        result = MedicalAdministrator.createParticipant(cosmoIDOne,
                participantFirstNameOne, participantLastNameOne,
                participantBirthdateOne, physicianFNameOne, failEmptyField,
                healthNumberOne, phoneOne, addressOne);
        assertTrue(result.equals("One of your fields is empty"));

        result = MedicalAdministrator.createParticipant(cosmoIDOne,
                participantFirstNameOne, participantLastNameOne,
                participantBirthdateOne, physicianFNameOne, physicianLNameOne,
                failEmptyField, phoneOne, addressOne);
        assertTrue(result.equals("One of your fields is empty"));

        result = MedicalAdministrator.createParticipant(cosmoIDOne,
                participantFirstNameOne, participantLastNameOne,
                participantBirthdateOne, physicianFNameOne, physicianLNameOne,
                healthNumberOne, failEmptyField, addressOne);
        assertTrue(result.equals("One of your fields is empty"));

        result = MedicalAdministrator.createParticipant(cosmoIDOne,
                participantFirstNameOne, participantLastNameOne,
                participantBirthdateOne, physicianFNameOne, physicianLNameOne,
                healthNumberOne, phoneOne, failEmptyField);
        assertTrue(result.equals("One of your fields is empty"));
    }

    /**
     * 
     * Purpose: Checks to make sure cosmoID is a number. Fails if the cosmoID is
     * not a number
     */
    @Test
    public void TestCosmoIDNumber()
    {
        String result = MedicalAdministrator.createParticipant(failNumeric,
                participantFirstNameOne, participantFirstNameOne,
                participantBirthdateOne, physicianFNameOne, physicianLNameOne,
                healthNumberOne, phoneOne, addressOne);
        assertTrue(result.equals("CosmoID must be a number"));
    }

    /**
     * 
     * Purpose: Checks to make sure Health Number is 9 digits long. Fails if it
     * is not 9 digits
     */
    @Test
    public void testfailHealth()
    {
        String result = MedicalAdministrator.createParticipant(cosmoIDOne,
                participantFirstNameOne, participantFirstNameOne,
                participantBirthdateOne, physicianFNameOne, physicianLNameOne,
                failNumeric, phoneOne, addressOne);
        assertTrue(result.equals("Health Number must be 9 digits"));
    }

    /**
     * 
     * Purpose: Checks to see if phone number is 10 digits long. Fails if it is
     * not 10 digits.
     */
    @Test
    public void testfailPhone()
    {
        String result = MedicalAdministrator.createParticipant(cosmoIDOne,
                participantFirstNameOne, participantFirstNameOne,
                participantBirthdateOne, physicianFNameOne, physicianLNameOne,
                healthNumberOne, failNumeric, addressOne);
        assertTrue(result.equals("Phone Number must be 10 digits"));
    }

    /**
     * 
     * Purpose: Checks to see if you can add 2 people with the same cosmoID
     */
    @Test
    public void testCosmoIDExists()
    {
        String result = MedicalAdministrator.createParticipant(cosmoIDOne,
                participantFirstNameOne, participantFirstNameOne,
                participantBirthdateOne, physicianFNameOne, physicianLNameOne,
                healthNumberOne, phoneOne, addressOne);

        // assertTrue(result.equals(""));

        result = MedicalAdministrator.createParticipant(cosmoIDOne,
                participantFirstNameOne, participantFirstNameOne,
                participantBirthdateOne, physicianFNameOne, physicianLNameOne,
                healthNumberOne, phoneOne, addressOne);
        System.out.println(result);
        assertTrue(result.equals("That CosmoID already exists"));
    }

    /**
     * 
     * Purpose: Checks to see if physician was added successfully not 10 digits.
     */
    @Test
    public void testAddPhysician()
    {
        String result = MedicalAdministrator.createParticipant(cosmoIDTwo,
                participantFirstNameTwo, participantFirstNameTwo,
                participantBirthdateTwo, physicianFNameTwo, physicianLNameTwo,
                healthNumberTwo, phoneTwo, addressTwo);

        assertTrue(result.equals(""));

    }

    /**
     * 
     * Purpose: Checks to see if existing physician was connected to a
     * participant not 10 digits.
     */
    @Test
    public void testPhysicianExists()
    {
        String result = MedicalAdministrator.createParticipant(cosmoIDThree,
                participantFirstNameThree, participantFirstNameThree,
                participantBirthdateThree, physicianFNameTwo,
                physicianLNameTwo, healthNumberThree, phoneThree, addressThree);

        assertTrue(result.equals(""));

    }

    /**
     * Purpose: Test that the image is written in the right place when the
     * medical adminstrator adds a new participant
     */
    @Test
    public void testImageIsAdded()
    {
        DatabaseHelper db = new DatabaseHelper();
        db.connect();
        ResultSet testImage = db.select("imagePath", "Participant", "cosmoID="
                + cosmoIDThree, "");
        
        String imagePath = "";
        try
        {
            imagePath = testImage.getString(1);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        
        assertTrue(imagePath.equals(imageThree));

    }
    
    

    @After
    public void tearDown()
    {
    }

}
