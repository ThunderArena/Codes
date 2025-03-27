public class SimplifiedDES {
    private static final int[] P10 = {3, 5, 2, 7, 4, 10, 1, 9, 8, 6};
    private static final int[] P8 = {6, 3, 7, 4, 8, 5, 10, 9};
    private static final int[] IP = {2, 6, 3, 1, 4, 8, 5, 7};
    private static final int[] IP_INV = {4, 1, 3, 5, 7, 2, 8, 6};
    private static final int[] EP = {4, 1, 2, 3, 2, 3, 4, 1};
    private static final int[] P4 = {2, 4, 3, 1};
    
    private static final int[][] SBOX1 = {
        {1, 0, 3, 2},
        {3, 2, 1, 0},
        {0, 2, 1, 3},
        {3, 1, 3, 2}
    };
    
    private static final int[][] SBOX2 = {
        {0, 1, 2, 3},
        {2, 0, 1, 3},
        {3, 0, 1, 0},
        {2, 1, 0, 3}
    };
    
    private static String permute(String input, int[] permTable) {
        StringBuilder output = new StringBuilder();
        for (int pos : permTable) {
            output.append(input.charAt(pos - 1));
        }
        return output.toString();
    }
    
    private static String leftShift(String input, int shifts) {
        return input.substring(shifts) + input.substring(0, shifts);
    }
    
    private static String xor(String a, String b) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < a.length(); i++) {
            result.append(a.charAt(i) ^ b.charAt(i));
        }
        return result.toString();
    }
    
    private static String sBox(String input, int[][] sBox) {
        int row = Integer.parseInt("" + input.charAt(0) + input.charAt(3), 2);
        int col = Integer.parseInt("" + input.charAt(1) + input.charAt(2), 2);
        int value = sBox[row][col];
        return String.format("%2s", Integer.toBinaryString(value)).replace(' ', '0');
    }
    
    private static String fFunction(String right, String key) {
        String expanded = permute(right, EP);
        String xored = xor(expanded, key);
        String sBoxOutput = sBox(xored.substring(0, 4), SBOX1) + sBox(xored.substring(4), SBOX2);
        return permute(sBoxOutput, P4);
    }
    
    private static String[] generateKeys(String key) {
        String permutedKey = permute(key, P10);
        String left = permutedKey.substring(0, 5);
        String right = permutedKey.substring(5);
        
        left = leftShift(left, 1);
        right = leftShift(right, 1);
        String key1 = permute(left + right, P8);
        
        left = leftShift(left, 2);
        right = leftShift(right, 2);
        String key2 = permute(left + right, P8);
        
        return new String[]{key1, key2};
    }
    
    public static String encrypt(String plaintext, String key) {
        String[] keys = generateKeys(key);
        String permutedText = permute(plaintext, IP);
        
        String left = permutedText.substring(0, 4);
        String right = permutedText.substring(4);
        
        String fOutput = fFunction(right, keys[0]);
        left = xor(left, fOutput);
        
        String swapped = right + left;
        
        fOutput = fFunction(swapped.substring(4), keys[1]);
        left = xor(swapped.substring(0, 4), fOutput);
        right = swapped.substring(4);
        
        return permute(left + right, IP_INV);
    }
    
    public static void main(String[] args) {
        String plaintext = "10101010";
        String key = "1010000010";
        
        String ciphertext = encrypt(plaintext, key);
        System.out.println("Ciphertext: " + ciphertext);
    }
}
