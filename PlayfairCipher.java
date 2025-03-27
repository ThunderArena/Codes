import java.util.Scanner;

public class PlayfairCipher {

    static char[][] matrix = new char[5][5];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the key: ");
        String key = scanner.nextLine().toUpperCase();

        System.out.print("Enter the text: ");
        String text = scanner.nextLine().toUpperCase();

        prepareKeyMatrix(key);

        String ciphertext = encrypt(text);
        System.out.println("Ciphertext: " + ciphertext);
         // Decryption (assuming text contains only valid digraphs)
        String plaintext = decrypt(ciphertext);
        System.out.println("Plaintext: " + plaintext);
        
        scanner.close();
    }

    // Prepare the 5x5 matrix with the key and remaining alphabets
    public static void prepareKeyMatrix(String key) {
        boolean[] visited = new boolean[26];
        int k = 0;

        // Add key to the matrix
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (c != 'J' && !visited[c - 'A']) {
                matrix[k / 5][k % 5] = c;
                visited[c - 'A'] = true;
                k++;
            }
        }

        // Add remaining alphabets to the matrix
        for (char c = 'A'; c <= 'Z'; c++) {
            if (c != 'J' && !visited[c - 'A']) {
                matrix[k / 5][k % 5] = c;
                visited[c - 'A'] = true;
                k++;
            }
        }

        // Replace 'J' with 'I' in the matrix
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matrix[i][j] == 'J') {
                    matrix[i][j] = 'I';
                }
            }
        }

        //print the matrix created
        System.out.println("The key matrix is: ");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Encrypt the plaintext
    public static String encrypt(String plaintext) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < plaintext.length(); i += 2) {
            char c1 = plaintext.charAt(i);
            char c2 = (i + 1 < plaintext.length()) ? plaintext.charAt(i + 1) : 'X';

            if (c1 == c2) {
                c2 = 'X';
            }

            sb.append(getDiagraph(c1, c2, false)); // Pass false for encryption
        }

        return sb.toString();
    }

    // Decrypt the ciphertext
    public static String decrypt(String ciphertext) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i += 2) {
            char c1 = ciphertext.charAt(i);
            char c2 = ciphertext.charAt(i + 1);

            sb.append(getDiagraph(c1, c2, true)); // Pass true for decryption
        }

        return sb.toString();
    }

    // Get the encrypted/decrypted digraph (with decryption flag)
    public static String getDiagraph(char c1, char c2, boolean isDecrypt) {
        int row1 = 0, col1 = 0, row2 = 0, col2 = 0;

        // Find positions of c1 and c2 in the matrix
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matrix[i][j] == c1) {
                    row1 = i;
                    col1 = j;
                }
                if (matrix[i][j] == c2) {
                    row2 = i;
                    col2 = j;
                }
            }
        }

        // Apply encryption/decryption rules based on positions
        if (isDecrypt) { // Decryption
            if (row1 == row2) { // Same row
                col1 = (col1 - 1 + 5) % 5; // Ensure non-negative value
                col2 = (col2 - 1 + 5) % 5;
            } else if (col1 == col2) { // Same column
                row1 = (row1 - 1 + 5) % 5;
                row2 = (row2 - 1 + 5) % 5;
            } else { // Different row and column
                int temp = col1;
                col1 = col2;
                col2 = temp;
            }
        } else { // Encryption
            if (row1 == row2) { // Same row
                col1 = (col1 + 1) % 5;
                col2 = (col2 + 1) % 5;
            } else if (col1 == col2) { // Same column
                row1 = (row1 + 1) % 5;
                row2 = (row2 + 1) % 5;
            } else { // Different row and column
                int temp = col1;
                col1 = col2;
                col2 = temp;
            }
        }

        char c3 = matrix[row1][col1];
        char c4 = matrix[row2][col2];

        return "" + c3 + c4;
    }
}