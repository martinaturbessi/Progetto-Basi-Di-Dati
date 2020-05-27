package controller;

import DB.UtenteQuery;
import Entity.Utente;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegisterController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tf_nome;

    @FXML
    private TextField tf_cognome;

    @FXML
    private TextField tf_userID;

    @FXML
    private TextField tf_email;

    @FXML
    private TextField tf_password;

    @FXML
    private TextField tf_confermaPass;

    @FXML
    private Button btn_registra;

    @FXML
    private Hyperlink btn_back;

    @FXML
    private CheckBox adminRole;

    @FXML
    void doBtnRegistra(ActionEvent event) {
        String nome = tf_nome.getText();
        String cognome = tf_cognome.getText();
        String username = tf_userID.getText();
        String password = tf_password.getText();
        String confermaPassword = tf_confermaPass.getText();
        String email = tf_email.getText();
        //Controllo delle credenziali per la registrazione
        if(controlloCredenziali(nome, cognome, username, password, confermaPassword, email) == false) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Credenziali Sbagliate!");
            alert.setContentText("Le credenziali inserite non solo corrette, ricontrollare!");
            alert.showAndWait();
        }
        else    {
            if (findUtente(tf_userID.getText()) == true)    {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Registrazione fallita!");
                alert.setContentText("Utente già registrato!");

                alert.showAndWait();
            }
            else    {
                if (adminRole.isSelected()) {
                    aggiungiUtente(nome,cognome,username,password,email,"admin");
                }
                else    {
                    aggiungiUtente(nome,cognome,username,password,email,"user");
                }
            }
        }
    }

    public boolean controlloCredenziali(String nome, String cognome, String username, String password,
                                        String ripPassword, String email) {

        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        if (nome.length() == 0) {
            return false;
        }
        if (nome.matches(".*\\d+.*")) {
            return false;
        }
        if (cognome.matches(".*\\d+.*")) {
            return false;
        }
        if (cognome.length() == 0) {
            return false;
        }
        if (username.length() < 6) {
            return false;
        }
        if (!email.matches(emailPattern)) {
            return false;
        }
        if (password.length() < 6) {
            return false;
        }
        if (!ripPassword.equals(password)) {
            return false;
        }
        return true;
    }

    @FXML
    void doBtnBack(ActionEvent event) {
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
    void initialize() {
        assert tf_nome != null : "fx:id=\"tf_nome\" was not injected: check your FXML file 'RegistrazioneUtente.fxml'.";
        assert tf_cognome != null : "fx:id=\"tf_cognome\" was not injected: check your FXML file 'RegistrazioneUtente.fxml'.";
        assert tf_userID != null : "fx:id=\"tf_userID\" was not injected: check your FXML file 'RegistrazioneUtente.fxml'.";
        assert tf_email != null : "fx:id=\"tf_email\" was not injected: check your FXML file 'RegistrazioneUtente.fxml'.";
        assert tf_password != null : "fx:id=\"tf_password\" was not injected: check your FXML file 'RegistrazioneUtente.fxml'.";
        assert tf_confermaPass != null : "fx:id=\"tf_confermaPass\" was not injected: check your FXML file 'RegistrazioneUtente.fxml'.";
        assert btn_registra != null : "fx:id=\"btn_registra\" was not injected: check your FXML file 'RegistrazioneUtente.fxml'.";
        assert adminRole != null : "fx:id=\"adminRole\" was not injected: check your FXML file 'RegistrazioneUtente.fxml'.";
        assert btn_back != null : "fx:id=\"btn_back\" was not injected: check your FXML file 'RegistrazioneUtente.fxml'.";
    }

    public boolean findUtente(String username) {
        boolean b = false;
        UtenteQuery query = new UtenteQuery();
        try {
            b = query.findUtente(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }

    public Utente aggiungiUtente(String nome, String cognome, String username, String password, String email,
                                 String ruolo) {

        Utente u = null;
        UtenteQuery query = new UtenteQuery();
        try {
            u = query.aggiungiUtente(nome, cognome, username, email, password, ruolo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;

    }
}


