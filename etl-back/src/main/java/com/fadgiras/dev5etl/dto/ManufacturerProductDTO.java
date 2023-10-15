package com.fadgiras.dev5etl.dto;

public class ManufacturerProductDTO {
    private int fabid;
    private long productCount;

    public ManufacturerProductDTO(int fabid, long productCount) {
        this.fabid = fabid;
        this.productCount = productCount;
    }

    public int getFabid() {
        return fabid;
    }

    public void setFabid(int fabid) {
        this.fabid = fabid;
    }

    public long getProductCount() {
        return productCount;
    }

    public void setProductCount(long productCount) {
        this.productCount = productCount;
    }

    @Override
    public String toString() {
        return "ManufacturerProductInfo{" +
                "fabid=" + fabid +
                ", productCount=" + productCount +
                '}';
    }
}
