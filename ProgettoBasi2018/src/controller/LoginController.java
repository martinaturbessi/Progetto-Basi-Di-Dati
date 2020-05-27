package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import DB.UtenteQuery;
import Entity.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button loginBtn;

    @FXML
    private TextField userID;

    @FXML
    private TextField pass;

    @FXML
    void initialize() {
        assert loginBtn != null : "fx:id=\"loginBtn\" was not injected: check your FXML file 'Untitled'.";
        assert userID != null : "fx:id=\"userID\" was not injected: check your FXML file 'Untitled'.";
        assert pass != null : "fx:id=\"pass\" was not injected: check your FXML file 'Untitled'.";
    }

    public static String role;

    public static void roleSet(Utente utente) {

        if (utente.getRuolo().equalsIgnoreCase("admin"))    {
            role = "admin";
        }
        else    {
            role = "user";
        }
    }

    @FXML
    void doLoginBtn(ActionEvent event) throws Exception{
        String username = userID.getText();
        String password = pass.getText();
        UtenteQuery uq = new UtenteQuery();
        Utente u = uq.cercaUtente(username, password);
        if (u == null)  {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Errore");
            alert.setContentText("Nome utente o password errati.");
            System.out.println("Utente non registrato");
            Optional<ButtonType> result = alert.showAndWait();
            return;
        }
        String ruolo = u.getRuolo();

        if (ruolo.equalsIgnoreCase("admin")) {
            roleSet(u);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HomeAmministratore.fxml"));
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
        else if (ruolo.equalsIgnoreCase("user")){
            roleSet(u);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HomeUtente.fxml"));
                Parent anchorParent = loader.load();
                Scene anchorScene = new Scene(anchorParent);
                Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                window.setScene(anchorScene);
                window.show();
            }
            catch(IOException e){
                e.printStackTrace();
                System.out.println("Errore nel caricamento del file fxml");
            }
        }
    }

    public static LoginController instance;

    public static LoginController getInstance() {
        if (instance == null)
            instance = new LoginController();
        return instance;
    }

    public LoginController() {

    }

    public Utente login(String username, String password) throws SQLException {
        Utente u = null;
        UtenteQuery utenteQuery = new UtenteQuery();
        if (controlloDati(username, password)) {
            u = utenteQuery.cercaUtente(username, password);
            if (u == null) {
                return u;
            }
            return u;
        }
        return u;
    }

    public boolean controlloDati(String username, String password) {
        if (username.equals("") || password.equals("")){
            return false;
        }
        return true;

    }

    public static String getRoleString()    {
        return role;
    }
}

