package view;


import event.SelectionRandonneeEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import javafx.scene.layout.*;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.FicheTechnique;
import utilitaire.GestionRando;
import model.Randonnee;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class ViewRandonneeInMenu extends HBox implements Observer {

    private final Label titre;
    private final Label diff;
    private final Label dist;
    private final Label duree;
    private final Label text;
    private final Button favori;
    private final Button infos;
    private final Randonnee randonnee;
    private final Button modifier;

    public ViewRandonneeInMenu(Randonnee rando, GestionRando gestion) {
        super(8);
        randonnee = rando;
        randonnee.addObserver(this);
        this.setPrefSize(900,80);
        this.setMaxHeight(80);

        VBox gauche = new VBox();
        gauche.setPrefWidth(800);
        FicheTechnique f = randonnee.getFicheTechnique();
        //titre
        HBox titr = new HBox();
        titr.setPrefWidth(500);
        titre = new Label("Titre :");
        titre.setUnderline(true);
        titre.setFont(Font.font("Comic Sans MS",12));
        Label titrtemp = new Label("   "+randonnee.getTitre());
        titrtemp.setFont(Font.font("Comic Sans MS",12));
        titr.getChildren().addAll(titre,titrtemp);

        //difficulté
        HBox dif = new HBox();
        diff = new Label("Difficulté :" );
        diff.setUnderline(true);
        diff.setFont(Font.font("Comic Sans MS",12));
        Label diftemp = new Label("   "+f.getDifficulte().toString());
        diftemp.setFont(Font.font("Comic Sans MS",12));
        dif.getChildren().addAll(diff,diftemp);

        //distance
        HBox dis = new HBox();
        int d = f.getDistance();
        dist = new Label("Distance :" );
        dist.setUnderline(true);
        dist.setFont(Font.font("Comic Sans MS",12));
        Label distemp = new Label("   "+d + "m");
        distemp.setFont(Font.font("Comic Sans MS",12));
        dis.getChildren().addAll(dist,distemp);

        //durée
        HBox dure = new HBox();
        duree = new Label("Durée :");
        duree.setFont(Font.font("Comic Sans MS",12));
        duree.setUnderline(true);
        Label duretemp = new Label("   "+f.getDuree());
        duretemp.setFont(Font.font("Comic Sans MS",12));
        dure.getChildren().addAll(duree,duretemp);


        gauche.getChildren().addAll(titr, dif, dis, dure);
        this.getChildren().add(gauche);

        text = new Label("Description : \n" + randonnee.getDesc());
        text.setMaxWidth(350);
        this.getChildren().add(text);

        Pane pane = new Pane();
        HBox.setHgrow(pane, Priority.ALWAYS);
        this.getChildren().add(pane);

        modifier = new Button();
        modifier.setStyle("-fx-background-radius: 0; -fx-background-color: transparent;");
        ImageView btmodif= new ImageView(new Image(getClass().getResourceAsStream("../resources/images/modifier.png")));
        btmodif.setFitHeight(80);
        btmodif.setFitWidth(80);
        modifier.setGraphic(btmodif);
        this.getChildren().add(modifier);
        modifier.setOnAction(event -> fireEvent(new SelectionRandonneeEvent(SelectionRandonneeEvent.RANDO_SELEC_MOD,randonnee)));

        infos = new Button();
        infos.setStyle("-fx-background-radius: 0; -fx-background-color: transparent;");
        ImageView btinfos= new ImageView(new Image(getClass().getResourceAsStream("../resources/images/infos.png")));
        btinfos.setFitHeight(80);
        btinfos.setFitWidth(80);
        infos.setGraphic(btinfos);
        this.getChildren().add(infos);
        infos.setOnAction(event -> fireEvent(new SelectionRandonneeEvent(SelectionRandonneeEvent.RANDO_SELEC,randonnee)));
        //ICI FAUT LINK A LA FONCTION handle en bas

        favori = new Button();
        Image imNot = new Image(getClass().getResourceAsStream("../resources/images/isNotFavori.png"));
        Image imIs = new Image(getClass().getResourceAsStream("../resources/images/isFavori.png"));
        ImageView imv;
        if (randonnee.getFavori()) {
            imv = new ImageView(imIs);
        } else {
            imv = new ImageView(imNot);
        }
        imv.setFitHeight(80);
        imv.setFitWidth(80);
        imv.setPreserveRatio(true);
        favori.setGraphic(imv);
        favori.setStyle("-fx-background-radius: 0; -fx-background-color: transparent;");
        favori.setOnAction(actionEvent -> {
            if (randonnee.getFavori()) {
                gestion.removeFavori(rando);
                imv.setImage(imNot);
            } else {
                gestion.addFavori(rando);
                imv.setImage(imIs);
            }
        });
        this.getChildren().add(favori);
    }

/*
    public void handleInfosBt(ActionEvent actionEvent) throws IOException {
        BorderPane homePage = new BorderPane();
        ToolBar bh = FXMLLoader.load(getClass().getResource("/resources/xmls/BarreHaut.fxml"));
        ToolBar bb = FXMLLoader.load(getClass().getResource("/resources/xmls/BarreBasFiche.fxml"));
        homePage.setTop(bh);
        homePage.setBottom(bb);
        ViewRandonnee mid = new ViewRandonnee(randonnee, this.gestion);
        homePage.setCenter(mid);
        Scene homeScene = new Scene(homePage, 1000,600);
        Stage stageApp = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stageApp.setScene(homeScene);
        stageApp.show();
    }
*/

    @Override
    public void update(Observable o, Object arg) {
            //Rien pour le moment
    }
}
