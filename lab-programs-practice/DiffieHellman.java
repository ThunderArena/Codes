import java.util.Random;
import java.util.Scanner;

public class DiffieHellman {

    public static long modPower(long base, long exponent, long modulus) {
        long result = 1;
        for (int i = 0; i < exponent; i++) {
            result = (result * base) % modulus;
        }
        return result;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Enter common prime (q) and generator (alpha): ");
        long prime = scanner.nextLong();
        long generator = scanner.nextLong();

        scanner.close();
        long privateKeyA = 1 + random.nextLong(prime - 1);
        long privateKeyB = 1 + random.nextLong(prime - 1);

        long publicKeyA = modPower(generator, privateKeyA, prime);
        long publicKeyB = modPower(generator, privateKeyB, prime);


        System.out.println("Public keys exchanged");

        long sharedSecretA = modPower(publicKeyB, privateKeyA, prime);
        long sharedSecretB = modPower(publicKeyA, privateKeyB, prime);

        System.out.println("Shared secrets: ");
        System.out.println("A's shared key: "+ sharedSecretA);
        System.out.println("B's shared secret: "+sharedSecretB);


    }
}
