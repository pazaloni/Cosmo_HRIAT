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

public class EncryptionHelper 
{
    private static final byte[] KEY = {12, -89, -94, -38, 6, 18, -43, 67, -62, 71, -114, -113, 7, 33, -97, 26};
    Cipher cipher;
    SecretKeySpec secretKey;
    
    public EncryptionHelper()
    {
        secretKey = new SecretKeySpec(KEY, "AES");
        try
        {
            cipher = Cipher.getInstance("AES");
        }
        catch ( NoSuchAlgorithmException | NoSuchPaddingException e )
        {
            e.printStackTrace();
        }
    }
    
    public String encrypt(String plainText )
    {
        byte[] plainTextByte = plainText.getBytes();
        try
        {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        }
        catch ( InvalidKeyException e )
        {
            e.printStackTrace();
        }
        byte[] encryptedByte = null;
        try
        {
            encryptedByte = cipher.doFinal(plainTextByte);
        }
        catch ( IllegalBlockSizeException | BadPaddingException e )
        {
            e.printStackTrace();
        }
        Base64.Encoder encoder = Base64.getEncoder();
        String encryptedText = encoder.encodeToString(encryptedByte);
        return encryptedText;
    }
    
    public String decrypt(String  encryptedText )
    {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] encryptedTextByte = decoder.decode(encryptedText);
        try
        {
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
        }
        catch ( InvalidKeyException e )
        {
            e.printStackTrace();
        }
        byte[] decryptedByte = null;
        try
        {
            decryptedByte = cipher.doFinal(encryptedTextByte);
        }
        catch ( IllegalBlockSizeException | BadPaddingException e )
        {
            e.printStackTrace();
        }
        String decryptedText = new String(decryptedByte);
        return decryptedText;
    }
    
    
}
