package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class RandonneeSubController {
    @FXML
    private SplitPane carteEtGraphe;
    @FXML
    private TitledPane titledPanePhotos;
    @FXML
    private Text description;
    @FXML
    private Label titre;
    @FXML
    private Label duree;
    @FXML
    private Label distance;
    @FXML
    private Label difficulte;
    @FXML
    private Label depart;
    @FXML
    private Label arrivee;
    @FXML
    private Label denpos;
    @FXML
    private Label denneg;
    @FXML
    private ImageView imagediff;

    public Label getArrivee() {
        return arrivee;
    }

    public ImageView getImagediff() {
        return imagediff;
    }

    public Label getDenneg() {
        return denneg;
    }

    public SplitPane getCarteEtGraphe() {
        return carteEtGraphe;
    }

    public Text getDescription() {
        return description;
    }

    public void setDescription(String text){
        this.description.setText(text);
    }

    public Label getTitre() {
        return titre;
    }

    public Label getDuree() {
        return duree;
    }

    public Label getDistance() {
        return distance;
    }

    public Label getDifficulte() {
        return difficulte;
    }

    public Label getDepart() {
        return depart;
    }

    public void setImagelist(ListView imagelist){
        titledPanePhotos.setContent(imagelist);
    }

    public Label getDenpos() {
        return denpos;
    }
}

