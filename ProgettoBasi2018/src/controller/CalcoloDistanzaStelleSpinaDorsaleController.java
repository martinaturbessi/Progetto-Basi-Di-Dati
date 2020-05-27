package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import DB.CalcoloDistanzaStelleSpinaDorsaleQuery;
import DB.RicercaStelleInFilamentiQuery;
import Entity.PuntiSegmenti;
import Entity.Stella;
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

public class CalcoloDistanzaStelleSpinaDorsaleController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Hyperlink backBtn;

    @FXML
    private TextField tfFil;

    @FXML
    private RadioButton rbBtnDist;

    @FXML
    private RadioButton rdBtnFlux;

    @FXML
    private Button btnCalculate;

    @FXML
    private TableView<Stella> tabStelle;

    @FXML
    private TableColumn<Stella, Integer> columnStelle;

    @FXML
    private TableColumn<Stella, Double> columnDistanza;

    @FXML
    private TableColumn<Stella, Double> columnFlux;

    @FXML
    private Button nextBtn;

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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Loading failed!");
            alert.setContentText("An error was occurred during the load of the .fxml file!");
            alert.showAndWait();
        }
    }

    private int rowsPerPage = 20;

    @FXML
    void doBtnCalculate(ActionEvent event) throws SQLException {
        if (tfFil.getText().equalsIgnoreCase("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Impossibile Procedere");
            alert.setContentText("Inserire un filamento!");
            alert.showAndWait();
        } else {
            Integer idfil = Integer.parseInt(tfFil.getText());
            ArrayList<Stella> star = distanzaStelleSpina(idfil);
            //System.out.println(star);

            if (rdBtnFlux.isSelected()) {
                orderStars(star, "flux");
            }
            else if (rbBtnDist.isSelected())    {
                orderStars(star, "distance");
            }
            else    {
                System.out.println("Selezionare una scelta. ");
            }

            ObservableList<Stella> list = FXCollections.observableArrayList(star);
            FilteredList<Stella> filteredList = new FilteredList<Stella>(list, filamento -> star.indexOf(filamento) < rowsPerPage);
            //SortedList<Stella> sortedData = new SortedList<>(list);
            //sortedData.comparatorProperty().bind(tabStelle.comparatorProperty());
            tabStelle.setItems(filteredList);
        }

    }

    public ArrayList<Stella> distanzaStelleSpina(int idfil) throws SQLException {
        RicercaStelleInFilamentiQuery rsq = new RicercaStelleInFilamentiQuery();
        CalcoloDistanzaStelleSpinaDorsaleQuery dist = new CalcoloDistanzaStelleSpinaDorsaleQuery();
        ArrayList<Stella> stelle = rsq.FindStelleInFilamento(idfil);
        ArrayList<Stella> result = new ArrayList<>();
        ArrayList<PuntiSegmenti> punti = dist.getPuntiBranch(idfil);
        for (int i = 0; i < stelle.size(); i++) {
            double distMin = Integer.MAX_VALUE;
            for (int j = 0; j < punti.size(); j++) {
                double distanza = Math.sqrt(
                        Math.pow((stelle.get(i).getLat() - punti.get(j).getGlatBr()), 2.0) + Math
                                .pow((stelle.get(i).getLongi() - punti.get(j).getGlonBr()), 2.0));
                if (distanza < distMin) {
                    distMin = distanza;
                }
            }
            Stella s = new Stella(stelle.get(i).getIdstar(), distMin, stelle.get(i).getFlux());
            result.add(s);
        }
        return result;

    }

    @FXML
    void doNextBtn(ActionEvent event) throws SQLException {
        Integer idfil = Integer.parseInt(tfFil.getText());
        if (idfil == null) {
            //show alert
        }
        ArrayList<Stella> star = distanzaStelleSpina(idfil);
        if (rdBtnFlux.isSelected()) {
            orderStars(star, "flux");
        }
        else if (rbBtnDist.isSelected())    {
            orderStars(star, "distance");
        }
        else    {
            System.out.println("Selezionare una scelta. ");
        }

        ObservableList<Stella> list = FXCollections.observableArrayList(star.subList(rowsPerPage,star.size()));
        rowsPerPage = rowsPerPage + 20;
        //creo una lista filtrata basata sulla observablelist in modo da limitarne le entry
        FilteredList<Stella> filteredList = new FilteredList<Stella>(list, filamento -> star.indexOf(filamento) < rowsPerPage);
        tabStelle.setItems(filteredList);
    }

    public static ArrayList<Stella> orderStars(ArrayList<Stella> stars, String type) {
        switch (type) {
            case "distance":
                DistanceComparator d_c = new DistanceComparator();
                stars.sort(d_c);
                break;
            case "flux":
                FluxComparator f_c = new FluxComparator();
                stars.sort(f_c);
                break;
        }

        return stars;
    }

    @FXML
    void initialize() {
        tabStelle.setEditable(true);
        columnStelle.setCellValueFactory(new PropertyValueFactory<Stella,Integer>("idstar"));
        columnFlux.setCellValueFactory(new PropertyValueFactory<Stella,Double>("flux"));
        columnDistanza.setCellValueFactory(new PropertyValueFactory<Stella,Double>("distance"));

        assert backBtn != null : "fx:id=\"backBtn\" was not injected: check your FXML file 'CalcoloDistanzaStellaSpinaDorsale.fxml'.";
        assert tfFil != null : "fx:id=\"tfFil\" was not injected: check your FXML file 'CalcoloDistanzaStellaSpinaDorsale.fxml'.";
        assert rbBtnDist != null : "fx:id=\"rbBtnDist\" was not injected: check your FXML file 'CalcoloDistanzaStellaSpinaDorsale.fxml'.";
        assert rdBtnFlux != null : "fx:id=\"rdBtnFlux\" was not injected: check your FXML file 'CalcoloDistanzaStellaSpinaDorsale.fxml'.";
        assert btnCalculate != null : "fx:id=\"btnCalculate\" was not injected: check your FXML file 'CalcoloDistanzaStellaSpinaDorsale.fxml'.";
        assert tabStelle != null : "fx:id=\"tabStelle\" was not injected: check your FXML file 'CalcoloDistanzaStellaSpinaDorsale.fxml'.";
        assert columnStelle != null : "fx:id=\"columnStelle\" was not injected: check your FXML file 'CalcoloDistanzaStellaSpinaDorsale.fxml'.";
        assert columnDistanza != null : "fx:id=\"columnDistanza\" was not injected: check your FXML file 'CalcoloDistanzaStellaSpinaDorsale.fxml'.";
        assert columnFlux != null : "fx:id=\"columnFlux\" was not injected: check your FXML file 'CalcoloDistanzaStellaSpinaDorsale.fxml'.";
        assert nextBtn != null : "fx:id=\"nextBtn\" was not injected: check your FXML file 'CalcoloDistanzaStellaSpinaDorsale.fxml'.";

    }
}
