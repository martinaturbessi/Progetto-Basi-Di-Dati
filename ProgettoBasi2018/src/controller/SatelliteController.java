package controller;

import java.io.IOException;
import java.net.URL;
//import java.sql.Date;
import java.sql.SQLException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;

import DB.SatelliteQuery;
import Entity.Satellite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class SatelliteController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tf_nomeSat;

    @FXML
    private DatePicker dpPrimaOss;

    @FXML
    private CheckBox checkConclusa;

    @FXML
    private DatePicker dpTermineAtt;

    @FXML
    private Button insertBtn;

    @FXML
    private Hyperlink satelliteBackBtn;

    @FXML
    void doInsertBtn(ActionEvent event) {
        String nomeSat = tf_nomeSat.getText();
        LocalDate primaOss = dpPrimaOss.getValue();
        LocalDate termineAtt = dpTermineAtt.getValue();
        if (controlloDati(nomeSat)==false) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Dati Sbagliati!");
            alert.setContentText("I dati inseriti non sono corretti, ricontrollare!");
            alert.showAndWait();
        }

        //todo Controllo dati corretti

        if (findSatellite(nomeSat) == true)    {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Registrazione fallita!");
            alert.setContentText("Satellite già registrato!");

            alert.showAndWait();
        }
        else    {
            aggiungiSatellite(nomeSat, primaOss, termineAtt);
        }
    }


    @FXML
    void doSatelliteBackBtn(ActionEvent event) {
        try {
            Parent anchorParent = FXMLLoader.load((getClass().getResource("/view/HomeAmministratore.fxml")));
            Scene login_scene = new Scene(anchorParent);
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

            window.setScene(login_scene);
            window.show();
        }catch(IOException e)   {
            e.printStackTrace();
            System.out.println("Errore nel caricamento del file .fxml");
        }
    }

    public boolean controlloDati(String nomesat) {

        if (nomesat.length() == 0) {
            return false;
        }

        if (dpPrimaOss.getValue().equals("")) {
            return false;
        }

        if (checkConclusa.isSelected()) {

            if (dpPrimaOss.getValue().equals("")) {
                return false;
            }

            /*SimpleDateFormat f = new SimpleDateFormat("yyyy/mm/dd");

            LocalDate after = dpPrimaOss.getValue();
            LocalDate before = dpTermineAtt.getValue();

            java.util.Date inizio = f.parse(primaoss);
            java.util.Date fine =  f.parse(termineoss);

            if (fine.after(inizio)) {
                return true;
            }
            if (fine.before(inizio)) {
                return false;
            }*/
        }
        return true;
    }


    @FXML
    void initialize() {
        assert tf_nomeSat != null : "fx:id=\"tf_nomeSat\" was not injected: check your FXML file 'InsertSatellite.fxml'.";
        assert dpPrimaOss != null : "fx:id=\"dpPrimaOss\" was not injected: check your FXML file 'InsertSatellite.fxml'.";
        assert checkConclusa != null : "fx:id=\"checkConclusa\" was not injected: check your FXML file 'InsertSatellite.fxml'.";
        assert dpTermineAtt != null : "fx:id=\"dpTermineAtt\" was not injected: check your FXML file 'InsertSatellite.fxml'.";
        assert insertBtn != null : "fx:id=\"insertBtn\" was not injected: check your FXML file 'InsertSatellite.fxml'.";
        assert satelliteBackBtn != null : "fx:id=\"satelliteBackBtn\" was not injected: check your FXML file 'InsertSatellite.fxml'.";
    }

    public boolean findSatellite(String nomeSat) {
        boolean b = false;
        SatelliteQuery query = new SatelliteQuery();
        try {
            b = query.findSatellite(nomeSat);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }

    public Satellite aggiungiSatellite(String nomeSat, LocalDate primaOss, LocalDate termineAtt) {

        Satellite s = null;
        SatelliteQuery query = new SatelliteQuery();
        try {
            s = query.aggiungiSatellite(nomeSat, primaOss, termineAtt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return s;

    }
}

