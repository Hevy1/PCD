import io.jenetics.jpx.GPX;
import io.jenetics.jpx.WayPoint;
import model.Difficulte;
import model.Randonnee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilitaire.GestionSauvegarde;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSauvergarde {

    Randonnee rando;
    GPX gpx;

    @BeforeEach
    public void setUp() {
        WayPoint p0 = WayPoint.builder().lat(48).lon(6).ele(200).build();
        WayPoint p1 = WayPoint.builder().lat(49).lon(6).ele(210).build();
        WayPoint p2 = WayPoint.builder().lat(50).lon(6).ele(220).build();
        WayPoint p3 = WayPoint.builder().lat(50).lon(7).ele(200).build();
        WayPoint p4 = WayPoint.builder().lat(50).lon(8).ele(190).build();

        List<String> tags = new ArrayList<>();
        tags.add("TN");
        tags.add("Telecom");
        tags.add("Nancy");

        gpx = GPX.builder()
                .addRoute(route -> route
                        .name("name")
                        .desc("desc")
                        .addPoint(p0).addPoint(p1).addPoint(p2).addPoint(p3).addPoint(p4)
                )
                .build();
        rando = new Randonnee(42, Difficulte.confirme, tags, gpx, null);
    }

    @Test
    public void save() {
        GestionSauvegarde.sauver(rando);

        // On regarde si les 2 fichiers ont bien été créés
        File fileGpx = new File("./saves/gpx/42.gpx");
        File fileSer = new File("./saves/ser/42.ser");
        assertTrue(fileGpx.exists());
        assertTrue(fileSer.exists());
    }

    @Test
    public void load() {
        GestionSauvegarde.sauver(rando);
        File file = new File("./saves/ser/42.ser");
        Randonnee recup = GestionSauvegarde.restaurer(file);
        assertEquals(rando, recup);

    }

    @Test
    public void delete() {
        GestionSauvegarde.sauver(rando);
        File fileGpx = new File("./saves/gpx/42.gpx");
        File fileSer = new File("./saves/ser/42.ser");
        assertTrue(fileGpx.exists());
        assertTrue(fileSer.exists());

        GestionSauvegarde.supprimer(rando);
        assertTrue(!fileGpx.exists());
        assertTrue(!fileSer.exists());
    }
}
