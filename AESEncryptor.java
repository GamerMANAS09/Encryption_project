package Encryption_project;
import javax.crypto.*;// for Cipher, SecretKey, KeyGenerator etc. (Java cryptographic operations)
import javax.crypto.spec.*;// for SecretKeySpec, GCMParameterSpec etc.
import java.security.*;// for SecureRandom
import java.security.spec.*;// for KeySpec and PBEKeySpec
import java.util.Base64;//for encoding encrypted bytes into string and decoding back

public class AESEncryptor {
    private SecretKey key;// AES key for encryption and decryption

    // Constructor initializes the AES key using PBKDF2 with a fixed salt
    public AESEncryptor(String password) throws Exception {
        byte[] salt = "fixedSalt1234567".getBytes();  // used static salt for my understanding
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256"); // used PBKDF2 with HMAC SHA-256 
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        this.key = new SecretKeySpec(tmp.getEncoded(), "AES"); // this wraps up the temperorary key into AES format 
    }

    // Encrypts the input string using AES in GCM mode
    public String encrypt(String plainText) throws Exception {
        byte[] iv = new byte[12]; // GCM standard
        new SecureRandom().nextBytes(iv);
        GCMParameterSpec spec = new GCMParameterSpec(128, iv);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key, spec);
        byte[] encrypted = cipher.doFinal(plainText.getBytes());

        // Combine IV + CipherText
        byte[] combined = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);

        return Base64.getEncoder().encodeToString(combined);
    }

    // Decrypts the input string using AES in GCM mode
    public String decrypt(String encryptedText) throws Exception {
        byte[] combined = Base64.getDecoder().decode(encryptedText);
        byte[] iv = new byte[12];
        byte[] ciphertext = new byte[combined.length - 12];

        System.arraycopy(combined, 0, iv, 0, 12);
        System.arraycopy(combined, 12, ciphertext, 0, ciphertext.length);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(128, iv));
        byte[] plain = cipher.doFinal(ciphertext);
        return new String(plain);
    }
}
