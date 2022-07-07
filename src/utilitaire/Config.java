package utilitaire;

import java.io.Serializable;

public class Config implements Serializable {
    private int nbRando;

    public Config(int i) { this.nbRando = i; }

    public int getNbRando() { return nbRando; }

    public void setNbRando(int nbRando) { this.nbRando = nbRando; }
}
