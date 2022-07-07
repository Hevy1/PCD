package test;

import application.Main;
import com.sothawo.mapjfx.Coordinate;
import io.jenetics.jpx.GPX;
import io.jenetics.jpx.WayPoint;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.*;
import utilitaire.Config;
import utilitaire.GestionRando;
import utilitaire.GestionSauvegarde;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MainTest extends Application {

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) throws Exception {
        /*GPX gpx = TestRandonnee.test("titre");
        ArrayList<String> tags = new ArrayList<>();
        tags.add("TN");
        tags.add("Telecom");
        tags.add("Nancy");
        Randonnee tn = new Randonnee(42, "15 min", Difficulte.facile, tags, gpx);
        FicheTechnique f = tn.getFicheTechnique();

        System.out.println(f.getDistance());
        System.out.println(f.getDen_pos());
        System.out.println(f.getDen_neg());

        GestionSauvegarde.sauver(tn);
        Randonnee test = GestionSauvegarde.restaurer("./saves/ser/42.ser");
        FicheTechnique ftest = test.getFicheTechnique();
        System.out.println(test.getTitre());
        System.out.println(ftest.getDepart());
        GestionSauvegarde.supprimer(tn);*/


        BorderPane pane = new BorderPane();
        /*Image im = new Image("file:/home/user/Téléchargements/Awaken.jpg");
        ImageView imv = new ImageView(im);
        imv.setFitWidth(500);
        imv.setPreserveRatio(true);
        pane.setCenter(imv);*/
        stage.setTitle("ça a fonctionné");
        stage.setScene(new Scene(pane, 500, 300));
        stage.show();


        GestionRando gestionRando = new GestionRando();
        List<Coordinate> coords = new ArrayList<>();
        coords.add(new Coordinate(48.669086030709096,6.155729001991288));
        coords.add(new Coordinate(48.668536900431638,6.158517158262269));
        coords.add(new Coordinate(48.667987770154181,6.161305314533250));
        coords.add(new Coordinate(48.667438639876723,6.164093470804230));
        coords.add(new Coordinate(48.666889509599265,6.166881627075211));
        List<String> tags = new ArrayList<>();tags.add("Du");tags.add("Duu");tags.add("Dudu");
        List<String> tags1 = new ArrayList<>();tags1.add("mieux");tags1.add("AVANT");tags1.add("jeu");
        List<String> tags2 = new ArrayList<>();tags2.add("gros");tags2.add("grosse");tags2.add("grosses");
        List<String> tags3 = new ArrayList<>();tags3.add("shrek");tags3.add("shrekos");tags3.add("shreka");
        List<String> tags4 = new ArrayList<>();tags4.add("free");tags4.add("JUL");tags4.add("balkany");
        List<String> tags5 = new ArrayList<>();tags5.add("mygale");tags5.add("tres");tags5.add("jolie");
        List<String> tags6 = new ArrayList<>();tags6.add("bg");tags6.add("pas");tags6.add("jul");
        List<String> tags7 = new ArrayList<>();tags7.add("daronne");tags7.add("cave");tags7.add("difficile");
        List<String> tags8 = new ArrayList<>();tags8.add("clown");tags8.add("clowned");tags8.add("cirque");
        List<String> tags9 = new ArrayList<>();tags9.add("shrek");tags9.add("shrekos");tags9.add("shrekshrek");
        List<String> tags10 = new ArrayList<>();tags10.add("lointain");tags10.add("shrek");tags10.add("fort");
        List<String> tags11 = new ArrayList<>();tags11.add("tata");tags11.add("tonton");tags11.add("quitte");
        gestionRando.creer_rando(Difficulte.facile, tags, coords, "Baby shark","du du dudu", null, null);
        gestionRando.creer_rando(Difficulte.intermediaire, tags1, coords,"Ta playstation", "c'était mieux avant", null, null);
        gestionRando.creer_rando(Difficulte.normal, tags2, coords,"Ta grosse", "sans complément", null, null);
        gestionRando.creer_rando(Difficulte.facile, tags3, coords,"Gros Shrek", "ça c'est Adrien", null, null);
        gestionRando.creer_rando(Difficulte.intermediaire, tags4, coords,"Free JUL", "Free Balkany", null, null);
        gestionRando.creer_rando(Difficulte.intermediaire, tags5, coords,"Ta grosse mygale", "jolie mygale", null, null);
        gestionRando.creer_rando(Difficulte.confirme, tags6, coords,"JUL le bg", "cchacun sa vision des choses", null, null);
        gestionRando.creer_rando(Difficulte.confirme, tags7, coords,"Ta grosse Daronne", "Randonnee des plus magnifiques caves du pays", null, null);
        gestionRando.creer_rando(Difficulte.normal, tags8, coords,"Clowned", "Randonne parmis les plus grand cirques de France", null, null);
        gestionRando.creer_rando(Difficulte.normal, tags9, coords,"Shrek is love", "Découvrez le monde de Shrek", null, null);
        gestionRando.creer_rando(Difficulte.confirme, tags10, coords,"très franchement j'irai bien au concert de fort fort lointain", "Alors n'hésitez plus", null, null);
        gestionRando.creer_rando(Difficulte.facile, tags11, coords,"ta tata ta quitte", "et ton tonton tond ton thon", null, null);

/*
        Config config =  new Config(0);
        File f = new File(Main.repo +  "config.ser");
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(config);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        //System.out.println(System.getProperty("user.home"));

    }
}
