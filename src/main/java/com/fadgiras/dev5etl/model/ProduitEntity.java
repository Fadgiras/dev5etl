package com.fadgiras.dev5etl.model;

import javax.persistence.*;

@Entity
@Table(name = "produit")
public class ProduitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "DATE")
    String date;

    @Column(name = "PRODID")
    int prodid;

    @Column(name = "CATID")
    int catid;

    @Column(name = "FABID")
    int fabid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
