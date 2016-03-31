package tests;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import helpers.DatabaseHelper;
import helpers.EncryptionHelper;
import helpers.ManageStaffAccountHelper;

import org.junit.Before;
import org.junit.Test;

import core.Participant;
import core.StaffAccount;

public class TestEncryption
{
    //instance of the encryption helper
    EncryptionHelper eh;

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
        
    @Before
    public void setUp() throws Exception
    {
       
        //instantiated EncryptionHelper 
        eh = new EncryptionHelper();
        
        //text only string
        plainText1 = "jackhammer";
        //numerical only string
        plainText2 = "1234";
        //blank string
        plainText3 = "";
        //mixed text and numerical string
        plainText4 = "a2s3fvg50c";
       
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

}
