package fr.gsb.gsb_entites;

public class Coefeciant {

    private int idCoef ;
    private String libelle ;

    public Coefeciant(int idCoef, String libelle) {
        this.idCoef = idCoef;
        this.libelle = libelle;
    }

    public Coefeciant() {

    }

    public Coefeciant(String libelle) {

        this.libelle = libelle;
    }

    public int getIdCoef() {
        return idCoef;
    }

    public void setIdCoef(int idCoef) {
        this.idCoef = idCoef;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return this.libelle ;
    }
}
