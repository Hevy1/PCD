package test;
import io.jenetics.jpx.*;
import model.*;

import java.util.ArrayList;

public class TestRandonnee {

    public TestRandonnee() {};

    public static GPX test(String titre) {
        WayPoint tn = WayPoint.builder().lat(48.669086030709096).lon(6.155729001991288).ele(296).build();
        WayPoint tn1 = WayPoint.builder().lat(48.668536900431638).lon(6.158517158262269).ele(274).build();
        WayPoint tn2 = WayPoint.builder().lat(48.667987770154181).lon(6.161305314533250).ele(252).build();
        WayPoint tn3 = WayPoint.builder().lat(48.667438639876723).lon(6.164093470804230).ele(230).build();
        WayPoint tn4 = WayPoint.builder().lat(48.666889509599265).lon(6.166881627075211).ele(208).build();

        Route route = Route.builder()
                .name(titre)
                .desc("Une randonnée qui vous fera découvrir les décors magnifiques et environnements spectaculaires de l'école d'ingénieur du digital Telecom Nancy !")
                .addPoint(tn).addPoint(tn1).addPoint(tn2).addPoint(tn3)
                .build();

        route = route.toBuilder().addPoint(tn4).build();

        GPX gpx = GPX.builder().addRoute(route).build();

        return gpx;
/*        gpx.routes()
                .flatMap(Route::points)
                .forEach(System.out::println);
        */
    }

    public static Randonnee test2(String titre) {
        GPX gpx = TestRandonnee.test(titre);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("TN");
        tags.add("Telecom");
        tags.add("Nancy");
        Randonnee tn = new Randonnee(42, Difficulte.facile, tags, gpx, null);

        return tn;
    }
}
