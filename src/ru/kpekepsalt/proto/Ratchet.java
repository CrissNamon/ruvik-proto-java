package ru.kpekepsalt.proto;

import java.security.NoSuchAlgorithmException;

public class Ratchet {

    private ChainKey chainKey;
    private MessageKey messageKey;

    public Ratchet(ChainKey chainKey) {
        this.chainKey = new ChainKey(chainKey.getKey());
        this.messageKey = new MessageKey(null);
    }

    public void updateChain() throws NoSuchAlgorithmException{
        HMAC key = new HMAC();
        byte[] adding = new byte[chainKey.getKey().length];
        adding = ByteUtils.fill(adding, (byte) 0x02);
        chainKey.setKey(
                key.create(Hash.SHA_256, chainKey.getKey(), adding)
        );
    }

    public void updateMessageKey() throws NoSuchAlgorithmException {
        HMAC key = new HMAC();
        byte[] msg = new byte[chainKey.getKey().length];
        msg = ByteUtils.fill(msg, (byte) 0x01);
        messageKey.setKey(
                key.create(
                    Hash.SHA_256,
                    chainKey.getKey(),
                    msg
                )
        );
    }

    public ChainKey getChainKey() {
        return chainKey;
    }

    public void setChainKey(ChainKey chainKey) {
        this.chainKey = new ChainKey(chainKey.getKey());
    }

    public MessageKey getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(MessageKey messageKey) {
        this.messageKey = messageKey;
    }
}
