package utilitaire;

import application.Main;
import com.sothawo.mapjfx.Coordinate;
import io.jenetics.jpx.GPX;
import io.jenetics.jpx.Route;
import io.jenetics.jpx.WayPoint;
import model.Difficulte;
import model.EchecCreationRando;
import model.FicheTechnique;
import model.Randonnee;
import utilitaire.Config;
import utilitaire.GestionSauvegarde;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class GestionRando {
    private static int nombreRando = 0;
    static private List<Randonnee> list_rando;
    static private List<Randonnee> list_fav;
    static private List<Randonnee> display_list;
    private Config config;

    public GestionRando(){

        nombreRando = recupNb();

        list_rando = new ArrayList<>();
        list_fav = new ArrayList<>();

        File repertoire = new File(Main.repo + "ser/");
        File[] files = repertoire.listFiles();
        Randonnee rando;
        assert files != null;
        for (File file : files) {
            rando = GestionSauvegarde.restaurer(file);
            list_rando.add(rando);
            if (rando.getFavori()) {
                list_fav.add(rando);
            }
        }

        display_list = list_rando;

    }

    public void creer_rando(Difficulte diff, List<String> tags, List<Coordinate> coords, String name, String desc, List<Integer> alts, List<File> images) throws Exception{
        nombreRando++;
        config.setNbRando(nombreRando);
        GPX gpx = creer_gpx(coords, name, desc, alts);
        Randonnee rando = new Randonnee(nombreRando, diff, tags, gpx, images);
        boolean add = list_rando.add(rando);
        if (!(add)){
            throw new EchecCreationRando();
        }
        GestionSauvegarde.sauver(rando);
        saveConfig(this.config);
    }

    public void creer_rando(Difficulte diff, List<String> tags, List<File> images, GPX gpx) throws Exception{
        nombreRando++;
        config.setNbRando(nombreRando);
        Randonnee rando = new Randonnee(nombreRando, diff, tags, gpx, images);
        boolean add = list_rando.add(rando);
        if (!(add)){
            throw new EchecCreationRando();
        }
        GestionSauvegarde.sauver(rando);
        saveConfig(this.config);
    }

    public static GPX creer_gpx(List<Coordinate> coords, String name, String desc, List<Integer> alts) {
        Route route = Route.builder().name(name).desc(desc).build();
        boolean b = (alts==null);
        WayPoint p;
        for (int i=0; i<coords.size(); i++) {
            Coordinate c = coords.get(i);
            if (b) {
                p = WayPoint.builder().lat(c.getLatitude()).lon(c.getLongitude()).ele(-1).build();
            } else {
                p = WayPoint.builder().lat(c.getLatitude()).lon(c.getLongitude()).ele(alts.get(i)).build();
            }
            route = route.toBuilder().addPoint(p).build();
        }
        return GPX.builder().addRoute(route).build();
    }

    static public void suppr_rando(Randonnee rando) {
        GestionSauvegarde.supprimer(rando);
        list_fav.remove(rando);
        list_rando.remove(rando);
        display_list.remove(rando);
    }

    public void addFavori(Randonnee rando) {
        rando.setFavori(true);
        GestionSauvegarde.sauver(rando);
        list_fav.add(rando);
    }

    public void removeFavori(Randonnee rando) {
        rando.setFavori(false);
        GestionSauvegarde.sauver(rando);
        list_fav.remove(rando);
    }

    private int recupNb() {
        File f = new File(Main.repo + "config.ser");
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            this.config = (Config) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return this.config.getNbRando();
    }

    public static void saveConfig(Config conf) {
        File f = new File(Main.repo + "config.ser");
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Randonnee> getList_rando(){
        return list_rando;
    }
    public List<Randonnee> getList_fav(){
        return list_fav;
    }
    public String getTitre(int numrando){
        return list_rando.get(numrando).getTitre();
    }
    public FicheTechnique getFicheTechnique(int numrando) {
        return list_rando.get(numrando).getFicheTechnique();
    }
    public int getId(int numrando) {
        return list_rando.get(numrando).getId();
    }
    public List<String> getTags(int numrando) {
        return list_rando.get(numrando).getTags();
    }
    public String getDesc(int numrando) {
        return list_rando.get(numrando).getDesc();
    }
    public Object getGPX(int numrando) {
        return list_rando.get(numrando).getGpx();
    }
    public void setList(List<Randonnee> listrando){
        list_rando = listrando;}

    public void HeuristiqueMotCle(List<Randonnee> ListeRandonnee, String Motscle){
        if (Motscle.equals("")){
            display_list = ListeRandonnee;
        }
        else{
            String separateur = " ";
            String[] etape = Motscle.split(separateur);
            ArrayList<String> Motscles = new ArrayList<>(Arrays.asList(etape));
            ArrayList<Randonnee> result = new ArrayList<>();
            ArrayList<Integer> index = new ArrayList<>();
            int indicetabrando = 0;
            for (indicetabrando = 0; indicetabrando < ListeRandonnee.size(); indicetabrando++) {
                //Calul de la note de la randonnée
                int note = 0;
                int indicetabMot = 0;
                //On gere la casse
                int numtags = 0;
                for (numtags = 0; numtags < ListeRandonnee.get(indicetabrando).getTags().size(); numtags++) {
                    ListeRandonnee.get(indicetabrando).getTags().get(numtags).toLowerCase();
                }
                for (indicetabMot = 0; indicetabMot < Motscles.size(); indicetabMot++) {
                    if (ListeRandonnee.get(indicetabrando).getTags().contains(Motscles.get(indicetabMot).toLowerCase())) {
                        note++;
                        //On compte le nombre de mots clés en commun entre la recherche et ceux de la randonnée.
                    }
                }
                //On dispose maintenant de la note de la randonnée
                //Nous allons maintenant procéder à l'aide d'un tableau d'indexes permettant d'ordonner les randonnées et de placer
                //la nouvelle randonnee a sa place dans le tableau resultats.
                if (index.contains(note)) {
                    int emplacement = index.lastIndexOf(note);
                    index.add(emplacement, note);
                    //On a rajouté l'équivalent de la randonné dans le tableau index
                    //On connait l'index où l'on va mettre la randonnée dans son tableau
                    result.add(emplacement, ListeRandonnee.get(indicetabrando));
                    //La nouvelle randonée est à sa place dans la table resultat
                } else {
                    //C'est la première fois que la note est rencontrée
                    //On parcours la liste des indexes et on le met au bon endroit
                    int emplacement = index.size() - 1;
                    while (emplacement >= 0 && index.get(emplacement) < note) {
                        emplacement--;
                    }
                    if (emplacement == -1) {
                        //Note la plus élévée jamais rencontrée, on la met au début
                        index.add(0, note);
                        //On s'occupe maintenant de la randonnée
                        result.add(0, ListeRandonnee.get(indicetabrando));
                    } else {
                        //On met la note a sa place dans les deux tableau:
                        index.add(emplacement + 1, note);
                        result.add(emplacement + 1, ListeRandonnee.get(indicetabrando));
                    }
                }

            }
            //A partir d'ici result contient toutes les randonnées rangées par ordre décroissant de note
            //On va supprimer les résultat non pertinents
            while (index.contains(0)) {
                result.remove(result.size() - 1);
                index.remove(index.size() - 1);
            }
            display_list = result;
        }
    }

    public void HeuristiqueTitre(List<Randonnee> ListeRandonnee, String Recherche) {
        //L'heuristique devra fonctioner comme suit:
        //Elle renvoie en premier les randonnées dont le titre commence par la même chose que la recherche
        //On commence par prendre les mots de la recherche dans un tableau
        if (Recherche.equals("")) {
            this.setList_displayable(ListeRandonnee);
        } else {
            String[] Mots = Recherche.split(" ");
            ArrayList<String> MotsRechliste = Arrays.stream(Mots).map(String::toLowerCase).collect(Collectors.toCollection(ArrayList::new));
            ArrayList<Integer> index = new ArrayList<>();
            ArrayList<Randonnee> result = new ArrayList<>();
            int numrando = 0;
            for (numrando = 0; numrando < ListeRandonnee.size(); numrando++) {
                //Pour chaque randonnée on va regarder si son titre contient les mots de la recherche et combien il en contient
                String[] tabrando = ListeRandonnee.get(numrando).getTitre().split(" ");
                ArrayList<String> Motsliste = new ArrayList<>();
                for (String s : tabrando) {
                    Motsliste.add(s.toLowerCase());
                }
                int note = 0;
                int nummottitre = 0;
                for (nummottitre = 0; nummottitre < MotsRechliste.size(); nummottitre++) {
                    if (Motsliste.contains(MotsRechliste.get(nummottitre).toLowerCase())) {
                        note += 1;
                        if (nummottitre < Motsliste.size() && MotsRechliste.get(nummottitre).contains(Motsliste.get(nummottitre).toLowerCase())) {
                            note += 3;
                        }
                    }
                }

                //Maintenant on a la note du mot
                if (index.contains(note)) {
                    int emplacement = index.lastIndexOf(note);
                    index.add(emplacement, note);
                    //On a rajouté l'équivalent de la randonné dans le tableau index
                    //On connait l'index où l'on va mettre la randonnée dans son tableau
                    result.add(emplacement, ListeRandonnee.get(numrando));
                    //La nouvelle randonée est à sa place dans la table resultat
                } else {
                    //C'est la première fois que la note est rencontrée
                    //On parcours la liste des indexes et on le met au bon endroit
                    int emplacement = index.size() - 1;
                    while (emplacement >= 0 && index.get(emplacement) < note) {
                        emplacement--;
                    }
                    if (emplacement == -1) {
                        //Note la plus élévée jamais rencontrée, on la met au début
                        index.add(0, note);
                        //On s'occupe maintenant de la randonnée
                        result.add(0, ListeRandonnee.get(numrando));
                    } else {
                        //On met la note a sa place dans les deux tableau:
                        if (emplacement == 0) {
                            index.add(1, note);
                            result.add(1, ListeRandonnee.get(numrando));
                        } else {
                            index.add(emplacement + 1, note);
                            result.add(emplacement + 1, ListeRandonnee.get(numrando));
                        }
                    }
                }
                //System.out.println("");
            }
            //Maintenant on va gérer le fait que les recherches non pertinentes ne sont pas affichées
            while (index.contains(0)) {
                result.remove(result.size() - 1);
                index.remove(index.size() - 1);
            }
            display_list = result;
        }
    }

    public List<Randonnee> getList_displayable(){
        return display_list;
    }

    public void setList_displayable(List<Randonnee> newval){
        display_list = newval;
    }
}
