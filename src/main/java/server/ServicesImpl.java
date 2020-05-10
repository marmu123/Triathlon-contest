package server;

import domain.Arbitru;
import domain.Participant;
import domain.Proba;
import domain.Rezultat;
import repository.FilterRepositoryParticipant;
import repository.FilterRepositoryRezultat;
import repository.IRepository;
import service.IObserver;
import service.IServices;
import service.Service;
import utils.TipProba;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ServicesImpl implements IServices {

    private Service service;
    //private Map<String, IObserver> loggedClients;
    private final int defaultThreadsNo=10;

    public ServicesImpl(Service service) {

        this.service=service;
        //loggedClients= new ConcurrentHashMap<>();

    }

    @Override
    public void login(Arbitru user, IObserver client) throws Exception {
        if(service.checkLogin(user.getName(), user.getPassword()))
        {
            //loggedClients.put(user.getName(), client);
            System.out.println("S-a LOGAT" + user.getName());
        }
        else
            System.out.println("LOGARE ESUATA");

    }

    public synchronized Map<String, IObserver> getLoggedUsers() {
        return null;
    }

    @Override
    public Iterable<Rezultat> getAllRezultate() {
        return service.findAllRezultat();
    }

    @Override
    public Rezultat addRezultat(String participant, String numeArbitru, TipProba tipProba, double nrPuncte) {
        return service.addRezultat(participant,numeArbitru,tipProba,nrPuncte);
    }

    @Override
    public Iterable<Participant> findAllForAProbe(TipProba tipProba) {
        return service.findAllForAProbe(tipProba);
    }

    @Override
    public Iterable<Participant> findAllParticipanti() {
        return service.findAllParticipanti();
    }

    @Override
    public Arbitru findArbitru(String user) {
        return service.findArbitru(user);
    }

    @Override
    public double getPunctajTotalParticipant(String participant) {
        return service.getPunctajTotalParticipant(participant);
    }

    @Override
    public double getPunctajParticipantPentruOProba(String participant, String tipProba) {
        return service.getPunctajParticipantPentruOProba(participant,tipProba);
    }
}