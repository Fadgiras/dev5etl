package com.fadgiras.dev5etl.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.springframework.boot.convert.DurationFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "point_vente")
public class PointVenteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @JsonAlias("DATE")
    @Column(name = "DATE")
    @Temporal(TemporalType.DATE)
    Date date;

    @JsonAlias("PRODID")
    @Column(name = "PRODID")
    int prodid;

    @JsonAlias("CATID")
    @Column(name = "CATID")
    int catid;

    @JsonAlias("FABID")
    @Column(name = "FABID")
    int fabid;

    @JsonAlias("MAGID")
    @Column(name = "MAGID")
    int magid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getProdid() {
        return prodid;
    }

    public void setProdid(int prodid) {
        this.prodid = prodid;
    }

    public int getCatid() {
        return catid;
    }

    public void setCatid(int catid) {
        this.catid = catid;
    }

    public int getFabid() {
        return fabid;
    }

    public void setFabid(int fabid) {
        this.fabid = fabid;
    }

    public int getMagid() {
        return magid;
    }

    public void setMagid(int magid) {
        this.magid = magid;
    }

    @Override
    public String toString() {
        return "PointVenteEntity{" +
                "id=" + id +
                ", date=" + date +
                ", prodid=" + prodid +
                ", catid=" + catid +
                ", fabid=" + fabid +
                ", magid=" + magid +
                '}';
    }
}
