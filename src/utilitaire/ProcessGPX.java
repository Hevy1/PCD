package utilitaire;

import com.sothawo.mapjfx.Coordinate;
import io.jenetics.jpx.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ProcessGPX {

    public static List<Coordinate> getCoords(GPX gpx) throws NoSuchElementException {
        ArrayList<Coordinate> coords = new ArrayList<>();

        if (gpx.getRoutes().size()!=0) {
            Route route = gpx.getRoutes().get(0);
            for (WayPoint p : route.getPoints()) {
                coords.add(new Coordinate(p.getLatitude().doubleValue(),p.getLongitude().doubleValue()));
            }
        } else if (gpx.getTracks().size()!=0) {
            Track t = gpx.getTracks().get(0);
            for (TrackSegment s : t.getSegments()) {
                for (WayPoint p : s.getPoints()) {
                    coords.add(new Coordinate(p.getLatitude().doubleValue(),p.getLongitude().doubleValue()));
                }
            }
        } else {
            throw new NoSuchElementException("Pas de trace ou route");
        }

        return coords;
    }

    public static List<Integer> getAltitudes(GPX gpx) throws NoSuchElementException {
        List<Integer> alts = new ArrayList<>();

        if (gpx.getRoutes().size()!=0) {
            Route route = gpx.getRoutes().get(0);
            for (WayPoint p : route.getPoints()) {
                Optional<Length> ele = p.getElevation();
                if (ele.isPresent()) {
                    alts.add(ele.get().intValue());
                } else {
                    return null;
                }
            }
        } else if (gpx.getTracks().size()!=0) {
            Track t = gpx.getTracks().get(0);
            for (TrackSegment s : t.getSegments()) {
                for (WayPoint p : s.getPoints()) {
                    Optional<Length> ele = p.getElevation();
                    if (ele.isPresent()) {
                        alts.add(ele.get().intValue());
                    } else {
                        return null;
                    }
                }
            }
        } else {
            throw new NoSuchElementException("Pas de trace ou route");
        }

        return alts;
    }

    public static String getName(GPX gpx) throws NoSuchElementException {
        String str;
        if (gpx.getRoutes().size()!=0) {
            str =  gpx.getRoutes().get(0).getName().get();
        } else if (gpx.getTracks().size()!=0) {
            str =  gpx.getTracks().get(0).getName().get();
        } else {
            throw new NoSuchElementException("Pas de trace ou route");
        }
        return str;
    }

    public static String getDesc(GPX gpx) throws NoSuchElementException {
        String str = null;
        if (gpx.getRoutes().size()!=0) {
            Optional<String> strOpt = gpx.getRoutes().get(0).getDescription();
            if (strOpt.isPresent()) {
                str = strOpt.get();
            }
        } else if (gpx.getTracks().size()!=0) {
            Optional<String> strOpt = gpx.getTracks().get(0).getDescription();
            if (strOpt.isPresent()) {
                str = strOpt.get();
            }
        } else {
            throw new NoSuchElementException("Pas de trace ou route");
        }
        return str;
    }

}
