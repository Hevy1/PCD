package test;

import java.util.ArrayList;
import java.util.List;

public class ListeMot {
        int id;
        ArrayList<String> LM;
        public ListeMot(String mot,int id){
            this.LM = new ArrayList<String>();
            this.id = id;
        }
        public void add(String mot){
            LM.add(mot);
        }
        public ArrayList<String> getLM(){
            return LM;
        }
        public int getId(){
            return this.id;
        }
        public ArrayList<ListeMot> HeuristiqueMotCle(ArrayList<ListeMot> ListeRandonnee, List<String> Motscle){
            ArrayList<ListeMot> result = new ArrayList<ListeMot>();
            ArrayList<Integer> index = new ArrayList<Integer>();
            int indicetabrando = 0;
            for (indicetabrando = 0;indicetabrando<ListeRandonnee.size();indicetabrando++) {
                //Calul de la note de la randonnée
                int note = 0;
                int indicetabMot = 0;
                for (indicetabMot = 0; indicetabMot<Motscle.size();indicetabMot++){
                    if (ListeRandonnee.get(indicetabrando).getLM().contains(Motscle.get(indicetabMot))){
                        note++;
                        //On compte le nombre de mots clés en commun entre la recherche et ceux de la randonnée.
                    }
                }
                //On dispose maintenant de la note de la randonnée
                //Nous allons maintenant procéder à l'aide d'un tableau d'indexes permettant d'ordonner les randonnées et de placer
                //la nouvelle randonnee a sa place dans le tableau resultats.
                if (index.contains(note)){
                    int emplacement = index.lastIndexOf(note);
                    index.add(emplacement,note);
                    //On a rajouté l'équivalent de la randonné dans le tableau index
                    //On connait l'index où l'on va mettre la randonnée dans son tableau
                    result.add(emplacement,ListeRandonnee.get(indicetabrando));
                    //La nouvelle randonée est à sa place dans la table resultat
                }
                else{
                    //C'est la première fois que la note est rencontrée
                    //On parcours la liste des indexes et on le met au bon endroit
                    int emplacement;
                    if(index.isEmpty()){
                        emplacement = 0;
                    }
                    else {
                        emplacement = index.size() - 1;
                        while (emplacement >= 0 && index.get(emplacement) < note) {
                            emplacement--;
                        }

                    }
                    if (emplacement == -1){
                        //Note la plus élévée jamais rencontrée, on la met au début
                        index.add(0,note);
                        //On s'occupe maintenant de la randonnée
                        result.add(0, ListeRandonnee.get(indicetabrando));
                    }
                    else{
                        //On met la note a sa place dans les deux tableau:
                        index.add(emplacement,note);
                        result.add(emplacement,ListeRandonnee.get(indicetabrando));
                    }
                }

            }
            //A partir d'ici result contient toutes les randonnées rangées par ordre décroissant de note
            return result;
        }
    }

