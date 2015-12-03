import static org.junit.Assert.*;

import org.junit.After;
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
    
    private String usernameThree;
    private String firstNameThree;
    private String lastNameThree;
    private String emailThree;
    private String firstPasswordThree;
    private String secondPasswordThree;
    private String securityThree;

    private String failEmailWithoutBoth;
    private String failEmailWithoutAt;
    private String failEmailWithoutPeriod;
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
        
        usernameThree = "sallyJ";
        firstNameThree = "Sally";
        lastNameThree = "Jones";
        emailThree = "sally@Jones.com";
        firstPasswordThree = "sillySally";
        secondPasswordThree = "sillySally";
        securityThree = "0";

        failEmailWithoutBoth = "1234";
        failEmailWithoutAt = "hello.hello";
        failEmailWithoutPeriod = "1234@1234";
        failPassword = "random";
        failEmptyField = "";

    }

    @Test
    public void testAddUserPasswordSame()
    {
        String result = test.addUser(usernameOne, lastNameOne, firstNameOne,
                emailOne, firstPasswordOne, failPassword, securityOne);
        assertTrue(result.equals("Passwords do not match"));
    }

    @Test
    public void testAddUserEmailFormat()
    {
        String result = test.addUser(usernameOne, lastNameOne, firstNameOne,
                failEmailWithoutBoth, firstPasswordOne, secondPasswordOne,
                securityOne);
        assertTrue(result.equals("Email is not valid"));

        result = test.addUser(usernameOne, lastNameOne, firstNameOne,
                failEmailWithoutAt, firstPasswordOne, secondPasswordOne,
                securityOne);
        assertTrue(result.equals("Email is not valid"));

        result = test.addUser(usernameOne, lastNameOne, firstNameOne,
                failEmailWithoutPeriod, firstPasswordOne, secondPasswordOne,
                securityOne);
        assertTrue(result.equals("Email is not valid"));
    }

    @Test
    public void testAddUserEmptyField()
    {
        String result = test.addUser(failEmptyField, lastNameOne, firstNameOne,
                emailOne, firstPasswordOne, secondPasswordOne, securityOne);
        assertTrue(result.equals("One or more of your fields is empty"));

        result = test.addUser(usernameOne, failEmptyField, firstNameOne,
                emailOne, firstPasswordOne, secondPasswordOne, securityOne);
        assertTrue(result.equals("One or more of your fields is empty"));

        result = test.addUser(usernameOne, lastNameOne, failEmptyField,
                emailOne, firstPasswordOne, secondPasswordOne, securityOne);
        assertTrue(result.equals("One or more of your fields is empty"));

        result = test.addUser(usernameOne, lastNameOne, firstNameOne, emailOne,
                failEmptyField, secondPasswordOne, securityOne);
        assertTrue(result.equals("One or more of your fields is empty"));

        result = test.addUser(usernameOne, lastNameOne, firstNameOne, emailOne,
                firstPasswordOne, failEmptyField, securityOne);
        assertTrue(result.equals("One or more of your fields is empty"));

        result = test.addUser(usernameOne, lastNameOne, firstNameOne, emailOne,
                firstPasswordOne, secondPasswordOne, failEmptyField);
        assertTrue(result.equals("One or more of your fields is empty"));

    }

    @Test
    public void testAddUserUserNameTaken()
    {
        String result = test.addUser(usernameOne, lastNameOne, firstNameOne,
                emailOne, firstPasswordOne, secondPasswordOne, securityOne);
        assertTrue(result.equals(""));

        result = test.addUser(usernameOne, lastNameOne, firstNameOne, emailOne,
                firstPasswordOne, secondPasswordOne, securityOne);
        assertTrue(result.equals("Username is already taken"));
    }

    @Test
    public void TestAddUserPass()
    {
        String result = test.addUser(usernameTwo, lastNameTwo, firstNameTwo,
                emailTwo, firstPasswordTwo, secondPasswordTwo, securityTwo);
        assertTrue(result.equals(""));
    }
    
    @Test
    public void testEditUserPasswordSame()
    {
        String result = test.editUser(usernameOne, lastNameOne, firstNameOne,
                emailOne, firstPasswordOne, failPassword, securityOne);
        assertTrue(result.equals("Passwords do not match"));
    }

    @Test
    public void testEditUserEmailFormat()
    {
        String result = test.editUser(usernameOne, lastNameOne, firstNameOne,
                failEmailWithoutBoth, firstPasswordOne, secondPasswordOne,
                securityOne);
        assertTrue(result.equals("Email is not valid"));

        result = test.editUser(usernameOne, lastNameOne, firstNameOne,
                failEmailWithoutAt, firstPasswordOne, secondPasswordOne,
                securityOne);
        assertTrue(result.equals("Email is not valid"));

        result = test.editUser(usernameOne, lastNameOne, firstNameOne,
                failEmailWithoutPeriod, firstPasswordOne, secondPasswordOne,
                securityOne);
        assertTrue(result.equals("Email is not valid"));
    }

    @Test
    public void testEditUserEmptyField()
    {
        String result = test.editUser(failEmptyField, lastNameOne, firstNameOne,
                emailOne, firstPasswordOne, secondPasswordOne, securityOne);
        assertTrue(result.equals("One or more of your fields is empty"));

        result = test.editUser(usernameOne, failEmptyField, firstNameOne,
                emailOne, firstPasswordOne, secondPasswordOne, securityOne);
        assertTrue(result.equals("One or more of your fields is empty"));

        result = test.editUser(usernameOne, lastNameOne, failEmptyField,
                emailOne, firstPasswordOne, secondPasswordOne, securityOne);
        assertTrue(result.equals("One or more of your fields is empty"));

        result = test.editUser(usernameOne, lastNameOne, firstNameOne, emailOne,
                failEmptyField, secondPasswordOne, securityOne);
        assertTrue(result.equals("One or more of your fields is empty"));

        result = test.editUser(usernameOne, lastNameOne, firstNameOne, emailOne,
                firstPasswordOne, failEmptyField, securityOne);
        assertTrue(result.equals("One or more of your fields is empty"));

        result = test.editUser(usernameOne, lastNameOne, firstNameOne, emailOne,
                firstPasswordOne, secondPasswordOne, failEmptyField);
        assertTrue(result.equals("One or more of your fields is empty"));

    }


    @Test
    public void TestEditUserPass()
    {
        String result = test.addUser(usernameThree, lastNameThree, 
                firstNameThree, emailThree, firstPasswordThree, 
                secondPasswordThree, securityThree);
        assertTrue(result.equals(""));
        
        result = test.editUser(usernameThree, lastNameTwo, firstNameThree,
                emailThree, firstPasswordThree, secondPasswordThree, 
                securityThree);
        assertTrue(result.equals(""));
    }

    @After
    public void tearDown()
    {
        test.removeUser(usernameOne);
        test.removeUser(usernameTwo);
        test.removeUser(usernameThree);
    }

}
