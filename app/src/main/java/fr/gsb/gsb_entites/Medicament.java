package fr.gsb.gsb_entites;

public class Medicament {

	private String depotLegal ;
	private String nomCommercial ;
    private int qte ;

    public Medicament(String depotLegal, String nomCommercial) {
        this.depotLegal = depotLegal;
        this.nomCommercial = nomCommercial;

    }

    public Medicament(String depotLegal, String nomCommercial, int qte) {
        this.depotLegal = depotLegal;
        this.nomCommercial = nomCommercial;
        this.qte = qte;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public String getDepotLegal() {
        return depotLegal;
    }

    public void setDepotLegal(String depotLegal) {
        this.depotLegal = depotLegal;
    }

    public String getNomCommercial() {
        return nomCommercial;
    }

    public void setNomCommercial(String nomCommercial) {
        this.nomCommercial = nomCommercial;
    }

    @Override
    public String toString() {
        return nomCommercial;
    }
}
