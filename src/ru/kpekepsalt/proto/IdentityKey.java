package ru.kpekepsalt.proto;

import java.math.BigInteger;

public class IdentityKey {
    private BigInteger a;
    private BigInteger b;

    public IdentityKey(BigInteger a, BigInteger b) {
        this.a = a;
        this.b = b;
    }

    public BigInteger getA() {
        return a;
    }

    public void setA(BigInteger a) {
        this.a = a;
    }

    public BigInteger getB() {
        return b;
    }

    public void setB(BigInteger b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return a.toString()+b.toString();
    }
}
