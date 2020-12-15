package ru.kpekepsalt.proto;

import java.math.BigInteger;
import java.util.Random;


public class IdentityKeyPair {
    private IdentityKey publicIdentityKey;
    private IdentityKey privateIdentityKey;
    private final int KEY_BIT_LENGTH = 1024;

    private int keyLength;

    private final int[] first_primes_list = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29,
            31, 37, 41, 43, 47, 53, 59, 61, 67,
            71, 73, 79, 83, 89, 97, 101, 103,
            107, 109, 113, 127, 131, 137, 139,
            149, 151, 157, 163, 167, 173, 179,
            181, 191, 193, 197, 199, 211, 223,
            227, 229, 233, 239, 241, 251, 257,
            263, 269, 271, 277, 281, 283, 293,
            307, 311, 313, 317, 331, 337, 347, 349};

    public IdentityKeyPair(int keyLength) {
        this.keyLength = keyLength;
    }

    public IdentityKey getPublicKey() {
        return publicIdentityKey;
    }

    public void setPublicKey(IdentityKey publicIdentityKey) {
        this.publicIdentityKey = publicIdentityKey;
    }

    public IdentityKey getPrivateKey() {
        return privateIdentityKey;
    }

    public void setPrivateKey(IdentityKey privateIdentityKey) {
        this.privateIdentityKey = privateIdentityKey;
    }

    public void generateKeys() {
        BigInteger p = new BigInteger(keyLength, 10, new Random());
        BigInteger q = new BigInteger(keyLength, 10, new Random());
        BigInteger n = p.multiply(q);
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        BigInteger e;
        do
        {
           e = new BigInteger(keyLength, new Random() ) ;
        }
        while(
                (e.compareTo(n) != -1)
                        ||
                        (e.gcd(phi).compareTo(BigInteger.ONE) != 0)
        ) ;

        BigInteger d = e.modInverse(phi);
        IdentityKey identityKey = new IdentityKey(e, n);
        setPublicKey(identityKey);
        identityKey = new IdentityKey(d, n);
        setPrivateKey(identityKey);
    }
}
