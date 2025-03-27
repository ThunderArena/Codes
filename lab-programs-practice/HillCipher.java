import java.util.*;
public class HillCipher {
    public static String alphabet = "abcdefghijklmnopqrstuvwxyz";

    public static String encr(char first, char second, char third, int[][] matrix) {
        String ct = "";
        int num1 = alphabet.indexOf(first);
        int num2 = alphabet.indexOf(second);
        int num3 = alphabet.indexOf(third);

        ct = "" + alphabet.charAt(Math.floorMod(num1 * matrix[0][0] + num2 * matrix[1][0] + num3 * matrix[2][0], 26)) +
                alphabet.charAt(Math.floorMod(num1 * matrix[0][1] + num2 * matrix[1][1] + num3 * matrix[2][1], 26)) +
                alphabet.charAt(Math.floorMod(num1 * matrix[0][2] + num2 * matrix[1][2] + num3 * matrix[2][2], 26));

        return ct;
    }

    public static String decr(char first, char second, char third, int[][] matrix) {
        String dt = "";
        int num1 = alphabet.indexOf(first);
        int num2 = alphabet.indexOf(second);
        int num3 = alphabet.indexOf(third);

        int DET = Math.floorMod(matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[2][1] * matrix[1][2]) -
                        matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[2][0] * matrix[1][2]) +
                        matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[2][0] * matrix[1][1]), 26);

        for (int i = 0; i < 26; i++) {
            if ((26 * i + 1) % DET == 0) {
                DET = (26 * i + 1) / DET;
                break;
            }
        }

        int[][] tempmatrix = new int[3][3];
        tempmatrix[0][0] = Math.floorMod((matrix[1][1] * matrix[2][2] - matrix[2][1] * matrix[1][2]) * DET, 26);
        tempmatrix[0][1] = Math.floorMod((-1 * (matrix[0][1] * matrix[2][2] - matrix[0][2] * matrix[2][1])) * DET, 26);
        tempmatrix[0][2] = Math.floorMod((matrix[0][1] * matrix[1][2] - matrix[0][2] * matrix[1][1]) * DET, 26);
        tempmatrix[1][0] = Math.floorMod((-1 * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0])) * DET, 26);
        tempmatrix[1][1] = Math.floorMod((matrix[0][0] * matrix[2][2] - matrix[0][2] * matrix[2][0]) * DET, 26);
        tempmatrix[1][2] = Math.floorMod((-1 * (matrix[0][0] * matrix[1][2] - matrix[0][2] * matrix[1][0])) * DET, 26);
        tempmatrix[2][0] = Math.floorMod((matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]) * DET, 26);
        tempmatrix[2][1] = Math.floorMod((-1 * (matrix[0][0] * matrix[2][1] - matrix[0][1] * matrix[2][0])) * DET, 26);
        tempmatrix[2][2] = Math.floorMod((matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]) * DET, 26);

        dt = "" + alphabet.charAt(Math.floorMod(num1 * tempmatrix[0][0] + num2 * tempmatrix[1][0] + num3 * tempmatrix[2][0], 26)) +
                alphabet.charAt(Math.floorMod(num1 * tempmatrix[0][1] + num2 * tempmatrix[1][1] + num3 * tempmatrix[2][1], 26)) +
                alphabet.charAt(Math.floorMod(num1 * tempmatrix[0][2] + num2 * tempmatrix[1][2] + num3 * tempmatrix[2][2], 26));

        return dt;
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

        System.out.println("The 3x3 matrix is: ");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

        System.out.print("Enter Plaintext: ");
        String pt = scan.nextLine().toLowerCase();

        if (pt.length() % 3 == 1) {
            pt = pt + "xy";
        }
        if (pt.length() % 3 == 2) {
            pt = pt + "x";
        }

        String ct = "";
        for (int i = 0; i < pt.length(); i += 3) {
            ct += encr(pt.charAt(i), pt.charAt(i + 1), pt.charAt(i + 2), matrix);
        }
        System.out.println("The cipher text = " + ct);

        String dt = "";
        for (int i = 0; i < ct.length(); i += 3) {
            dt += decr(ct.charAt(i), ct.charAt(i + 1), ct.charAt(i + 2), matrix);
        }
        System.out.println("The decrypted text = " + dt);
    }
}

