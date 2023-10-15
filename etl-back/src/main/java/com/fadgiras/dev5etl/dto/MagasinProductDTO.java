package com.fadgiras.dev5etl.dto;

public class MagasinProductDTO {
    private int magid;
    private long numberOfDistinctProducts;

    public MagasinProductDTO(int magid, long numberOfDistinctProducts) {
        this.magid = magid;
        this.numberOfDistinctProducts = numberOfDistinctProducts;
    }

    public int getMagid() {
        return magid;
    }

    public void setMagid(int magid) {
        this.magid = magid;
    }

    public long getNumberOfDistinctProducts() {
        return numberOfDistinctProducts;
    }

    public void setNumberOfDistinctProducts(long numberOfDistinctProducts) {
        this.numberOfDistinctProducts = numberOfDistinctProducts;
    }

    @Override
    public String toString() {
        return "MagasinProductDTO{" +
                "magid=" + magid +
                ", numberOfDistinctProducts=" + numberOfDistinctProducts +
                '}';
    }
}
