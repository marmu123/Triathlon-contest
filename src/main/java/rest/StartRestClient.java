package rest;

import domain.Proba;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import rest.client.UsersClient;
import utils.TipProba;

/**
 * Created by grigo on 5/11/17.
 */
public class StartRestClient {
    private final static UsersClient usersClient=new UsersClient();
    public static void main(String[] args) {
        RestTemplate restTemplate=new RestTemplate();
        Proba proba=new Proba(100,"a", TipProba.NATATIE);
        try{


            show(()-> {
                try {
                    Proba tmp=usersClient.update(proba);
                    Proba p2=usersClient.getById(Integer.toString(tmp.getId()));
                    System.out.println(p2);
                    proba.setTipProba(TipProba.ALERGARE);
                    Proba tmp1=usersClient.update(proba);
                    Proba p22=usersClient.getById(Integer.toString(tmp1.getId()));
                    System.out.println(p22);


                    proba.setId(p2.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            show(()->{
                Proba[] res= new Proba[0];
                try {
                    res = usersClient.getAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for(Proba u:res){
                    System.out.println(u);
                }
            });
            show(()-> {
                try {
                    int count=usersClient.getAll().length;
                    usersClient.delete(Integer.toString(proba.getId()));
                    if(count==usersClient.getAll().length)
                        throw new Exception("Exceptie delete");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        }catch(RestClientException ex){
            System.out.println("Exception ... "+ex.getMessage());
        }

    }



    private static void show(Runnable task) {
        try {
            task.run();
        } catch (Exception e) {
            System.out.println("Service exception"+ e);
        }
    }
}
