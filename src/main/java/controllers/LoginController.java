package controllers;

import domain.Arbitru;
import domain.Rezultat;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.IObserver;
import service.IServices;
import service.Service;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class LoginController implements IObserver {
    @FXML
    private TextField userInput;
    @FXML private PasswordField passInput;
    @FXML private Button loginButton;
    Service service;
    private IServices server;
    private Stage loginStage;

//    public LoginController() throws RemoteException {
//    }

    @FXML
    public void initialize(){
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try{
                    handleLoginButton();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void setService(Service s,Stage stage,IServices services){
        this.service=s;
        this.loginStage=stage;
        this.server=services;
    }

    @FXML
    private void handleLoginButton() {
        String user = userInput.getText();
        String pass = passInput.getText();

        if (service.checkLogin(user, pass)){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/arbitru.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1,900,500));
                Arbitru arb=service.findArbitru(user);
                stage.setTitle("User: Arbitru  Nume: "+arb.getName());
                ArbitruController arbitruController = fxmlLoader.getController();
                arbitruController.setService(service,arb,this);

                server.login(arb,arbitruController);

                stage.show();
                loginStage.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        else{
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("BAD CREDENTIALS");
            alert.show();
        }
    }


    @Override
    public void rezultatAdaugat(Rezultat r) throws Exception {
        System.out.println("Pas 1\n");
        server.addRezultat(r);
    }

    @Override
    public void loggedIn(Arbitru user) {

    }

    @Override
    public void refresh(Rezultat r) {

    }
}
