package tests;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import helpers.DatabaseHelper;
import helpers.EncryptionHelper;
import helpers.ManageStaffAccountHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import core.Participant;
import core.StaffAccount;

/**
 * Purpose: To test the functionality of the EncryptionHelper
 * @author Steven Palchinski cst209
 */
public class TestEncryption
{
    //instance of the encryption helper
    EncryptionHelper eh;
    
    //instances of StaffAccount and ManageStaffAccountHelper
    StaffAccount test;
    ManageStaffAccountHelper testHelper;

    //initial strings
    String plainText1;
    String plainText2;
    String plainText3;
    String plainText4;
    
    //encrypted strings
    String encrypted1;
    String encrypted2;
    String encrypted3;
    String encrypted4;
    
    //decrypted strings
    String decrypted1;
    String decrypted2;
    String decrypted3;
    String decrypted4;
    
    //User strings
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
        
    @Before
    public void setUp() throws Exception
    {
       
        //instantiated EncryptionHelper 
        eh = new EncryptionHelper();
        
        test = new StaffAccount();
        testHelper = new ManageStaffAccountHelper();
        
        //text only string
        plainText1 = "jackhammer";
        //numerical only string
        plainText2 = "1234";
        //blank string
        plainText3 = "";
        //mixed text and numerical string
        plainText4 = "a2s3fvg50c";
        
        //User creation strings
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
       
     }

    /**
     * to test the encryption of various types of strings
     */
    @Test
    public void testEncryptionHelper()
    {
        //encrypt the text only string
        encrypted1 = eh.encrypt(plainText1);
        //assert that the plaintext and encrypted are different 
        assertFalse(plainText1.equals(encrypted1));
        //decrypt the encrypted string
        decrypted1 = eh.decrypt(encrypted1);
        //assert that the decrypted string is the same as the original string
        assertTrue(decrypted1.equals(plainText1));
        
        //encrypt the numerical only string
        encrypted2 = eh.encrypt(plainText2);
        //assert that the plaintext and encrypted are different 
        assertFalse(plainText2.equals(encrypted2));
        //decrypt the encrypted string
        decrypted2 = eh.decrypt(encrypted2);
        //assert that the decrypted string is the same as the original string
        assertTrue(decrypted2.equals(plainText2));
        
        //encrypt the blank string
        encrypted3 = eh.encrypt(plainText3);
        //assert that the plaintext and encrypted are different 
        assertFalse(plainText3.equals(encrypted3));
        //decrypt the encrypted string
        decrypted3 = eh.decrypt(encrypted3);
        //assert that the decrypted string is the same as the original string
        assertTrue(decrypted3.equals(plainText3));
        
        //encrypt the mixed string string
        encrypted4 = eh.encrypt(plainText4);
        //assert that the plaintext and encrypted are different ;
        assertFalse(plainText4.equals(encrypted4));
        //decrypt the encrypted string
        decrypted4 = eh.decrypt(encrypted4);
        //assert that the decrypted string is the same as the original string
        assertTrue(decrypted4.equals(plainText4));
     
        
    }
    
    /**
     * To test that the getPassword works properly to decrypt
     */
    @Test
    public void testGetPassword()
    {
        //add the user to the database
        String results1 = testHelper.addUser(usernameOne, lastNameOne, firstNameOne,
                emailOne, firstPasswordOne, secondPasswordOne, securityOne);
        //compare original plain text password matches the password from the database
        assertTrue(firstPasswordOne.equals(testHelper.queryStaff(usernameOne).GetPassword()));
        
    }
    
    /**
     * To test that a new account will have the password that is encrypted properly
     */
    @Test
    public void createUser()
    {
        //add the new user to the database
        String results2 = testHelper.addUser(usernameTwo, lastNameTwo, firstNameTwo, emailTwo, 
                firstPasswordTwo, secondPasswordTwo, securityTwo);
        //confirm that the password entered matches the decrypted password from the database
        assertTrue(firstPasswordTwo.equals(testHelper.queryStaff(usernameTwo).GetPassword()));
    }
    
    /**
     * remove the created user from the database 
     */
    @After
    public void tearDown()
    {
        testHelper.removeUser(usernameOne);
        testHelper.removeUser(usernameTwo);
    }

}
