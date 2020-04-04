package server;


import domain.Arbitru;
import domain.Proba;
import repository.*;
import server.utils.AbstractServer;
import server.utils.RpcConcurrentServer;
import service.IServices;
import service.Service;

import java.io.IOException;
import java.rmi.ServerException;
import java.util.Properties;

public class StartRpcServer {
    private static int defaultPort=55555;
    public static void main(String[] args) throws ServerException {
        Properties serverProps=new Properties();
        try {
            serverProps.load(StartRpcServer.class.getResourceAsStream("/server.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatserver.properties "+e);
            return;
        }



        IRepository<String, Arbitru> repoArbitri=new JdbcRepositoryArbitru(serverProps);
        IRepository<Integer, Proba> repoProbe=new JdbcRepositoryProba(serverProps);
        FilterRepositoryParticipant repoParticipanti=new JdbcRepositoryParticipant(serverProps);
        FilterRepositoryRezultat repoRezultate=new JdbcRepositoryRezultat(serverProps);
//        System.out.println("Nr. arbitri: "+repoArbitri.size());
//        System.out.println("Nr. probe: "+repoProbe.size());
//        //repoParticipanti.findAllForAProbe(TipProba.CICLISM).forEach(System.out::println);
//        System.out.println("Nr.rezultate: "+ repoRezultate.size());
        IServices service=new ChatServicesImpl(repoArbitri,repoProbe,repoRezultate,repoParticipanti);
//        Service service =  context.getBean(Service.class);


        int serverPort=defaultPort;
        try {
            serverPort = Integer.parseInt(serverProps.getProperty("server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: "+serverPort);
        AbstractServer server = new RpcConcurrentServer(serverPort, service);
        try {
            server.start();
            System.out.println("BOSS");
        } finally {
            server.stop();
        }
    }
}
