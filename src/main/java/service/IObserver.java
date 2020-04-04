package service;


import domain.Arbitru;
import domain.Rezultat;



public interface IObserver {
     void rezultatAdaugat(Rezultat r) throws Exception;
     void loggedIn(Arbitru user);
     void refresh(Rezultat r);
}
