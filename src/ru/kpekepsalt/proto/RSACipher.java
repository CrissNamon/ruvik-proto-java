package ru.kpekepsalt.proto;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RSACipher {
    private BigInteger d;
    private BigInteger e;
    public BigInteger n;

    public RSACipher() {}

    public RSACipher(IdentityKey publicKey) {
        this.n = publicKey.getB();
    }

    public BigInteger encrypt(BigInteger message, IdentityKey publicKey) {
        if(isModulusSmallerThanMessage(message)) {
            throw new IllegalArgumentException();
        }
        return message.modPow(
                publicKey.getA(),
                publicKey.getB()
        );
    }

    public List<BigInteger> encrypt(String message, IdentityKey publicKey) {
        List<BigInteger> encrypted = new ArrayList<>();
        List<BigInteger> toEncrypt = messageToDecimal(message);
        for (BigInteger bigInteger : toEncrypt) {
            encrypted.add(
                    encrypt(bigInteger, publicKey)
            );
        }
        return encrypted;
    }

    public BigInteger decrypt(BigInteger encryptedMessage, IdentityKey privateKey) {
        return encryptedMessage.modPow(
                privateKey.getA(),
                privateKey.getB()
        );
    }

    public List<BigInteger> decrypt(List<BigInteger> encryptedMessage, IdentityKey privateKey) {
        List<BigInteger> decryption = new ArrayList<>();
        for (BigInteger bigInteger : encryptedMessage) {
            decryption.add(
                    decrypt(bigInteger, privateKey)
            );
        }
        return decryption;
    }

    public List<BigInteger> messageToDecimal(String message) {
        List<BigInteger> toDecimal = new ArrayList<>();
        BigInteger messageBytes = new BigInteger(message.getBytes());
        if (isModulusSmallerThanMessage(messageBytes)) {
            toDecimal = getValidEncryptionBlocks(splitMessages(new ArrayList<String>() {
                {
                    add(message);
                }
            }));
        } else {
            toDecimal.add(messageBytes);
        }
        List<BigInteger> decimal = new ArrayList<BigInteger>();
        for (BigInteger bigInteger : toDecimal) {
            decimal.add(bigInteger);
        }
        return decimal;
    }

    public String decimalToMessage(List<BigInteger> list) {
        StringBuilder plainText = new StringBuilder();
        for (BigInteger bigInteger : list) {
            plainText.append(new String(bigInteger.toByteArray()));
        }
        return plainText.toString();
    }

    public String decimalToString(List<BigInteger> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for(BigInteger bigInteger : list) {
            stringBuilder.append(
                    bigInteger.toString()+" "
            );
        }
        return stringBuilder.toString();
    }

    private boolean isModulusSmallerThanMessage(BigInteger messageBytes) {
        return n.compareTo(messageBytes) == -1;
    }

    private List<BigInteger> getValidEncryptionBlocks(List<String> messages) {
        List<BigInteger> validBlocks = new ArrayList<BigInteger>();
        BigInteger messageBytes = new BigInteger(messages.get(0).getBytes());
        if (!isModulusSmallerThanMessage(messageBytes)) {
            for (String msg : messages) {
                validBlocks.add(new BigInteger(msg.getBytes()));
            }
            return validBlocks;
        } else {
            return getValidEncryptionBlocks(splitMessages(messages));
        }

    }

    private List<String> splitMessages(List<String> messages) {
        List<String> splitedMessages = new ArrayList<String>(messages.size() * 2);
        for (String message : messages) {
            int half = (int) Math.ceil(((double) message.length()) / ((double) 2));
            splitedMessages.add(message.substring(0, half));
            if (half < message.length()) {
                splitedMessages.add(message.substring(half, message.length()));
            }
        }

        return splitedMessages;
    }

    public List<BigInteger> stringToDecimal(String message) {
        String[] arr = message.split(" ");
        List<BigInteger> decimal = new ArrayList<>();
        for(String str : arr) {
            decimal.add(new BigInteger(str));
        }
        return decimal;
    }

    public void printDecimal(List<BigInteger> decimal) {
        for(BigInteger bigInteger : decimal) {
            System.out.print(bigInteger+" ");
        }
        System.out.println();
    }
}
