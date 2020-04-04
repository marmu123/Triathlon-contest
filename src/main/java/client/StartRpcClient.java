package client;



import controllers.ArbitruController;
import controllers.LoginController;
import domain.Arbitru;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.IServices;
import service.Service;
import java.io.IOException;
import java.util.Properties;


public class StartRpcClient {
    private static int defaultChatPort=55556;
    private static String defaultServer="localhost";
    public static LoginController main(Service service) throws Exception {
        Properties clientProps=new Properties();
        try {
            clientProps.load(StartRpcClient.class.getResourceAsStream("/client.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatclient.properties "+e);
            return null;
        }
        String serverIP=clientProps.getProperty("server.host",defaultServer);
        int serverPort=defaultChatPort;
        try{
            serverPort= Integer.parseInt(clientProps.getProperty("server.port"));
        }catch(NumberFormatException ex){
            System.err.println("Wrong port number "+ex.getMessage());
            System.out.println("Using default port: "+defaultChatPort);
        }
        System.out.println("Using server IP "+serverIP);
        System.out.println("Using server port "+serverPort);

        IServices server=new ClientServicesRpcProxy(serverIP, serverPort);

        FXMLLoader fxmlLoader = new FXMLLoader(StartRpcClient.class.getResource("/login.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(anchorPane,250 ,250));
        stage.show();
        LoginController controllerLogin = fxmlLoader.getController();
        controllerLogin.setService(service,stage,server);
        return controllerLogin;



    }
}
