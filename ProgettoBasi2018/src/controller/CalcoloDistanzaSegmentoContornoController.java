package controller;

import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import DB.CalcoloDistanzaSegmentoContornoQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CalcoloDistanzaSegmentoContornoController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Hyperlink backBtn;

    @FXML
    private TextField tfSegm;

    @FXML
    private Button calculateBtn;

    @FXML
    private Label lbMinV;

    @FXML
    private Label lbMaxV;

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
    void doCalculate(ActionEvent event) throws SQLException {
        if(!controlloDati()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Impossibile Procedere");
            alert.setContentText("Inserire l'id di un segmento!");
            alert.showAndWait();
        }
        CalcoloDistanzaSegmentoContornoQuery calcoloDistanzaSegmentoContornoQuery = new CalcoloDistanzaSegmentoContornoQuery();
        Integer idbranch = Integer.parseInt(tfSegm.getText());
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        if (idbranch == null) {
            System.out.println("Ho un fil nullo!");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Nessun Segmento Trovato");
            alert.setContentText("Non esiste nessun segmento corrispondente\nall'id inserito");
            alert.showAndWait();
            return;
        } else {
            double minV = calcoloDistanzaSegmentoContornoQuery.calcoloDistanzaMinima(idbranch);
            double maxV = calcoloDistanzaSegmentoContornoQuery.calcoloDistanzaMassima(idbranch);
            lbMinV.setText("Distanza dal vertice minimo = " + df.format(minV));
            lbMaxV.setText("Distanza dal vertice massimo = " + df.format(maxV));
        }
    }

    public boolean controlloDati() {

        if (tfSegm.getText().equalsIgnoreCase("")) {
            return false;
        }
        return true;
    }

    @FXML
    void initialize() {
        assert backBtn != null : "fx:id=\"backBtn\" was not injected: check your FXML file 'CalcoloDistanzaSegmentiContorno.fxml'.";
        assert tfSegm != null : "fx:id=\"tfSegm\" was not injected: check your FXML file 'CalcoloDistanzaSegmentiContorno.fxml'.";
        assert calculateBtn != null : "fx:id=\"calculateBtn\" was not injected: check your FXML file 'CalcoloDistanzaSegmentiContorno.fxml'.";
        assert lbMinV != null : "fx:id=\"lbMinV\" was not injected: check your FXML file 'CalcoloDistanzaSegmentiContorno.fxml'.";
        assert lbMaxV != null : "fx:id=\"lbMaxV\" was not injected: check your FXML file 'CalcoloDistanzaSegmentiContorno.fxml'.";

    }
}




