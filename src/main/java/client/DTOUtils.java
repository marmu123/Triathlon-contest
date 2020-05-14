package client;


import domain.Arbitru;
import domain.Rezultat;

public class DTOUtils {
    public static Arbitru getFromDTO(ArbitruDTO arbitruDTO){
        String id=arbitruDTO.getName();
        String pass=arbitruDTO.getPassword();
        return new Arbitru();//new Arbitru(id,pass)

    }
    public static ArbitruDTO getDTO(Arbitru user){
        String id=user.getName();
        String pass=user.getPassword();
        return new ArbitruDTO();//new ArbitruDTO(id,pass)
    }

    public static Rezultat getFromDTO(RezultatDTO rezultatDTO){
       return new Rezultat(
               rezultatDTO.getIdProba(),
               rezultatDTO.getNumeParticipant(),
               rezultatDTO.getNumarPuncte());
    }

    public static RezultatDTO getDTO(Rezultat rez){

        return new RezultatDTO(
                rez.getIdProba(),
                rez.getNumeParticipant(),
                rez.getNumarPuncte()
        );
    }

    public static Arbitru[] getFromDTO(ArbitruDTO[] users){
        Arbitru[] loggedUsers=new Arbitru[users.length];
        for(int i=0;i<users.length;i++){
            loggedUsers[i]=getFromDTO(users[i]);
        }
        return loggedUsers;
    }
}
