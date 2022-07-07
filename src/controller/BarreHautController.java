package controller;

import application.Main;

import event.SelectionRandonneeEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Randonnee;
import view.ViewListeRandonnees;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BarreHautController implements Initializable {
    @FXML
    private ImageView logo;
    @FXML
    private MenuButton loupe;
    @FXML
    private MenuItem item1;
    @FXML
    private MenuItem item2;
    @FXML
    private TextField recherche;
    @FXML
    private CheckBox setFavRech;

    Boolean FavRech;

    public BarreHautController(){
        FavRech = false;
    }

    @FXML
    public void appuiLogo(MouseEvent event) throws IOException {
       //System.out.println("Logo appuyé");
        Parent homePage = FXMLLoader.load(getClass().getResource("/resources/xmls/Accueil.fxml"));
        Scene homeScene = new Scene(homePage,Main.getWidth(),Main.getHeight());
        Stage stageApp= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageApp.setScene(homeScene);
        stageApp.show();
    }

    @FXML
    public void appuiLoupe1() throws IOException {
        //On commence par changer la scene pour aller chercher dans la listes des randonnées.
        BorderPane homePage = new BorderPane();
        ToolBar bh = FXMLLoader.load(getClass().getResource("/resources/xmls/BarreHaut.fxml"));
        ToolBar bb = FXMLLoader.load(getClass().getResource("/resources/xmls/BarreBas.fxml"));
        homePage.setTop(bh);
        homePage.setBottom(bb);
        if (!this.FavRech) {
            Main.gestionRando.HeuristiqueTitre(Main.gestionRando.getList_rando(),recherche.getText());
            //System.out.println(Main.gestionRando.getList_displayable().size());
        }
        else{
            //System.out.println(Main.gestionRando.getList_fav().size());
            Main.gestionRando.HeuristiqueTitre(Main.gestionRando.getList_fav(), recherche.getText());
            //System.out.println(Main.gestionRando.getList_displayable().size());
        }
        Stage stageApp = (Stage) loupe.getScene().getWindow();
        ListView mid = new ViewListeRandonnees(Main.gestionRando);

        mid.addEventHandler(SelectionRandonneeEvent.RANDO_SELEC_MOD, event1 -> {
            //System.out.println("MOD EVENT");
            Randonnee randonnee = event1.getRandonnee();
            BorderPane homePage2 = new BorderPane();
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
            Object joannes;
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
        Stage stage = (Stage) loupe.getScene().getWindow();
        stage.setScene(homeScene);
        stage.show();
    }

    public void appuiLoupe2() throws IOException {
        //On commence par changer la scene pour aller chercher dans la listes des randonnées.
        BorderPane homePage = new BorderPane();
        ToolBar bh = FXMLLoader.load(getClass().getResource("/resources/xmls/BarreHaut.fxml"));
        ToolBar bb = FXMLLoader.load(getClass().getResource("/resources/xmls/BarreBas.fxml"));
        homePage.setTop(bh);
        homePage.setBottom(bb);
        if (!this.FavRech) {
            Main.gestionRando.HeuristiqueMotCle(Main.gestionRando.getList_rando(), recherche.getText());
        }
        else{
            Main.gestionRando.HeuristiqueMotCle(Main.gestionRando.getList_fav(), recherche.getText());
        }
        Stage stageApp = (Stage) loupe.getScene().getWindow();
        ListView mid = new ViewListeRandonnees(Main.gestionRando);

        mid.addEventHandler(SelectionRandonneeEvent.RANDO_SELEC_MOD, event1 -> {
            //System.out.println("MOD EVENT");
            Randonnee randonnee = event1.getRandonnee();
            BorderPane homePage2 = new BorderPane();
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
            Object joannes;
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
        Stage stage = (Stage) loupe.getScene().getWindow();
        stage.setScene(homeScene);
        stage.show();
    }

    public void PresssetFavRech(){
        this.FavRech = !this.FavRech;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }


}
