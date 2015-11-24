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
        boolean result = test.addUser(usernameOne, lastNameOne, firstNameOne, emailOne,
                firstPasswordOne, failPassword, securityOne);
        assertFalse(result);
    }
    
    @Test
    public void testEmailFormat()
    {
        boolean result = test.addUser(usernameOne, lastNameOne, firstNameOne, failEmail,
                firstPasswordOne, secondPasswordOne, securityOne);
        assertFalse(result);
    }
    
    @Test
    public void testEmptyField()
    {
        boolean result = test.addUser(failEmptyField, lastNameOne, firstNameOne, emailOne,
                firstPasswordOne, secondPasswordOne, securityOne);
        assertFalse(result);
        
        result = test.addUser(usernameOne, failEmptyField, firstNameOne, emailOne,
                firstPasswordOne, secondPasswordOne, securityOne);
        assertFalse(result);
        
        result = test.addUser(usernameOne, lastNameOne, failEmptyField, emailOne,
                firstPasswordOne, secondPasswordOne, securityOne);
        assertFalse(result);
        
        result = test.addUser(usernameOne, lastNameOne, firstNameOne, failEmptyField,
                firstPasswordOne, secondPasswordOne, securityOne);
        assertFalse(result);
        
        result = test.addUser(usernameOne, lastNameOne, firstNameOne, emailOne, failEmptyField,
                secondPasswordOne, securityOne);
        assertFalse(result);
        
        result = test.addUser(usernameOne, lastNameOne, firstNameOne, 
                emailOne, firstPasswordOne, failEmptyField, securityOne);
        assertFalse(result);
        
        result = test.addUser(usernameOne, lastNameOne, firstNameOne, 
                emailOne, firstPasswordOne, secondPasswordOne, failEmptyField);
        assertFalse(result);
    }
    
    

//    @Test
//    public void TestAddUserPass()
//    {
//        boolean result = test.addUser(username, lastName, firstName, email,
//                firstPassword, secondPassword, security);
//        assertTrue(result);
//    }

}
