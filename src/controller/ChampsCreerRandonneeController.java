package controller;

import event.ErreurApplicationEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Difficulte;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


    public class ChampsCreerRandonneeController {

        @FXML
        private TextArea champTitre;

        @FXML
        private TextArea champDescription;

        @FXML
        private TextArea champTags;

        private Difficulte difficulte = null;

        public  List<File> images = null;

        @FXML
        private MenuItem facileBt;
        @FXML
        private MenuItem normalBt;
        @FXML
        private MenuItem intermediaireBt;
        @FXML
        private MenuItem confirmeBt;
        @FXML
        private SplitMenuButton diffiBt;

        @FXML
        public void setFacile() throws IOException {
            diffiBt.setText("Facile");
            difficulte = Difficulte.facile;
        }

        @FXML
        public void setNormal() throws IOException {
            diffiBt.setText("Normal");
            difficulte = Difficulte.normal;
        }

        @FXML
        public void setInter() throws IOException {
            diffiBt.setText("Intermédiaire");
            difficulte = Difficulte.intermediaire;
        }

        @FXML
        public void setConfirme() throws IOException {
            diffiBt.setText("Confirmé");
            difficulte = Difficulte.confirme;
        }

        private final FileChooser fileChooser = new FileChooser();
        @FXML
        public void chooseImages() throws Exception{
            if (images==null) {
                images = new ArrayList<>();
            }
            Stage fileTab = new Stage();
            try {
                List<File> files = fileChooser.showOpenMultipleDialog(fileTab);
                if (files == null){
                    throw new IllegalArgumentException("Pas d'image choisie");
                }
                for (File f : files) {
                    String str = f.toString();
                    String[] split = str.split("\\.");
                    String ext = split[split.length - 1];
                    if (!ext.equals("jpg") && !ext.equals("jpeg") && !ext.equals("png") && !ext.equals("gif") && !ext.equals("bmp")) {
                        throw new IllegalArgumentException("Le lien ne mène pas à une image lisible");
                    }
                }
                images.addAll(files);
            } catch (Exception e){
                champDescription.fireEvent(new ErreurApplicationEvent(ErreurApplicationEvent.ERREUR_APPLICATION, ("Pas d'image ajoutée")));
            }

        }

        public String getChampTitre() {
            return champTitre.getText();
        }

        public String getChampDescription() {
            return champDescription.getText();
        }

        public String[] getTags() { return  champTags.getText().split(" ");}

        public void setChampTitre(String titre) {
            champTitre.setText(titre);
        }

        public void setChampDescription(String desc){
            champDescription.setText(desc);
        }

        public void setChampTags(List<String> tags){
            StringBuilder buffer = new StringBuilder();
            for (String st : tags){
                buffer.append(st).append(" ");
            }
            champTags.setText(buffer.toString());
        }

        public void setImages(List<File> files) {
            this.images = files;
        }

        public List<File> getImages() {
            return this.images;
        }

        public Difficulte getDifficulte() {
            return difficulte;
        }

    }


