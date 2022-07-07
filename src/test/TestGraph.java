package test;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class TestGraph extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Line Chart Sample");
        Point p1 = new Point(0.0,1);
        Point p2 = new Point(0.5,2);
        Point p3 = new Point(1.0, 3);
        Point p4 = new Point(1.5,4);
        Point p5 = new Point(2.0,5);
        Point p6 = new Point(2.5,6);
        Point p7 = new Point(3.0,7);
        Point p8 = new Point(3.5,8);
        Point p9 = new Point(4.0,9);
        ArrayList<Point> Points = new ArrayList<Point>();
        Points.add(p1);
        Points.add(p2);
        Points.add(p3);
        Points.add(p4);
        Points.add(p5);
        Points.add(p6);
        Points.add(p7);
        Points.add(p8);
        Points.add(p9);
        LineChart<Number, Number> lineChart= CalculGraph(Points);

        HBox bg = new HBox();
        bg.getChildren().add(lineChart);
        Scene scene = new Scene(bg, 800, 600);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public LineChart<Number, Number> CalculGraph(List<Point> points) {
        ArrayList<Double> abscisse = new ArrayList<Double>();
        ArrayList<Integer> ordonne = new ArrayList<Integer>();
        //initialisation
        abscisse.add(0.0);
        ordonne.add(points.get(0).getY());
        int numeropoint = 1;
        for (numeropoint = 1; numeropoint < points.size(); numeropoint++) {
            //Ajout de l'ordonnée
            ordonne.add(points.get(numeropoint).getY());
            abscisse.add(points.get(numeropoint).getX());
        }
        //Maintenant le graphe
        //On définit les axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        //On nommes les axes
        xAxis.setLabel("distance");
        yAxis.setLabel("altitude");
        //On met en place le graphique
        final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        //On le nomme
        lineChart.setTitle("Graphique du denivele");
        //On va mettre en place les couples
        XYChart.Series series = new XYChart.Series();
        int compteurpoint = 0;
        for (compteurpoint = 0; compteurpoint < abscisse.size(); compteurpoint++) {
            series.getData().add(new XYChart.Data(abscisse.get(compteurpoint), ordonne.get(compteurpoint)));
        }
        lineChart.getData().add(series);
        return lineChart;

    }
}
