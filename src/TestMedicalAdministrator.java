import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestMedicalAdministrator
{
    MedicalAdministrator testHelper;

    private String cosmoIDOne;
    private String participantFirstNameOne;
    private String participantLastNameOne;
    private LocalDate participantBirthdateOne;
    private String familyPhysicianOne;
    private String healthNumberOne;
    private String phoneOne;
    private String addressOne;
    
    private String cosmoIDTwo;
    private String participantFirstNameTwo;
    private String participantLastNameTwo;
    private LocalDate participantBirthdateTwo;
    private String familyPhysicianTwo;
    private String healthNumberTwo;
    private String phoneTwo;
    private String addressTwo;
    

    private String failEmailWithoutBoth;
    private String failEmailWithoutAt;
    private String failEmailWithoutPeriod;
    private String failNumeric;
    private String failEmptyField;
    private LocalDate failDate;

    @Before
    public void setUp() throws Exception
    {
    	String date1 = "07-07-2007";
    	String date2 = "20-12-2012";
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        cosmoIDOne = "777";
    	participantFirstNameOne = "Jeff";
    	participantLastNameOne = "Jones";
    	participantBirthdateOne = LocalDate.parse(date1,formatter);
    	familyPhysicianOne= "Dr Phill";
    	healthNumberOne="111111111";
    	phoneOne = "1111111111";
    	addressOne = "123 Fake Street";
    	
    	cosmoIDTwo ="666";
		participantFirstNameTwo = "Chad";
		participantLastNameTwo = "Stones";
		participantBirthdateTwo = LocalDate.parse(date2,formatter);
		familyPhysicianTwo = "Louis Cipher";
		healthNumberTwo="999999999";
		phoneTwo =	"666911666";
		addressTwo = "123 Cookie Blvd";

        
        failEmailWithoutBoth = "1234";
        failEmailWithoutAt = "hello.hello";
        failEmailWithoutPeriod = "1234@1234";
        failNumeric = "random";
        
        failEmptyField = "";
        failDate = null;

    }


    @Test
    public void testFieldIsEmpty()
    {
        String result = MedicalAdministrator.createParticipant(cosmoIDOne, failEmptyField, participantLastNameOne, participantBirthdateOne, familyPhysicianOne, healthNumberOne, phoneOne, addressOne);
        assertTrue(result.equals("One of your fields is empty"));
        
        result = MedicalAdministrator.createParticipant(failEmptyField, participantFirstNameOne, participantLastNameOne, participantBirthdateOne, familyPhysicianOne, healthNumberOne, phoneOne, addressOne);
        assertTrue(result.equals("One of your fields is empty"));
        result = MedicalAdministrator.createParticipant(cosmoIDOne, participantFirstNameOne, failEmptyField, participantBirthdateOne, familyPhysicianOne, healthNumberOne, phoneOne, addressOne);
        assertTrue(result.equals("One of your fields is empty"));
        result = MedicalAdministrator.createParticipant(cosmoIDOne, participantFirstNameOne, participantLastNameOne, failDate, familyPhysicianOne, healthNumberOne, phoneOne, addressOne);
        assertTrue(result.equals("One of your fields is empty"));
        result = MedicalAdministrator.createParticipant(cosmoIDOne, participantFirstNameOne, participantLastNameOne, participantBirthdateOne, failEmptyField, healthNumberOne, phoneOne, addressOne);
        assertTrue(result.equals("One of your fields is empty"));        
        result = MedicalAdministrator.createParticipant(cosmoIDOne, participantFirstNameOne, participantLastNameOne, participantBirthdateOne, healthNumberOne, failEmptyField, phoneOne, addressOne);
        assertTrue(result.equals("One of your fields is empty"));        
        result = MedicalAdministrator.createParticipant(cosmoIDOne, participantFirstNameOne, participantLastNameOne, participantBirthdateOne, healthNumberOne, healthNumberOne, failEmptyField, addressOne);
        assertTrue(result.equals("One of your fields is empty"));
        result = MedicalAdministrator.createParticipant(cosmoIDOne, participantFirstNameOne, participantLastNameOne, participantBirthdateOne, healthNumberOne, failEmptyField, phoneOne, failEmptyField);
        assertTrue(result.equals("One of your fields is empty"));  
    }
   


    @Test
    public void TestCosmoIDNumber()
    {
    	String result = MedicalAdministrator.createParticipant(failNumeric, participantFirstNameOne, participantFirstNameOne, participantBirthdateOne, familyPhysicianOne, healthNumberOne, phoneOne, addressOne);
        assertTrue(result.equals("CosmoID must be a number"));
    }
    
    @Test
    public void testfailHealth()
    {
    	String result = MedicalAdministrator.createParticipant(cosmoIDOne, participantFirstNameOne, participantFirstNameOne, participantBirthdateOne, familyPhysicianOne, failNumeric, phoneOne, addressOne);
        assertTrue(result.equals("Health Number must be 9 digits"));
    }

    @Test
    public void testfailPhone()
    {
    	String result = MedicalAdministrator.createParticipant(cosmoIDOne, participantFirstNameOne, participantFirstNameOne, participantBirthdateOne, familyPhysicianOne, healthNumberOne, failNumeric, addressOne);
        assertTrue(result.equals("Phone Number must be 10 digits"));
    }
    
    
    @Test
    public void testCosmoIDExists()
    {
        String result = MedicalAdministrator.createParticipant(cosmoIDOne, participantFirstNameOne, participantFirstNameOne, participantBirthdateOne, familyPhysicianOne, healthNumberOne, phoneOne, addressOne);
        
        //assertTrue(result.equals(""));

        result = MedicalAdministrator.createParticipant(cosmoIDOne, participantFirstNameOne, participantFirstNameOne, participantBirthdateOne, familyPhysicianOne, healthNumberOne, phoneOne, addressOne);
        System.out.println(result);
        assertTrue(result.equals("That CosmoID already exists"));
    }
    
    @After
    public void tearDown()
    {
    }

}
