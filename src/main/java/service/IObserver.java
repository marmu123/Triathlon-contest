package service;


import domain.Arbitru;
import domain.Rezultat;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface IObserver extends Remote {
     void rezultatAdaugat(Rezultat r) throws Exception;
     void loggedIn(Arbitru user)throws RemoteException;
     void refresh(Rezultat r)throws RemoteException;
}
