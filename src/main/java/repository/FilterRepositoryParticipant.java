package repository;

import domain.Participant;
import utils.TipProba;

public interface FilterRepositoryParticipant extends IRepository<String, Participant> {
    Iterable<Participant> findAllForAProbe(TipProba tipProba);

}
