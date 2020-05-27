package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import DB.RicercaFilPerIDeNomeQuery;
import Entity.Filamento;
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

public class RicercaFilPerIDeNomeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Hyperlink backBtn;

    @FXML
    private TextField tfNomeIDFil;

    @FXML
    private ComboBox<String> cbResearch;

    @FXML
    private Button btnResearch;

    @FXML
    private TableView<Filamento> tabResult;

    @FXML
    private TableColumn<Filamento, Integer> idTab;

    @FXML
    private TableColumn<Filamento, String> nomeTab;

    @FXML
    private TableColumn<Filamento, Double> longTab;

    @FXML
    private TableColumn<Filamento, Double> latTab;

    @FXML
    private TableColumn<Filamento, Double> extLongTab;

    @FXML
    private TableColumn<Filamento, Double> extLatTab;

    @FXML
    private TableColumn<Filamento, Integer> segmRelTab;

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

    @FXML
    public void doBtnResearch(ActionEvent event) {
        if(!controlloDati()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Impossibile Procedere");
            alert.setContentText("Inserire tutti i dati!");
            alert.showAndWait();
        }
        RicercaFilPerIDeNomeQuery rf = new RicercaFilPerIDeNomeQuery();
        String tab = cbResearch.getValue();
        String value = tfNomeIDFil.getText();
        int idfil;
        if (tab.equals("Nome"))  {
            idfil = rf.getIdFilFromName(value);
        }else if (tab.equals("ID"))  {
            try {
                idfil = Integer.parseInt(value);
            }catch(Exception e)   {
                //scrivere alert per l'utente
                showAlert(2);
                return;
            }
        }
        else    {
            showAlert(1);
            return;
        }
        Filamento fil = rf.getCentroideAndEstensione(idfil);
        if (fil==null)  {
            System.out.println("Ho un fil nullo!");
            showAlert(1);
            return;
        }

        ObservableList<Filamento> list = FXCollections.observableArrayList(fil);
        tabResult.setItems(list);
    }

    public void showAlert(int i){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        switch (i){
            case 1:
                //nessun filamento trovato
                alert.setHeaderText("Nessun Filamento Trovato");
                alert.setContentText("Non esiste nessun filamento corrispondente\nal nome o all'id inserito");
                alert.showAndWait();
            case 2:
                //valore nell textview non valido (se volevo fare una ricerca per id ma ho messo una stringa)
                alert.setHeaderText("Impossibile Procedere");
                alert.setContentText("Inserisci un valore corretto\nper procedere con la ricerca");
                alert.showAndWait();
            default:
                return;
        }
    }

    public boolean controlloDati() {

        if (tfNomeIDFil.getText().equalsIgnoreCase("")) {
            return false;
        }
        if (cbResearch.getSelectionModel().isEmpty()) {
            return false;
        }
        return true;
    }

    @FXML
    void initialize() {
        ObservableList<String> listFile = FXCollections.observableArrayList("ID", "Nome");
        cbResearch.setItems(listFile);
        tabResult.setEditable(true);
        idTab.setCellValueFactory(new PropertyValueFactory<Filamento, Integer>("idfil"));
        nomeTab.setCellValueFactory(new PropertyValueFactory<Filamento, String>("name"));
        latTab.setCellValueFactory(new PropertyValueFactory<Filamento, Double>("latCentroide"));
        longTab.setCellValueFactory(new PropertyValueFactory<Filamento, Double>("longCentroide"));
        extLatTab.setCellValueFactory(new PropertyValueFactory<Filamento, Double>("estensioneLat"));
        extLongTab.setCellValueFactory(new PropertyValueFactory<Filamento, Double>("estensioneLong"));
        segmRelTab.setCellValueFactory(new PropertyValueFactory<Filamento, Integer>("numeroSegmenti"));
        assert backBtn != null : "fx:id=\"backBtn\" was not injected: check your FXML file 'RicercaFilPerIDeNome.fxml'.";
        assert tfNomeIDFil != null : "fx:id=\"tfNomeIDFil\" was not injected: check your FXML file 'RicercaFilPerIDeNome.fxml'.";
        assert cbResearch != null : "fx:id=\"cbResearch\" was not injected: check your FXML file 'RicercaFilPerIDeNome.fxml'.";
        assert btnResearch != null : "fx:id=\"btnResearch\" was not injected: check your FXML file 'RicercaFilPerIDeNome.fxml'.";
        assert tabResult != null : "fx:id=\"tabResult\" was not injected: check your FXML file 'RicercaFilPerIDeNome.fxml'.";
        assert idTab != null : "fx:id=\"idTab\" was not injected: check your FXML file 'RicercaFilPerIDeNome.fxml'.";
        assert nomeTab != null : "fx:id=\"nomeTab\" was not injected: check your FXML file 'RicercaFilPerIDeNome.fxml'.";
        assert longTab != null : "fx:id=\"longTab\" was not injected: check your FXML file 'RicercaFilPerIDeNome.fxml'.";
        assert latTab != null : "fx:id=\"latTab\" was not injected: check your FXML file 'RicercaFilPerIDeNome.fxml'.";
        assert extLongTab != null : "fx:id=\"extLongTab\" was not injected: check your FXML file 'RicercaFilPerIDeNome.fxml'.";
        assert extLatTab != null : "fx:id=\"extLatTab\" was not injected: check your FXML file 'RicercaFilPerIDeNome.fxml'.";
        assert segmRelTab != null : "fx:id=\"segmRelTab\" was not injected: check your FXML file 'RicercaFilPerIDeNome.fxml'.";

    }
}
