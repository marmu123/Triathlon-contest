import controllers.LoginController;
import domain.Arbitru;
import domain.Proba;
import domain.Rezultat;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.*;
import service.Service;
import utils.TipProba;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class MainJavaFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        Properties serverProps=new Properties();
        try {
            serverProps.load(new FileReader("bd.config"));
            //System.setProperties(serverProps);

            System.out.println("Properties set. ");
            //System.getProperties().list(System.out);
            serverProps.list(System.out);
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
            return;
        }


        IRepository<String, Arbitru> repoArbitri=new JdbcRepositoryArbitru(serverProps);
        IRepository<Integer, Proba> repoProbe=new JdbcRepositoryProba(serverProps);
        FilterRepositoryParticipant repoParticipanti=new JdbcRepositoryParticipant(serverProps);
        FilterRepositoryRezultat repoRezultate=new JdbcRepositoryRezultat(serverProps);
        System.out.println("Nr. arbitri: "+repoArbitri.size());
        System.out.println("Nr. probe: "+repoProbe.size());
        //repoParticipanti.findAllForAProbe(TipProba.CICLISM).forEach(System.out::println);
        System.out.println("Nr.rezultate: "+ repoRezultate.size());
        Service service=new Service(repoArbitri,repoProbe,repoRezultate,repoParticipanti);


        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/login.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(anchorPane));
            LoginController controllerLogin = fxmlLoader.getController();
            controllerLogin.setService(service,stage);
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}