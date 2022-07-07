package view;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Randonnee;

import java.util.ArrayList;

public class ViewListeImage extends ListView {

        public ViewListeImage(Randonnee randonnee){
            ArrayList<Image> listeim = new ArrayList<>();
            //On va remplir la liste

            for (String chemin : randonnee.getListecheminsimages()) {
                Image newimage = new Image("file:" + chemin);
                listeim.add(newimage);
            }

            final ObservableList<ImageView> listeimageobservable = FXCollections.observableArrayList();
            int numphoto = 0;
            for (numphoto = 0; numphoto < listeim.size(); numphoto++){
                Image nouvelleimage = listeim.get(numphoto);
                ImageView nouvellevue = new ImageView(nouvelleimage);
                nouvellevue.setFitHeight(270);
                nouvellevue.setPreserveRatio(true);
                listeimageobservable.add(nouvellevue);
            }
            this.setItems(listeimageobservable);
            this.setOrientation(Orientation.HORIZONTAL);

        }

        public ViewListeImage(){
            super();
        }
}