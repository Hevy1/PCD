package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model.FicheTechnique;
import model.Randonnee;
import utilitaire.GestionRando;

import java.util.Observable;
import java.util.Observer;

public class ViewRandonnee extends GridPane implements Observer {
    private final Label titre;
    private final Label diff;
    private final Label dist;
    private final Label duree;
    private final Text text;
    private final Button favori;
    private final Randonnee randonnee;

    public ViewRandonnee(Randonnee rando, GestionRando gestion){
        randonnee = rando;
        randonnee.addObserver(this);
        FicheTechnique f = randonnee.getFicheTechnique();
        titre = new Label("Titre : " + randonnee.getTitre());
        diff = new Label("Difficulté : " + f.getDifficulte());
        int d = f.getDistance();
        dist = new Label("Distance : " + d + "m");
        duree = new Label("Durée : " + f.getDuree());
        text = new Text("Description : \n" + randonnee.getDesc());
        favori = new Button();
        Image im = new Image(getClass().getResourceAsStream("../resources/images/favori.jpg"));
        ImageView imv = new ImageView(im);
        imv.setFitHeight(80);
        imv.setPreserveRatio(true);
        favori.setGraphic(imv);
        favori.setStyle("-fx-background-radius: 0; -fx-background-color: transparent;");
        favori.setOnAction(actionEvent -> gestion.addFavori(randonnee));

        this.add(titre,0,0);
        this.add(diff,1,0);
        this.add(dist,0,1);
        this.add(duree,1,1);
        this.add(text,0,2);
        this.add(favori,4,0);


    }


    @Override
    public void update(Observable o, Object arg) {

    }
}
