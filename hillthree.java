import java.util.Scanner;

public class hillthree {
    public static String alphabet = "abcdefghijklmnopqrstuvwxyz";

    public static String Encryption(char first, char second, char third, int[][] matrix) {
        int num1 = -1, num2 = -1, num3 = -1;
        num1 = alphabet.indexOf(first);
        num2 = alphabet.indexOf(second);
        num3 = alphabet.indexOf(third);

        String ct = "";
        ct = "" + alphabet.charAt(Math.floorMod((num1 * matrix[0][0] + num2 * matrix[1][0] + num3 * matrix[2][0]), 26)) + 
                  alphabet.charAt(Math.floorMod((num1 * matrix[0][1] + num2 * matrix[1][1] + num3 * matrix[2][1]), 26)) + 
                  alphabet.charAt(Math.floorMod((num1 * matrix[0][2] + num2 * matrix[1][2] + num3 * matrix[2][2]), 26));
        return ct;
    }

    public static String Decryption(char first, char second, char third, int[][] matrix) {
        int DET = Math.floorMod(
        matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1]) -
        matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0]) +
        matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]), 26);
        int[][] tempmatrix = new int[3][3];

        for (int i = 0; i < 26; i++) {
            if ((26 * i + 1) % DET == 0) {
                DET = (26 * i + 1) / DET;
                break;
            }
        }

        tempmatrix[0][0] = Math.floorMod((matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1]) * DET, 26);
        tempmatrix[0][1] = Math.floorMod((-1 * (matrix[0][1] * matrix[2][2] - matrix[0][2] * matrix[2][1])) * DET, 26);
        tempmatrix[0][2] = Math.floorMod((matrix[0][1] * matrix[1][2] - matrix[0][2] * matrix[1][1]) * DET, 26);
        tempmatrix[1][0] = Math.floorMod((-1 * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0])) * DET, 26);
        tempmatrix[1][1] = Math.floorMod((matrix[0][0] * matrix[2][2] - matrix[0][2] * matrix[2][0]) * DET, 26);
        tempmatrix[1][2] = Math.floorMod((-1 * (matrix[0][0] * matrix[1][2] - matrix[0][2] * matrix[1][0])) * DET, 26);
        tempmatrix[2][0] = Math.floorMod((matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]) * DET, 26);
        tempmatrix[2][1] = Math.floorMod((-1 * (matrix[0][0] * matrix[2][1] - matrix[0][1] * matrix[2][0])) * DET, 26);
        tempmatrix[2][2] = Math.floorMod((matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]) * DET, 26);

        int[] characterarr = new int[3];
        characterarr[0] = alphabet.indexOf(first);
        characterarr[1] = alphabet.indexOf(second);
        characterarr[2] = alphabet.indexOf(third);
        
        String toreturn = "" +
            alphabet.charAt(Math.floorMod(characterarr[0] * tempmatrix[0][0] + characterarr[1] * tempmatrix[1][0] + characterarr[2] * tempmatrix[2][0], 26)) +
            alphabet.charAt(Math.floorMod(characterarr[0] * tempmatrix[0][1] + characterarr[1] * tempmatrix[1][1] + characterarr[2] * tempmatrix[2][1], 26)) +
            alphabet.charAt(Math.floorMod(characterarr[0] * tempmatrix[0][2] + characterarr[1] * tempmatrix[1][2] + characterarr[2] * tempmatrix[2][2], 26));

        return toreturn;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int[][] matrix = new int[3][3];

        System.out.println("Enter the 3x3 matrix");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrix[i][j] = scan.nextInt();
            }
        }
        scan.nextLine();

        // System.out.println("The 3x3 matrix is: ");
        // for (int i = 0; i < 3; i++) {
        //     for (int j = 0; j < 3; j++) {
        //         System.out.print(matrix[i][j] + " ");
        //     }
        //     System.out.println("");
        // }

        System.out.print("Enter Plaintext: ");
        String pt = scan.nextLine();

        if(pt.length()%3 == 1){
            pt = pt + "xy";
        }
        if(pt.length()%3 == 2){
            pt = pt + "x";
        }
        String encryptedtext = "";
        for (int i = 0; i < pt.length(); i += 3) {
            encryptedtext += Encryption(pt.charAt(i), pt.charAt(i + 1), pt.charAt(i + 2), matrix);
        }
        System.out.println("Cipher text " + encryptedtext);

        String decryptedtext = "";
        for (int i = 0; i < encryptedtext.length(); i += 3) {
            decryptedtext += Decryption(encryptedtext.charAt(i), encryptedtext.charAt(i + 1), encryptedtext.charAt(i + 2), matrix);
        }
        System.out.println("Decrypted text " + decryptedtext);
    }
}