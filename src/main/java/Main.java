import domain.Arbitru;
import domain.Proba;
import domain.Rezultat;
import javafx.util.Pair;
import repository.*;
import utils.TipProba;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties serverProps=new Properties();
        try {
            serverProps.load(new FileReader("bd.config"));
            //System.setProperties(serverProps);

            System.out.println("Properties set. ");
            //System.getProperties().list(System.out);
            serverProps.list(System.out);
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
            return;
        }
        IRepository<String, Arbitru> repoArbitri=new JdbcRepositoryArbitru(serverProps);
        IRepository<Integer, Proba> repoProbe=new JdbcRepositoryProba(serverProps);
        FilterRepositoryParticipant repoParticipanti=new JdbcRepositoryParticipant(serverProps);
        IRepository<Pair<Integer,String>, Rezultat> repoRezultate=new JdbcRepositoryRezultat(serverProps);
        //System.out.println("Nr. arbitri: "+repoArbitri.size());
        //System.out.println("Nr. probe: "+repoProbe.size());
        repoParticipanti.findAllForAProbe(TipProba.CICLISM).forEach(System.out::println);
       // System.out.println("Nr.rezultate: "+ repoRezultate.size());
    }
}
