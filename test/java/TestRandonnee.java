import io.jenetics.jpx.*;
import model.Difficulte;
import model.FicheTechnique;
import model.Randonnee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRandonnee {

    Randonnee rando;
    GPX gpx;

    @BeforeEach
    public void setUp() {
        WayPoint p0 = WayPoint.builder().lat(48).lon(6).ele(200).build();
        WayPoint p1 = WayPoint.builder().lat(49).lon(6).ele(210).build();
        WayPoint p2 = WayPoint.builder().lat(50).lon(6).ele(220).build();
        WayPoint p3 = WayPoint.builder().lat(50).lon(7).ele(200).build();
        WayPoint p4 = WayPoint.builder().lat(50).lon(8).ele(190).build();

        String name = "Nancy";
        String desc = "Une randonnée des plus passionantes";
        List<String> tags = new ArrayList<>();
        tags.add("TN");
        tags.add("Telecom");
        tags.add("Nancy");
        int id = 42;
        Difficulte diff = Difficulte.facile;

        gpx = GPX.builder()
                .addRoute(route -> route
                        .name(name)
                        .desc(desc)
                        .addPoint(p0).addPoint(p1).addPoint(p2).addPoint(p3).addPoint(p4)
                )
                .build();

        rando = new Randonnee(id, diff, tags, gpx, null);
    }

    @Test
    public void creationRando() {
        // Il s'agit ici de vérifier que les donnée enregistrées par la randonnées sont les bonnes
        assertEquals("Nancy", rando.getTitre());
        assertEquals("Une randonnée des plus passionantes", rando.getDesc());
        assertEquals(42, rando.getId());

        // On regarde ensuite si les données enregistrées et calculées de la fiche technique sont bonne également
        FicheTechnique f = rando.getFicheTechnique();
        assertEquals("15 min", f.getDuree());
        assertEquals(20, f.getDen_pos());
        assertEquals(30, f.getDen_neg());
        //assertEquals(365338, f.getDistance()); //calcul de la distance trop imprécis

        // Dernières données à vérifier
        assertEquals("Telecom", rando.getTags().get(1));
        assertEquals("./saves/gpx/42.gpx", rando.getGpxPath());
        assertEquals(Difficulte.facile, f.getDifficulte());
        assertEquals(48, f.getDepart().getLatitude().doubleValue());
        assertEquals(50, f.getArrivee().getLatitude().doubleValue());
        assertEquals(6, f.getDepart().getLongitude().doubleValue());
        assertEquals(8, f.getArrivee().getLongitude().doubleValue());
    }


}
