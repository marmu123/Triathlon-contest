package repository;

import domain.Rezultat;
import javafx.util.Pair;
import utils.TipProba;

public interface FilterRepositoryRezultat extends IRepository<Pair<Integer,String>, Rezultat> {
    double getTotalScoreForAParticipant(String participant);
    double getScoreOfAParticipantForAProbe(String participant, TipProba tipProba);
}
