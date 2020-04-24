package server;

import domain.Arbitru;
import domain.Proba;
import domain.Rezultat;
import repository.FilterRepositoryParticipant;
import repository.FilterRepositoryRezultat;
import repository.IRepository;
import service.IObserver;
import service.IServices;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ServicesImpl implements IServices {

    private IRepository<String, Arbitru> repoArbitri;
    private IRepository<Integer, Proba> repoProbe;
    private FilterRepositoryRezultat repoRezultate;
    private FilterRepositoryParticipant repoParticipanti;
    private Map<String, IObserver> loggedClients;
    private final int defaultThreadsNo=10;

    public ServicesImpl(IRepository<String, Arbitru> repoArbitri, IRepository<Integer, Proba> repoProbe, FilterRepositoryRezultat repoRezultate, FilterRepositoryParticipant repoParticipanti) {

        this.repoArbitri = repoArbitri;
        this.repoProbe = repoProbe;
        this.repoRezultate = repoRezultate;
        this.repoParticipanti = repoParticipanti;
        loggedClients= new ConcurrentHashMap<>();

    }

    @Override
    public void addRezultat(Rezultat rezultat) throws Exception {
        System.out.println("ADAUGARE REZULTAT IN SERVER");
        System.out.println("NR CLIENTI LOGATI "+loggedClients.entrySet().size());
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for (Map.Entry<String, IObserver> entry : loggedClients.entrySet()) {
            System.out.println(entry.getKey() + "/" + entry.getValue());
            IObserver chatClient=loggedClients.get(entry.getKey());
            System.out.println(chatClient);
            System.out.println(entry.getKey());
            if (chatClient!=null)
                executor.execute(() -> {
                    try {
                        chatClient.rezultatAdaugat(rezultat);

                        //chatClient.refresh(rezultat);
                    } catch (Exception e) {
                        System.err.println("Error notifying friend " + e);
                    }
                });
        }
    }

    @Override
    public void login(Arbitru user, IObserver client) throws Exception {
        int id=0;
        loggedClients.put(user.getName(), client);
        System.out.println("S-a LOGAT" + client);
    }

    public synchronized Map<String, IObserver> getLoggedUsers() {
        return loggedClients;
    }
}