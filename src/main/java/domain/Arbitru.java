package domain;

import java.io.Serializable;
import java.util.Objects;

public class Arbitru implements Serializable {
    public Arbitru() {
    }

    private String name;
    private String password;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Arbitru{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Arbitru arbitru = (Arbitru) o;
        return Objects.equals(name, arbitru.name) &&
                Objects.equals(password, arbitru.password);
    }



    @Override
    public int hashCode() {
        return Objects.hash(name, password);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Arbitru(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
