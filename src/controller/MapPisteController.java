package controller;

import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.CoordinateLine;
import com.sothawo.mapjfx.MapView;
import com.sothawo.mapjfx.Marker;
import com.sothawo.mapjfx.event.MapViewEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class MapPisteController {

    private final MapView carte;

    final List<Marker> selection;

    CoordinateLine piste;

    private boolean est_ferme = false;

    public MapPisteController(){
        this.carte = new MapView();

        selection = new ArrayList<>();

        carte.initialize();

    }

    public void afficher_piste(List<Coordinate> coordinates){
        //System.out.println("AAAAAAAAHHHHHH");
        if (coordinates.get(0).toString().equals(coordinates.get(coordinates.size()-1).toString())){
            coordinates.remove(coordinates.size()-1);
            est_ferme = true;
        }
        for (int i = 0 ;i<coordinates.size();i++){
            //System.out.println("TEST: "+i);
            Marker.Provided couleur;
            if (i == 0 ){
                couleur = Marker.Provided.RED;
            } else if (i == coordinates.size()-1){
                couleur = Marker.Provided.GREEN;
            } else {
                couleur = Marker.Provided.BLUE;
            }
            Marker marker = Marker.createProvided(couleur).setVisible(true).setPosition(coordinates.get(i));
            selection.add(marker);
            carte.addMarker(marker);
        }
        piste = (new CoordinateLine(coordinates)).setVisible(true).setColor(Color.MAGENTA).setClosed(est_ferme);
        carte.addCoordinateLine(piste);
    }

    public MapView getCarte(){
        return carte;
    }

    public List<Coordinate> getCoordonnees(){
        List<Coordinate> coordinate_liste = new ArrayList<>();
        for (Marker marker : selection) {
            coordinate_liste.add(marker.getPosition());
        }
        return coordinate_liste;
    }

    public boolean isEst_ferme() {
        return est_ferme;
    }

    public void rendre_cliquable() {
        carte.addEventHandler(MapViewEvent.MAP_CLICKED, event -> {
            event.consume();
            Coordinate coord = event.getCoordinate();
            ajouter_position(coord);
        });

        carte.addEventHandler(MapViewEvent.MAP_RIGHTCLICKED, event -> {
            carte.removeMarker(selection.get(selection.size()-1));
            selection.remove(selection.size()-1);
            if(selection.size() > 1) {
                Marker buffer = selection.remove(selection.size() - 1);
                carte.removeMarker(buffer);
                Marker marker = Marker.createProvided(Marker.Provided.GREEN).setPosition(buffer.getPosition()).setVisible(true);
                carte.addMarker(marker);
                selection.add(marker);
            }
        });
    }

    private void ajouter_position(Coordinate coordonnee){
        Marker marker;
        if (selection.size() > 1) {
            marker = Marker.createProvided(Marker.Provided.GREEN).setPosition(coordonnee).setVisible(true);
            Marker buffer = selection.remove(selection.size()-1);
            carte.removeMarker(buffer);
            Marker newMarker = Marker.createProvided(Marker.Provided.BLUE).setPosition(buffer.getPosition()).setVisible(true);
            carte.addMarker(newMarker);
            selection.add(newMarker);
        } else if (selection.size() == 1){
            marker = Marker.createProvided(Marker.Provided.GREEN).setPosition(coordonnee).setVisible(true);
        } else {
            marker = Marker.createProvided(Marker.Provided.RED).setPosition(coordonnee).setVisible(true);
        }
        carte.addMarker(marker);
        selection.add(marker);
    }

    public void afficher_piste(){
        carte.removeCoordinateLine(piste);
        piste = (new CoordinateLine(getCoordonnees())).setColor(Color.CYAN).setWidth(7).setVisible(true).setClosed(est_ferme).setFillColor(new Color(0,0,0,0));
        carte.addCoordinateLine(piste);
    }
}
