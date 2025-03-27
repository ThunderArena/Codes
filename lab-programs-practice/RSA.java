import java.util.Scanner;

public class RSA {

    public static int p,q,n,phi,m;

    public static int gcd(int a, int b) {
        if(a == 0)
            return b;

        return gcd(b % a, a);
    }

    public static int modPower(int base, int exponent, int modulus) {
        int result = 1;
        for (int i = 0; i < exponent; i++) {
            result = (result * base) % modulus;
        }
        return result;
    }

    public static int findPublicKey(int phi) {
        for(int e = 2; e < phi; e++) {
            if(gcd(e, phi) == 1)
                return e;
        }
        return -1;
    }

    public static int findPrivateKey(int e, int phi) {
        for (int d = 1; d < phi; d++) {
            if ((d * e) % phi == 1)
                return d;
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println("RSA Algorithm: ");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter p: ");
        p = scanner.nextInt();
        System.out.print("Enter q: ");
        q = scanner.nextInt();

        System.out.print("Enter the message to encrypt: ");
        m = scanner.nextInt();
        n = p*q;
        phi = (p-1)*(q-1);
        

        int e = findPublicKey(phi);
        int d = findPrivateKey(e, phi);

        int encrypt = modPower(m, e, n);
        System.out.println("Encrypted text: "+encrypt);

        int decrypt = modPower(encrypt, d, n);
        System.out.println("Plaintext: "+decrypt);
    }
}
