package domain;

import java.io.Serializable;
import java.util.Objects;

public class Participant implements Serializable {
    private String nume;

    @Override
    public String toString() {
        return "Participant{" +
                "nume='" + nume + '\'' +
                '}';
    }

    public String getNume() {
        return nume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return Objects.equals(nume, that.nume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nume);
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Participant(String nume) {
        this.nume = nume;
    }
}
