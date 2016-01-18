import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TestBasicStaff
{
    private String cosmoID;
    private String participantfirstName;
    private String participantLastName;
    private LocalDate birthDate;
    private String physicianFName;
    private String physicianLName;
    private String participantHealthNumber;
    private String participantPhone;
    private String participantAddress;
    
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String firstPassword;
    private String secondPassword;
    private String security;
    
    
    
    private BasicStaff test;

    @Before
    public void setUp() throws Exception
    {
        username = "lebowskyD";
        firstName = "dude";
        lastName = "lebowsky";
        email = "dude@awesome.com";
        firstPassword = "password";
        secondPassword = "password";
        security = "1";
        
        ManageStaffAccountHelper testManager = new ManageStaffAccountHelper();
        
      //  testManager.addUser(username, lastName, firstName, email, password, repeatPW, securityLv);
        
       // test = new BasicStaff(username, firstName, lastName, email, firstPassword, security );
        

        cosmoID = "1111";
        participantfirstName = "John";
        participantLastName = "Smith";
        birthDate = LocalDate.of(1990, 1, 12);
        physicianFName = "John";
        physicianLName = "Doctor";
        participantHealthNumber = "123123123";
        participantPhone = "1231231231";
        participantAddress = "123 Fake Street";

    }
    /**
     * st041
     * 
     * Purpose:Tests to see if the search function is working
     * 
     * @throws Exception
     */

    /**
     * Test name searches
     * 
     * Purpose:
     */
    @Test
    public void testSearchName()
    {
     MedicalAdministrator.createParticipant(cosmoID, participantfirstName, participantLastName, 
             birthDate, physicianFName, physicianLName, participantHealthNumber, 
             participantPhone, participantAddress);
     
      
      
        
    }
    @After
    public void tearDown() throws Exception
    {
    }




}
