import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TestStaffAccount
{
    StaffAccount test;
    ManageStaffAccountHelper testHelper;

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
    
    private String usernameThree;
    private String firstNameThree;
    private String lastNameThree;
    private String emailThree;
    private String firstPasswordThree;
    private String secondPasswordThree;
    private String securityThree;

    private String failUsername;
    private String failPassword;
    private String failEmptyField;
    
    @Before
    public void setUp() throws Exception
    {
        test = new StaffAccount();
        testHelper = new ManageStaffAccountHelper();

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
        
        usernameThree = "sallyJ";
        firstNameThree = "Sally";
        lastNameThree = "Jones";
        emailThree = "sally@Jones.com";
        firstPasswordThree = "sillySally";
        secondPasswordThree = "sillySally";
        securityThree = "0";

        String result = testHelper.addUser(usernameThree, lastNameThree, 
                firstNameThree, emailThree, firstPasswordThree, 
                secondPasswordThree, securityThree);
        
       result = testHelper.addUser(usernameOne, lastNameOne, firstNameOne,
                emailOne, firstPasswordOne, secondPasswordOne, securityOne);
       
       result = testHelper.addUser(usernameTwo, lastNameTwo, firstNameTwo,
               emailTwo, firstPasswordTwo, secondPasswordTwo, securityTwo);
        
        failPassword = "random";
        failEmptyField = "";
        failUsername = "George";
    }

    

    @Test
    public void testBasicLogin()
    {

        StaffAccount user = test.login(usernameThree, firstPasswordThree);
        assertTrue(user.getClass().getName().equals("BasicStaff"));
    }
    
    @Test
    public void testMedicalAdminLogin()
    {
        
        StaffAccount user = test.login(usernameOne, firstPasswordOne);
        assertTrue(user.getClass().getName().equals("MedicalAdministrator"));
    }
    
    @Test
    public void testTechAdminLogin()
    {
        
        StaffAccount user = test.login(usernameTwo, firstPasswordTwo);
        assertTrue(user.getClass().getName().equals("TechnicalAdministrator"));
    }
    
    @Test
    public void testWrongUserName()
    {
        
        StaffAccount user = test.login(failUsername, firstPasswordThree);
        assertTrue(user == null);
    }
    
    @Test
    public void testWrongPassword()
    {
        
        StaffAccount user = test.login(usernameOne, failPassword);
        assertTrue(user == null);
    }
    
    @Test
    public void testEmptyUserName()
    {
        
        StaffAccount user = test.login(failEmptyField, firstPasswordThree);
        assertTrue(user == null);
    }
    
    @Test
    public void testEmptyPassword()
    {
        
        StaffAccount user = test.login(usernameOne, failEmptyField);
        assertTrue(user == null);
    }
    
    @Test
    public void testBothEmptyFields()
    {
        
        StaffAccount user = test.login(failEmptyField, failEmptyField);
        assertTrue(user == null);
    }
    
    
    
    @After
    public void tearDown() throws Exception
    {
        testHelper.removeUser(usernameOne);
        testHelper.removeUser(usernameTwo);
        testHelper.removeUser(usernameThree);
    }

}
