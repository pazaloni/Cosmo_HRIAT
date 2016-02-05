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
    
    private String cosmoIDFour;
    private String participantFirstNameFour;
    private String participantLastNameFour;
    private LocalDate participantBirthdateFour;
    private String physicianFNameFour;
    private String physicianLNameFour;
    private String healthNumberFour;
    private String phoneFour;
    private String addressFour;

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

        healthNumberThree = "999999999";
        phoneThree = "6669116666";
        addressThree = "123 Yummy yum";
        
        cosmoIDFour = "18481";
        participantFirstNameFour = "Jon";
        participantLastNameFour = "Smith";
        LocalDate participantBirthdateFour = LocalDate.parse(date2, formatter);
        physicianFNameFour = "Doc";
        physicianLNameFour = "Brown";
        healthNumberFour = "123123132";
        phoneFour = "1231231231";
        addressFour = "123 Crescent Street";
    

        failEmailWithoutBoth = "1234";
        failEmailWithoutAt = "hello.hello";
        failEmailWithoutPeriod = "1234@1234";
        failNumeric = "random";

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
                participantBirthdateOne, physicianFNameOne, 
                physicianLNameOne, healthNumberOne,
                phoneOne, addressOne);
        assertTrue(result.equals("One of your fields is empty"));

        result = MedicalAdministrator.createParticipant(failEmptyField,
                participantFirstNameOne, participantLastNameOne,
                participantBirthdateOne, physicianFNameOne, 
                physicianLNameOne, healthNumberOne,
                phoneOne, addressOne);
        assertTrue(result.equals("One of your fields is empty"));

        result = MedicalAdministrator.createParticipant(cosmoIDOne,
                participantFirstNameOne, failEmptyField,
                participantBirthdateOne, physicianFNameOne, 
                physicianLNameOne, healthNumberOne,
                phoneOne, addressOne);
        assertTrue(result.equals("One of your fields is empty"));

        result = MedicalAdministrator.createParticipant(cosmoIDOne,
                participantFirstNameOne, participantLastNameOne, failDate,
                physicianFNameOne, physicianLNameOne, healthNumberOne, 
                phoneOne, addressOne);
        assertTrue(result.equals("One of your fields is empty"));

        result = MedicalAdministrator.createParticipant(cosmoIDOne,
                participantFirstNameOne, participantLastNameOne,
                participantBirthdateOne, failEmptyField, 
                physicianLNameOne, healthNumberOne,
                phoneOne, addressOne);
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
     * Purpose: Checks to see if physician was added successfully
     * not 10 digits.
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
     * participant
     * not 10 digits.
     */
    @Test
    public void testPhysicianExists()
    {
        String result = MedicalAdministrator.createParticipant(cosmoIDThree,
                participantFirstNameThree, participantFirstNameThree,
                participantBirthdateThree, physicianFNameTwo, physicianLNameTwo,
                healthNumberThree, phoneThree, addressThree);
        
        assertTrue(result.equals(""));

    }
    
    /**
     * 
     * Purpose: to test if the first name is changed
     * @throws SQLException 
     */
    @Test public void testChangeFirstName() throws SQLException
    {
        String result = MedicalAdministrator.createParticipant(cosmoIDFour,
                participantFirstNameFour, participantFirstNameFour,
                participantBirthdateFour, physicianFNameFour, physicianLNameFour,
                healthNumberFour, phoneFour, addressFour);
        
        DatabaseHelper db = new DatabaseHelper();
        
        db.connect();
        
        
        ResultSet results = db.select("firstName" , "Participant", "cosmoID =" + this.cosmoIDFour, "");
        
        String name = results.getString(1);
        
        System.out.println(name);
        
        assertTrue(result.equals(""));
        
        String editResult = MedicalAdministrator.editParticipant(cosmoIDFour,
                "Bob", participantLastNameFour,
                participantBirthdateFour, healthNumberFour, addressFour);
        
        assertTrue(editResult.equals(""));
        
        
    }

    @After
    public void tearDown()
    {
    }

}
