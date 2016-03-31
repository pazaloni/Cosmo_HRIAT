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

    EncryptionHelper eh;

    String plainText1;
    String plainText2;
    String plainText3;
    String plainText4;
    
    String encrypted1;
    String encrypted2;
    String encrypted3;
    String encrypted4;
    
    String decrypted1;
    String decrypted2;
    String decrypted3;
    String decrypted4;
        
    @Before
    public void setUp() throws Exception
    {
       
        eh = new EncryptionHelper();
        
        plainText1 = "jackhammer";
        plainText2 = "1234";
        plainText3 = " ";
        plainText4 = "a2s3fvg50c";
       
     }

    @Test
    public void testEncryptionHelper()
    {
        encrypted1 = eh.encrypt(plainText1);
        assertFalse(plainText1.equals(encrypted1));
        decrypted1 = eh.decrypt(encrypted1);
        assertTrue(decrypted1.equals(plainText1));
        
        encrypted2 = eh.encrypt(plainText2);
        assertFalse(plainText2.equals(encrypted2));
        decrypted2 = eh.decrypt(encrypted2);
        assertTrue(decrypted2.equals(plainText2));
        
        encrypted3 = eh.encrypt(plainText3);
        assertFalse(plainText3.equals(encrypted3));
        decrypted3 = eh.decrypt(encrypted3);
        assertTrue(decrypted3.equals(plainText3));
        
        encrypted4 = eh.encrypt(plainText4);
        assertFalse(plainText4.equals(encrypted4));
        assertFalse(plainText4.equals(encrypted4));
        decrypted4 = eh.decrypt(encrypted4);
        assertTrue(decrypted4.equals(plainText4));
     
        
    }

}
