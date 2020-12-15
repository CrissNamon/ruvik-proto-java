package ru.kpekepsalt.proto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HMAC {

    private final int MD5_BLOCK_SIZE = 64;
    private final int SHA1_BLOCK_SIZE = 64;
    private final int SHA256_BLOCK_SIZE = 64;

    private final byte IPAD = 0x36;
    private final byte OPAD = 0x5C;
    private final byte ZERO = 0x00;

    public byte[] create(Hash hash, byte[] key, byte[] message) throws NoSuchAlgorithmException {
        MessageDigest digest = getDigestInstanceOf(hash.getHash());
        int BLOCK_SIZE = getBlockSizeOf(hash);
        if(key.length > BLOCK_SIZE) {
            key = digest.digest(key);
        }
        if(key.length < BLOCK_SIZE) {
            byte[] tmp = new byte[BLOCK_SIZE-key.length];
            tmp = ByteUtils.fill(tmp, ZERO);
            key = ByteUtils.concat(key, tmp);
        }

        byte[] iPad = new byte[BLOCK_SIZE];
        iPad = ByteUtils.fill(iPad, IPAD);
        byte[] oPad = new byte[BLOCK_SIZE];
        oPad = ByteUtils.fill(oPad, OPAD);

        byte[] iKeyPad = ByteUtils.xOR(iPad, key);
        byte[] oKeyPad = ByteUtils.xOR(oPad, key);
        byte[] iHash = digest.digest(ByteUtils.concat(iKeyPad, message));
        byte[] hmac = digest.digest(ByteUtils.concat(oKeyPad, iHash));
        return hmac;
    }

    private MessageDigest getDigestInstanceOf(String alg) throws NoSuchAlgorithmException{
        switch (alg) {
            case "MD5":
                return MessageDigest.getInstance("MD5");
            case "SHA-256":
                return MessageDigest.getInstance("SHA-256");
            case "SHA-1":
                return MessageDigest.getInstance("SHA-1");
            default:
                throw new NoSuchAlgorithmException();
        }
    }

    private int getBlockSizeOf(Hash hash) {
        switch(hash) {
            case MD5:
                return MD5_BLOCK_SIZE;
            case SHA_256:
                return SHA256_BLOCK_SIZE;
            case SHA_1:
                return SHA1_BLOCK_SIZE;
            default:
                return 0;
        }
    }
}
