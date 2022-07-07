package controller;

import application.Main;
import event.SelectionRandonneeEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Randonnee;
import view.ViewListeFavoris;
import view.ViewListeRandonnees;

import java.io.IOException;

public class BarreBasController {
    @FXML
    private Button Precedent;
    @FXML
    private Button AllerFavoris;
    @FXML
    private Button GoRando;

    public void pressAccueil(MouseEvent event) throws IOException {
        BorderPane homePage ;
        homePage = FXMLLoader.load(getClass().getResource("/resources/xmls/Accueil.fxml"));
        Stage stageApp = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(homePage, Main.getWidth(),Main.getHeight());
        stageApp.setScene(scene);
        stageApp.show();
    }

    public void retourRech(MouseEvent event) throws IOException {

        BorderPane homePage = new BorderPane();
        ToolBar bh = FXMLLoader.load(getClass().getResource("/resources/xmls/BarreHaut.fxml"));
        ToolBar bb = FXMLLoader.load(getClass().getResource("/resources/xmls/BarreBas.fxml"));
        homePage.setTop(bh);
        homePage.setBottom(bb);
        Stage stageApp = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ListView mid = new ViewListeRandonnees(Main.gestionRando);

        mid.addEventHandler(SelectionRandonneeEvent.RANDO_SELEC_MOD, event1 -> {
            //System.out.println("MOD EVENT");
            Randonnee randonnee = event1.getRandonnee();
            BorderPane homePage2;
            try {
                RandonneeModController rmc = new RandonneeModController(randonnee);
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
            Randonnee randonnee = event1.getRandonnee();
            BorderPane homePage2 = new BorderPane();
            try {
                ToolBar bh2 = FXMLLoader.load(getClass().getResource("/resources/xmls/BarreHaut.fxml"));
                ToolBar bb2 = FXMLLoader.load(getClass().getResource("/resources/xmls/BarreBasFiche.fxml"));
                homePage2.setTop(bh2);
                homePage2.setBottom(bb2);
                SplitPane mid2 = (new RandonneController(randonnee)).getVue();
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
    }



    public void pressAllerFavoris(MouseEvent event) throws IOException {
        BorderPane homePage = new BorderPane();
        ToolBar bh = FXMLLoader.load(getClass().getResource("/resources/xmls/BarreHaut.fxml"));
        ToolBar bb = FXMLLoader.load(getClass().getResource("/resources/xmls/BarreBasfavoris.fxml"));
        homePage.setTop(bh);
        homePage.setBottom(bb);
        Stage stageApp = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Main.gestionRando.setList_displayable(Main.gestionRando.getList_fav());
        ListView mid = new ViewListeFavoris(Main.gestionRando);

        mid.addEventHandler(SelectionRandonneeEvent.RANDO_SELEC_MOD, event1 -> {
            //System.out.println("INFO EVENT MOD");
            Randonnee randonnee = event1.getRandonnee();
            BorderPane homePage2;
            try {
                RandonneeModController rmc = new RandonneeModController(randonnee);
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
            Randonnee randonnee = event1.getRandonnee();
            BorderPane homePage2 = new BorderPane();
            try {
                ToolBar bh2 = FXMLLoader.load(getClass().getResource("/resources/xmls/BarreHaut.fxml"));
                ToolBar bb2 = FXMLLoader.load(getClass().getResource("/resources/xmls/BarreBasFiche.fxml"));
                homePage2.setTop(bh2);
                homePage2.setBottom(bb2);
                SplitPane mid2 = (new RandonneController(randonnee)).getVue();
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
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(homeScene);
        stage.show();
    }

    public void pressCreerRandonnee(MouseEvent event) throws IOException {
        //Parent homePage = FXMLLoader.load(getClass().getResource("/resources/xmls/MenuCreerRandonneeBouton.fxml"));
        Scene homeScene = new Scene(new MenuCreerRandonneeController().getBorderPane(),Main.getWidth(),Main.getHeight());
        Stage stageApp = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageApp.setScene(homeScene);
        stageApp.show();
    }

    public void allerRandonnee(MouseEvent event) throws IOException {
        BorderPane homePage = new BorderPane();
        ToolBar bh = FXMLLoader.load(getClass().getResource("/resources/xmls/BarreHaut.fxml"));
        ToolBar bb = FXMLLoader.load(getClass().getResource("/resources/xmls/BarreBas.fxml"));
        homePage.setTop(bh);
        homePage.setBottom(bb);
        Stage stageApp = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Main.gestionRando.setList_displayable(Main.gestionRando.getList_rando());
        ListView mid = new ViewListeRandonnees(Main.gestionRando);

        mid.addEventHandler(SelectionRandonneeEvent.RANDO_SELEC_MOD, event1 -> {
            //System.out.println("MOD EVENT");
            Randonnee randonnee = event1.getRandonnee();
            BorderPane homePage2 ;
            try {
                RandonneeModController rmc = new RandonneeModController(randonnee);
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
                Randonnee randonnee = event1.getRandonnee();
                BorderPane homePage2 = new BorderPane();
                try {
                    ToolBar bh2 = FXMLLoader.load(getClass().getResource("/resources/xmls/BarreHaut.fxml"));
                    ToolBar bb2 = FXMLLoader.load(getClass().getResource("/resources/xmls/BarreBasFiche.fxml"));
                    homePage2.setTop(bh2);
                    homePage2.setBottom(bb2);
                    SplitPane mid2 = (new RandonneController(randonnee)).getVue();
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
    }
}
