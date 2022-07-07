package utilitaire;

import application.Main;
import io.jenetics.jpx.GPX;
import model.Randonnee;

import java.io.*;
import java.util.List;

public class GestionSauvegarde {

    public GestionSauvegarde() {}

    public static void sauver(Randonnee rando) {
        String path = Main.repo + "ser/" + rando.getId() + ".ser";
        File f = new File(path);

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(rando);
        } catch (IOException e) {
            e.printStackTrace();
        }

        GPX gpx = rando.getGpx();
        String gpxPath = rando.getGpxPath();
        try {
            GPX.write(gpx, gpxPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Randonnee restaurer(File file) {
        Randonnee rando = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            rando = (Randonnee) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            assert rando != null;
            rando.setGpx(GPX.read(rando.getGpxPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rando;
    }

    public static void supprimer(Randonnee rando) {
            File fGpx = new File(Main.repo + "gpx/" + rando.getId() + ".gpx");
            File fSer = new File(Main.repo + "ser/" + rando.getId() + ".ser");
            List<String> listechemin = rando.getListecheminsimages();
            if (listechemin != null) {
                for (String chemin : listechemin) {
                    File fichier = new File(chemin);
                    fichier.delete();
                }

                File dossier = new File(Main.repo + "images/" + rando.getId());
                dossier.delete();
            }
            fGpx.delete();
            fSer.delete();
    }
}
