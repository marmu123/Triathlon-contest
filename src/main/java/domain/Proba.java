package domain;

import utils.TipProba;

import java.io.Serializable;
import java.util.Objects;

public class Proba implements Serializable {
    private int id;
    private String numeArbitru;
    private TipProba tipProba;

    public Proba(String numeArbitru, TipProba tipProba) {
        this.numeArbitru = numeArbitru;
        this.tipProba = tipProba;
    }

    public Proba() {
    }

    @Override
    public String toString() {
        return "Proba{" +
                "id=" + id +
                ", numeArbitru='" + numeArbitru + '\'' +
                ", tipProba=" + tipProba +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proba proba = (Proba) o;
        return id == proba.id &&
                Objects.equals(numeArbitru, proba.numeArbitru) &&
                Objects.equals(tipProba, proba.tipProba);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numeArbitru, tipProba);
    }

    public String getNumeArbitru() {
        return numeArbitru;
    }

    public void setNumeArbitru(String numeArbitru) {
        this.numeArbitru = numeArbitru;
    }

    public TipProba getTipProba() {
        return tipProba;
    }

    public void setTipProba(TipProba tipProba) {
        this.tipProba = tipProba;
    }

    public Proba(int id, String numeArbitru, TipProba tipProba) {
        this.id = id;
        this.numeArbitru = numeArbitru;
        this.tipProba = tipProba;
    }
}
