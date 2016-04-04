package helpers;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
/**
 * Purpose: To provide all the encryption for the program
 * 
 * @author Steven Palchinski cst209
 */
public class EncryptionHelper 
{
    //The byte array permanently set in order to maintain consistent encrypting/decrypting
    private static final byte[] KEY = {12, -89, -94, -38, 6, 18, -43, 67, -62, 71, -114, -113, 7, 33, -97, 26};
    //instance of the Cipher used for encrypting/decrypting
    Cipher cipher;
    //instance of the SecretKey.  Allows the coder to use a pre-existing byte array 
    SecretKeySpec secretKey;
    
    /**
     * Purpose: Constructor that generates the secret key and the cipher
     * 
     * @author: Steven Palchinski cst209
     */
    public EncryptionHelper()
    {
        //generate the secret key using the passed in byte array and using the AES algorithm
        secretKey = new SecretKeySpec(KEY, "AES");
        try
        {
            //creates a cipher using the AES algorithm
            cipher = Cipher.getInstance("AES");
        }
        catch ( NoSuchAlgorithmException | NoSuchPaddingException e )
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Purpose: Will take in a plain text string and return a encrypted string
     * @param plainText The plain text string to encrypt
     * @return encryptedText the encrypted string 
     * @author Steven Palchinski cst209
     */
    public String encrypt(String plainText )
    {
        //create a byte array from the plain text
        byte[] plainTextByte = plainText.getBytes();
        try
        {
            //initiate the cipher's encrypt mode using the secret key
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        }
        catch ( InvalidKeyException e )
        {
            e.printStackTrace();
        }
        //instantiate the encrypted byte array, initially as null
        byte[] encryptedByte = null;
        try
        {
            //encrypt the plainText byte array
            encryptedByte = cipher.doFinal(plainTextByte);
        }
        catch ( IllegalBlockSizeException | BadPaddingException e )
        {
            e.printStackTrace();
        }
        //create a 64 bit encoder to translate the encrypted byte array to a string
        Base64.Encoder encoder = Base64.getEncoder();
        //encode the byte array to a string 
        String encryptedText = encoder.encodeToString(encryptedByte);
        //return the encrypted string
        return encryptedText;
    }
    
    /**
     * Purpose: Will take in an encrypted string and return a plain text string 
     * @param encryptedText the encrypted string to decrypt
     * @return decryptedText the decrypted string
     * @author Steven Palchinski cst209
     */
    public String decrypt(String  encryptedText )
    {
        //create the 64 bit decoder to translate the encrypted string to a encrypted byte array
        Base64.Decoder decoder = Base64.getDecoder();
        //decode the string to a byte array
        byte[] encryptedTextByte = decoder.decode(encryptedText);
        try
        {
            //initiate the cipher's decrypt mode using the secret key
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
        }
        catch ( InvalidKeyException e )
        {
            e.printStackTrace();
        }
        //instantialize the decrypted byte array, intially to null 
        byte[] decryptedByte = null;
        try
        {
            //decrypt the encrypted byte array
            decryptedByte = cipher.doFinal(encryptedTextByte);
        }
        catch ( IllegalBlockSizeException | BadPaddingException e )
        {
            e.printStackTrace();
        }
        //convert the decrypted byte array to a string
        String decryptedText = new String(decryptedByte);
        //return the string
        return decryptedText;
    } 
}
