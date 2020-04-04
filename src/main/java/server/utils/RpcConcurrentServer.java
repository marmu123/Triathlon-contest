package server.utils;


import client.ClientRpcWorker;
import service.IServices;

import java.net.Socket;


public class RpcConcurrentServer extends AbsConcurrentServer {
    private IServices server;
    public RpcConcurrentServer(int port, IServices server) {
        super(port);
        this.server = server;
        System.out.println("Chat- ChatRpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ClientRpcWorker worker=new ClientRpcWorker(server, client);
//        ClientRpcReflectionWorker worker=new ClientRpcReflectionWorker(server, client);
        Thread tw=new Thread(worker);
//        Platform.runLater(tw);
        return tw;
    }

    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }
}
