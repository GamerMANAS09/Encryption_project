package Encryption_project;
// for testing the HybridCipher class which combines custom and AES encryption

public class TestHybrid {
    public static void main(String[] args) throws Exception {


        // Scanner scanner = new Scanner(System.in);


        HybridCipher cipher = new HybridCipher("ManasKey123");

        // System.out.println("Enter a string to encrypt:");
        // String plainText = scanner.nextLine();

        String plainText = "This is a secret message.";
        String b = "This is a secret message.";

        String encrypted = cipher.encrypt(plainText);
        String encryptedb = cipher.encrypt(b);
        String decrypted = cipher.decrypt(encrypted);
        String decryptedb = cipher.decrypt(encryptedb);

        System.out.println("Original : " + plainText);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Encrypted: " + encryptedb);
        System.out.println("Decrypted: " + decrypted);
        System.out.println("Decrypted: " + decryptedb);
    }
}
