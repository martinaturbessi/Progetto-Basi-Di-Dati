package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomePageAdminController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Hyperlink btnLogout;

    @FXML
    private Hyperlink importFileBtn;

    @FXML
    private Hyperlink registerNewUserBtn;

    @FXML
    private Hyperlink satelliteBtn;

    @FXML
    private Hyperlink instrumentBtn;

    @FXML
    private Hyperlink filIDBtn;

    @FXML
    private Hyperlink filContrastoBtn;

    @FXML
    private Hyperlink filSegmentiBtn;

    @FXML
    private Hyperlink filRegioneBtn;

    @FXML
    private Hyperlink stellaInFilBtn;

    @FXML
    private Hyperlink stellaRegioneBtn;

    @FXML
    private Hyperlink stellaSpinaBtn;

    @FXML
    private Hyperlink segmContornoBtn;

    @FXML
    void doImportFileBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ImportaFile.fxml"));
            Parent anchorParent = loader.load();
            Scene anchorScene = new Scene(anchorParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(anchorScene);
            window.show();
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Errore nel caricamento del file fxml");
        }
    }

    @FXML
    void doInstrumentBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/InsertStrumento.fxml"));
            Parent anchorParent = loader.load();
            Scene anchorScene = new Scene(anchorParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(anchorScene);
            window.show();
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Errore nel caricamento del file fxml");
        }
    }

    @FXML
    void doRegisterNewUserBtn(ActionEvent event)    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RegistrazioneUtente.fxml"));
            Parent anchorParent = loader.load();
            Scene anchorScene = new Scene(anchorParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(anchorScene);
            window.show();
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Errore nel caricamento del file fxml");
        }
    }

    @FXML
    void doSatelliteBtn(ActionEvent event)  {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/InsertSatellite.fxml"));
            Parent anchorParent = loader.load();
            Scene anchorScene = new Scene(anchorParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(anchorScene);
            window.show();
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Errore nel caricamento del file fxml");
        }
    }

    @FXML
    void doFilamentoIDBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RicercaFilPerIDeNome.fxml"));
            Parent anchorParent = loader.load();
            Scene anchorScene = new Scene(anchorParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(anchorScene);
            window.show();
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Errore nel caricamento del file fxml");
        }
    }

    @FXML
    void doFilContrastoBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RicercaPerContrastoEllitticita.fxml"));
            Parent anchorParent = loader.load();
            Scene anchorScene = new Scene(anchorParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(anchorScene);
            window.show();
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Errore nel caricamento del file fxml");
        }
    }

    @FXML
    void doFilRegioneBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RicercaFilInRegione.fxml"));
            Parent anchorParent = loader.load();
            Scene anchorScene = new Scene(anchorParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(anchorScene);
            window.show();
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Errore nel caricamento del file fxml");
        }
    }

    @FXML
    void doFilSegmentiBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RicercaPerSegmenti.fxml"));
            Parent anchorParent = loader.load();
            Scene anchorScene = new Scene(anchorParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(anchorScene);
            window.show();
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Errore nel caricamento del file fxml");
        }
    }

    @FXML
    void doStellaInFilBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RicercaStelleInFilamenti.fxml"));
            Parent anchorParent = loader.load();
            Scene anchorScene = new Scene(anchorParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(anchorScene);
            window.show();
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Errore nel caricamento del file fxml");
        }
    }

   @FXML
    void doStellaRegioneBtn(ActionEvent event) {
       try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RicercaStelleInRettangolo.fxml"));
           Parent anchorParent = loader.load();
           Scene anchorScene = new Scene(anchorParent);
           Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
           window.setScene(anchorScene);
           window.show();
       }
       catch(IOException e){
           e.printStackTrace();
           System.out.println("Errore nel caricamento del file fxml");
       }
    }

    @FXML
    void doStellaSpinaBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CalcoloDistanzaStellaSpinaDorsale.fxml"));
            Parent anchorParent = loader.load();
            Scene anchorScene = new Scene(anchorParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(anchorScene);
            window.show();
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Errore nel caricamento del file fxml");
        }
    }

    @FXML
    void doSegmContornoBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CalcoloDistanzaSegmentoContorno.fxml"));
            Parent anchorParent = loader.load();
            Scene anchorScene = new Scene(anchorParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(anchorScene);
            window.show();
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Errore nel caricamento del file fxml");
        }
    }

    @FXML
    void doBtnLogout(ActionEvent event) {
        try {
            Parent anchorParent = FXMLLoader.load((getClass().getResource("/view/Login.fxml")));
            Scene login_scene = new Scene(anchorParent);
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

            window.setScene(login_scene);
            window.show();
        }catch(IOException e)   {
            e.printStackTrace();
            System.out.println("Errore nel caricamento del file .fxml");
        }
    }

    @FXML
    void initialize() {
        assert btnLogout != null : "fx:id=\"btnLogout\" was not injected: check your FXML file 'HomeAmministratore.fxml'.";
        assert importFileBtn != null : "fx:id=\"importFileBtn\" was not injected: check your FXML file 'HomeAmministratore.fxml'.";
        assert registerNewUserBtn != null : "fx:id=\"registerNewUserBtn\" was not injected: check your FXML file 'HomeAmministratore.fxml'.";
        assert satelliteBtn != null : "fx:id=\"satelliteBtn\" was not injected: check your FXML file 'HomeAmministratore.fxml'.";
        assert instrumentBtn != null : "fx:id=\"instrumentBtn\" was not injected: check your FXML file 'HomeAmministratore.fxml'.";
        assert filIDBtn != null : "fx:id=\"filIDBtn\" was not injected: check your FXML file 'HomeAmministratore.fxml'.";
        assert filContrastoBtn != null : "fx:id=\"filContrastoBtn\" was not injected: check your FXML file 'HomeAmministratore.fxml'.";
        assert filSegmentiBtn != null : "fx:id=\"filSegmentiBtn\" was not injected: check your FXML file 'HomeAmministratore.fxml'.";
        assert filRegioneBtn != null : "fx:id=\"filRegioneBtn\" was not injected: check your FXML file 'HomeAmministratore.fxml'.";
        assert stellaInFilBtn != null : "fx:id=\"stellaInFilBtn\" was not injected: check your FXML file 'HomeAmministratore.fxml'.";
        assert stellaRegioneBtn != null : "fx:id=\"stellaRegioneBtn\" was not injected: check your FXML file 'HomeAmministratore.fxml'.";
        assert stellaSpinaBtn != null : "fx:id=\"stellaSpinaBtn\" was not injected: check your FXML file 'HomeAmministratore.fxml'.";
    }

}
