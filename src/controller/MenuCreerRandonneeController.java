package controller;

import application.Main;
import com.sothawo.mapjfx.Coordinate;
import event.ErreurApplicationEvent;
import event.ImportGPXEvent;
import event.SelectionRandonneeEvent;
import io.jenetics.jpx.GPX;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Difficulte;
import model.Randonnee;
import utilitaire.ProcessGPX;
import view.ViewListeRandonnees;
import view.ViewMapEtButtons;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class MenuCreerRandonneeController {

    private final BorderPane grandBorderPane;

    private final BorderPane petitBorderPane;

    private final VBox vbox;

    private final ViewMapEtButtons carteCreer;

    private final BorderPane champsCreer;

    private final Button bouton;

    private List<Integer> alts;

    private final ChampsCreerRandonneeController ccrc;

    public MenuCreerRandonneeController() throws IOException {
        grandBorderPane = new BorderPane();
        grandBorderPane.setMinSize(Main.getWidth(),Main.getHeight());

        carteCreer = new ViewMapEtButtons();

        carteCreer.addEventHandler(ImportGPXEvent.GPX_IMPORTED, event -> {
            GPX gpx = event.getGpx();
            alts = ProcessGPX.getAltitudes(gpx);
            //System.out.println("Event déclenché au niveau de Menu");
            afficher_infos(gpx);
        });

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/xmls/ChampsCreerRandonnee.fxml"));
        champsCreer = loader.load();
        ccrc = loader.getController();

        //<Button maxHeight="-Infinity" maxWidth="-Infinity" mnemonicParsing="false" onAction="#creerRandonnee" prefHeight="26.0" prefWidth="600.0" text="Creer la randonnee" />
        bouton = FXMLLoader.load(getClass().getResource("/resources/xmls/MenuCreerRandonneeBouton.fxml"));

        bouton.setOnAction(event -> {
            try {
                creerRandonnee();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        vbox = new VBox();
        vbox.getChildren().addAll(
                champsCreer,
                bouton
        );
        petitBorderPane = new BorderPane();
        petitBorderPane.setCenter(carteCreer);
        petitBorderPane.setBottom(vbox);
        grandBorderPane.setTop(FXMLLoader.load(getClass().getResource("/resources/xmls/BarreHaut.fxml")));
        grandBorderPane.setCenter(petitBorderPane);
        grandBorderPane.setBottom(FXMLLoader.load(getClass().getResource("/resources/xmls/BarreBas.fxml")));
    }

    public void creerRandonnee() throws Exception {
        List<Coordinate> coordinateList = carteCreer.getCoordonnees();
        if (coordinateList.size()<2){
            carteCreer.fireEvent(new ErreurApplicationEvent(ErreurApplicationEvent.ERREUR_APPLICATION, "Impossible de créer la randonnée, il n'y a pas assez de points"));
            return;
        }
        if (carteCreer.getEst_ferme()){
            coordinateList.add(coordinateList.get(0));
        }

        Difficulte diff = ccrc.getDifficulte();
        if (diff == null){
            champsCreer.fireEvent(new ErreurApplicationEvent(ErreurApplicationEvent.ERREUR_APPLICATION, ("Difficulte non renseignée")));
            return;
        }
        String titre = ccrc.getChampTitre();
        if (titre.equals("")){
            champsCreer.fireEvent(new ErreurApplicationEvent(ErreurApplicationEvent.ERREUR_APPLICATION, ("La randonné n'a pas de titre")));
            return;
        }
        List<String> tags = Arrays.asList(ccrc.getTags());
        String contenu = ccrc.getChampDescription();
        Main.gestionRando.creer_rando(diff, tags, coordinateList, titre, contenu, this.alts, ccrc.getImages());
        this.grandBorderPane.fireEvent(new ErreurApplicationEvent(ErreurApplicationEvent.ERREUR_APPLICATION, "Randonnée crée"));

    }

        //System.out.print(titre+"\n\n");
        //System.out.print(contenu+"\n\n");


    public BorderPane getBorderPane() {
        return grandBorderPane;
    }

    private void afficher_infos(GPX gpx){
        ccrc.setChampTitre(ProcessGPX.getName(gpx));
        ccrc.setChampDescription(ProcessGPX.getDesc(gpx));
    }
}
