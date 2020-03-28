package service;

import domain.Arbitru;
import domain.Participant;
import domain.Proba;
import domain.Rezultat;
import javafx.util.Pair;
import repository.*;
import utils.TipProba;

public class Service {
    private IRepository<String, Arbitru> repoArbitri;
    private IRepository<Integer, Proba> repoProbe;
    private FilterRepositoryRezultat repoRezultate;
    private FilterRepositoryParticipant repoParticipanti;

    public Service(IRepository<String, Arbitru> repoArbitri, IRepository<Integer, Proba> repoProbe, FilterRepositoryRezultat repoRezultate, FilterRepositoryParticipant repoParticipanti) {
        this.repoArbitri = repoArbitri;
        this.repoProbe = repoProbe;
        this.repoRezultate = repoRezultate;
        this.repoParticipanti = repoParticipanti;
    }

    public Iterable<Arbitru> findAllArbitri(){
        return repoArbitri.findAll();
    }
    public Iterable<Proba> findAllProbe(){return repoProbe.findAll();}
    public Iterable<Rezultat> findAllRezultat(){return repoRezultate.findAll();}
    public Iterable<Participant> findAllParticipanti(){return repoParticipanti.findAll();}

    public Iterable<Participant> findAllForAProbe(TipProba tipProba){
        return repoParticipanti.findAllForAProbe(tipProba);
    }

    public void addRezultat(String participant,String numeArbitru,TipProba tipProba,double nrPuncte){
        if(repoRezultate.getScoreOfAParticipantForAProbe(participant,tipProba)!=0)
            return;//throw error
        Proba p=repoProbe.save(new Proba(numeArbitru,tipProba));
        repoRezultate.save(new Rezultat(p.getId(),participant,nrPuncte));
    }
    public boolean checkLogin(String user,String pass){
        Arbitru arbitru=repoArbitri.findOne(user);
        return arbitru!=null && arbitru.getPassword().equals(pass);
    }


    public Arbitru findArbitru(String user) {
        return repoArbitri.findOne(user);
    }
    public double getPunctajTotalParticipant(String participant){
        return repoRezultate.getTotalScoreForAParticipant(participant);
    }
    public double getPunctajParticipantPentruOProba(String participant,String tipProba){
        return repoRezultate.getScoreOfAParticipantForAProbe(participant,TipProba.valueOf(tipProba));
    }
}
