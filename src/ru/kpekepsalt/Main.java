package ru.kpekepsalt;

import ru.kpekepsalt.proto.*;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            //Create public and private keys
            IdentityKeyPair identityKeyPair = new IdentityKeyPair(384);
            identityKeyPair.generateKeys();

            //Initialize RSA cipher
            RSACipher cypherService = new RSACipher(identityKeyPair.getPublicKey());
            //Session key - random string
            String sessionKey = CipherUtils.randomStringKey(128);
            System.out.println("SESSION CREATED: "+sessionKey);
            //Encrypt session key
            List<BigInteger> encSession = cypherService.encrypt(sessionKey, identityKeyPair.getPublicKey());
            String encryptedSessionKey = cypherService.decimalToString(encSession);
            System.out.println("ENCRYPTED SESSION: "+encryptedSessionKey);
            //Decrypt session key
            List<BigInteger> encryptedSession = cypherService.stringToDecimal(encryptedSessionKey);
            String decryptedSessionKey = cypherService.decimalToMessage(cypherService.decrypt(encryptedSession, identityKeyPair.getPrivateKey()));
            System.out.println("DECRYPTED SESSION: "+ decryptedSessionKey);

            //Message for encryption
            String string = "This is a test message for testing AES encryption..";
            //One time key used with session key to create chain key
            String oneTimeKey = CipherUtils.randomStringKey(32);
            System.out.println("ONE TIME KEY: "+oneTimeKey);
            //Create chain key
            HMAC hmac = new HMAC();
            ChainKey chainKey = new ChainKey(
                    hmac.create(
                            Hash.SHA_256,
                            sessionKey.getBytes(),
                            oneTimeKey.getBytes()
                    )
            );
            //Initialize ratchet
            Ratchet ratchet = new Ratchet(chainKey);
            System.out.println("CHAIN CREATED: "+ByteUtils.bytesToHex(ratchet.getChainKey().getKey()));
            //Get message key from ratchet
            ratchet.updateMessageKey();
            System.out.println("MESSAGE KEY: "+ByteUtils.bytesToHex(ratchet.getMessageKey().getKey()));

            //Initialize AES encryption with message key as key and session key as initialization vector
            AESCipher aes = new AESCipher(ratchet.getMessageKey().getKey(), ByteUtils.hexToByteArray(sessionKey));
            //Encrypt message
            byte[] aesEncrypted = aes.CBC_encrypt(string.getBytes());
            String aesEncryptedString = ByteUtils.bytesToHex(aesEncrypted);
            System.out.println("AES ENCRYPTED: "+aesEncryptedString);
            //Decrypt message
            byte[] aesDecrypted = aes.CBC_decrypt(aesEncrypted);
            System.out.println("AES DECRYPTED: "+ByteUtils.bytesToString(aesDecrypted));
        }catch(NoSuchAlgorithmException e){
            System.out.println("HASH FUNCTION FOR HMAC NOT FOUND");
        }
    }
}
