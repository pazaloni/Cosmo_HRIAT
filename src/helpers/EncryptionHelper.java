package helpers;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionHelper
{
    private static byte[] iv = {0x0a, 0x01, 0x02, 0x03, 0x0b, 0x0c, 0x0d};
    
    
    public byte[] encryptPassword(byte[] inpBytes, SecretKey key, String plaintext) throws Exception
    {
        Cipher cipher = Cipher.getInstance(plaintext);
        IvParameterSpec ips = new IvParameterSpec(iv);
        cipher.init(cipher.DECRYPT_MODE, key, ips);
        return cipher.doFinal(inpBytes);
    }
    
    public String decryptPassword(String encrypted)
    {
        String decrypted = "";
        return decrypted;
    }
}
