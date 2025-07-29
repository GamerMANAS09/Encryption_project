package Encryption_project;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CustomEncryptor {

    private String key; // key used for all encryption and decryprtion steps

    // used hashmaps for substitution and reverse substitution
    private Map<Character, Character> substitutionMap = new HashMap<>(); // 
    private Map<Character, Character> reverseSubstitutionMap = new HashMap<>();

    // Saves the key and generates onetime character mappings using the key based shuffling
    // This is a simple custom encryption algorithm that uses substitution, XOR, and block shuffling
    public CustomEncryptor(String key) {
        this.key = key;
        generateSubstitutionTable();
    }

    // Builds a consistent but random looking substitution mapping based on the key
    // This ensures that the same key always produces the same substitution mapping 
    private void generateSubstitutionTable() {
        List<Character> baseChars = new ArrayList<>();
        for (char c = 32; c <= 126; c++) {
            baseChars.add(c); // printable ASCII
        }

        List<Character> shuffled = new ArrayList<>(baseChars);
        long seed = key.hashCode();
        Collections.shuffle(shuffled, new Random(seed));

        for (int i = 0; i < baseChars.size(); i++) {
            substitutionMap.put(baseChars.get(i), shuffled.get(i));
            reverseSubstitutionMap.put(shuffled.get(i), baseChars.get(i));
        }
    }

    public String encrypt(String plainText) {
        // Step 1: Substitution
        StringBuilder substituted = new StringBuilder();
        for (char c : plainText.toCharArray()) {
            substituted.append(substitutionMap.getOrDefault(c, c));
        }

        // Step 2: XOR
        String xored = dynamicShiftXOR(substituted.toString());

        // Step 3: Block Shuffle
        String shuffled = blockShuffle(xored);

        // Step 4: Base64
        return Base64.getEncoder().encodeToString(shuffled.getBytes(StandardCharsets.UTF_8));
    }

    public String decrypt(String cipherText) {
        // Step 1: Base64 decode
        String decoded = new String(Base64.getDecoder().decode(cipherText), StandardCharsets.UTF_8);

        // Step 2: Reverse block shuffle
        String unshuffled = reverseBlockShuffle(decoded);

        // Step 3: Reverse XOR
        String unxored = reverseDynamicShiftXOR(unshuffled);

        // Step 4: Reverse substitution
        StringBuilder original = new StringBuilder();
        for (char c : unxored.toCharArray()) {
            original.append(reverseSubstitutionMap.getOrDefault(c, c));
        }

        return original.toString();
    }

  
    private String dynamicShiftXOR(String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char dataChar = input.charAt(i);
            char keyChar = key.charAt(i % key.length());

            int shifted = (dataChar + (keyChar % 7)) % 256; // Shifts using (keyChar % 7)
            int xor = shifted ^ keyChar;
            sb.append((char) xor);
        }
        return sb.toString();
    }

      // Reverses the XOR logic 
    private String reverseDynamicShiftXOR(String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char cipherChar = input.charAt(i);
            char keyChar = key.charAt(i % key.length());

            int xor = cipherChar ^ keyChar;
            int shiftedBack = (xor - (keyChar % 7)) & 0xFF; // Subtracts shift (wraps with & 0xFF for byte range safety)

            if (shiftedBack < 0) {
                shiftedBack += 256; // Ensure non-negative value
            }

            sb.append((char) shiftedBack);
        }
        return sb.toString();
    }

    // Shuffles blocks of characters based on the key
    // This adds an additional layer of complexity to the encryption
    private String blockShuffle(String input) {
        int blockSize = key.length();
        StringBuilder result = new StringBuilder();
        Random rand = new Random(key.hashCode());

        for (int i = 0; i < input.length(); i += blockSize) {
            int end = Math.min(i + blockSize, input.length());
            char[] block = input.substring(i, end).toCharArray();

            List<Integer> indices = new ArrayList<>();
            for (int j = 0; j < block.length; j++) indices.add(j);// Create a list of indices for the block
            Collections.shuffle(indices, rand);

            char[] shuffled = new char[block.length];
            for (int j = 0; j < block.length; j++) {
                shuffled[j] = block[indices.get(j)];
            }
            result.append(shuffled);
        }
        return result.toString();
    }

    // repeats same shuffle logic to reconstruct original block positions
    // This is the reverse of the block shuffle
    // It uses the same key to ensure that the original block positions are restored
    private String reverseBlockShuffle(String input) {
        int blockSize = key.length();
        StringBuilder result = new StringBuilder();
        Random rand = new Random(key.hashCode());

        for (int i = 0; i < input.length(); i += blockSize) {
            int end = Math.min(i + blockSize, input.length());
            char[] block = input.substring(i, end).toCharArray();// Extract the block of characters

            List<Integer> indices = new ArrayList<>();
            for (int j = 0; j < block.length; j++) indices.add(j);

            List<Integer> shuffledIndices = new ArrayList<>(indices);// Create a copy of indices to shuffle
            Collections.shuffle(shuffledIndices, rand);

            char[] originalBlock = new char[block.length];
            for (int j = 0; j < block.length; j++) {
                originalBlock[shuffledIndices.get(j)] = block[j];// Place each character back to its original position
            }

            result.append(originalBlock);
        }
        return result.toString();
    }
}
