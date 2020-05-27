package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import DB.RicercaPerSegmentiQuery;
import Entity.PuntiSegmenti;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class RicercaPerSegmentiController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Hyperlink backBtn;

    @FXML
    private TextField tfMin;

    @FXML
    private TextField tfMax;

    @FXML
    private Button ricercaBtn;

    @FXML
    private TableView<PuntiSegmenti> filamentiTab;

    @FXML
    private TableColumn<PuntiSegmenti, Integer> filColumn;

    private int rowsPerPage = 20;
    RicercaPerSegmentiQuery ricercaPerSegmentiQuery = new RicercaPerSegmentiQuery();

    @FXML
    void doResearchBtn(ActionEvent event) throws SQLException {
        int min = Integer.parseInt(tfMin.getText());
        int max = Integer.parseInt(tfMax.getText());


        if (min < 2) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Impossibile Procedere");
            alert.setContentText("Inserire in Minimo un valore maggiore di 2!");
            alert.showAndWait();
        } else if (min > max) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Impossibile Procedere");
            alert.setContentText("Non è possibile inserire un valore minimo maggiore del massimo!");
            alert.showAndWait();
        } else {
            ArrayList<PuntiSegmenti> segm = ricercaPerSegmentiQuery.getNumeroSegmenti(min, max);
            if (segm == null) {
                System.out.println("Ho un fil nullo!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Nessun Filamento Trovato");
                alert.setContentText("Non esiste nessun filamento corrispondente ai valori inseriti inserito!");
                alert.showAndWait();
                return;
            }
            ObservableList<PuntiSegmenti> list = FXCollections.observableArrayList(segm);
            FilteredList<PuntiSegmenti> filteredList = new FilteredList<PuntiSegmenti>(list, filamento -> segm.indexOf(filamento) < rowsPerPage);
            filamentiTab.setItems(filteredList);
        }
    }

    @FXML
    void doNextBtn(ActionEvent event)   throws SQLException {
        int min = Integer.parseInt(tfMin.getText());
        int max = Integer.parseInt(tfMax.getText());

        ArrayList<PuntiSegmenti> segm = ricercaPerSegmentiQuery.getNumeroSegmenti(min, max);
        if (segm == null) {
            System.out.println("Ho un filamento nullo!");
            return;
        }
        ObservableList<PuntiSegmenti> list = FXCollections.observableArrayList(segm.subList(rowsPerPage,segm.size()));
        rowsPerPage = rowsPerPage + 20;
        //creo una lista filtrata basata sulla observablelist in modo da limitarne le entry
        FilteredList<PuntiSegmenti> filteredList = new FilteredList<PuntiSegmenti>(list, filamento -> segm.indexOf(filamento) < rowsPerPage);
        filamentiTab.setItems(filteredList);
    }

    @FXML

    void doBtnBack(ActionEvent event) {
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

    @FXML

    void initialize() {
            filamentiTab.setEditable(true);
            filColumn.setCellValueFactory(new PropertyValueFactory<PuntiSegmenti, Integer>("idfil"));
            assert backBtn != null : "fx:id=\"backBtn\" was not injected: check your FXML file 'RicercaPerSegmenti.fxml'.";
            assert tfMin != null : "fx:id=\"tfMin\" was not injected: check your FXML file 'RicercaPerSegmenti.fxml'.";
            assert tfMax != null : "fx:id=\"tfMax\" was not injected: check your FXML file 'RicercaPerSegmenti.fxml'.";
            assert ricercaBtn != null : "fx:id=\"ricercaBtn\" was not injected: check your FXML file 'RicercaPerSegmenti.fxml'.";
            assert filamentiTab != null : "fx:id=\"filamentiTab\" was not injected: check your FXML file 'RicercaPerSegmenti.fxml'.";
            assert filColumn != null : "fx:id=\"filColumn\" was not injected: check your FXML file 'RicercaPerSegmenti.fxml'.";
    }
}