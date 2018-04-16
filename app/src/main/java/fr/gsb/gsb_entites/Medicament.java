package fr.gsb.gsb_entites;

public class Medicament {

	private String depotLegal ;
	private String nomCommercial ;


    public Medicament(String depotLegal, String nomCommercial) {
        this.depotLegal = depotLegal;
        this.nomCommercial = nomCommercial;

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
