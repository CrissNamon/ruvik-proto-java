package ru.kpekepsalt.proto;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.InputMismatchException;

public class ByteUtils {

    public static byte[] xOR(byte[] a, byte[] b) throws InputMismatchException{
        if(a.length != b.length) {
            throw new InputMismatchException();
        }
        byte[] result = new byte[a.length];
        for(int i=0;i<b.length;i++) {
            result[i] = (byte) (a[i] ^ b[i]);
        }
        return result;
    }

    public static byte[] concat(byte[] a, byte[] b) {
        byte[] result = new byte[a.length+b.length];
        for(int i=0; i<result.length; i++) {
            if(i < a.length) {
                result[i] = a[i];
            }else{
                result[i] = b[i-a.length];
            }
        }
        return result;
    }

    public static byte[] fill(byte[] arr, byte a) {
        for(int i=0;i<arr.length;i++) {
            arr[i] = a;
        }
        return arr;
    }

    public static byte[] paddingForward(byte[] arr, int size) {
        byte[] newArr = new byte[size];
        for(int i=0;i<size;i++) {
            newArr[i] = arr[i];
        }
        return newArr;
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for(byte b:bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    public static String bytesToString(byte[] bytes) {
        return new String(bytes);
    }

    public static byte[] hexToByteArray(String s) {
        int len = s.length()/2;
        byte[] data = new byte[len];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

}
