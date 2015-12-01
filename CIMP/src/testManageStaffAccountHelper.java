import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class testManageStaffAccountHelper
{
    ManageStaffAccountHelper test;

    private String usernameOne;
    private String firstNameOne;
    private String lastNameOne;
    private String emailOne;
    private String firstPasswordOne;
    private String secondPasswordOne;
    private String securityOne;
    
    private String usernameTwo;
    private String firstNameTwo;
    private String lastNameTwo;
    private String emailTwo;
    private String firstPasswordTwo;
    private String secondPasswordTwo;
    private String securityTwo;

    private String failEmail;
    private String failPassword;
    private String failEmptyField;

    @Before
    public void setUp() throws Exception
    {
        test = new ManageStaffAccountHelper();

        usernameOne = "lebowskyD";
        firstNameOne = "dude";
        lastNameOne = "lebowsky";
        emailOne = "dude@awesome.com";
        firstPasswordOne = "password";
        secondPasswordOne = "password";
        securityOne = "1";
        
        usernameTwo = "snowJ";
        firstNameTwo = "john";
        lastNameTwo = "snow";
        emailTwo = "john@awesome.com";
        firstPasswordTwo = "thrones";
        secondPasswordTwo = "thrones";
        securityTwo = "2";
        
        

        failEmail = "1234";
        failPassword = "random";
        failEmptyField = "";

    }

    @Test
    public void testPasswordSame()
    {
        String result = test.addUser(usernameOne, lastNameOne, firstNameOne, emailOne,
                firstPasswordOne, failPassword, securityOne);
        assertTrue(result.equals("Passwords do not match"));
    }
    
    @Test
    public void testEmailFormat()
    {
        String result = test.addUser(usernameOne, lastNameOne, firstNameOne, failEmail,
                firstPasswordOne, secondPasswordOne, securityOne);
        assertTrue(result.equals("Email is not valid"));
    }
    
    @Test
    public void testEmptyField()
    {
        String result = test.addUser(failEmptyField, lastNameOne, firstNameOne, emailOne,
                firstPasswordOne, secondPasswordOne, securityOne);
        assertTrue(result.equals("One or more of your fields is empty"));
        
        result = test.addUser(usernameOne, failEmptyField, firstNameOne, emailOne,
                firstPasswordOne, secondPasswordOne, securityOne);
        assertTrue(result.equals("One or more of your fields is empty"));
        
        result = test.addUser(usernameOne, lastNameOne, failEmptyField, emailOne,
                firstPasswordOne, secondPasswordOne, securityOne);
        assertTrue(result.equals("One or more of your fields is empty"));
        
        result = test.addUser(usernameOne, lastNameOne, firstNameOne, emailOne, failEmptyField,
                secondPasswordOne, securityOne);
        assertTrue(result.equals("One or more of your fields is empty"));
        
        result = test.addUser(usernameOne, lastNameOne, firstNameOne, 
                emailOne, firstPasswordOne, failEmptyField, securityOne);
        assertTrue(result.equals("One or more of your fields is empty"));
        
        result = test.addUser(usernameOne, lastNameOne, firstNameOne, 
                emailOne, firstPasswordOne, secondPasswordOne, failEmptyField);
        assertTrue(result.equals("One or more of your fields is empty"));
        
        
    }
    
    @Test
    public void testUserNameTaken()
    {
        String result = test.addUser(usernameOne, lastNameOne, firstNameOne, emailOne,
                firstPasswordOne, secondPasswordOne, securityOne);
        assertTrue(result.equals(""));
        
        result = test.addUser(usernameOne, lastNameOne, firstNameOne, emailOne,
                firstPasswordOne, secondPasswordOne, securityOne);
        assertTrue(result.equals("Username is already taken"));
    }

    @Test
    public void TestAddUserPass()
    {
        String result = test.addUser(usernameTwo, lastNameTwo, firstNameTwo, emailTwo,
                firstPasswordTwo, secondPasswordTwo, securityTwo);
        assertTrue(result.equals(""));
    }

}
