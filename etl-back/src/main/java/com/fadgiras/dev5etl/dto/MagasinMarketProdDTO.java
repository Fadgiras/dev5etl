package com.fadgiras.dev5etl.dto;

public class MagasinMarketProdDTO {
    private int magid;
    private long numberProd;

    public MagasinMarketProdDTO(int magid, long numberProd) {
        this.magid = magid;
        this.numberProd = numberProd;
    }

    // Getters, setters, and other methods...

    public int getMagid() {
        return magid;
    }

    public void setMagid(int magid) {
        this.magid = magid;
    }

    public long getNumberProd() {
        return numberProd;
    }

    public void setNumberProd(long numberProd) {
        this.numberProd = numberProd;
    }

    @Override
    public String toString() {
        return "MagasinMarketShareDTO{" +
                "magid=" + magid +
                ", numberProd=" + numberProd +
                '}';
    }
}
