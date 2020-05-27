package controller;

import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import DB.InclusioneQuery;
import DB.RicercaStelleInRettangoloQuery;
import Entity.PuntoContorno;
import Entity.Stella;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class RicercaStelleInRettangoloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnResearch;

    @FXML
    private TextField tfLatCentr;

    @FXML
    private TextField tfLongCentr;

    @FXML
    private TextField tfLato_lat;

    @FXML
    private TextField tfLato_long;

    @FXML
    private PieChart chartInFil;

    @FXML
    private PieChart chartNotInFil;

    @FXML
    private Label lblUnboundInFil;

    @FXML
    private Label lblPrestellarInFil;

    @FXML
    private Label lblProtostellarInFil;

    @FXML
    private Label lblUnboundNotInFil;

    @FXML
    private Label lblPrestellarNotInFil;

    @FXML
    private Label lblProtostellarNotInFil;

    @FXML
    public void researchInRectangle(ActionEvent event) {
        ArrayList<Stella> stelle = new ArrayList<>();
        ArrayList<Stella> result = new ArrayList<>();
        float lato_lat;
        float lato_long;
        double lat_centroide;
        double long_centroide;

        chartInFil.setVisible(false);
        chartNotInFil.setVisible(false);

        try{
            lato_lat = Float.parseFloat(tfLato_lat.getText());
            lato_long = Float.parseFloat(tfLato_long.getText());
            lat_centroide = Double.parseDouble(tfLatCentr.getText());
            long_centroide = Double.parseDouble(tfLongCentr.getText());
        }catch (Exception e){
            showAlert();
            return;
        }

        stelle = RicercaStelleInRettangoloQuery.searchInRectangle(lat_centroide, long_centroide, lato_lat, lato_long);
        System.out.println("Numero di stelle trovate: " + stelle.size());

        ArrayList<Integer> filInRectangle = RicercaStelleInRettangoloQuery.getIdFilInRectangle(lat_centroide, long_centroide, lato_lat, lato_long);

        ArrayList<Stella> stelleInFil = new ArrayList<Stella>();
        ArrayList<Stella> stelleNotInFil = new ArrayList<Stella>();
        ArrayList<PuntoContorno> puntiById = new ArrayList<PuntoContorno>();

        double num;
        double den;

        double absolute;

        double st_L;
        double st_B;
        double c_Li;
        double c_Bi;
        double c_Li_plus;
        double c_Bi_plus;

        for (int i = 0; i < stelle.size(); i++){
                System.out.println("Controllando la stella con ID: " + stelle.get(i).getIdstar()
                        + " --- Longitudine: " + stelle.get(i).getLongi()
                        + " --- Latitudine: " + stelle.get(i).getLat());
            st_L = stelle.get(i).getLongi();
            st_B = stelle.get(i).getLat();
            loopToBreak:
            for(int j = 0; j < filInRectangle.size(); j++) { //scorro tutti i filamenti
                System.out.println("Filamento" + j);
                if (InclusioneQuery.isStarInInclusione(stelle.get(i).getIdstar())) { //verifica se la stella è all'interno di un
                    //filamento, controllando se è presente nella tabella inclusione
                    System.out.println("La Stella " + i + "° è all'interno di un filamento");
                    stelleInFil.add(stelle.get(i));
                    break loopToBreak;

                } else { //altrimenti applico la formula

                    int id = filInRectangle.get(j);
                    //prendo i punti di questo filamento
                    puntiById = RicercaStelleInRettangoloQuery.getPuntiByIdFil(id);

                    double sum = 0;
                    for (int k = 0; k <= puntiById.size() - 2; k++) {
                        c_Li = puntiById.get(k).getLongitudine();
                        c_Li_plus = puntiById.get(k + 1).getLongitudine();
                        c_Bi = puntiById.get(k).getLatitudine();
                        c_Bi_plus = puntiById.get(k + 1).getLatitudine();

                        num = (c_Li - st_L) * (c_Bi_plus - st_B) - (c_Bi - st_B) * (c_Li_plus - st_L);
                        den = (c_Li - st_L) * (c_Li_plus - st_L) + (c_Bi - st_B) * (c_Bi_plus - st_B);
                        sum += Math.atan((num) / (den));

                    }
                    //System.out.println("Sum is: " + sum);

                    absolute = Math.abs(Math.toRadians(sum));
                    //System.out.println("Absolute is: " + absolute);
                    if ((absolute >= 0.01)) {
                        //stelle.remove(i);
                        stelleInFil.add(stelle.get(i));
                        System.out.println("La Stella " + i + "° è all'interno di un filamento");
                        //inserisco la relazione nella tabella inclusione:
                        InclusioneQuery.updateInclusione(stelle.get(i).getIdstar(), filInRectangle.get(j));
                        break loopToBreak;
                    }

                    if (j == filInRectangle.size() - 1) {
                        stelleNotInFil.add(stelle.get(i));
                        System.out.println("La Stella " + i + "° NON è all'interno di NESSUN filamento");
                    }
                }
            }


            }

        initializePieCharts(stelleInFil, stelleNotInFil);
        //initializePieCharts2(properties, stelle.size());
    }


    public void initializePieCharts(ArrayList<Stella> stelleInFil, ArrayList<Stella> stelleNotInFil ){

        //splitto in due array
        //ArrayList<Stella> stelleInFil = splitArrayByInFil(stelle, true);
        //ArrayList<Stella> stelleNotInFil = splitArrayByInFil(stelle, false);

        //per ogni array mi faccio ritornare la percentuale per ogni tipo di stella
        double unboundInFil_perc = starTypePercentage(stelleInFil, "UNBOUND");
        double prestellarInFil_perc = starTypePercentage(stelleInFil, "PRESTELLAR");
        double protostellarInFil_perc = starTypePercentage(stelleInFil, "PROTOSTELLAR");

        double unboundNotInFil_perc = starTypePercentage(stelleNotInFil, "UNBOUND");
        double prestellarNotInFil_perc = starTypePercentage(stelleNotInFil, "PRESTELLAR");
        double protostellarNotInFil_perc = starTypePercentage(stelleNotInFil, "PROTOSTELLAR");

        System.out.println("Percentuale Unbound in un filamento : " + unboundInFil_perc);
        System.out.println("Percentuale Prestellar in un filamento: " + prestellarInFil_perc);
        System.out.println("Percentuale Protostellar in un filamento: " + protostellarInFil_perc);

        System.out.println("Percentuale Unbound NON in un filamento : " + unboundNotInFil_perc);
        System.out.println("Percentuale Prestellar NON in un filamento: " + prestellarNotInFil_perc);
        System.out.println("Percentuale Protostellar NON in un filamento: " + protostellarNotInFil_perc);

        chartInFil.setVisible(true);
        chartNotInFil.setVisible(true);
        lblUnboundInFil.setVisible(false);
        lblProtostellarInFil.setVisible(false);
        lblPrestellarInFil.setVisible(false);
        lblPrestellarNotInFil.setVisible(false);
        lblUnboundNotInFil.setVisible(false);
        lblProtostellarNotInFil.setVisible(false);

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        lblUnboundInFil.setText(df.format(unboundInFil_perc) + "%");
        lblUnboundNotInFil.setText(df.format(unboundNotInFil_perc) + "%");
        lblPrestellarInFil.setText(df.format(prestellarInFil_perc) + "%");
        lblPrestellarNotInFil.setText(df.format(prestellarNotInFil_perc) + "%");
        lblProtostellarInFil.setText(df.format(protostellarInFil_perc) + "%");
        lblProtostellarNotInFil.setText(df.format(protostellarNotInFil_perc) + "%");


        ObservableList<PieChart.Data> chartInFilData
                = FXCollections.observableArrayList(
                new PieChart.Data("Unbound", unboundInFil_perc),
                new PieChart.Data("Prestellar", prestellarInFil_perc),
                new PieChart.Data("Protostellar", protostellarInFil_perc));
        chartInFil.setData(chartInFilData);
        chartInFil.setStartAngle(90);

        ObservableList<PieChart.Data> chartNotInFilData
                = FXCollections.observableArrayList(
                new PieChart.Data("Unbound", unboundNotInFil_perc),
                new PieChart.Data("Prestellar", prestellarNotInFil_perc),
                new PieChart.Data("Protostellar", protostellarNotInFil_perc));
        chartNotInFil.setData(chartNotInFilData);
        chartNotInFil.setStartAngle(90);
    }



    public double starTypePercentage(ArrayList<Stella> stelle, String type){
        float count = 0;
        double percentuale;
        for (Stella star : stelle) {
            if(star.getType().equals(type)){
                count++;
            }
        }
        System.out.println("Numero di " + type + ": " + count);
        percentuale = (100 * count)/(stelle.size());

        return percentuale;
    }

    public void showAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Si è verificato un errore");
        alert.setHeaderText("Impossibile Procedere");
        alert.setContentText("Controlla di aver compilato correttamente\ntutti i campi per proseguire.");
        alert.showAndWait();
    }

    @FXML
    void initialize() {
        chartInFil.setVisible(false);
        chartNotInFil.setVisible(false);
        lblUnboundInFil.setVisible(false);
        lblProtostellarInFil.setVisible(false);
        lblPrestellarInFil.setVisible(false);
        lblPrestellarNotInFil.setVisible(false);
        lblUnboundNotInFil.setVisible(false);
        lblProtostellarNotInFil.setVisible(false);

        assert btnResearch != null : "fx:id=\"btnResearch\" was not injected: check your FXML file 'StelleInRettangolo.fxml'.";
        assert tfLatCentr != null : "fx:id=\"tfLatCentr\" was not injected: check your FXML file 'StelleInRettangolo.fxml'.";
        assert tfLongCentr != null : "fx:id=\"tfLongCentr\" was not injected: check your FXML file 'StelleInRettangolo.fxml'.";
        assert tfLato_lat != null : "fx:id=\"tfLato_lat\" was not injected: check your FXML file 'StelleInRettangolo.fxml'.";
        assert tfLato_long != null : "fx:id=\"tfLato_long\" was not injected: check your FXML file 'StelleInRettangolo.fxml'.";
        assert chartInFil != null : "fx:id=\"chartInFil\" was not injected: check your FXML file 'StelleInRettangolo.fxml'.";
        assert chartNotInFil != null : "fx:id=\"chartNotInFil\" was not injected: check your FXML file 'StelleInRettangolo.fxml'.";

    }
}
