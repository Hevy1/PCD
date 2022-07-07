package test;

import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.MapView;
import com.sothawo.mapjfx.event.MapViewEvent;
import controller.MapPisteController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Randonnee;
import utilitaire.GestionRando;

import java.util.ArrayList;
import java.util.List;

public class TestMapPiste extends Application {

    @Override
    public void start(Stage stage) {
        MapPisteController mpc = new MapPisteController();
        stage.setScene(new Scene(mpc.getCarte()));
        stage.show();
        List<Coordinate> coordinates = new ArrayList<Coordinate>();
        coordinates.add(new Coordinate(49.015511, 8.323497));
        coordinates.add(new Coordinate(50.015511, 8.323497));
        mpc.getCarte().addEventHandler(MapViewEvent.MAP_CLICKED, event -> {
            mpc.afficher_piste(coordinates);
            mpc.getCarte().setCenter(new Coordinate(49.015511, 8.323497));
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}