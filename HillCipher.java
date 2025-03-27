import java.util.Scanner;

public class HillCipher {
    static int[][] keyMatrix = new int[3][3];
    
    public static void getKeyMatrix(Scanner scanner) {
        System.out.println("Enter 3x3 key matrix (integers):");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                keyMatrix[i][j] = scanner.nextInt();
            }
        }
        System.out.println("Key Matrix:");
        printMatrix(keyMatrix);
    }
    
    public static int[][] calculateAdjugate(int[][] matrix) {
        int[][] adjugate = new int[3][3];
        int sign;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sign = ((i + j) % 2 == 0) ? 1 : -1;
                adjugate[j][i] = sign * calculateMinor(matrix, i, j); 
            }
        }
        System.out.println("Adjugate Matrix:");
        printMatrix(adjugate);
        return adjugate;
    }
    
    public static int calculateMinor(int[][] matrix, int row, int col) {
        int[] temp = new int[4];
        int index = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i != row && j != col) {
                    temp[index++] = matrix[i][j];
                }
            }
        }
        return temp[0] * temp[3] - temp[1] * temp[2];
    }
    
    public static int calculateDeterminant(int[][] matrix) {
        int determinant = matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1])
             - matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0])
             + matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]);
        System.out.println("Determinant: " + determinant);
        return determinant;
    }
    
    public static int[][] calculateInverse(int[][] matrix) {
        int determinant = calculateDeterminant(matrix) % 26;
        if (determinant < 0) determinant += 26;
        System.out.println("Determinant: "+determinant);
        
        int inverseDet = -1;
        for (int i = 0; i < 26; i++) {
            if ((determinant * i) % 26 == 1) {
                inverseDet = i;
                break;
            }
        }
        
        int[][] adjugate = calculateAdjugate(matrix);
        int[][] inverseMatrix = new int[3][3];
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                inverseMatrix[i][j] = (adjugate[i][j] * inverseDet) % 26;
                if (inverseMatrix[i][j] < 0) inverseMatrix[i][j] += 26;
            }
        }
        System.out.println("Inverse Key Matrix:");
        printMatrix(inverseMatrix);
        return inverseMatrix;
    }
    
    public static String encrypt(String message) {
        System.out.println("Plaintext: " + message);
        StringBuilder cipherText = new StringBuilder();
        for (int i = 0; i < message.length(); i += 3) {
            int[] messageVector = new int[3];
            int[] cipherVector = new int[3];
            
            for (int j = 0; j < 3; j++) {
                if (i + j < message.length()) {
                    messageVector[j] = message.charAt(i + j) - 'A';
                } else {
                    messageVector[j] = 0;
                }
            }
            
            for (int x = 0; x < 3; x++) {
                cipherVector[x] = 0;
                for (int y = 0; y < 3; y++) {
                    cipherVector[x] += keyMatrix[x][y] * messageVector[y];
                }
                cipherVector[x] %= 26;
            }
            
            for (int j = 0; j < 3; j++) {
                cipherText.append((char) (cipherVector[j] + 'A'));
            }
        }
        System.out.println("Ciphertext: " + cipherText.toString());
        return cipherText.toString();
    }
    
    public static String decrypt(String cipherText, int[][] inverseMatrix) {
        StringBuilder plainText = new StringBuilder();
        for (int i = 0; i < cipherText.length(); i += 3) {
            int[] cipherVector = new int[3];
            int[] messageVector = new int[3];
            
            for (int j = 0; j < 3; j++) {
                cipherVector[j] = cipherText.charAt(i + j) - 'A';
            }
            
            for (int x = 0; x < 3; x++) {
                messageVector[x] = 0;
                for (int y = 0; y < 3; y++) {
                    messageVector[x] += inverseMatrix[x][y] * cipherVector[y];
                }
                messageVector[x] %= 26;
            }
            
            for (int j = 0; j < 3; j++) {
                plainText.append((char) (messageVector[j] + 'A'));
            }
        }
        System.out.println("Decrypted Text: " + plainText.toString());
        return plainText.toString();
    }
    
    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        getKeyMatrix(scanner);
        
        System.out.print("Enter the plaintext (uppercase): ");
        String message = scanner.next().toUpperCase();
        
        int[][] inverseMatrix = calculateInverse(keyMatrix);
        
        String encryptedText = encrypt(message);
        String decryptedText = decrypt(encryptedText, inverseMatrix);
        
        scanner.close();
    }
}
