package com.fadgiras.dev5etl.dto;

import java.util.Date;

public class FabProdsDateDTO {

    private Long prod;
    private Date date;

    public FabProdsDateDTO(Long prod, Date date) {
        this.prod = prod;
        this.date = date;
    }

    // Getters and Setters

    public Long getProd() {
        return prod;
    }

    public void setProd(Long prod) {
        this.prod = prod;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "FabProdsDateDTO{" +
                "prod=" + prod +
                ", date=" + date +
                "}\n";
    }
}
