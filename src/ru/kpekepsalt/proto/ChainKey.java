package ru.kpekepsalt.proto;

public class ChainKey {

    private byte[] key;

    public ChainKey(byte[] key) {
        this.key = key;
    }

    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    public void fromSession(){

    }
}
