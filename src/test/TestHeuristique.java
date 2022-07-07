package test;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;
public class TestHeuristique {
    public static class Main {

        public static void main(String[] args) {
            ListeMot l1 = new ListeMot("Bonjour",4);
            l1.add("Ceci");l1.add("est");l1.add("un");l1.add("test");
            ListeMot l2 = new ListeMot("Bonjour",3);
            l2.add("Ceci");l2.add("est");l2.add("un");
            ListeMot l3 = new ListeMot("Bonjour",2);
            l3.add("Ceci");l3.add("est");
            ListeMot l4 = new ListeMot("Bonjour",1);
            l4.add("Ceci");
            ArrayList<String> Motcle = new ArrayList<String>();
            Motcle.add("Bonjour");Motcle.add("Ceci");Motcle.add("est");Motcle.add("un");Motcle.add("Test");
            ArrayList<ListeMot> ALM= new ArrayList<ListeMot>();
            ALM.add(l4);ALM.add(l3);ALM.add(l2);ALM.add(l1);
            int i=0;
            for (i=0;i<ALM.size();i++){
                System.out.print(ALM.get(i).getId());
            }
            ALM = l1.HeuristiqueMotCle(ALM,Motcle);
            int j=0;
            for (j=0;j<ALM.size();j++){
                System.out.print(ALM.get(j).getId());
            }
        }

    }
}
