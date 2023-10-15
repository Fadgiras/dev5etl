package com.fadgiras.dev5etl.dto;

import java.util.Date;
import java.time.LocalDate;

public class FabricantHealthDTO {
    private int magid;
    private Date month;
    private long marketShare;

    public FabricantHealthDTO(int magid, Date month, long marketShare) {
        this.magid = magid;
        this.month = month;
        this.marketShare = marketShare;
    }

    public int getMagid() {
        return magid;
    }

    public void setMagid(int magid) {
        this.magid = magid;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public long getMarketShare() {
        return marketShare;
    }

    public void setMarketShare(long marketShare) {
        this.marketShare = marketShare;
    }

    @Override
    public String toString() {
        return "FabricantHealthDTO{" +
                "magid=" + magid +
                ", month=" + month +
                ", marketShare=" + marketShare +
                '}';
    }
}
