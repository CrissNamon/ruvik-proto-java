package ru.kpekepsalt.proto;

import java.math.BigInteger;

import java.util.Random;

public class CipherUtils {

    public static byte[] randomByteKey(int length) {
        byte[] bytes = new byte[length];
        new Random().nextBytes(bytes);
        return bytes;
    }

    public static String randomStringKey(int length) {
        String key = ByteUtils.bytesToHex(randomByteKey(length));
        if(key.length() > length) {
            key = key.substring(0, length);
        }
        return key;
    }
}
