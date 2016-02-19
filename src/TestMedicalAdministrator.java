import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    DatabaseHelper db = new DatabaseHelper();
    
    // Used to test the filepath for the participant image
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
    
    private String cosmoIDFour;
    private String participantFirstNameFour;
    private String participantLastNameFour;
    private LocalDate participantBirthdateFour;
    private String physicianFNameFour;
    private String physicianLNameFour;
    private String healthNumberFour;
    private String phoneFour;
    private String addressFour;
    private String imageThree;

    private String failEmailWithoutBoth;
    private String failEmailWithoutAt;
    private String failEmailWithoutPeriod;
    private String failNumeric;
    private String failEmptyField;
    private LocalDate failDate;

    private String path = "/images/4433.jpg";

    /**
     * 
     * Purpose: Set up the data for the participants to be added
     * 
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception
    {
        
        db.connect();
        
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
        
        db.delete("Participant", "cosmoID = " + cosmoIDOne);
        
        

        cosmoIDTwo = "666";
        participantFirstNameTwo = "Chad";
        participantLastNameTwo = "Stones";
        participantBirthdateTwo = LocalDate.parse(date2, formatter);
        physicianFNameTwo = "Louis ";
        physicianLNameTwo = "Cipher";
        healthNumberTwo = "999999999";
        phoneTwo = "6669116666";
        addressTwo = "123 Cookie Blvd";

        db.delete("Participant", "cosmoID = " + cosmoIDTwo);
        
        cosmoIDThree = "999";
        participantFirstNameThree = "vim";
        participantLastNameThree = "snickles";
        participantBirthdateThree = LocalDate.parse(date2, formatter);
        imageThree = "RandomImage.jpg";

        healthNumberThree = "999999999";
        phoneThree = "6669116666";
        addressThree = "123 Yummy yum";
        
        db.delete("Participant", "cosmoID = " + cosmoIDThree);
        
        cosmoIDFour = "18481";
        participantFirstNameFour = "Jon";
        participantLastNameFour = "Smith";
        participantBirthdateFour = LocalDate.parse(date2, formatter);
        physicianFNameFour = "Doc";
        physicianLNameFour = "Brown";
        healthNumberFour = "123123132";
        phoneFour = "1231231231";
        addressFour = "123 Crescent Street";
        
        db.delete("Participant", "cosmoID = " + cosmoIDFour);
    

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
                healthNumberOne, phoneOne, addressOne, path);
        assertTrue(result.equals("One of your fields is empty"));

        result = MedicalAdministrator.createParticipant(failEmptyField,
                participantFirstNameOne, participantLastNameOne,
                participantBirthdateOne, physicianFNameOne, physicianLNameOne,
                healthNumberOne, phoneOne, addressOne, path);
        assertTrue(result.equals("One of your fields is empty"));

        result = MedicalAdministrator.createParticipant(cosmoIDOne,
                participantFirstNameOne, failEmptyField,
                participantBirthdateOne, physicianFNameOne, physicianLNameOne,
                healthNumberOne, phoneOne, addressOne, path);
        assertTrue(result.equals("One of your fields is empty"));

        result = MedicalAdministrator.createParticipant(cosmoIDOne,
                participantFirstNameOne, participantLastNameOne, failDate,
                physicianFNameOne, physicianLNameOne, healthNumberOne,
                phoneOne, addressOne, path);
        assertTrue(result.equals("One of your fields is empty"));

        result = MedicalAdministrator.createParticipant(cosmoIDOne,
                participantFirstNameOne, participantLastNameOne,
                participantBirthdateOne, failEmptyField, physicianLNameOne,
                healthNumberOne, phoneOne, addressOne, path);
        assertTrue(result.equals("One of your fields is empty"));

        result = MedicalAdministrator.createParticipant(cosmoIDOne,
                participantFirstNameOne, participantLastNameOne,
                participantBirthdateOne, physicianFNameOne, failEmptyField,
                healthNumberOne, phoneOne, addressOne, path);
        assertTrue(result.equals("One of your fields is empty"));

        result = MedicalAdministrator.createParticipant(cosmoIDOne,
                participantFirstNameOne, participantLastNameOne,
                participantBirthdateOne, physicianFNameOne, physicianLNameOne,
                failEmptyField, phoneOne, addressOne, path);
        assertTrue(result.equals("One of your fields is empty"));

        result = MedicalAdministrator.createParticipant(cosmoIDOne,
                participantFirstNameOne, participantLastNameOne,
                participantBirthdateOne, physicianFNameOne, physicianLNameOne,
                healthNumberOne, failEmptyField, addressOne, path);
        assertTrue(result.equals("One of your fields is empty"));

        result = MedicalAdministrator.createParticipant(cosmoIDOne,
                participantFirstNameOne, participantLastNameOne,
                participantBirthdateOne, physicianFNameOne, physicianLNameOne,
                healthNumberOne, phoneOne, failEmptyField, path);
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
                healthNumberOne, phoneOne, addressOne, path);
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
                failNumeric, phoneOne, addressOne, path);
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
                healthNumberOne, failNumeric, addressOne, path);
        System.out.println(result);
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
                healthNumberOne, phoneOne, addressOne, path);

        // assertTrue(result.equals(""));

        result = MedicalAdministrator.createParticipant(cosmoIDOne,
                participantFirstNameOne, participantFirstNameOne,
                participantBirthdateOne, physicianFNameOne, physicianLNameOne,
                healthNumberOne, phoneOne, addressOne, path);
        System.out.println(result);
        assertTrue(result.equals("That CosmoID already exists"));
        
        DatabaseHelper db = new DatabaseHelper();
        db.connect();
        db.delete("Participant", "cosmoID="+cosmoIDOne);
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
                healthNumberTwo, phoneTwo, addressTwo, path);

        assertTrue(result.equals(""));
        
        DatabaseHelper db = new DatabaseHelper();
        db.connect();
        db.delete("Participant", "cosmoID="+cosmoIDTwo);

    }

    /**
     * 
     * Purpose: Checks to see if existing physician was connected to a 
     * participant
     */
    @Test
    public void testPhysicianExists()
    {
        String result = MedicalAdministrator.createParticipant(cosmoIDThree,
                participantFirstNameThree, participantFirstNameThree,
                participantBirthdateThree, physicianFNameTwo,
                physicianLNameTwo, healthNumberThree, phoneThree, addressThree,
                path);
        System.out.println(result);
        assertTrue(result.equals(""));
        
        DatabaseHelper db = new DatabaseHelper();
        db.connect();
        db.delete("Participant", "cosmoID="+cosmoIDThree);

    }
    
    /**
     * 
     * Purpose: to test if the first name is changed
     * @throws SQLException 
     */
    @Test 
    public void testChangeFirstName() throws SQLException
    {
        //create the original participant
        String result = MedicalAdministrator.createParticipant(cosmoIDFour,
                participantFirstNameFour, participantFirstNameFour,
                participantBirthdateFour, physicianFNameFour, physicianLNameFour,
                healthNumberFour, phoneFour, addressFour, path);
        
        //get it from the database
        ResultSet results = db.select("firstName" , "Participant", "cosmoID = " + this.cosmoIDFour, "");      
        
        //get the name of the participant
        String name = "";
        while (results.next())
        {
            name = results.getString(1); 
        }
        //make sure the insertion was successful
        assertTrue(result.equals(""));
        
        //edit the participant
        String editResult = MedicalAdministrator.editParticipant(cosmoIDFour,
                "Bob", participantLastNameFour,
                participantBirthdateFour, healthNumberFour, addressFour);
        
        ResultSet editedResultSet = db.select("firstName" , "Participant", "cosmoID = " + this.cosmoIDFour, "");  
        
        //get the new name
        String editedName = "";
        while (editedResultSet.next())
        {
            editedName = editedResultSet.getString(1); 
        }

        //make sure the insertion was successful
        assertTrue(editResult.equals(""));
        
        assertTrue(!name.equals(editedName));
        assertTrue(editedName.equals("Bob"));
        
        db.delete("Participant", "cosmoID = " + cosmoIDFour);
    }
    
    
    /**
     * 
     * Purpose: to test if the last name is changed
     * @throws SQLException 
     */
    @Test 
    public void testChangeLastName() throws SQLException
    {
        //create the original participant
        String result = MedicalAdministrator.createParticipant(cosmoIDFour,
                participantFirstNameFour, participantFirstNameFour,
                participantBirthdateFour, physicianFNameFour, physicianLNameFour,
                healthNumberFour, phoneFour, addressFour, path);
        
        //get it from the database
        ResultSet results = db.select("lastName" , "Participant", "cosmoID = " + this.cosmoIDFour, "");      
        
        //get the name of the participant
        String name = "";
        while (results.next())
        {
            name = results.getString(1); 
        }
        //make sure the insertion was successful
        assertTrue(result.equals(""));
        
        //edit the participant
        String editResult = MedicalAdministrator.editParticipant(cosmoIDFour,
                participantFirstNameFour, "Smith",
                participantBirthdateFour, healthNumberFour, addressFour);
        
        ResultSet editedResultSet = db.select("lastName" , "Participant", "cosmoID = " + this.cosmoIDFour, "");  
        
        //get the new name
        String editedName = "";
        while (editedResultSet.next())
        {
            editedName = editedResultSet.getString(1); 
        }

        //make sure the insertion was successful
        assertTrue(editResult.equals(""));
        
        assertTrue(!name.equals(editedName));
        assertTrue(editedName.equals("Smith"));
        
        db.delete("Participant", "cosmoID = " + cosmoIDFour);
    }
    
    /**
     * 
     * Purpose: to test if the birth date is changed
     * @throws SQLException 
     */
    @Test 
    public void testChangeBirthdate() throws SQLException
    {
        
        //create the original participant
        String result = MedicalAdministrator.createParticipant(cosmoIDFour,
                participantFirstNameFour, participantFirstNameFour,
                participantBirthdateFour, physicianFNameFour, physicianLNameFour,
                healthNumberFour, phoneFour, addressFour, path);
        
        //get it from the database
        ResultSet results = db.select("dateOfBirth" , "Participant", "cosmoID = " + this.cosmoIDFour, "");      
        
        //get the birthdate of the participant
        String birthdate = "";
        DateFormat format = new SimpleDateFormat("dd-MM-YYYY");
        
        while (results.next())
        {
            birthdate = format.format(results.getTimestamp(1));
        }
        //make sure the insertion was successful
        assertTrue(result.equals(""));
        
        //edit the participant with a different birthdate
        String editResult = MedicalAdministrator.editParticipant(cosmoIDFour,
                participantFirstNameFour, participantLastNameFour,
                participantBirthdateOne, healthNumberFour, addressFour);
        
        //get edited result set
        ResultSet editedResultSet = db.select("dateOfBirth" , "Participant", "cosmoID = " + this.cosmoIDFour, "");  
        
        //get the new name
        String editedBirthDate = "";
        while (editedResultSet.next())
        {
            editedBirthDate = format.format(editedResultSet.getTimestamp(1)); 
        }

        //make sure the insertion was successful
        assertTrue(editResult.equals(""));
        
        assertTrue(!birthdate.equals(editedBirthDate));
        assertTrue(editedBirthDate.equals("07-07-2007"));
        
        db.delete("Participant", "cosmoID = " + cosmoIDFour);
    }
    
    /**
     * 
     * Purpose: to test if the PHN is changed
     * @throws SQLException 
     */
    @Test 
    public void testPHNChanged() throws SQLException
    {
        
        //create the original participant
        String result = MedicalAdministrator.createParticipant(cosmoIDFour,
                participantFirstNameFour, participantFirstNameFour,
                participantBirthdateFour, physicianFNameFour, physicianLNameFour,
                healthNumberFour, phoneFour, addressFour, path);
        
        //get it from the database
        ResultSet results = db.select("personalHealthNumber" , "Participant", "cosmoID = " + this.cosmoIDFour, "");      
        
        //get the phn of the participant
        String phn = "";
        
        while (results.next())
        {
            phn = results.getString(1);
        }
        //make sure the insertion was successful
        assertTrue(result.equals(""));
        
        //edit the participant with a different birthdate
        String editResult = MedicalAdministrator.editParticipant(cosmoIDFour,
                participantFirstNameFour, participantLastNameFour,
                participantBirthdateOne, "123412341", addressFour);
        
        //get edited result set
        ResultSet editedResultSet = db.select("personalHealthNumber" , "Participant", "cosmoID = " + this.cosmoIDFour, "");  
        
        //get the new name
        String editedPHN = "";
        while (editedResultSet.next())
        {
            editedPHN = editedResultSet.getString(1);
        }

        //make sure the insertion was successful
        assertTrue(editResult.equals(""));
        
        assertTrue(!phn.equals(editedPHN));
        assertTrue(editedPHN.equals("123412341"));
        
        db.delete("Participant", "cosmoID = " + cosmoIDFour);
    }
    
    /**
     * 
     * Purpose: to test if the PHN is changed
     * @throws SQLException 
     */
    @Test 
    public void testAddressChanged() throws SQLException
    {
        
        //create the original participant
        String result = MedicalAdministrator.createParticipant(cosmoIDFour,
                participantFirstNameFour, participantFirstNameFour,
                participantBirthdateFour, physicianFNameFour, physicianLNameFour,
                healthNumberFour, phoneFour, addressFour, path);
        
        //get it from the database
        ResultSet results = db.select("address" , "Participant", "cosmoID = " + this.cosmoIDFour, "");      
        
        //get the phn of the participant
        String address = "";
        
        while (results.next())
        {
            address = results.getString(1);
        }
        //make sure the insertion was successful
        assertTrue(result.equals(""));
        
        //edit the participant with a different birthdate
        String editResult = MedicalAdministrator.editParticipant(cosmoIDFour,
                participantFirstNameFour, participantLastNameFour,
                participantBirthdateOne, healthNumberFour, "19595 Testing Avenue");
        
        //get edited result set
        ResultSet editedResultSet = db.select("address" , "Participant", "cosmoID = " + this.cosmoIDFour, "");  
        
        //get the new name
        String editedAddress = "";
        while (editedResultSet.next())
        {
            editedAddress = editedResultSet.getString(1);
        }

        //make sure the insertion was successful
        assertTrue(editResult.equals(""));
        
        assertTrue(!address.equals(editedAddress));
        assertTrue(editedAddress.equals("19595 Testing Avenue"));
        
        db.delete("Participant", "cosmoID = " + cosmoIDFour);
    }


    /**
     * Purpose: Test that the image is written in the right place when the
     * medical adminstrator adds a new participant
     */
    @Test
    public void testImageIsAdded()
    {
        String imagePathDB = "/images/" + cosmoIDThree + ".jpg";
        String imagePathLocal = "random.jpg";

        MedicalAdministrator.createParticipant(cosmoIDThree,
                participantFirstNameThree, participantLastNameThree,
                participantBirthdateThree, physicianFNameTwo,
                physicianLNameTwo, healthNumberThree, phoneThree, addressThree,
                imagePathDB);

        DatabaseHelper db = new DatabaseHelper();
        db.connect();
        
        

        ResultSet testImage = db.select("imagePath", "Participant", "cosmoID="
                + cosmoIDThree, "");

        String imagePathSaved = "";
        try
        {
            if(testImage.next())
            {
                imagePathSaved = testImage.getString(1);    
            }
            else                
            {
                throw new SQLException();
            }
            
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }
        
        assertTrue(imagePathSaved.equals(imagePathDB));
        db.delete("Participant", "cosmoID="+cosmoIDThree);


    }
    @After
    public void tearDown()
    {
    }

}
