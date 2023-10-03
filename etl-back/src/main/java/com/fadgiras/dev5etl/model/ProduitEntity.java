package com.fadgiras.dev5etl.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "produit")
public class ProduitEntity {

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

    @Override
    public String toString() {
        return "ProduitEntity{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", prodid=" + prodid +
                ", catid=" + catid +
                ", fabid=" + fabid +
                '}';
    }
}
