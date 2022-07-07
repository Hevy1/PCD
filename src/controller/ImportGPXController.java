package controller;

import event.ErreurApplicationEvent;
import event.ImportGPXEvent;
import io.jenetics.jpx.GPX;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ImportGPXController {

    @FXML
    TextField tf;

    @FXML
    VBox importVBOX;

    @FXML
    public void valide() {
        String path = tf.getText();
        GPX gpx ;
        try {
            gpx = GPX.read(path);
            //System.out.println("GPX trouvé");
            tf.fireEvent(new ImportGPXEvent(ImportGPXEvent.GPX_IMPORTED, gpx));
        } catch (IOException e) {
            //e.printStackTrace();
            //System.out.println("GPX introuvable");
            tf.fireEvent(new ErreurApplicationEvent(ErreurApplicationEvent.ERREUR_APPLICATION, "GPX introuvable"));
        } catch (RuntimeException e) {
            tf.fireEvent(new ErreurApplicationEvent(ErreurApplicationEvent.ERREUR_APPLICATION,"Fichier gpx incompatible: Chemin illégal"));
        }
    }

}
