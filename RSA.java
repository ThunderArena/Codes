import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;

public class RSA {

    // Key components
    private BigInteger n; // Modulus
    private BigInteger e; // Public key exponent
    private BigInteger d; // Private key exponent

    // Constructor to initialize RSA key generation
    public RSA(int bitLength) {
        SecureRandom random = new SecureRandom();

        // Step 1: Choose two large prime numbers, p and q
        BigInteger p = new BigInteger(bitLength / 2, 100, random);
        BigInteger q = new BigInteger(bitLength / 2, 100, random);

        // Step 2: Compute n = p * q
        n = p.multiply(q);

        // Step 3: Compute φ(n) = (p-1) * (q-1)
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        // Step 4: Choose public key exponent e (1 < e < φ(n), and gcd(e, φ(n)) = 1)
        e = new BigInteger("65537"); // Common choice for e (2^16 + 1)

        // Step 5: Compute private key exponent d (d * e ≡ 1 mod φ(n))
        d = e.modInverse(phi);

        // Print keys
        System.out.println("Public Key (e, n): (" + e + ", " + n + ")");
        System.out.println("Private Key (d, n): (" + d + ", " + n + ")");
    }

    // Encrypt a message
    public BigInteger encrypt(BigInteger message) {
        return message.modPow(e, n); // C = M^e % n
    }

    // Decrypt a message
    public BigInteger decrypt(BigInteger ciphertext) {
        return ciphertext.modPow(d, n); // M = C^d % n
    }

    // Main method for testing
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the bit length for primes (e.g., 1024): ");
        int bitLength = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Initialize RSA with the specified bit length
        RSA rsa = new RSA(bitLength);

        System.out.print("Enter a message to encrypt: ");
        String plaintext = scanner.nextLine();

        // Convert plaintext to BigInteger
        BigInteger message = new BigInteger(plaintext.getBytes());

        // Encrypt the message
        BigInteger ciphertext = rsa.encrypt(message);
        System.out.println("Encrypted message (Ciphertext): " + ciphertext);

        // Decrypt the message
        BigInteger decryptedMessage = rsa.decrypt(ciphertext);
        String recoveredPlaintext = new String(decryptedMessage.toByteArray());
        System.out.println("Decrypted message (Plaintext): " + recoveredPlaintext);

        scanner.close();
    }
}