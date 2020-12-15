package ru.kpekepsalt.proto;

public enum Hash {
    MD5("MD5"),
    SHA_1("SHA-1"),
    SHA_256("SHA-256");

    private String hash;

    Hash(String name) {
        this.hash = name;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
