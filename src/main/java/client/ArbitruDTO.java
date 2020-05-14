package client;

import domain.Arbitru;

import java.io.Serializable;
import java.util.Objects;

public class ArbitruDTO extends Arbitru implements Serializable {

    private String name;
    private String password;

//    public ArbitruDTO(String name, String password) {
//        super(name, password);
//    }

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


}

