package controller;

import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import DB.RicercaStelleInFilamentiQuery;
import Entity.Stella;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RicercaStelleInFilamentiController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Hyperlink backBtn;

    @FXML
    private TextField tfFil;

    @FXML
    private Button btnResearch;

    @FXML
    private Label lbStelle;

    @FXML
    private PieChart piePercentuale;

    @FXML
    private Label lbProto;

    @FXML
    private Label lbPre;

    @FXML
    private Label lbUnb;

    @FXML
    void doBtnResearch(ActionEvent event) throws SQLException {
        RicercaStelleInFilamentiQuery rsq = new RicercaStelleInFilamentiQuery();
        int idfil = Integer.parseInt(tfFil.getText());
        int i;
        double protostellar_perc;
        double prestellar_perc;
        double unbound_perc;
        double protostellar = 0;
        double prestellar = 0;
        double unbound = 0;

        ArrayList<Stella> stelle = rsq.FindStelleInFilamento(idfil);
        //System.out.println("stelle trovate = " + stelle.size());

        if (!rsq.findFilamento(idfil)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Nessun Filamento Trovato");
            alert.setContentText("Non esiste nessun filamento corrispondente\nalla ricerca effettuata");
            alert.showAndWait();
        }else {
            if(stelle == null){
                System.out.println("Nessuna stella trovata");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Nessuna Stella Trovata!");
                alert.setContentText("Non esiste nessuna stella corrispondente alla ricerca effettuata");
                alert.showAndWait();
            }
            String stelleTrovate = "Stelle trovate : ";
            System.out.println(stelleTrovate + stelle.size());

            double totStelle = stelle.size();
            lbStelle.setText("Stelle trovate: " + totStelle);

            //Contiamo quante stelle abbiamo per ogni tipo
            for(i=0; i < totStelle - 1; i++) {
                Stella s = stelle.get(i);
                System.out.println(s.getType());
                switch(s.getType()) {
                    case "PROTOSTELLAR" :
                        protostellar++;
                        break;
                    case "PRESTELLAR" :
                        prestellar++;
                        break;
                    case "UNBOUND" :
                        unbound++;
                        break;
                }
            }

            //Calcoliamo le percentuali dei diversi tipi di stelle e le mostriamo tramite un grafico a torta
            protostellar_perc = (protostellar / totStelle)*100;
            prestellar_perc = (prestellar / totStelle)*100;
            unbound_perc = (unbound / totStelle)*100;
            /*System.out.println("percentuale unbound: " + unbound_perc);
            System.out.println("percentuale proto: " + protostellar_perc);
            System.out.println("percentuale pre: " + prestellar_perc);*/
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Protostellar" , protostellar_perc),
                    new PieChart.Data("Prestellar" , prestellar_perc),
                    new PieChart.Data("Unbound" , unbound_perc));
            piePercentuale.setData(pieChartData);
            piePercentuale.setStartAngle(90);
            piePercentuale.setTitle("Percentuali");

            DecimalFormat df = new DecimalFormat("#.###");
            df.setRoundingMode(RoundingMode.CEILING);
            lbProto.setText(df.format(protostellar_perc)+ "%");
            lbPre.setText(df.format(prestellar_perc)+ "%");
            lbUnb.setText(df.format(unbound_perc)+ "%");

            //piePercentuale.set
            //todo Per settare il colore del pieChart


        }
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Loading failed!");
            alert.setContentText("An error was occurred during the load of the .fxml file!");
            alert.showAndWait();
        }
    }


    @FXML
    void initialize() {
        assert backBtn != null : "fx:id=\"backBtn\" was not injected: check your FXML file 'RicercaStelleInFilamenti.fxml'.";
        assert tfFil != null : "fx:id=\"tfFil\" was not injected: check your FXML file 'RicercaStelleInFilamenti.fxml'.";
        assert btnResearch != null : "fx:id=\"btnResearch\" was not injected: check your FXML file 'RicercaStelleInFilamenti.fxml'.";
        assert lbStelle != null : "fx:id=\"lbStelle\" was not injected: check your FXML file 'RicercaStelleInFilamenti.fxml'.";
        assert piePercentuale != null : "fx:id=\"piePercentuale\" was not injected: check your FXML file 'RicercaStelleInFilamenti.fxml'.";
        assert lbProto != null : "fx:id=\"lbProto\" was not injected: check your FXML file 'RicercaStelleInFilamenti.fxml'.";
        assert lbPre != null : "fx:id=\"lbPre\" was not injected: check your FXML file 'RicercaStelleInFilamenti.fxml'.";
        assert lbUnb != null : "fx:id=\"lbUnb\" was not injected: check your FXML file 'RicercaStelleInFilamenti.fxml'.";
    }
}
