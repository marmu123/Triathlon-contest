//package controllers;
//
//import domain.Arbitru;
//import domain.Participant;
//import domain.Rezultat;
//import javafx.application.Platform;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.beans.value.ObservableListValue;
//import javafx.beans.value.ObservableValue;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.*;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.util.Callback;
//import service.IObserver;
//import service.IServices;
//import service.Service;
//import utils.TipProba;
//
//import java.io.Serializable;
//import java.rmi.RemoteException;
//import java.rmi.server.UnicastRemoteObject;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.StreamSupport;
//
//public class ArbitruController extends UnicastRemoteObject implements IObserver, Serializable {
//
//
//    @FXML public TableColumn<Participant,String> mainTableColumnNume;
//    @FXML public TableColumn<Participant,String> mainTableColumnPunctajTotal;
//
//    @FXML public TableColumn<Participant,String> natatieTableColumnNume;
//    @FXML public TableColumn<Participant,String> natatieTableColumnPunctaj;
//
//    @FXML public TableView<Participant> mainTableView;
//    @FXML public TableView<Participant> natatieTableView;
//    @FXML public TableView<Participant> alergareTableView;
//    @FXML public TableView<Participant> ciclismTableView;
//
//    @FXML public TableColumn<Participant,String> ciclismTableColumnNume;
//    @FXML public TableColumn<Participant,String> ciclismTableColumnPunctaj;
//
//    @FXML public TableColumn<Participant,String> alergareTableColumnNume;
//    @FXML public TableColumn<Participant,String> alergareTableColumnPunctaj;
//    @FXML public Button buttonAddRezultat;
//    @FXML public TextField textFieldNrPuncte;
//    @FXML public ComboBox<String> comboTipProba;
//
//
//    ObservableList<Participant> mainModel= FXCollections.observableArrayList();
//    ObservableList<Participant> natatieModel= FXCollections.observableArrayList();
//    ObservableList<Participant> ciclismModel= FXCollections.observableArrayList();
//    ObservableList<Participant> alergareModel= FXCollections.observableArrayList();
//    Service service;
//    LoginController loginController;
//    Arbitru arbitru;
//
//    public ArbitruController() throws RemoteException {
//    }
//
//    public void setService(Service service,Arbitru arbitru,LoginController loginController){
//        this.service=service;
//        this.arbitru=arbitru;
//        this.loginController=loginController;
//        initModel();
//    }
//
//    public void initModel(){
//        List<Participant> mainList= StreamSupport.stream(service.findAllParticipanti().spliterator(),false)
//                .collect(Collectors.toList());
//        mainModel.setAll(mainList);
//
//        List<Participant> natatieList=StreamSupport.stream(service.findAllForAProbe(TipProba.NATATIE).spliterator(),false)
//                .collect(Collectors.toList());
//        natatieModel.setAll(natatieList);
//
//        List<Participant> ciclismList=StreamSupport.stream(service.findAllForAProbe(TipProba.CICLISM).spliterator(),false)
//                .collect(Collectors.toList());
//
//        ciclismModel.setAll(ciclismList);
//        List<Participant> alergareList=StreamSupport.stream(service.findAllForAProbe(TipProba.ALERGARE).spliterator(),false)
//                .collect(Collectors.toList());
//        alergareModel.setAll(alergareList);
//
//        natatieTableView.getSortOrder().clear();
//        natatieTableColumnPunctaj.setSortType(TableColumn.SortType.DESCENDING);
//        natatieTableView.getSortOrder().add(natatieTableColumnPunctaj);
//        natatieTableView.sort();
//
//        ciclismTableView.getSortOrder().clear();
//        ciclismTableColumnPunctaj.setSortType(TableColumn.SortType.DESCENDING);
//        ciclismTableView.getSortOrder().add(ciclismTableColumnPunctaj);
//        ciclismTableView.sort();
//
//        alergareTableView.getSortOrder().clear();
//        alergareTableColumnPunctaj.setSortType(TableColumn.SortType.DESCENDING);
//        alergareTableView.getSortOrder().add(alergareTableColumnPunctaj);
//        alergareTableView.sort();
//    }
//
//
//    @FXML
//    public void initialize(){
//        mainTableColumnNume.setCellValueFactory(new PropertyValueFactory<>("nume"));
//        mainTableColumnPunctajTotal.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Participant, String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue<String> call(TableColumn.CellDataFeatures<Participant, String> param) {
//                return new SimpleStringProperty(Double.toString(service.getPunctajTotalParticipant(param.getValue().getNume())));
//            }
//        });
//        mainTableView.setItems(mainModel);
//
//
//        natatieTableColumnNume.setCellValueFactory(new PropertyValueFactory<>("nume"));
//        natatieTableColumnPunctaj.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Participant, String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue<String> call(TableColumn.CellDataFeatures<Participant, String> param) {
//                return new SimpleStringProperty(Double.toString(service.getPunctajParticipantPentruOProba(param.getValue().getNume(),"NATATIE")));
//            }
//        });
//        natatieTableView.setItems(natatieModel);
//
//        ciclismTableColumnNume.setCellValueFactory(new PropertyValueFactory<>("nume"));
//        ciclismTableColumnPunctaj.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Participant, String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue<String> call(TableColumn.CellDataFeatures<Participant, String> param) {
//                return new SimpleStringProperty(Double.toString(service.getPunctajParticipantPentruOProba(param.getValue().getNume(),"CICLISM")));
//            }
//        });
//        ciclismTableView.setItems(ciclismModel);
//
//        alergareTableColumnNume.setCellValueFactory(new PropertyValueFactory<>("nume"));
//        alergareTableColumnPunctaj.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Participant, String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue<String> call(TableColumn.CellDataFeatures<Participant, String> param) {
//                return new SimpleStringProperty(Double.toString(service.getPunctajParticipantPentruOProba(param.getValue().getNume(),"ALERGARE")));
//            }
//        });
//        alergareTableView.setItems(alergareModel);
//
//        ObservableList<String> obss=FXCollections.observableArrayList();
//        List<String> obssList=new ArrayList<>();
//        obssList.add("NATATIE");
//        obssList.add("CICLISM");
//        obssList.add("ALERGARE");
//        obss.setAll(obssList);
//        comboTipProba.setItems(obss);
//
//
//
//
//    }
//
//    @FXML public void handleAddRezultat(ActionEvent actionEvent) throws Exception {
//        String err="";
//        if(textFieldNrPuncte.getText().equals(""))
//            err+="Introduceti punctajul\n";
//
//        try {
//            Double.parseDouble(textFieldNrPuncte.getText());
//        } catch(NumberFormatException e){
//            err+="Punctajul trebuie sa fie un numar\n";
//        }
//        if(mainTableView.getSelectionModel().getSelectedItem()==null)
//            err+="Alegeti un participant din tabel\n";
//        if(!err.equals("")) {
//            Alert alert=new Alert(Alert.AlertType.ERROR);
//            alert.setContentText(err);
//            alert.show();
//        }
//        else {
//            Rezultat r=service.addRezultat(mainTableView.getSelectionModel().getSelectedItem().getNume(),arbitru.getName(),TipProba.valueOf(comboTipProba.getValue()),Double.parseDouble(textFieldNrPuncte.getText()));
//            loginController.rezultatAdaugat(r);
//            refresh(r);
//            //rezultatAdaugat(r);
//        }
//
//
//    }
//
//    @Override
//    public void rezultatAdaugat(Rezultat r) throws Exception {
//        System.out.println("Arbitru controller rez adaugat\n");
//        //initModel();
//        refresh(r);
//    }
//
//    @Override
//    public void loggedIn(Arbitru user) {
//
//    }
//
//    @Override
//    public void refresh(Rezultat r) {
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                initModel();
//            }
//        });
//    }
//}
//
