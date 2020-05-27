package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DB.StrumentoQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StrumentoController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Hyperlink backBtn;

    @FXML
    private TextField tf_nomeStrumento;

    @FXML
    private TextField tf_nomeSat;

    @FXML
    private TextField tf_bande;

    @FXML
    private Button insertBtnStr;

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

    @FXML
    void doInsertBtn(ActionEvent event) throws SQLException {
        String nome = tf_nomeStrumento.getText();
        String nome_sat = tf_nomeSat.getText();
        Double b = Double.parseDouble(tf_bande.getText());
        StrumentoQuery sq = new StrumentoQuery();
        if (nome == null || nome_sat == null || b == null)  {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Registrazione fallita!");
            alert.setContentText("Inserire tutti i valori!");
            alert.showAndWait();
        }
        else    {
            if (sq.findStrumentoBanda(nome, nome_sat, b) == true)    {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Registrazione fallita!");
                alert.setContentText("Strumento già registrato!");

                alert.showAndWait();
            }else {
                sq.aggiungiStrumentoBanda(nome, nome_sat, b);
            }


        }
    }

    @FXML
    void initialize() {
        assert backBtn != null : "fx:id=\"backBtn\" was not injected: check your FXML file 'InsertStrumento.fxml'.";
        assert tf_nomeStrumento != null : "fx:id=\"tf_nomeStrumento\" was not injected: check your FXML file 'InsertStrumento.fxml'.";
        assert tf_nomeSat != null : "fx:id=\"tf_nomeSat\" was not injected: check your FXML file 'InsertStrumento.fxml'.";
        assert tf_bande != null : "fx:id=\"tf_bande\" was not injected: check your FXML file 'InsertStrumento.fxml'.";
        assert insertBtnStr != null : "fx:id=\"insertBtnStr\" was not injected: check your FXML file 'InsertStrumento.fxml'.";

    }
}