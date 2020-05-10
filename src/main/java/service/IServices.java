package service;


import domain.Arbitru;
import domain.Participant;
import domain.Rezultat;
import utils.TipProba;

import java.util.List;
import java.util.Map;

public interface IServices {
     void login(Arbitru user, IObserver client) throws Exception;
     Map<String, IObserver> getLoggedUsers() throws Exception;
     Iterable<Rezultat> getAllRezultate();
     Rezultat addRezultat(String participant, String numeArbitru, TipProba tipProba, double nrPuncte);
     Iterable<Participant> findAllForAProbe(TipProba tipProba);
     Iterable<Participant> findAllParticipanti();
     Arbitru findArbitru(String user);
     double getPunctajTotalParticipant(String participant);
     double getPunctajParticipantPentruOProba(String participant,String tipProba);


     }
