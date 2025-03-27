import java.util.Scanner;

public class VignereCipher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Vignere Cipher: ");
        System.out.print("Enter the plaintext: ");
        String text = scanner.nextLine().toUpperCase();

        System.out.print("Enter the key: ");
        String key = scanner.nextLine().toUpperCase();

        String ciphertext = encrypt(text, key);

        System.out.println("Encrypted text: "+ciphertext);

        String plaintext = decrypt(ciphertext, key);
        System.out.println("Decrypted text: "+plaintext);

        scanner.close();
    }

    public static String encrypt(String plaintext, String key) {
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0, j = 0 ; i < plaintext.length(); i++) {
            char plainChar = plaintext.charAt(i);

            char keyChar = key.charAt(j % key.length());

            int encryptedChar = Math.floorMod((plainChar - 'A') + (keyChar - 'A'), 26) + 'A';
            stringBuilder.append((char)encryptedChar);
            j++;

        }

        return stringBuilder.toString();
    }

    
    public static String decrypt(String ciphertext, String key) {
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0, j = 0 ; i < ciphertext.length(); i++) {
            char cipherChar = ciphertext.charAt(i);

            char keyChar = key.charAt(j % key.length());

            int decryptedChar = Math.floorMod((cipherChar - 'A') - (keyChar - 'A'), 26) + 'A';
            stringBuilder.append((char)decryptedChar);
            j++;

        }

        return stringBuilder.toString();
    }

}
