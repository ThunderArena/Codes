import java.math.BigInteger;
//import java.security.SecureRandom;
import java.util.Scanner;

public class RSA {
    private BigInteger n, d, e;
    
    public RSA(BigInteger p, BigInteger q) {
        n = p.multiply(q); // n = p * q
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE)); // φ(n) = (p-1) * (q-1)

        e = calculateE(phi); // Dynamically find e
        d = e.modInverse(phi); // Compute d such that (d * e) % φ(n) = 1
    }

    // Function to find a valid e that is coprime with φ(n)
    private BigInteger calculateE(BigInteger phi) {
        BigInteger e = BigInteger.valueOf(3); // Start from 3
        while (e.gcd(phi).compareTo(BigInteger.ONE) > 0) { 
            e = e.add(BigInteger.TWO); // Increment by 2 to ensure it's always odd
        }
        return e;
    }

    // Encrypts the message
    public BigInteger encrypt(BigInteger message) {
        return message.modPow(e, n);
    }

    // Decrypts the message
    public BigInteger decrypt(BigInteger ciphertext) {
        return ciphertext.modPow(d, n);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Take user input for p and q
        System.out.print("Enter a prime number p: ");
        BigInteger p = scanner.nextBigInteger();
        System.out.print("Enter a prime number q: ");
        BigInteger q = scanner.nextBigInteger();

        // Check if p and q are prime numbers
        if (!p.isProbablePrime(10) || !q.isProbablePrime(10)) {
            System.out.println("Error: Both p and q must be prime numbers.");
            return;
        }

        // Ensure p and q are different
        if (p.equals(q)) {
            System.out.println("Error: p and q must be different.");
            return;
        }

        // Generate RSA keys
        RSA rsa = new RSA(p, q);
        System.out.println("Public Key (e, n): (" + rsa.e + ", " + rsa.n + ")");
        System.out.println("Private Key (d, n): (" + rsa.d + ", " + rsa.n + ")");

        // Take user input for message
        System.out.print("Enter a number to encrypt (must be smaller than n): ");
        BigInteger message = scanner.nextBigInteger();

        if (message.compareTo(rsa.n) >= 0) {
            System.out.println("Error: Message must be smaller than n.");
            return;
        }

        // Encrypt and decrypt
        BigInteger encrypted = rsa.encrypt(message);
        System.out.println("Encrypted Message: " + encrypted);

        BigInteger decrypted = rsa.decrypt(encrypted);
        System.out.println("Decrypted Message: " + decrypted);

        scanner.close();
    }
}
