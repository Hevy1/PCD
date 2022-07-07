package utilitaire;

import application.Main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GestionImage {

    public static List<String> sauver(List<File> files, int id) {
        int i = 0;
        if (files == null) {
            return null;
        }
        List<String> chemins = new ArrayList<>();
        for (File file : files) {
            BufferedImage buff = null;
            String chemin = null;
            try {
                buff = ImageIO.read(file);
                Files.createDirectories(Paths.get(Main.repo + "images/" + id + "/"));
                chemin = Main.repo + "images/" + id + "/" + i + ".png";
                ImageIO.write(buff,"png", new File(chemin));
            } catch (IOException e) {
                e.printStackTrace();
            }
            chemins.add(chemin);
            i++;
        }
        return chemins;
    }


}
