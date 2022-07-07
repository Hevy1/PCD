package model;

import com.sothawo.mapjfx.Coordinate;
import io.jenetics.jpx.GPX;
import io.jenetics.jpx.Route;
import io.jenetics.jpx.WayPoint;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class FicheTechnique implements Serializable {
    private String duree;
    private float distance;
    private int den_pos;
    private int den_neg;
    private Difficulte diff;

    private Pair<Double, Double> depart;
    private Pair<Double, Double> arrivee;

    public FicheTechnique(Difficulte diff, GPX gpx) {
        this.diff = diff;
        Route route = gpx.getRoutes().get(0);
        List<WayPoint> points = route.getPoints();
        initDistance(points);
        initDuree();
    }

    private void initDistance(List<WayPoint> points) {
        int n = points.size();
        WayPoint p1 = points.get(0);
        WayPoint p2 = points.get(n-1);
        this.depart = new Pair<>(p1.getLatitude().doubleValue(), p1.getLongitude().doubleValue());
        this.arrivee = new Pair<>(p2.getLatitude().doubleValue(), p2.getLongitude().doubleValue());

        p2 = p1;
        for (WayPoint p : points) {
                if (!p.equals(p1)) {
                    calculDis(p,p2);
                    calculDen(p,p2);
                    p2 = p;
                }
        }
    }

    private void calculDis(WayPoint p, WayPoint p2) {
        double phiA = p.getLatitude().doubleValue()/360*2*Math.PI;
        double phiB = p2.getLatitude().doubleValue()/360*2*Math.PI;
        double lamb = p.getLongitude().doubleValue()/360*2*Math.PI - p2.getLongitude().doubleValue()/360*2*Math.PI;
        double temp = Math.sin(phiA)*Math.sin(phiB) + Math.cos(phiA)*Math.cos(phiB)*Math.cos(lamb);
        this.distance += Math.acos(temp)*6378137;
    }

    private void calculDen(WayPoint p, WayPoint p2) {
        if (den_pos!=-1) {
            int diffEle = 0;
            int ele1 = 0;
            int ele2 = 0;
            try {
                ele1 = p.getElevation().get().intValue();
                ele2 = p2.getElevation().get().intValue();
            } catch (
                    NoSuchElementException e) {
                e.printStackTrace();
            }
            diffEle = ele1 - ele2;
            if (ele1==-1 || ele2==-1) {
                this.den_pos = -1;
                this.den_neg = -1;
            } else {
                if (diffEle < 0) {
                    this.den_neg -= diffEle;
                } else {
                    this.den_pos += diffEle;
                }
            }
        }
    }

    private void initDuree() {
        int dur = (int) this.distance*60/4000;
        if (dur<55) {
            dur = dur+(5-dur%5);
            duree = dur + "min";
        } else {
            int min =dur%60;
            int h = dur/60;
            if (min>55) {
                h ++;
                duree = h + "h";
            } else {
                min = min + (5-dur%5);
                if (min==5) {
                    duree = h + "h05";
                } else {
                    duree = h + "h" + min;
                }
            }
        }
    }

    public String getDuree() { return this.duree; }

    public int getDistance() { return (int) this.distance; }

    public int getDen_pos() { return den_pos; }

    public int getDen_neg() { return den_neg; }

    public Difficulte getDifficulte() { return this.diff; }

    public Coordinate getDepart() { return new Coordinate(this.depart.getKey(), this.depart.getValue()); }

    public Coordinate getArrivee() { return new Coordinate(this.arrivee.getKey(), this.arrivee.getValue()); }

    public void setDiff(Difficulte diff) {
        this.diff = diff;
    }

    public LineChart<Number,Number> calculGraph(List<WayPoint> points){
        ArrayList<Double> abscisse = new ArrayList<>();
        ArrayList<Integer> ordonne = new ArrayList<>();
        //initialisation
        abscisse.add(0.0);
        ordonne.add(points.get(0).getElevation().get().intValue());
        int numeropoint = 1;
        for (numeropoint = 1; numeropoint < points.size();numeropoint++){
            //Ajout de l'ordonnée
            ordonne.add(points.get(numeropoint).getElevation().get().intValue());
            //Distance entre les deux points
            double phiA = points.get(numeropoint-1).getLatitude().doubleValue()/360*2*Math.PI;
            double phiB = points.get(numeropoint).getLatitude().doubleValue()/360*2*Math.PI;
            double lamb = points.get(numeropoint-1).getLongitude().doubleValue()/360*2*Math.PI - points.get(numeropoint).getLongitude().doubleValue()/360*2*Math.PI;
            double temp = Math.sin(phiA)*Math.sin(phiB) + Math.cos(phiA)*Math.cos(phiB)*Math.cos(lamb);
            //distance entre les deux points
            double distance = Math.acos(temp)*6378137;
            //On rajoute l'abscisse de l'ancien point et c'est bon
            abscisse.add(abscisse.get(numeropoint-1)+distance);
        }
        //Maintenant le graphe
        //On définit les axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        //On nommes les axes
        xAxis.setLabel("distance");
        yAxis.setLabel("altitude");
        //On met en place le graphique
        final LineChart<Number,Number> lineChart = new LineChart<>(xAxis, yAxis);
        //On le nomme
        lineChart.setTitle("Graphique du denivele");
        //On va mettre en place les couples
        XYChart.Series series = new XYChart.Series();
        int compteurpoint = 0;
        for(compteurpoint = 0;compteurpoint<abscisse.size();compteurpoint++) {
            series.getData().add(new XYChart.Data(abscisse.get(compteurpoint),  ordonne.get(compteurpoint)));
        }
        lineChart.setCreateSymbols(false);
        lineChart.setLegendVisible(false);
        lineChart.getData().add(series);
        return lineChart;

    }
}
