package Encryption_project;
public class HybridCipher {

    private CustomEncryptor custom;// an instance of CustomEncryptor class wich handles the custom encryption
    private AESEncryptor aes;// an instance of AESEncryptor class which handles the AES encryption


    // Constructor initializes both custom and AES encryptors with the provided key
    public HybridCipher(String key) throws Exception {
        this.custom = new CustomEncryptor(key);
        this.aes = new AESEncryptor(key);
    }

    // Encrypts the input string using a hybrid approach:
    public String encrypt(String input) throws Exception {
        String stage1 = custom.encrypt(input);
        return aes.encrypt(stage1);
    }

    // Decrypts the input string using a hybrid approach:
    public String decrypt(String encrypted) throws Exception {
        String stage1 = aes.decrypt(encrypted);
        return custom.decrypt(stage1);
    }
}
