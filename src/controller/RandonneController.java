package controller;

import com.sothawo.mapjfx.*;
import com.sothawo.mapjfx.event.MapViewEvent;
import event.ErreurApplicationEvent;
import io.jenetics.jpx.Route;
import io.jenetics.jpx.WayPoint;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Difficulte;
import model.FicheTechnique;
import model.Randonnee;
import utilitaire.ProcessGPX;
import view.ViewListeImage;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

import static java.lang.Math.min;
import static java.util.Objects.requireNonNull;

public class RandonneController implements Initializable {

    private final RandonneeSubController rsc;

    private final SplitPane vue;
    private Randonnee randonnee;

    private MapPisteController mpc;

    private boolean anti_casse_couille = true;

    public RandonneController(Randonnee randonnee) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/xmls/RandonnePrecis.fxml"));
        this.vue = fxmlLoader.load();

        this.rsc = fxmlLoader.getController();
        if (rsc == null) {
            vue.fireEvent(new ErreurApplicationEvent(ErreurApplicationEvent.ERREUR_APPLICATION,"Erreur lors du chargement"));
            return;
        }
        this.randonnee = requireNonNull(randonnee);

        if (randonnee.getListecheminsimages()!=null) {
            rsc.setImagelist(new ViewListeImage(randonnee));
        }else {

            rsc.setImagelist(new ViewListeImage());
        }

        //Description de la randonnée
        rsc.setDescription(randonnee.getDesc());
        rsc.getDescription().setWrappingWidth(500);

        rsc.getTitre().setText(randonnee.getTitre());
        FicheTechnique fichetec= randonnee.getFicheTechnique();

        //Gestion Durée
        rsc.getDuree().setText(fichetec.getDuree());

        //Gestion Distance
        int dist = fichetec.getDistance();
        if (dist<1000) {
            rsc.getDistance().setText(dist + "m");
        } else {
            double distD = dist - (dist%100);
            distD = distD/1000.0;
            rsc.getDistance().setText(distD + "km");
        }

        //Gestion de la diff
        Difficulte diff= fichetec.getDifficulte();
        rsc.getDifficulte().setText(diff.toString());
        ImageView imagediff = rsc.getImagediff();
        if (diff==Difficulte.facile){
            imagediff.setImage(new Image("resources/images/izi.png"));
        }else if (diff==Difficulte.intermediaire){
            imagediff.setImage(new Image("resources/images/medi.png"));
        }else if (diff== Difficulte.normal){
            imagediff.setImage(new Image("resources/images/normi.png"));
        }else{
            imagediff.setImage(new Image("resources/images/death.png"));
        }

        //Gestion des Départs et arrivées
        // TODO mieux afficher les coords
        double nd = fichetec.getDepart().getLatitude();
        double ed = fichetec.getDepart().getLongitude();
        double na = fichetec.getArrivee().getLatitude();
        double ea = fichetec.getArrivee().getLongitude();
        DecimalFormat f = new DecimalFormat();
        f.setMaximumFractionDigits(6);

        rsc.getDepart().setText("          N " + f.format(nd) + ", E " + f.format(ed));
        rsc.getArrivee().setText("          N " + f.format(na) + ", E " + f.format(ea));

        //Gestion des dénivelés
        int temp = fichetec.getDen_pos();
        int temp2 = fichetec.getDen_neg();
        if (temp!=-1){
            rsc.getDenpos().setText(temp + "m");
        }
        if (temp2!=-1){
            rsc.getDenneg().setText(temp2 + "m");
        }

        setVue(randonnee, min(temp,temp2));
    }

    public void setVue(Randonnee randonnee, int denivErro){
        //setup du SplitPane
        mpc = new MapPisteController();
        rsc.getCarteEtGraphe().getItems().add(mpc.getCarte());

        rsc.getCarteEtGraphe().setOrientation(Orientation.VERTICAL);
        rsc.getCarteEtGraphe().setDividerPositions(60.00);

        //Gerer la carte
        //On va la centrer sur le depart

        //Setup du graphe
        if(denivErro!=-1) {
            Route route = randonnee.getGpx().getRoutes().get(0);
            List<WayPoint> points = route.getPoints();
            LineChart<Number, Number> graphe = randonnee.getFicheTechnique().calculGraph(points);
            rsc.getCarteEtGraphe().getItems().add(graphe);
        }

        EventHandler<MapViewEvent> eventBullshit = event -> {
            if(anti_casse_couille) {
                List<Coordinate> coordinates = null;
                try {
                    coordinates = ProcessGPX.getCoords(randonnee.getGpx());
                } catch (NoSuchElementException e){
                    e.printStackTrace();
                    mpc.getCarte().fireEvent(new ErreurApplicationEvent(ErreurApplicationEvent.ERREUR_APPLICATION_CRITIQUE,"Erreur lors du chargement des coordonnées"));
                }
                mpc.afficher_piste(coordinates);
                mpc.getCarte().setExtent(Extent.forCoordinates(coordinates));
                mpc.getCarte().setZoom(mpc.getCarte().getZoom()*0.95);
                anti_casse_couille = false;
            }
        };
        List<Coordinate> coordinates = null;
        try {
            coordinates = ProcessGPX.getCoords(randonnee.getGpx());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            mpc.getCarte().fireEvent(new ErreurApplicationEvent(ErreurApplicationEvent.ERREUR_APPLICATION_CRITIQUE,"Erreur lors du chargement des coordonnées"));
        }
        mpc.getCarte().addEventHandler(MapViewEvent.MAP_POINTER_MOVED,eventBullshit);
        assert coordinates != null;
        mpc.getCarte().setCenter(coordinates.get(0));
    }

    public SplitPane getVue(){
        return vue;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
