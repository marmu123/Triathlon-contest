package controllers;

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
import service.Service;

public class LoginController {
    @FXML
    private TextField userInput;
    @FXML private PasswordField passInput;
    @FXML private Button loginButton;
    Service service;
    private Stage loginStage;

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

    public void setService(Service s,Stage stage){
        this.service=s;
        this.loginStage=stage;
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
                ArbitruController arbitruController = fxmlLoader.getController();
                arbitruController.setService(service,service.findArbitru(user));
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


}
