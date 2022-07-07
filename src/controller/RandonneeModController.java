package controller;

import application.Main;
import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.Extent;
import com.sothawo.mapjfx.MapView;
import com.sothawo.mapjfx.event.MapViewEvent;
import event.ErreurApplicationEvent;
import event.SelectionRandonneeEvent;
import io.jenetics.jpx.GPX;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Difficulte;
import model.FicheTechnique;
import model.Randonnee;
import utilitaire.GestionImage;
import utilitaire.GestionRando;
import utilitaire.GestionSauvegarde;
import utilitaire.ProcessGPX;
import view.ViewListeRandonnees;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class RandonneeModController {
    private final BorderPane grandBorderPane;

    private final BorderPane petitBorderPane;

    private final VBox vbox;

    private final MapPisteController mpc;

    private BorderPane champsCreer;

    private Button bouton_sup;

    private Button bouton_mod;

    private Button bouton_set_trace;

    private Button bouton_trace;

    private boolean anti_casse_couille = true;

    private ChampsCreerRandonneeController ccrc = null;

    private boolean trace_modifiable = false;


    public RandonneeModController(Randonnee randonnee) throws IOException {
        grandBorderPane = new BorderPane();
        grandBorderPane.setMinSize(1000, 600);
        mpc = new MapPisteController();
        MapView carteCreer = mpc.getCarte();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/xmls/ChampsCreerRandonnee.fxml"));
            champsCreer = loader.load();
            ccrc = loader.getController();
            HBox mes_boutons = FXMLLoader.load(getClass().getResource("/resources/xmls/MenuModifierRandonneeBouton.fxml"));
            bouton_mod = (Button) mes_boutons.getChildren().get(0);
            bouton_set_trace = (Button) mes_boutons.getChildren().get(1);
            bouton_trace = (Button) mes_boutons.getChildren().get(2);
            bouton_sup = (Button) mes_boutons.getChildren().get(3);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //<Button maxHeight="-Infinity" maxWidth="-Infinity" mnemonicParsing="false" onAction="#creerRandonnee" prefHeight="26.0" prefWidth="600.0" text="Creer la randonnee" />
        bouton_mod.setOnAction(event -> {
            try {
                modifierRandonnee(randonnee);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        bouton_sup.setOnAction(event -> {
            try{
                GestionRando.suppr_rando(randonnee);
                BorderPane homePage = new BorderPane();
                ToolBar bh = FXMLLoader.load(getClass().getResource("/resources/xmls/BarreHaut.fxml"));
                ToolBar bb = FXMLLoader.load(getClass().getResource("/resources/xmls/BarreBas.fxml"));
                homePage.setTop(bh);
                homePage.setBottom(bb);
                Stage stageApp = (Stage) ((Node) event.getSource()).getScene().getWindow();
                ListView mid = new ViewListeRandonnees(Main.gestionRando);

                mid.addEventHandler(SelectionRandonneeEvent.RANDO_SELEC_MOD, event1 -> {
                    //System.out.println("MOD EVENT");
                    Randonnee randonne = event1.getRandonnee();
                    BorderPane homePage2;
                    try {
                        RandonneeModController rmc = new RandonneeModController(randonne);
                        homePage2 = rmc.getGrandBorderPane();
                        Scene homeScene = new Scene(homePage2, Main.getWidth(),Main.getHeight());
                        stageApp.setScene(homeScene);
                        stageApp.show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                mid.addEventHandler(SelectionRandonneeEvent.RANDO_SELEC, event1 -> {
                    //System.out.println("INFO EVENT");
                    Randonnee randonn = event1.getRandonnee();
                    BorderPane homePage2 = new BorderPane();
                    try {
                        ToolBar bh2 = FXMLLoader.load(getClass().getResource("/resources/xmls/BarreHaut.fxml"));
                        ToolBar bb2 = FXMLLoader.load(getClass().getResource("/resources/xmls/BarreBasFiche.fxml"));
                        homePage2.setTop(bh2);
                        homePage2.setBottom(bb2);
                        SplitPane mid2 = (new RandonneController(randonn)).getVue();
                        homePage2.setCenter(mid2);
                        Scene homeScene = new Scene(homePage2, Main.getWidth(),Main.getHeight());

                        stageApp.setScene(homeScene);
                        stageApp.show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                mid.setFocusTraversable(false);
                homePage.setCenter(mid);
                Scene homeScene = new Scene(homePage, Main.getWidth(),Main.getHeight());

                stageApp.setScene(homeScene);
                stageApp.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        vbox = new VBox();
        HBox hbox = new HBox();
        hbox.getChildren().addAll(bouton_mod, bouton_set_trace, bouton_trace,bouton_sup);
        vbox.getChildren().addAll(
                champsCreer,
                hbox
        );
        petitBorderPane = new BorderPane();
        petitBorderPane.setCenter(carteCreer);
        petitBorderPane.setBottom(vbox);
        
        try {
            grandBorderPane.setTop(FXMLLoader.load(getClass().getResource("/resources/xmls/BarreHaut.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        grandBorderPane.setCenter(petitBorderPane);

        try {
            grandBorderPane.setBottom(FXMLLoader.load(getClass().getResource("/resources/xmls/BarreBasFiche.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
            Mise en place des informations
        */

        GPX gpx = randonnee.getGpx();

        //afficher la piste de la randonnée

        EventHandler<MapViewEvent> eventBullshit = event -> {
            if(anti_casse_couille) {
                List<Coordinate> coordinates = null;
                try {
                    coordinates = ProcessGPX.getCoords(gpx);
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

        //Afficher les bonnes valeurs dans les champs de texte
        ccrc.setChampTitre(ProcessGPX.getName(gpx));
        ccrc.setChampDescription(ProcessGPX.getDesc(gpx));

        List<String> tags = randonnee.getTags();
        ccrc.setChampTags(tags); //tagsf

        List<String> chemins = randonnee.getListecheminsimages();
        List<File> files = null;
        if (chemins != null) {
            files = new ArrayList<>();
            for (String chemin : chemins) {
                files.add(new File(chemin));
            }
        }
        ccrc.setImages(files);

        //Mettre la difficulté
        switch (randonnee.getFicheTechnique().getDifficulte()){
            case facile: ccrc.setFacile();
            case normal: ccrc.setNormal();
            case intermediaire: ccrc.setInter();
            case confirme: ccrc.setConfirme();
        }

        //Implémenter la carte modifiable

        //Quand on veut rendre cliquable

        bouton_set_trace.setOnAction(event -> {
            trace_modifiable = true;
            mpc.rendre_cliquable();
            bouton_set_trace.setDisable(true);
            bouton_trace.setDisable(false);
        });

        bouton_trace.setDisable(true);
        bouton_trace.setOnAction(event -> mpc.afficher_piste());
    }

    private void modifierRandonnee(Randonnee randonnee) throws Exception {

        List<Coordinate> coordinateList = mpc.getCoordonnees();
        if (coordinateList.size()<2){
            grandBorderPane.fireEvent(new ErreurApplicationEvent(ErreurApplicationEvent.ERREUR_APPLICATION, "Impossible de modifier la randonnée, elle ne possède plus assez de points"));
            return;
        }

        Difficulte diff = ccrc.getDifficulte();
        if (diff == null){
            champsCreer.fireEvent(new ErreurApplicationEvent(ErreurApplicationEvent.ERREUR_APPLICATION, "Difficulte non renseignée"));
            return;
        }
        randonnee.getFicheTechnique().setDiff(diff);

        String titre = ccrc.getChampTitre();
        randonnee.setTitre(titre);
        List<String> tags = Arrays.asList(ccrc.getTags());
        randonnee.setTags(tags);
        String contenu = ccrc.getChampDescription();
        randonnee.setDesc(contenu);
        List<String> chemins = GestionImage.sauver(ccrc.getImages(),randonnee.getId());
        randonnee.setListecheminsimages(chemins);



        if (trace_modifiable) {
            if (mpc.isEst_ferme()) {
                coordinateList.add(coordinateList.get(0));
            }
            randonnee.setGpx(GestionRando.creer_gpx(coordinateList, titre, contenu, null));
            randonnee.setFicheTechnique(new FicheTechnique(diff, randonnee.getGpx()));
        } else {randonnee.setGpx(GestionRando.creer_gpx(coordinateList, titre, contenu, ProcessGPX.getAltitudes(randonnee.getGpx())));}

        GestionSauvegarde.sauver(randonnee);

        //System.out.print(titre+"\n\n");
        //System.out.print(contenu+"\n\n");
    }

    public BorderPane getGrandBorderPane() {
        return grandBorderPane;
    }
}
