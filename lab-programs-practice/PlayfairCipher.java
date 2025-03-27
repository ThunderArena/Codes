import java.util.Scanner;

public class PlayfairCipher {
    public static char keyMat[][] = new char[5][5];
    public static void main(String[] args) {
        System.out.println("Playfair Cipher: ");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter plaintext: ");
        String text = scanner.nextLine().toUpperCase();

        System.out.println("Enter key: ");
        String key = scanner.nextLine().toUpperCase();
        prepareKeyMatrix(key);

        String encryptedText = encrypt(text);
        System.out.println("Encrypted text: "+ encryptedText);

        String decryptedCipher = decrypt(encryptedText);
        System.out.println("Decrypted Text: " + decryptedCipher);

        scanner.close();
        
    }

    public static void prepareKeyMatrix(String key) {
        boolean visited[] = new boolean[26]; //array to keep track of visited letters
        int k = 0; //variable to insert elements to array

        for(int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if(c != 'J' && !visited[c - 'A']) {
                keyMat[k / 5][k % 5] = c;
                visited[c - 'A'] = true;
                k++;
            }
        }

        //add remaining 
        for (char c = 'A'; c <= 'Z'; c++) {
            if(c != 'J' && !visited[c - 'A']) {
                keyMat[k / 5][k % 5] = c;
                visited[c-'A'] = true;
                k++;
            }
        }

        //replace j with i
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (keyMat[i][j] == 'J') {
                    keyMat[i][j] = 'I';
                }
            }
        }

        //print the matrix
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(keyMat[i][j] + " ");
            }
            System.out.println();
        }

    }

    public static String encrypt(String plaintext) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i+= 2) {
          char c1 = plaintext.charAt(i);
          char c2 = (i + 1 < plaintext.length()) ? plaintext.charAt(i + 1) : 'X';

          if(c1 == c2)
            c2 = 'X';

          sb.append(getDiagraph(c1, c2, false));
            
        }
        return sb.toString();
    }

    public static String decrypt(String ciphertext) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i+= 2) {
          char c1 = ciphertext.charAt(i);
          char c2 = ciphertext.charAt(i + 1);

          sb.append(getDiagraph(c1, c2, true));
            
        }
        return sb.toString();
    }


    public static String getDiagraph(char c1, char c2, boolean isDecrypt) {
        int row1 = 0, row2 = 0, col1 = 0, col2 = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if(keyMat[i][j] == c1) {
                    row1 = i;
                    col1 = j;
                }
                if(keyMat[i][j] == c2) {
                    row2 = i;
                    col2 = j;
                }
            }
            
        }

        if(isDecrypt) {
            if(row1 == row2) {
                col1 = Math.floorMod(col1 - 1, 5);
                col2 = Math.floorMod(col2 - 1, 5);
            }
            else if(col1 == col2) {
                row1 = Math.floorMod(row1 - 1, 5);
                row2 = Math.floorMod(row2 - 1, 5);
            } else {
                int temp = col1;
                col1 = col2;
                col2 = temp;
            }
        }else {
            if(row1 == row2) {
                col1 = (col1+1) % 5;
                col2 = (col2 + 1) % 5;
            } else if(col1 == col2) {
                row1 = (row1 + 1) % 5;
                row2 = (row2 + 1) % 5;
            } else {
                int temp = col1;
                col1 = col2;
                col2 = temp;
            }
        }

        char c3 = keyMat[row1][col1];
        char c4 = keyMat[row2][col2];

        return ""+c3+c4;
    }
}
