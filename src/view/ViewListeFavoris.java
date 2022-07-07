package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import model.Randonnee;
import utilitaire.GestionRando;

import java.util.List;

public class ViewListeFavoris extends ListView {
    public ViewListeFavoris(GestionRando gestio){
        super();
        final ObservableList<ViewRandonneeInMenu> listeimageobservable = FXCollections.observableArrayList();
        List<Randonnee> liste = gestio.getList_displayable();
        for (Randonnee rando : liste){
            ViewRandonneeInMenu vtemp= new ViewRandonneeInMenu(rando, gestio);
            listeimageobservable.add(vtemp);
        }

        this.setItems(listeimageobservable);


    }



}