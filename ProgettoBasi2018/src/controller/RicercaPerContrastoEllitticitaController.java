package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import DB.RicercaPerContrastoEllitticitaQuery;
import Entity.Filamento;
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

public class RicercaPerContrastoEllitticitaController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Hyperlink backBtn;

    @FXML
    private TextField tfBrillanza;

    @FXML
    private ComboBox<Integer> cbMin;

    @FXML
    private ComboBox<Integer> cbMax;

    @FXML
    private Button researchBtn;

    @FXML
    private Button nextBtn;

    @FXML
    private TableView<Filamento> filamentiTab;

    @FXML
    private TableColumn<Filamento, Integer> filColumn;

    public RicercaPerContrastoEllitticitaController() throws SQLException {
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


    private int rowsPerPage = 20;
    RicercaPerContrastoEllitticitaQuery ricercaPerContrastoElliticitaQuery = new RicercaPerContrastoEllitticitaQuery();


    @FXML
    public void doResearchBtn(ActionEvent event) throws SQLException {
        if(!controlloDati()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Impossibile Procedere");
            alert.setContentText("Inserire tutti i dati!");
            alert.showAndWait();
        }
        Double brillanza= Double.parseDouble(tfBrillanza.getText());
        int min = cbMin.getValue();
        int max = cbMax.getValue();

        if (brillanza < 0 || min > max) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Impossibile Procedere");
            alert.setContentText("Devi inserire un valore della brillanza maggiore di zero o un valore corretto di minimo e massimo!");
            alert.showAndWait();
        } else {
            ArrayList<Filamento> fil = ricercaPerContrastoElliticitaQuery.getNumerofilamenti(brillanza, min, max);
            if (fil == null) {
                System.out.println("Ho un filamento nullo!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Nessun filamento trovato!");
                alert.setContentText("Nessun filamento trovato corrispondente alla ricerca effettuata!");
                alert.showAndWait();
                return;
            }
            ObservableList<Filamento> list = FXCollections.observableArrayList(fil);
            //creo una lista filtrata basata sulla observablelist in modo da limitarne le entry
            FilteredList<Filamento> filteredList = new FilteredList<Filamento>(list, filamento -> fil.indexOf(filamento) < rowsPerPage);
            filamentiTab.setItems(filteredList);
        }
    }


    @FXML
    void doNextBtn(ActionEvent event)   throws SQLException {
        Double brillanza= Double.parseDouble(tfBrillanza.getText());
        int min = cbMin.getValue();
        int max = cbMax.getValue();

        ArrayList<Filamento> fil = ricercaPerContrastoElliticitaQuery.getNumerofilamenti(brillanza, min, max);
        if (fil == null) {
            System.out.println("Ho un filamento nullo!");
            return;
        }
        ObservableList<Filamento> list = FXCollections.observableArrayList(fil.subList(rowsPerPage,fil.size()));
        rowsPerPage = rowsPerPage + 20;
        //creo una lista filtrata basata sulla observablelist in modo da limitarne le entry
        FilteredList<Filamento> filteredList = new FilteredList<Filamento>(list, filamento -> fil.indexOf(filamento) < rowsPerPage);
        filamentiTab.setItems(filteredList);
    }

    public boolean controlloDati() {

        if (tfBrillanza.getText().equalsIgnoreCase("")) {
            return false;
        }
        if (cbMin.getSelectionModel().isEmpty()) {
            return false;
        }
        if (cbMax.getSelectionModel().isEmpty()) {
            return false;
        }
        return true;
    }

    @FXML
    void initialize() {
        ObservableList<Integer> listFileMin = FXCollections.observableArrayList(2, 3, 4, 5, 6, 7, 8, 9);
        cbMin.setItems(listFileMin);
        ObservableList<Integer> listFileMax = FXCollections.observableArrayList(2, 3, 4, 5, 6, 7, 8, 9);
        cbMax.setItems(listFileMax);
        filamentiTab.setEditable(true);
        filColumn.setCellValueFactory(new PropertyValueFactory<>("idfil"));
        assert backBtn != null : "fx:id=\"backBtn\" was not injected: check your FXML file 'RicercaPerContrastoEllitticita.fxml'.";
        assert tfBrillanza != null : "fx:id=\"tfBrillantezza\" was not injected: check your FXML file 'RicercaPerContrastoEllitticita.fxml'.";
        assert cbMin != null : "fx:id=\"cbMin\" was not injected: check your FXML file 'RicercaPerContrastoEllitticita.fxml'.";
        assert cbMax != null : "fx:id=\"cbMax\" was not injected: check your FXML file 'RicercaPerContrastoEllitticita.fxml'.";
        assert researchBtn != null : "fx:id=\"researchBtn\" was not injected: check your FXML file 'RicercaPerContrastoEllitticita.fxml'.";
        assert nextBtn != null : "fx:id=\"nextBtn\" was not injected: check your FXML file 'RicercaPerContrastoEllitticita.fxml'.";
        assert filamentiTab != null : "fx:id=\"filamentiTab\" was not injected: check your FXML file 'RicercaPerContrastoEllitticita.fxml'.";
        assert filColumn != null : "fx:id=\"filColumn\" was not injected: check your FXML file 'RicercaPerContrastoEllitticita.fxml'.";
    }
}

