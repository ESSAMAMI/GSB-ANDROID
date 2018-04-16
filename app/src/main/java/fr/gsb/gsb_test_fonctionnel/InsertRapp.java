package fr.gsb.gsb_test_fonctionnel;

import fr.gsb.gsb_entites.RapportVisite;

public class InsertRapp {

    private String idVisi ;
    private int rapId ;
    private int praId ;
    private String bilan ;
    private String dateRedac;
    private int rapCoef ;
    private String dateVisi ;
    private int estVu ;
    private int motif ;

    public InsertRapp(String idVisi, int rapId, int praId, String bilan, String dateRedac, int rapCoef, String dateVisi, int estVu, int motif) {
        this.idVisi = idVisi;
        this.rapId = rapId;
        this.praId = praId;
        this.bilan = bilan;
        this.dateRedac = dateRedac;
        this.rapCoef = rapCoef;
        this.dateVisi = dateVisi;
        this.estVu = estVu;
        this.motif = motif;
    }

    public String getIdVisi() {
        return idVisi;
    }

    public void setIdVisi(String idVisi) {
        this.idVisi = idVisi;
    }

    public int getRapId() {
        return rapId;
    }

    public void setRapId(int rapId) {
        this.rapId = rapId;
    }

    public int getPraId() {
        return praId;
    }

    public void setPraId(int praId) {
        this.praId = praId;
    }

    public String getBilan() {
        return bilan;
    }

    public void setBilan(String bilan) {
        this.bilan = bilan;
    }

    public String getDateRedac() {
        return dateRedac;
    }

    public void setDateRedac(String dateRedac) {
        this.dateRedac = dateRedac;
    }

    public int getRapCoef() {
        return rapCoef;
    }

    public void setRapCoef(int rapCoef) {
        this.rapCoef = rapCoef;
    }

    public String getDateVisi() {
        return dateVisi;
    }

    public void setDateVisi(String dateVisi) {
        this.dateVisi = dateVisi;
    }

    public int getEstVu() {
        return estVu;
    }

    public void setEstVu(int estVu) {
        this.estVu = estVu;
    }

    public int getMotif() {
        return motif;
    }

    public void setMotif(int motif) {
        this.motif = motif;
    }

    @Override
    public String toString() {
        return "InsertRapp{" +
                "idVisi='" + idVisi + '\'' +
                ", rapId=" + rapId +
                ", praId=" + praId +
                ", bilan='" + bilan + '\'' +
                ", dateRedac='" + dateRedac + '\'' +
                ", rapCoef=" + rapCoef +
                ", dateVisi='" + dateVisi + '\'' +
                ", estVu=" + estVu +
                ", motif=" + motif +
                '}';
    }
}
