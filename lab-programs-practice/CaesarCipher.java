import java.util.Scanner;

public class CaesarCipher {
    private static String letters = "abcdefghijklmnopqrstuvwxyz";
    
    private static String encrypt(String text, int shifts) {
        StringBuilder ciphertext = new StringBuilder();

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                int index = letters.indexOf(c);
                int newIndex = Math.floorMod(index + shifts, 26);

                ciphertext.append(letters.charAt(newIndex));
            }
        }

        return ciphertext.toString();
    }

    private static String decrypt(String text, int shifts) {
        StringBuilder plaintext = new StringBuilder();

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                int index = letters.indexOf(c);
                int newIndex = Math.floorMod(index - shifts, 26);

                plaintext.append(letters.charAt(newIndex));
            }
        }

        return plaintext.toString();
    }
    public static void main(String[] args) {
        System.out.println("Caesar Cipher: ");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the plaintext: ");
        String text = scanner.nextLine().toLowerCase();

        System.out.println("Enter the number of shifts (0 - 25)");
        int shift = scanner.nextInt();

        String ciphertext = encrypt(text, shift);
        System.out.println("Ciphertext: "+ciphertext);

        String plaintext = decrypt(ciphertext, shift);
        System.out.println("Plaintext: "+plaintext);
        scanner.close();
    }
}
