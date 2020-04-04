package service;


import domain.Arbitru;
import domain.Rezultat;

import java.util.Map;

public interface IServices {
     void addRezultat(Rezultat rezultat) throws Exception;
     void login(Arbitru user, IObserver client) throws Exception;
     Map<String, IObserver> getLoggedUsers() throws Exception;

}
