package client;

import domain.Rezultat;
import javafx.util.Pair;

import java.io.Serializable;

public class RezultatDTO implements Serializable {
    private Pair<Integer,String> id;
    private double numarPuncte;

    public RezultatDTO(Pair<Integer, String> id, double numarPuncte) {
        this.id = id;
        this.numarPuncte = numarPuncte;
    }
    public RezultatDTO(Integer idProba,String numeParticipant,double numarPuncte){
        this.id=new Pair<>(idProba,numeParticipant);
        this.numarPuncte=numarPuncte;
    }

    @Override
    public String toString() {
        return "Rezultat{" +
                "id=" + id +
                ", numarPuncte=" + numarPuncte +
                '}';
    }

    public Integer getIdProba(){
        return id.getKey();
    }
    public String getNumeParticipant(){
        return id.getValue();
    }

    public Pair<Integer, String> getId() {
        return id;
    }

    public void setId(Pair<Integer, String> id) {
        this.id = id;
    }

    public double getNumarPuncte() {
        return numarPuncte;
    }

    public void setNumarPuncte(double numarPuncte) {
        this.numarPuncte = numarPuncte;
    }
}
