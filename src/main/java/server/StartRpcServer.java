package server;


import domain.Arbitru;
import domain.Proba;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import repository.*;
import server.utils.AbstractServer;
import server.utils.RpcConcurrentServer;
import service.IServices;

import java.io.IOException;
import java.rmi.ServerException;
import java.util.Properties;

public class StartRpcServer {
    private static int defaultPort=55555;
    public static void main(String[] args) throws ServerException {
//        Properties serverProps=new Properties();
//        try {
//            serverProps.load(StartRpcServer.class.getResourceAsStream("/server.properties"));
//            System.out.println("Server properties set. ");
//            serverProps.list(System.out);
//        } catch (IOException e) {
//            System.err.println("Cannot find chatserver.properties "+e);
//            return;
//        }
//

//
//        IRepository<String, Arbitru> repoArbitri=new JdbcRepositoryArbitru(serverProps);
//        IRepository<Integer, Proba> repoProbe=new JdbcRepositoryProba(serverProps);
//        FilterRepositoryParticipant repoParticipanti=new JdbcRepositoryParticipant(serverProps);
//        FilterRepositoryRezultat repoRezultate=new JdbcRepositoryRezultat(serverProps);
//
//        IServices service=new ServicesImpl(repoArbitri,repoProbe,repoRezultate,repoParticipanti);
//

//        int serverPort=defaultPort;
//        try {
//            serverPort = Integer.parseInt(serverProps.getProperty("server.port"));
//        }catch (NumberFormatException nef){
//            System.err.println("Wrong  Port Number"+nef.getMessage());
//            System.err.println("Using default port "+defaultPort);
//        }
//        System.out.println("Starting server on port: "+serverPort);
//        AbstractServer server = new RpcConcurrentServer(serverPort, service);
//        try {
//            server.start();
//        } finally {
//            server.stop();
//        }

        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-server.xml");


    }
}
