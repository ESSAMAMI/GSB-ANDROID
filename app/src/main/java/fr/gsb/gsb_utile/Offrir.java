package fr.gsb.gsb_utile;

public class Offrir {

    private String idVisi ;
    private int numRap ;
    private String nomDepotMedocs ;
    private int qte ;
    private int tailleTab ;

    public Offrir(String idVisi, int numRap, String nomDepotMedocs, int qte, int taile) {
        this.idVisi = idVisi;
        this.numRap = numRap;
        this.nomDepotMedocs = nomDepotMedocs;
        this.qte = qte;
        this.tailleTab = taile ;

    }

    public String getIdVisi() {
        return idVisi;
    }

    public void setIdVisi(String idVisi) {
        this.idVisi = idVisi;
    }

    public int getNumRap() {
        return numRap;
    }

    public void setNumRap(int numRap) {
        this.numRap = numRap;
    }

    public String getNomDepotMedocs() {
        return nomDepotMedocs;
    }

    public void setNomDepotMedocs(String nomDepotMedocs) {
        this.nomDepotMedocs = nomDepotMedocs;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    @Override
    public String toString() {
        return "Offrir{" +
                "idVisi='" + idVisi + '\'' +
                ", numRap=" + numRap +
                ", nomDepotMedocs='" + nomDepotMedocs + '\'' +
                ", qte=" + qte +
                '}';
    }
}
