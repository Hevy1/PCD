package view;

import com.sothawo.mapjfx.*;
import com.sothawo.mapjfx.event.MapViewEvent;
import event.ErreurApplicationEvent;
import event.ImportGPXEvent;
import io.jenetics.jpx.GPX;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import utilitaire.ProcessGPX;

import java.io.IOException;
import java.util.*;

public class ViewMapEtButtons extends BorderPane {

    private final MapView map;
    private final GridPane barre_boutons;
    private final Coordinate center;
    private Coordinate last_position ;
    private final List<Marker> selection;
    private CoordinateLine piste;
    private final Parent parent;

    boolean est_ferme = false;
    boolean est_tracee = false;

    public ViewMapEtButtons() throws IOException {
        super();

        center = new Coordinate(48.66908012420193, 6.1556505919368984);
        last_position = center;

        selection = new ArrayList<>();

        this.map = new MapView();
        this.setCenter(map);
        map.initialize();
        map.setCenter(center);
        /*
            MENU POUR IMPORTER UN GPX
         */

        parent = FXMLLoader.load(getClass().getResource("../resources/xmls/ImportGPX.fxml"));

        /*
            BUTTONS
        */

        barre_boutons = new GridPane();
        VBox button_VBox = new VBox();
        button_VBox.getChildren().addAll(barre_boutons,parent);
        Button centerEcole = new Button("Retour TN");
        centerEcole.setOnAction(event -> map.setCenter(center));
        barre_boutons.add(centerEcole,0,0);

        Button fermer_ouvrir = new Button("fermer la boucle");
        fermer_ouvrir.setOnAction(event -> {
            if (est_ferme){
                fermer_ouvrir.setText("fermer la boucle");
            } else {
                fermer_ouvrir.setText("ouvrir la boucle");
            }
            est_ferme = !est_ferme;
            if (est_tracee){
                map.removeCoordinateLine(piste);
                piste.setClosed(est_ferme);
                map.addCoordinateLine(piste);
            }
        });
        barre_boutons.add(fermer_ouvrir,1,0);

        Button print_piste = new Button("Afficher Piste");
        print_piste.setOnAction(event -> afficher_piste());
        barre_boutons.add(print_piste,0,2);

        Button recommencer = new Button("Recommencer");
        recommencer.setOnAction(event -> effacer_piste());
        barre_boutons.add(recommencer,1,2);

        /*
            EVENTS
         */

        map.addEventHandler(MapViewEvent.MAP_CLICKED, event -> {
            event.consume();
            last_position = event.getCoordinate();
            ajouter_position(last_position);
        });

        map.addEventHandler(MapViewEvent.MAP_RIGHTCLICKED, event -> {
            map.removeMarker(selection.get(selection.size()-1));
            selection.remove(selection.size()-1);
            if(selection.size() > 1) {
                Marker buffer = selection.remove(selection.size() - 1);
                map.removeMarker(buffer);
                Marker marker = Marker.createProvided(Marker.Provided.GREEN).setPosition(buffer.getPosition()).setVisible(true);
                map.addMarker(marker);
                selection.add(marker);
            }
        });

        //Chargement du GPX sur la randonnée
        parent.addEventHandler(ImportGPXEvent.GPX_IMPORTED, event -> {
            GPX gpx = event.getGpx();
            //System.out.println("Event déclenché au niveau de Map");
            effacer_piste();

            List<Coordinate> coordinates = null;
            try {
                coordinates = ProcessGPX.getCoords(gpx);
            } catch (NoSuchElementException e) {
                this.fireEvent(new ErreurApplicationEvent(ErreurApplicationEvent.ERREUR_APPLICATION,"Fichier gpx incompatible: Pas de chemin ou trace identifiée"));
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                this.fireEvent(new ErreurApplicationEvent(ErreurApplicationEvent.ERREUR_APPLICATION,"Fichier gpx incompatible: Erreur Inconnue"));
            }

            if (coordinates.get(0).toString().equals(coordinates.get(coordinates.size()-1).toString())) {
                est_ferme = true ;
                fermer_ouvrir.setText("ouvrir");
                coordinates.remove(coordinates.size()-1);
            } else {
                est_ferme = false ;
                fermer_ouvrir.setText("fermer");
            }
            for (Coordinate coordinate : coordinates) {
                ajouter_position(coordinate);
            }
            afficher_piste();
            Extent rando_extent = Extent.forCoordinates(coordinates);
            map.setExtent(rando_extent);
            map.setZoom(map.getZoom()*0.95);
        });

        this.setLeft(button_VBox);


    }

    public List<Coordinate> getCoordonnees(){
        List<Coordinate> coordinate_liste = new ArrayList<>();
        for (Marker marker : selection) {
            coordinate_liste.add(marker.getPosition());
        }
        return coordinate_liste;
    }

    public boolean getEst_ferme(){
        return est_ferme;
    }

    public void effacer_piste(){
        if(est_tracee) {
            map.removeCoordinateLine(piste);
        }
        while(selection.size()!=0){
            Marker buffer = selection.remove(0);
            map.removeMarker(buffer);
            est_tracee = false;
        }
    }

    private void ajouter_position(Coordinate coordonnee){
        Marker marker;
        if (selection.size() > 1) {
            marker = Marker.createProvided(Marker.Provided.GREEN).setPosition(coordonnee).setVisible(true);
            Marker buffer = selection.remove(selection.size()-1);
            map.removeMarker(buffer);
            Marker newMarker = Marker.createProvided(Marker.Provided.BLUE).setPosition(buffer.getPosition()).setVisible(true);
            map.addMarker(newMarker);
            selection.add(newMarker);
        } else if (selection.size() == 1){
            marker = Marker.createProvided(Marker.Provided.GREEN).setPosition(coordonnee).setVisible(true);
        } else {
            marker = Marker.createProvided(Marker.Provided.RED).setPosition(coordonnee).setVisible(true);
        }
        map.addMarker(marker);
        selection.add(marker);
    }

    private void afficher_piste(){
        if (est_tracee){
            map.removeCoordinateLine(piste);
        } else {
            est_tracee = true;
        }
        piste = (new CoordinateLine(getCoordonnees())).setColor(Color.CYAN).setWidth(5).setVisible(true).setClosed(est_ferme).setFillColor(new Color(0,0,0,0));
        map.addCoordinateLine(piste);
    }
}

