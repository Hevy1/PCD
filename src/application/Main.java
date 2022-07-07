package application;

import event.ErreurApplicationEvent;
import io.jenetics.jpx.GPX;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Difficulte;
import utilitaire.Config;
import utilitaire.GestionErreur;
import utilitaire.GestionRando;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    public static GestionRando gestionRando;
    private static int height;
    private static int width;
    public static String repo = System.getProperty("user.home") + "/.TNhiking/";

    @Override
    public void start(Stage primaryStage) throws Exception{

        if (!Files.exists(Paths.get(this.repo))) {
            initialisation();
        }

        GraphicsEnvironment graphicsEnvironment=GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle maximumWindowBounds=graphicsEnvironment.getMaximumWindowBounds();
        height = (int)maximumWindowBounds.getHeight()-30;
        width  = (int)maximumWindowBounds.getWidth();
        Parent root = FXMLLoader.load(getClass().getResource("/resources/xmls/Accueil.fxml"));
        primaryStage.setTitle("TelecomHiking");
        primaryStage.setScene(new Scene(root, Main.getWidth(),Main.getHeight()));
        primaryStage.getIcons().add(new Image("/resources/images/marmotte.png"));
        gestionRando = new GestionRando();

        primaryStage.addEventHandler(ErreurApplicationEvent.ERREUR_APPLICATION, event -> new GestionErreur(event.getMessage(),primaryStage));

        primaryStage.addEventHandler(ErreurApplicationEvent.ERREUR_APPLICATION_CRITIQUE, event -> {
            new GestionErreur(event.getMessage(),primaryStage);
            primaryStage.close();
        });

        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    public static int getHeight() {return height;}
    public  static int getWidth() {return width;}

    private void initialisation() throws IOException {
        Files.createDirectory(Paths.get(this.repo));
        Files.createDirectory(Paths.get(this.repo + "ser/"));
        Files.createDirectory(Paths.get(this.repo + "gpx/"));
        Files.createDirectory(Paths.get(this.repo + "images/"));
        Config config =  new Config(0);
        GestionRando.saveConfig(config);
        GestionRando gest = new GestionRando();
        List<String> tags1 = new ArrayList<>(); tags1.add("nancy"); tags1.add("stanislas");
        List<String> tags2 = new ArrayList<>();
        List<String> tags3 = new ArrayList<>(); tags3.add("long"); tags3.add("foret");
        List<String> tags4 = new ArrayList<>(); tags4.add("TN"); tags4.add("telecom"); tags4.add("Nancy");
        List<String> tags5 = new ArrayList<>(); tags5.add("rond"); tags5.add("point"); tags5.add("gilet"); tags5.add("jaune");
        List<String> tags6 = new ArrayList<>(); tags6.add("rond"); tags6.add("point"); tags6.add("gilet"); tags6.add("jaune");
        List<File> im1 = new ArrayList<>();im1.add(new File("./saves/images/1/0.png"));im1.add(new File("./saves/images/1/1.png"));im1.add(new File("./saves/images/1/2.png"));
        List<File> im2 = new ArrayList<>();
        List<File> im3 = new ArrayList<>();im3.add(new File("./saves/images/3/0.png"));im3.add(new File("./saves/images/3/1.png"));im3.add(new File("./saves/images/3/2.png"));
        List<File> im4 = new ArrayList<>();im4.add(new File("./saves/images/4/0.png"));
        List<File> im5 = new ArrayList<>();im5.add(new File("./saves/images/5/0.png"));
        List<File> im6 = new ArrayList<>();
        try {
             gest.creer_rando(Difficulte.normal, tags1, im1, GPX.read("./saves/gpx/1.gpx"));
            gest.creer_rando(Difficulte.intermediaire, tags2, im2, GPX.read("./saves/gpx/2.gpx"));
            gest.creer_rando(Difficulte.confirme, tags3, im3, GPX.read("./saves/gpx/3.gpx"));
            gest.creer_rando(Difficulte.facile, tags4, im4, GPX.read("./saves/gpx/4.gpx"));
            gest.creer_rando(Difficulte.confirme, tags5, im5, GPX.read("./saves/gpx/5.gpx"));
            gest.creer_rando(Difficulte.intermediaire, tags6, im6, GPX.read("./saves/gpx/6.gpx"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
