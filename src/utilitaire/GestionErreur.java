package utilitaire;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GestionErreur {

    public GestionErreur(String erreur, Stage stageF) {
        Stage errorTab = new Stage();
        errorTab.initStyle(StageStyle.UTILITY);
        errorTab.initOwner(stageF);
        BorderPane pane = new BorderPane();
        Label error = new Label(erreur);
        Button leave = new Button("Revenir à la fenêtre principale");
        leave.setOnAction(actionEvent -> errorTab.close());
        pane.setBottom(leave);
        HBox hBox = new HBox();
        Pane pane1 = new Pane();
        Pane pane2 = new Pane();
        HBox.setHgrow(pane1, Priority.ALWAYS);
        HBox.setHgrow(pane2, Priority.ALWAYS);
        hBox.getChildren().addAll(pane1,leave,pane2);
        leave.setAlignment(Pos.CENTER);
        pane.setCenter(error);
        pane.setBottom(hBox);
        errorTab.setScene(new Scene(pane, 600 ,100));
        errorTab.showAndWait();
    }
}
