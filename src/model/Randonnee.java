package model;

import application.Main;
import io.jenetics.jpx.*;
import utilitaire.GestionImage;

import java.io.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Optional;

public class Randonnee extends Observable implements Serializable{
    private String titre;
    private FicheTechnique ft;
    private final int id;
    private String desc = null;
    private transient GPX gpx;
    private final String gpxPath;
    private List<String> tags;
    private List<String> listecheminsimages;
    private boolean favori;

    public Randonnee (int id, Difficulte diff, List<String> tags, GPX gpx, List<File> images) {
        this.id = id;
        this.tags = tags;
        this.favori = false;
        this.ft = new FicheTechnique(diff,gpx);
        this.gpx = gpx;
        Route route = gpx.getRoutes().get(0);

        Optional<String> descOpt = route.getDescription();
        if (descOpt.isPresent()) {
            this.desc = descOpt.get();
        }

        try {
            this.titre = route.getName().get();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }

        this.gpxPath = Main.repo + "gpx/" + this.id + ".gpx";

        this.listecheminsimages = GestionImage.sauver(images, this.id);
    }

    public String getTitre() { return this.titre; }

    public boolean getFavori() { return this.favori; }

    public void setFavori(boolean b) { this.favori = b; }

    public FicheTechnique getFicheTechnique() { return this.ft; }

    public void setFicheTechnique(FicheTechnique ft) { this.ft = ft; }

    public int getId() { return this.id; }

    public List<String> getTags() { return this.tags;}

    public String getDesc() { return this.desc; }

/*    public String getDesc() throws Exception{
        File file = new File(this.desc);
        String retour = "";
        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        String retour_buffer = retour;
        while ((st = br.readLine()) != null){
            retour = retour_buffer + st;
        }

        return retour;
    }*/

    public GPX getGpx() { return this.gpx; }

    public void setGpx(GPX gpx) { this.gpx = gpx; }

    public String getGpxPath() { return this.gpxPath; }

    public List<String> getListecheminsimages() {
        return this.listecheminsimages;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setListecheminsimages(List<String> listecheminsimages) {
        this.listecheminsimages = listecheminsimages;
    }
}
