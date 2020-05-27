package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import DB.RicercaFilInRegioneQuery;
import Entity.PuntoContorno;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class RicercaFilInRegioneController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Hyperlink backBtn;

    @FXML
    private TextField tfLat;

    @FXML
    private TextField tfLongi;

    @FXML
    private ComboBox<String> cbRegione;

    @FXML
    private TextField tfMisura;

    @FXML
    private Button btnResearch;

    @FXML
    private TableView<PuntoContorno> tabFilamenti;

    @FXML
    private TableColumn<PuntoContorno, Double> columnFilamenti;

    @FXML
    void doOnClickCb(ActionEvent event) {
        String regione = cbRegione.getValue();
        if (regione.equalsIgnoreCase("Cerchio")) {
            tfMisura.setPromptText("Inserire il raggio");
        }
        else if (regione.equalsIgnoreCase("Quadrato")) {
            tfMisura.setPromptText("Inserire il lato");
        }
        else {
            //showAlert(1);
            return;
        }
    }

    @FXML

    void doResearchBtn(ActionEvent event) throws SQLException {
        if(!controlloDati() ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Impossibile Procedere");
            alert.setContentText("Inserire tutti i dati!");
            alert.showAndWait();
        }
        double lat = Double.parseDouble(tfLat.getText());
        double longi = Double.parseDouble(tfLongi.getText());
        String regione = cbRegione.getValue();
        RicercaFilInRegioneQuery ricercaOggettiInRegioneQuery = new RicercaFilInRegioneQuery();
        if (regione.equalsIgnoreCase("Cerchio")) {
            double raggio = Double.parseDouble(tfMisura.getText());
            ArrayList<PuntoContorno> fil = ricercaOggettiInRegioneQuery.ricercaFilamentiCerchio(lat, longi, raggio);
            if (fil == null) {
                System.out.println("Ho un fil nullo!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Nessun filamento trovato!");
                alert.setContentText("Nessun filamento trovato corrispondente alla ricerca effettuata!");
                alert.showAndWait();
                return;
            }
            ObservableList<PuntoContorno> list = FXCollections.observableArrayList(fil);
            tabFilamenti.setItems(list);
        } else if (regione.equalsIgnoreCase("Quadrato")) {
            double lato = Double.parseDouble(tfMisura.getText());
            ArrayList<PuntoContorno> fil = ricercaOggettiInRegioneQuery.ricercaFilamentiQuadrato(lat, longi, lato);
            if (fil == null) {
                System.out.println("Ho un fil nullo!");
                System.out.println("Ho un fil nullo!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Nessun filamento trovato!");
                alert.setContentText("Nessun filamento trovato corrispondente alla ricerca effettuata!");
                alert.showAndWait();
                return;
            }
            ObservableList<PuntoContorno> list = FXCollections.observableArrayList(fil);
            tabFilamenti.setItems(list);
        }
    }

    @FXML
    void doBackBtn(ActionEvent event) {
        try {
            LoginController l = new LoginController();
            String searcha = "admin";
            String searchu = "user";
            if (l.getRoleString().equalsIgnoreCase(searcha)) {
                Parent anchorParent = FXMLLoader.load((getClass().getResource("/view/HomeAmministratore.fxml")));
                Scene login_scene = new Scene(anchorParent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                window.setScene(login_scene);
                window.show();
            }
            else if (l.getRoleString().equalsIgnoreCase(searchu))    {
                Parent anchorParent = FXMLLoader.load((getClass().getResource("/view/HomeUtente.fxml")));
                Scene login_scene = new Scene(anchorParent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                window.setScene(login_scene);
                window.show();
            }
        }catch(IOException e)   {
            e.printStackTrace();
            System.out.println("Errore nel caricamento del file .fxml");
        }
    }

    public boolean controlloDati() {

        if (tfLat.getText().equalsIgnoreCase("")) {
            return false;
        }
        if (tfLongi.getText().equalsIgnoreCase("")) {
            return false;
        }
        if (tfMisura.getText().equalsIgnoreCase("")) {
            return false;
        }
        if (cbRegione.getSelectionModel().isEmpty()) {
            return false;
        }
        return true;
    }

    @FXML
    void initialize() {
        ObservableList<String> listFile = FXCollections.observableArrayList("Cerchio", "Quadrato");
        cbRegione.setItems(listFile);
        tabFilamenti.setEditable(true);
        columnFilamenti.setCellValueFactory(new PropertyValueFactory<>("idfil"));
        assert backBtn != null : "fx:id=\"backBtn\" was not injected: check your FXML file 'RicercaFilInRegione.fxml'.";
        assert tfLat != null : "fx:id=\"tfLat\" was not injected: check your FXML file 'RicercaFilInRegione.fxml'.";
        assert tfLongi != null : "fx:id=\"tfLongi\" was not injected: check your FXML file 'RicercaFilInRegione.fxml'.";
        assert cbRegione != null : "fx:id=\"cbRegione\" was not injected: check your FXML file 'RicercaFilInRegione.fxml'.";
        assert tfMisura != null : "fx:id=\"tfMisura\" was not injected: check your FXML file 'RicercaFilInRegione.fxml'.";
        assert btnResearch != null : "fx:id=\"btnResearch\" was not injected: check your FXML file 'RicercaFilInRegione.fxml'.";
        assert tabFilamenti != null : "fx:id=\"tabFilamenti\" was not injected: check your FXML file 'RicercaFilInRegione.fxml'.";
        assert columnFilamenti != null : "fx:id=\"columnFilamenti\" was not injected: check your FXML file 'RicercaFilInRegione.fxml'.";

    }
}

