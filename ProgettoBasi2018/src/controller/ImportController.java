package controller;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import DB.ImportFileQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;

public class ImportController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboTabs;

    @FXML
    private Button importBtn;

    @FXML
    private TextField pathField;

    @FXML
    private Button btnChoose;

    @FXML
    private Hyperlink backBtn;

    @FXML
    void doImportBtn(ActionEvent event) throws Exception{
        ImportFileQuery ifq = new ImportFileQuery();
        boolean b;
        String tab = comboTabs.getValue();
        String path = pathField.getText();
        if (path.equals("") || tab == null){
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Errore");
            alertError.setHeaderText("Impossibile Continuare");
            alertError.setContentText("Inserisci tutte le informazioni\nper proseguire.");
            alertError.show();
            return;
        }

        switch (tab)    {
            case "Contorni Filamenti":
                b = ifq.puntiContorniImport(path);
                checkImportError(b);
                break;

            case "Filamenti":
                b = ifq.filamentiImport(path);
                checkImportError(b);
                break;

            case "Punti Segmenti":
                b = ifq.puntiSegmentiImport(path);
                checkImportError(b);
                break;
            case "Segmenti":
                b = ifq.segmentiImport(path);
                checkImportError(b);
                break;

            case "Stelle":
                b = ifq.stelleImport(path);
                checkImportError(b);
                break;
            default:
        }
    }

    @FXML
    void doBtnChoose(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);
        if(selectedFile != null)    {
            System.out.println("Is Read allow : " + selectedFile.canRead());
            String pathName = selectedFile.getAbsolutePath();
            pathField.setText(pathName);
            String ext = FilenameUtils.getExtension(pathName);
            if (!ext.equals("csv"))   {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Import failed!");
                alert.setContentText("An error was occurred during the import of the file! You've selected a file with wrong extension! It must be .csv!");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void doBackBtn(ActionEvent event) {
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

    public void checkImportError(boolean b) {
        if(!b) {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Error Dialog");
            alertError.setHeaderText("Errore");
            alertError.setContentText("Impossibile importare questo file."); //perché ad esempio i campi della tabella non corrispondono (?)
            alertError.show();
        }
    }

    @FXML
    void initialize() {
        ObservableList<String> listFile = FXCollections.observableArrayList("Contorni Filamenti"
                , "Filamenti", "Punti Segmenti", "Segmenti", "Stelle");
        comboTabs.setItems(listFile);
        assert backBtn != null : "fx:id=\"satelliteBackBtn\" was not injected: check your FXML file 'ImportaFile.fxml'.";
        assert comboTabs != null : "fx:id=\"comboTabs\" was not injected: check your FXML file 'ImportFile.fxml'.";
        assert importBtn != null : "fx:id=\"importBtn\" was not injected: check your FXML file 'ImportFile.fxml'.";
        assert btnChoose != null : "fx:id=\"btnChoose\" was not injected: check your FXML file 'ImportaFile.fxml'.";
        assert pathField != null : "fx:id=\"pathField\" was not injected: check your FXML file 'ImportaFile.fxml'.";
    }
}

